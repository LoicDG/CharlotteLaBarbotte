package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles;

import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.CanCollide;
import ca.qc.bdeb.sim203.TPCharlotte.Main;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.ObjetDuJeu;

public abstract class Projectiles extends ObjetDuJeu implements CanCollide {
    public Projectiles(double posX, double posY) {
        super(posX, posY);
    }

    @Override
    public void updatePhysique(double deltaTime) {
        x += vx * deltaTime;
    }

    public boolean estSortiEcran(double xCam) {
        return x + w < 0 || x > xCam || y + h < 0 || y > Main.HEIGHT;
    }
}

