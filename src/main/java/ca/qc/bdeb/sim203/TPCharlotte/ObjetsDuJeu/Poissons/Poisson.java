package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons;

import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.CanCollide;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.ObjetDuJeu;
import javafx.scene.image.Image;

public class Poisson extends ObjetDuJeu implements CanCollide {

    public Poisson(Image imagePoisson, double x, double y) {
        super(x, y);
        image = imagePoisson;
        w = imagePoisson.getWidth();
        h = imagePoisson.getHeight();
    }

    public Poisson(double x, double y) {
        super(x, y);
    }

    public boolean isEnCollision(CanCollide objet) {
        double dx = x - objet.getX();
        double dy = y - objet.getY();
        double dCarre = dx * dx + dy * dy;
        return dCarre < (w / 2 + objet.getW() / 2) *
                (w / 2 + objet.getH() / 2);
    }
}
