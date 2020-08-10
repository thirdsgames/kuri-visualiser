package com.thirds.kuri;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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

        sr.setProjectionMatrix(viewport.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.YELLOW);
        sr.circle(0, 0, 50, 50);
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
