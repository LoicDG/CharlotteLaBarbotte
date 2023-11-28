package ca.qc.bdeb.sim203.TPCharlotte.GameLogic;

import ca.qc.bdeb.sim203.TPCharlotte.Main;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons.Charlotte;
import javafx.scene.canvas.GraphicsContext;

public class Camera {
    private double x;
    private double width;
    private BarreDeVie barreDeVie;

    /**
     * Constructeur de la classe Camera
     * @param width Largeur de la vue de la Camera
     * @param barreDeVie Barre de vie de Charlotte qui doit toujours être vue par la Camera
     */
    public Camera(double width, BarreDeVie barreDeVie) {
        this.width = width;
        this.barreDeVie = barreDeVie;
    }

    public double getX() {
        return x;
    }

    public double getWidth() {
        return width;
    }

    /**
     * Méthode qui réinitialise la Camera.
     * Cette méthode ramène la caméra à sa position initiale.
     */
    public void resetX() {
        x = 0;
    }

    /**
     * Méthode qui fait en sorte que la Camera suive Charlotte
     * @param charlotte La Charlotte que la Camera doit suivre
     */
    public void suivre(Charlotte charlotte) {
        double xCible;
        if (charlotte.getVx() > 0) {
            xCible = charlotte.getX() - width * 0.2;
            if (xCible + width > Main.TAILLE_NIVEAU) {
                x = Main.TAILLE_NIVEAU - width;
            } else if (charlotte.getX() >= x + width * 0.2) {
                x = xCible;
            }
        }
        barreDeVie.setX(x + 10);
    }

    /**
     * Applique une translation négative à l'environnement graphique (contexte),
     * simulant le déplacement de la caméra dans le jeu.
     *
     * @param context Contexte graphique dans lequel appliquer la translation.
     */
    public void appliquer(GraphicsContext context) {
        context.translate(-x, 0);
    }
}
