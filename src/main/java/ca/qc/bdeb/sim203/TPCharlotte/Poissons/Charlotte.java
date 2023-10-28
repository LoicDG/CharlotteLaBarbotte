package ca.qc.bdeb.sim203.TPCharlotte.Poissons;

import ca.qc.bdeb.sim203.TPCharlotte.*;
import ca.qc.bdeb.sim203.TPCharlotte.Projectiles.EtoileDeMer;
import ca.qc.bdeb.sim203.TPCharlotte.Projectiles.Hippocampes;
import ca.qc.bdeb.sim203.TPCharlotte.Projectiles.Projectiles;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Charlotte extends Poisson {
    private int choixProjectile;
    private List<Projectiles> projectilesTires;
    private boolean espaceEstAppuye = false;
    private long tempsDuDernierTir;
    private static final long FREQUENCE_TIRS = 500; // 0.5 seconds in milliseconds


    public Charlotte(Image imagePoisson, double x, double y) {
        super(imagePoisson, x, y);
        projectilesTires = new ArrayList<>();
        tempsDuDernierTir = 0;
        choixProjectile = 1;
    }

    public void setX(double newX) {
        x = newX;
    }

    public void setY(double newY) {
        y = newY;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        if (Input.isPressed(KeyCode.D)) {
            if (Input.isPressed(KeyCode.Q)) {
                choixProjectile = 1;
            } else if (Input.isPressed(KeyCode.W)) {
                choixProjectile = 2;
            }
        }

        long tempsMaintenant = System.currentTimeMillis();
        if (Input.isPressed(KeyCode.SPACE) && tempsMaintenant - tempsDuDernierTir >= FREQUENCE_TIRS) {
            switch (choixProjectile) {
                case 1 -> {
                    projectilesTires.add(new EtoileDeMer(x, y));
                }
                case 2 -> {
                    for (int i = 0; i < 3; i++) {
                        projectilesTires.add(new Hippocampes(x, y));
                    }
                }
            }
            tempsDuDernierTir = tempsMaintenant;
        }
    }

    @Override
    public void draw(GraphicsContext context) {
        super.draw(context);
        for (var projectile : projectilesTires) {
            projectile.draw(context);
            if (Input.isPressed(KeyCode.D)) {
                context.setStroke(Color.WHITE);
                context.strokeRect(projectile.getPosX(), projectile.getPosY(),
                        projectile.getImageProjectile().getWidth(), projectile.getImageProjectile().getHeight());
            }
        }

    }

    private void checkLimites(double deltaTime) {
        boolean left = Input.isPressed(KeyCode.LEFT);
        boolean right = Input.isPressed(KeyCode.RIGHT);
        boolean up = Input.isPressed(KeyCode.UP);
        boolean down = Input.isPressed(KeyCode.DOWN);
        // Adjust velocity based on input
        ax = adjustAcceleration(vx, right, left);
        vx = adjustSpeed(vx);

        ay = adjustAcceleration(vy, down, up);
        vy = adjustSpeed(vy);

        // Update Charlotte's position
        x += vx * deltaTime;
        y += vy * deltaTime;

        // Ensure Charlotte stays within the screen boundaries
        x = adjustPosition(x, Main.WIDTH, imagePoisson.getWidth());
        y = adjustPosition(y, Main.HEIGHT, imagePoisson.getHeight());
    }

    private double adjustPosition(double pos, double tailleMax, double taillePoisson) {
        if (pos < 0) {
            pos = 0;
        } else if (pos > tailleMax - taillePoisson) {
            pos = tailleMax - taillePoisson;
        }
        return pos;
    }

    private double adjustSpeed(double v) {
        if (v > 300) v = 300;
        if (v < -300) v = -300;
        return v;
    }

    private double adjustAcceleration(double v, boolean plus, boolean moins) {
        double deceleration = -500;
        double a;
        if (moins) {
            a = -1000;
        } else if (plus) {
            a = 1000;
        } else {
            a = deceleration * v / Math.abs(v);
            if (v == 0) a = 0;
        }
        return a;
    }

    @Override
    protected void updatePhysique(double deltaTime) {
        super.updatePhysique(deltaTime);
        checkLimites(deltaTime);
        for (int i = projectilesTires.size() - 1; i >= 0; i--) {
            var projectile = projectilesTires.get(i);
            if (projectile.estSortiEcran()) {
                projectilesTires.remove(i);
            } else {
                projectile.updatePhysique(deltaTime);
            }
        }
    }
}
