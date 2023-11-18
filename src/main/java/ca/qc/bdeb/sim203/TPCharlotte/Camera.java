package ca.qc.bdeb.sim203.TPCharlotte;

import ca.qc.bdeb.sim203.TPCharlotte.Poissons.Charlotte;
import javafx.scene.canvas.GraphicsContext;

public class Camera {

    private double x;
    private double width;
    private HealthBar healthBar;


    public Camera(double width, HealthBar healthBar) {
        this.width = width;
        this.healthBar = healthBar;
    }

    public double getX() {
        return x;
    }

    public void follow(Charlotte charlotte) {
        double xCible;
        if (charlotte.getVx() > 0) {
            xCible = charlotte.getX() - width * 0.2;
            if (xCible < 0) {
                x = 0;
            } else if (xCible + width > Main.TAILLE_NIVEAU) {
                x = Main.TAILLE_NIVEAU - width;
            } else {
                x = xCible;
            }
        } else if (charlotte.getVx() < 0) {
            xCible = charlotte.getX() + width * 0.2;
            if (xCible < 0) {
                x = 0;
            } else if (xCible + width > Main.TAILLE_NIVEAU) {
                x = Main.TAILLE_NIVEAU - width;
            } else {
                x = xCible;
            }
        }
        healthBar.setX(x + 10);
    }

    public void apply(GraphicsContext context) {
        context.translate(-x, 0);
    }
}
