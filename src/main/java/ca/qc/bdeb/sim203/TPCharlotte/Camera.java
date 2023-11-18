package ca.qc.bdeb.sim203.TPCharlotte;

import ca.qc.bdeb.sim203.TPCharlotte.Poissons.Charlotte;
import javafx.scene.canvas.GraphicsContext;

public class Camera {

    private double x;
    private double width;


    public Camera(double width) {
        this.width = width;
    }
    public double getX() {
        return x;
    }
    public void follow(Charlotte charlotte) {
        double xCible;
        if (charlotte.getVx() > 0){
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
    }

    public void apply(GraphicsContext context) {
        context.translate(-x,0);
    }

}
