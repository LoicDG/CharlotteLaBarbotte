package ca.qc.bdeb.sim203.TPCharlotte;

import ca.qc.bdeb.sim203.TPCharlotte.Poissons.Charlotte;
import javafx.scene.image.Image;

public class Baril implements CanCollide {
    private double largeur;
    private double hauteur;
    private Image imageBaril;
    private double  x;
    private double y;
    private double w;
    private double h;
    private long tempsDeCreation;

    public Baril(double x, double y, long tempsDeCreation){
        this.x = x;
        this.y = y;
        this.tempsDeCreation = tempsDeCreation;
        imageBaril = new Image("code/baril.png");
        w = imageBaril.getWidth();
        h = imageBaril.getHeight();

    }
    public void updatePhysique() {
        double t = System.currentTimeMillis()-tempsDeCreation;
        y = ((590-y)/2)*Math.sin((2*Math.PI*t)/3000)+((590-y)/2);

    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getW() {
        return w;
    }

    @Override
    public double getH() {
        return h;
    }
}
