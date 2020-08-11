package com.thirds.kuri;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.thirds.kuri.position.OrbitalPosition;
import com.thirds.kuri.position.StaticPosition;
import com.thirds.kuri.unit.*;

import java.util.ArrayList;
import java.util.Random;

public class BodySystem {
    private final ArrayList<Body> bodies = new ArrayList<>();
    private final ArrayList<BodySystem> systems = new ArrayList<>();

    private BodySystem() {}

    public static BodySystem kuri() {
        BodySystem kuri = new BodySystem();
        Body sun = new Body(
                Color.YELLOW,
                Mass.solarMasses(1),
                Length.solarRadii(1),
                new StaticPosition(Position.cartesian(
                        Length.solarRadii(0),
                        Length.solarRadii(0)
                ))
        );
        kuri.bodies.add(sun);

        BodySystem gehdonBelt = gehdonBelt(sun);
        kuri.systems.add(gehdonBelt);

        BodySystem voktaBelt = voktaBelt(sun);
        kuri.systems.add(voktaBelt);

        BodySystem sechianSystem = sechianSystem(sun);
        kuri.systems.add(sechianSystem);

        BodySystem ucenovianSystem = ucenovianSystem(sun);
        kuri.systems.add(ucenovianSystem);

        return kuri;
    }

    private static BodySystem gehdonBelt(Body sun) {
        BodySystem system = new BodySystem();

        Random rng = new Random(123123123123L);
        for (int i = 0; i < 83; i++) {
            system.bodies.add(new Body(
                    Color.LIGHT_GRAY,
                    Mass.earthMasses(rng.nextFloat() * 0.2f + 0.2f),
                    Length.earthRadii(rng.nextFloat() * 0.2f + 0.2f),
                    new OrbitalPosition(
                            sun,
                            Length.astronomicalUnits((float) rng.nextGaussian() * 0.06f + 0.4f),
                            Angle.radians(rng.nextFloat() * (float)Math.PI * 2f)
                    )
            ));
        }

        return system;
    }

    private static BodySystem voktaBelt(Body sun) {
        BodySystem system = new BodySystem();

        Random rng = new Random(456456456456L);
        for (int i = 0; i < 347; i++) {
            system.bodies.add(new Body(
                    Color.LIGHT_GRAY,
                    Mass.earthMasses(rng.nextFloat() * 0.2f + 0.2f),
                    Length.earthRadii(rng.nextFloat() * 0.2f + 0.2f),
                    new OrbitalPosition(
                            sun,
                            Length.astronomicalUnits((float) rng.nextGaussian() * 0.4f + 3.5f),
                            Angle.radians(rng.nextFloat() * (float)Math.PI * 2f)
                    )
            ));
        }

        return system;
    }

    private static BodySystem sechianSystem(Body sun) {
        BodySystem system = new BodySystem();

        Body sechia = new Body(
                Color.ROYAL,
                Mass.earthMasses(1),
                Length.earthRadii(1),
                new OrbitalPosition(
                        sun,
                        Length.astronomicalUnits(1),
                        Angle.radians(0f)
                )
        );
        system.bodies.add(sechia);

        Body trewa = new Body(
                Color.LIGHT_GRAY,
                Mass.earthMasses(0.012f),
                Length.earthRadii(0.23f),
                new OrbitalPosition(
                        sechia,
                        Length.astronomicalUnits(0.00176f),
                        Angle.radians(2.3f)
                )
        );
        system.bodies.add(trewa);

        Body istia = new Body(
                Color.LIGHT_GRAY,
                Mass.earthMasses(0.009f),
                Length.earthRadii(0.2f),
                new OrbitalPosition(
                        sechia,
                        Length.astronomicalUnits(0.00356f),
                        Angle.radians(5.1f)
                )
        );
        system.bodies.add(istia);

        Body doreton = new Body(
                Color.LIGHT_GRAY,
                Mass.earthMasses(0.05f),
                Length.earthRadii(0.48f),
                new OrbitalPosition(
                        sechia,
                        Length.astronomicalUnits(0.006716f),
                        Angle.radians(0.2f)
                )
        );
        system.bodies.add(doreton);

        return system;
    }

    private static BodySystem ucenovianSystem(Body sun) {
        BodySystem system = new BodySystem();

        Body ucenov = new Body(
                Color.OLIVE,
                Mass.earthMasses(0.8f),
                Length.earthRadii(0.7f),
                new OrbitalPosition(
                        sun,
                        Length.astronomicalUnits(1.7f),
                        Angle.radians(0f)
                )
        );
        system.bodies.add(ucenov);

        Body karine = new Body(
                Color.LIGHT_GRAY,
                Mass.earthMasses(0.055f),
                Length.earthRadii(0.68f),
                new OrbitalPosition(
                        ucenov,
                        Length.astronomicalUnits(0.0031f),
                        Angle.radians(4.8f)
                )
        );
        system.bodies.add(karine);

        return system;
    }

    public void render(ShapeRenderer sr, float camZoom, AbsoluteTime time) {
        for (Body body : bodies) {
            body.render(sr, camZoom, time);
        }
        for (BodySystem system : systems) {
            system.render(sr, camZoom, time);
        }
    }
}
