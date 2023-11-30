package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu;

import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.CanCollide;
import javafx.scene.image.Image;

public class Baril extends ObjetDuJeu implements CanCollide {
    private boolean ouvert;
    private long tempsDeCreation;
    /**
     * Constructeur de la classe Baril.
     * @param x Position horizontale initiale du baril.
     * @param y Position verticale initiale du baril.
     * @param tempsDeCreation Temps de création du baril en millisecondes.
     */
    public Baril(double x, double y, long tempsDeCreation) {
        super(x, y);
        this.tempsDeCreation = tempsDeCreation;
        image = new Image("code/baril.png");
        w = image.getWidth();
        h = image.getHeight();
        ouvert = false;
    }

    public void setImageBaril(Image imageBaril) {
        this.image = imageBaril;
    }

    /**
     * Vérifie si le baril est ouvert.
     * @return true si le baril est ouvert, sinon false.
     */
    public boolean isOuvert() {
        return ouvert;
    }

    public void setOuvert(boolean ouvert) {
        this.ouvert = ouvert;
    }

    /**
     * Met à jour la position verticale du baril en fonction du temps écoulé depuis sa création.
     */
    public void updatePhysique() {
        double t = System.currentTimeMillis() - tempsDeCreation;
        y = ((590 - 166) / 2) * Math.sin((2 * Math.PI * t) / 3000) + ((590 - 166) / 2);
    }
}
