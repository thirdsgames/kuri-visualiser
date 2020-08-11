package com.thirds.kuri;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.thirds.kuri.position.OrbitalPosition;
import com.thirds.kuri.position.StaticPosition;
import com.thirds.kuri.unit.Length;
import com.thirds.kuri.unit.Mass;
import com.thirds.kuri.unit.Position;
import com.thirds.kuri.unit.Time;

import java.math.BigDecimal;

public class KuriScreen implements Screen {
    private final ScreenViewport viewport;
    private final ShapeRenderer sr = new ShapeRenderer();

    public KuriScreen() {
        viewport = new ScreenViewport();
        viewport.getCamera().position.set(0, 0, 0);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.01f, 0.01f, 0.03f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Body sun = new Body(
                Mass.solarMasses(1),
                Length.solarRadii(1),
                new StaticPosition(Position.cartesian(
                        Length.solarRadii(0),
                        Length.solarRadii(0)
                ))
        );
        Body sechia = new Body(
                Mass.earthMasses(1),
                Length.earthRadii(1),
                new OrbitalPosition(
                        sun,
                        Length.astronomicalUnits(1)
                )
        );

        Time time = Time.seconds(0);

        sr.setProjectionMatrix(viewport.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        sun.render(sr, ((OrthographicCamera) viewport.getCamera()).zoom, time);
        sechia.render(sr, ((OrthographicCamera) viewport.getCamera()).zoom, time);

        sr.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
