package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles;

import javafx.scene.image.Image;

public class EtoileDeMer extends Projectiles{
    public EtoileDeMer(double posX, double posY) {
        super(posX, posY);
        w = 36;
        h = 35;
        image = new Image("code/etoile.png");
        vx = 800;
    }
}
