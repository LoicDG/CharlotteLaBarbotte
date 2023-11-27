package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles;

import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.CanCollide;
import ca.qc.bdeb.sim203.TPCharlotte.Main;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.ObjetDuJeu;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Projectiles extends ObjetDuJeu implements CanCollide {
    public Projectiles(double posX, double posY) {
        super(posX, posY);
    }

    public Image getImageProjectile() {
        return image;
    }

    @Override
    public void updatePhysique(double deltaTime) {
        x += vx * deltaTime;
    }

    public boolean estSortiEcran(double xCam) {
        return x + w < 0 || x > xCam || y + h < 0 || y > Main.HEIGHT;
    }
}

