package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Charlotte extends Poisson {
    //Appuyer sur la touche P permet de changer les projectiles
    //c pr le debug mode, pcq ca m'aide a tester les projectiles
    private int choixProjectile;
    private List<Projectiles> projectilesTires;
    private boolean espaceEstAppuye = false;
    private boolean pEstAppuye = false;
    private long tempsDuDernierTir;
    private static final long FREQUENCE_TIRS = 500; // 0.5 seconds in milliseconds


    public Charlotte(Image imagePoisson, double x, double y) {
        super(imagePoisson, x, y);
        projectilesTires = new ArrayList<>();
        tempsDuDernierTir = 0;
        choixProjectile = 1;

    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        boolean espaceEstAppuyeMaintenant = Input.isPressed(KeyCode.SPACE);
        boolean left = Input.isPressed(KeyCode.LEFT);
        boolean right = Input.isPressed(KeyCode.RIGHT);
        boolean up = Input.isPressed(KeyCode.UP);
        boolean down = Input.isPressed(KeyCode.DOWN);
        boolean p = Input.isPressed(KeyCode.P);

        if (left) {
            ax = -1000;
        } else if (right) {
            ax = 1000;
        } else if (up) {
            ay = -1000;
        } else if (down) {
            ay = 1000;
        }
        if (!left && !right) {
            if (vx < 0) {
                ax = 100;
            } else {
                ax = -100;
            }
        }
        if (!up && !down) {
            if (vy < 0) {
                ay = 100;
            } else {
                ay = -100;
            }
        }
        if (p && !pEstAppuye){
            if (choixProjectile == 1) {
                choixProjectile = 2;
            } else if (choixProjectile == 2) {
                choixProjectile = 1;
            }
        } pEstAppuye = p;


        long tempsMaintenant = System.currentTimeMillis();
        if (espaceEstAppuye & !espaceEstAppuyeMaintenant&& tempsMaintenant - tempsDuDernierTir >= FREQUENCE_TIRS) {
            Projectiles newProjectile = null;
            switch (choixProjectile){
                case 1 -> {
                    newProjectile = new EtoileDeMer(x,y);projectilesTires.add(newProjectile);
                }
                case 2 -> {
                    for (int i = 0; i < 3; i++){
                        newProjectile = new Hippocampes(x,y);projectilesTires.add(newProjectile);
                    }
                }

            }
            tempsDuDernierTir = tempsMaintenant;
        }
        espaceEstAppuye = espaceEstAppuyeMaintenant;
    }

    @Override
    public void draw(GraphicsContext context) {
        super.draw(context);

        for (Projectiles projectile : projectilesTires) {
            projectile.dessiner(context);
        }

    }

    @Override
    protected void updatePhysique(double deltaTime) {
        super.updatePhysique(deltaTime);
        checkVitesseMax();
        for (Projectiles projectile : projectilesTires) {
            projectile.updatePhysique(deltaTime);
        }
    }

    private void checkVitesseMax() {
        if (vx >= 300) {
            vx = 300;
            ax = 0;
        } else if (vx <= -300) {
            vx = -300;
            ax = 0;
        }
        if (vy <= -300) {
            vy = -300;
            ay = 0;
        } else if (vy >= 300) {
            vy = 300;
            ay = 0;
        }
    }

}
