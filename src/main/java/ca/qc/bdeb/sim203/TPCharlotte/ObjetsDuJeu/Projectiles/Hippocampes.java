package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles;

import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.Input;
import javafx.scene.image.Image;


public class Hippocampes extends Projectiles {
    private double amplitudeSinus;
    private double periode = (Input.rnd.nextDouble(1, 4)) * 1000;
    private double posYinitial;
    private double tempsDeLaCreation;

    /**
     * Constructeur de la classe Hippocampes.
     *
     * @param posX Position horizontale initiale du projectile.
     * @param posY Position verticale initiale du projectile.
     */
    public Hippocampes(double posX, double posY) {
        super(posX, posY);
        posYinitial = posY;
        tempsDeLaCreation = System.currentTimeMillis();
        image = new Image("code/hippocampe.png");
        w = image.getWidth();
        h = image.getHeight();
        super.x -= w / 2;
        super.y -= h / 2;
        vx = 500;
        amplitudeSinus = Input.rnd.nextDouble(30, 61);
        int signe = Input.rnd.nextInt(2);
        if (signe == 1) {
            amplitudeSinus = -amplitudeSinus;
        }
    }

    /**
     * Met à jour la physique de l'Hippocampes en fonction du temps écoulé depuis la dernière mise à jour
     * selon un mouvement sinusoïdal.
     *
     * @param deltaTime Temps écoulé depuis la dernière mise à jour.
     */
    @Override
    public void updatePhysique(double deltaTime) {
        super.updatePhysique(deltaTime);
        double t = System.currentTimeMillis() - tempsDeLaCreation;
        y = amplitudeSinus * Math.sin((2 * Math.PI * t) / periode) + posYinitial;
    }
}
