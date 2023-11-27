package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles;

import javafx.scene.image.Image;

public class EtoileDeMer extends Projectiles {
    public EtoileDeMer(double posX, double posY) {
        super(posX, posY);
        image = new Image("code/etoile.png");
        w = image.getWidth();
        h = image.getHeight();
        super.x -= w / 2;
        super.y -= h / 2;
        vx = 800;
    }
}
