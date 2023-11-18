package ca.qc.bdeb.sim203.TPCharlotte;

import ca.qc.bdeb.sim203.TPCharlotte.Poissons.Charlotte;
import javafx.scene.canvas.GraphicsContext;

public class Camera {
    private double x;
    private double width;
    private Partie partie;

    public Camera(double width,Partie partie) {
        this.width = width;
        this.partie = partie;
    }

    public void follow(Charlotte charlotte) {
        if (charlotte.getVx() > 0){
        double xCible = charlotte.getX() - width * 0.2;

        if (xCible < 0) {
            x = 0;
        } else if (xCible + width > partie.getCurrentLevel().getTAILLE_DU_NIVEAU()) {
            x = partie.getCurrentLevel().getTAILLE_DU_NIVEAU() - width;
        } else {
            x = xCible;
        }
    }}

    public void apply(GraphicsContext context) {
        context.translate(-x,-0);
    }


}
