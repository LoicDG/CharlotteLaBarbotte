package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles;

import javafx.scene.image.Image;

public class EtoileDeMer extends Projectiles {
    /**
     * Constructeur de la classe EtoileDeMer
     *
     * @param posX Position horizontale initiale du projectile.
     * @param posY Position verticale initiale du projectile.
     */
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
