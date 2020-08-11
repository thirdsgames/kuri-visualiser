package com.thirds.kuri;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.thirds.kuri.position.OrbitalPosition;
import com.thirds.kuri.position.StaticPosition;
import com.thirds.kuri.unit.*;

public class KuriScreen implements Screen {
    private enum TimeScale {
        REV_YEAR(Time.years(-1)),
        REV_DAY(Time.days(-1)),
        REV_REAL_TIME(Time.seconds(-1)),
        PAUSE(Time.seconds(0)),
        REAL_TIME(Time.seconds(1)),
        DAY(Time.days(1)),
        YEAR(Time.years(1))
        ;

        TimeScale(Time duration) {
            this.duration = duration;
        }
        private Time duration;
    }

    private final OrthographicCamera worldCam;
    private Vector2 targetWorldPos = new Vector2();
    private float targetWorldZoom = 0.01f;
    private final ScreenViewport worldViewport;

    private final OrthographicCamera screenCam;
    private final ScreenViewport screenViewport;

    private final Stage stage;
    private final Label.LabelStyle labelStyle;

    private final BitmapFont fontLarge;

    private final Label timeLabel, posLabel;

    private final ShapeRenderer sr = new ShapeRenderer();
    private final SpriteBatch sb = new SpriteBatch();

    private AbsoluteTime time = AbsoluteTime.sinceFirstColony(Time.years(102).add(Time.days(24)).add(Time.seconds(2674f)));

    private TimeScale timeScale = TimeScale.DAY;

    private BodySystem kuri = BodySystem.kuri();

    public KuriScreen() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("CascadiaMono.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 16;
        fontLarge = gen.generateFont(param);

        worldCam = new OrthographicCamera();
        worldViewport = new ScreenViewport(worldCam);
        worldCam.position.set(0, 0, 0);
        worldCam.zoom = 0.01f;

        screenCam = new OrthographicCamera();
        screenViewport = new ScreenViewport(screenCam);

        stage = new Stage(screenViewport, sb);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = fontLarge;
        labelStyle.fontColor = Color.WHITE;

        Table timeTable = new Table();
        timeLabel = new Label("T", labelStyle);
        timeTable.add(timeLabel).left().pad(16f);
        timeTable.add(new Container<>()).growX();
        posLabel = new Label("P", labelStyle);
        timeTable.add(posLabel).right().pad(16f);

        rootTable.add(new Container<>()).growY().row();
        rootTable.add(timeTable).bottom().growX();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.01f, 0.01f, 0.03f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            increaseTimeScale();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            decreaseTimeScale();
        }

        time = time.add(timeScale.duration.scl(delta));
        String timeScaleString;
        switch (timeScale) {
            case REV_YEAR:
                timeScaleString = "<<<";
                break;
            case REV_DAY:
                timeScaleString = "<<";
                break;
            case REV_REAL_TIME:
                timeScaleString = "<";
                break;
            case PAUSE:
                timeScaleString = "||";
                break;
            case REAL_TIME:
                timeScaleString = ">";
                break;
            case DAY:
                timeScaleString = ">>";
                break;
            case YEAR:
                timeScaleString = ">>>";
                break;
            default:
                timeScaleString = "?";
        }
        timeLabel.setText("T: " + time + "  " + timeScaleString);

        float movementSpeed = 300f * worldCam.zoom;
        float scaleSpeed = 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            targetWorldPos.y += delta * movementSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            targetWorldPos.y -= delta * movementSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            targetWorldPos.x += delta * movementSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            targetWorldPos.x -= delta * movementSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            targetWorldZoom /= 1 + scaleSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            targetWorldZoom *= 1 + scaleSpeed * delta;
        }

        targetWorldZoom = MathUtils.clamp(targetWorldZoom, 0.0001f, 0.01f);

        worldCam.position.lerp(new Vector3(targetWorldPos.x, targetWorldPos.y, 0), 5 * delta);
        worldCam.zoom = MathUtils.lerp(worldCam.zoom, targetWorldZoom, 5 * delta);
        worldCam.update();
        posLabel.setText(String.format("P: (%01.3f, %01.3f)  Z: %02.3fx", worldCam.position.x, worldCam.position.y, 0.01f / worldCam.zoom));

        sr.setProjectionMatrix(worldViewport.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        kuri.render(sr, 1f / worldCam.zoom, time);

        sr.end();

        stage.act();
        stage.draw();

        /*sb.setProjectionMatrix(screenCam.combined);
        sb.begin();
        font.draw(sb, "Sechia", 0, 0);
        sb.end();*/
    }

    private void increaseTimeScale() {
        switch (timeScale) {
            case REV_YEAR:
                timeScale = TimeScale.REV_DAY;
                break;
            case REV_DAY:
                timeScale = TimeScale.REV_REAL_TIME;
                break;
            case REV_REAL_TIME:
                timeScale = TimeScale.PAUSE;
                break;
            case PAUSE:
                timeScale = TimeScale.REAL_TIME;
                break;
            case REAL_TIME:
                timeScale = TimeScale.DAY;
                break;
            case DAY:
                timeScale = TimeScale.YEAR;
                break;
            case YEAR:
                break;
        }
    }

    private void decreaseTimeScale() {
        switch (timeScale) {
            case REV_YEAR:
                break;
            case REV_DAY:
                timeScale = TimeScale.REV_YEAR;
                break;
            case REV_REAL_TIME:
                timeScale = TimeScale.REV_DAY;
                break;
            case PAUSE:
                timeScale = TimeScale.REV_REAL_TIME;
                break;
            case REAL_TIME:
                timeScale = TimeScale.PAUSE;
                break;
            case DAY:
                timeScale = TimeScale.REAL_TIME;
                break;
            case YEAR:
                timeScale = TimeScale.DAY;
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
        worldViewport.update(width, height, false);
        screenViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
