package ca.qc.bdeb.sim203.TPCharlotte.GameLogic;

import ca.qc.bdeb.sim203.TPCharlotte.Main;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons.Charlotte;
import javafx.scene.canvas.GraphicsContext;

public class Camera {
    private double x;
    private double width;
    private BarreDeVie barreDeVie;


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

    public void resetX() {
        x = 0;
    }

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

    public void appliquer(GraphicsContext context) {
        context.translate(-x, 0);
    }
}
