package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles;

import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.CanCollide;
import ca.qc.bdeb.sim203.TPCharlotte.Main;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.ObjetDuJeu;

public abstract class Projectiles extends ObjetDuJeu implements CanCollide {
    /**
     * Constructeur de la classe Projectiles.
     *
     * @param posX Position horizontale initiale du projectile.
     * @param posY Position verticale initiale du projectile.
     */
    public Projectiles(double posX, double posY) {
        super(posX, posY);
    }

    /**
     * Met à jour la position horizontale du projecgtile en fonction de la vitesse du projectile et
     * en fonction du temps écoulé depuis la dernière mise à jour.
     *
     * @param deltaTime Temps écoulé depuis la dernière mise à jour.
     */
    @Override
    public void updatePhysique(double deltaTime) {
        x += vx * deltaTime;
    }

    /**
     * Vérifie si le projectile est sorti de l'écran.
     *
     * @param xCam Position horizontale de la caméra.
     * @return true si le projectile est sorti de l'écran, sinon false.
     */
    public boolean estSortiEcran(double xCam) {
        return x + w < 0 || x > xCam || y + h < 0 || y > Main.HEIGHT;
    }
}

