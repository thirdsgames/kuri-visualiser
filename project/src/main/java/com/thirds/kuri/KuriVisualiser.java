package com.thirds.kuri;

import com.badlogic.gdx.Game;

public class KuriVisualiser extends Game {
    public KuriVisualiser() {
    }

    @Override
    public void create() {
        setScreen(new KuriScreen());
    }
}
