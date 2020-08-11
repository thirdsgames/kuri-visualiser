package com.thirds.kuri;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.thirds.kuri.position.OrbitalPosition;
import com.thirds.kuri.position.StaticPosition;
import com.thirds.kuri.unit.*;

public class KuriScreen implements Screen {
    private final OrthographicCamera worldCam;
    private final ScreenViewport worldViewport;

    private final OrthographicCamera screenCam;
    private final ScreenViewport screenViewport;

    private final Stage stage;
    private final Label.LabelStyle labelStyle;

    private final BitmapFont fontLarge;

    private final Label timeLabel;

    private final ShapeRenderer sr = new ShapeRenderer();
    private final SpriteBatch sb = new SpriteBatch();

    private AbsoluteTime time = AbsoluteTime.sinceFirstColony(Time.years(0));

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

        time = time.add(Time.years(delta));
        timeLabel.setText(time.toString());

        Body sun = new Body(
                Color.YELLOW,
                Mass.solarMasses(1),
                Length.solarRadii(1),
                new StaticPosition(Position.cartesian(
                        Length.solarRadii(0),
                        Length.solarRadii(0)
                ))
        );
        Body sechia = new Body(
                Color.ROYAL,
                Mass.earthMasses(1),
                Length.earthRadii(1),
                new OrbitalPosition(
                        sun,
                        Length.astronomicalUnits(1),
                        Angle.radians(1f)
                )
        );

        sr.setProjectionMatrix(worldViewport.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        sun.render(sr, 1f/ worldCam.zoom, time);
        sechia.render(sr, 1f/ worldCam.zoom, time);

        sr.end();

        stage.act();
        stage.draw();

        /*sb.setProjectionMatrix(screenCam.combined);
        sb.begin();
        font.draw(sb, "Sechia", 0, 0);
        sb.end();*/
    }

    @Override
    public void resize(int width, int height) {
        worldViewport.update(width, height);
        screenViewport.update(width, height);
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
