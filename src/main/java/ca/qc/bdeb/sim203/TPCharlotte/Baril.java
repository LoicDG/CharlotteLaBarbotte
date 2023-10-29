package ca.qc.bdeb.sim203.TPCharlotte;

import ca.qc.bdeb.sim203.TPCharlotte.Poissons.Charlotte;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Baril implements CanCollide {
    private double largeur;
    private double hauteur;

    public void setImageBaril(Image imageBaril) {
        this.imageBaril = imageBaril;
    }

    public boolean isEstOuvert() {
        return estOuvert;
    }

    public void setEstOuvert(boolean estOuvert) {
        this.estOuvert = estOuvert;
    }

    private boolean estOuvert;

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
        estOuvert = false;

    }
    public void updatePhysique() {
        double t = System.currentTimeMillis()-tempsDeCreation;
        y = ((590-166)/2)*Math.sin((2*Math.PI*t)/3000)+((590-166)/2);
    }
    public void draw(GraphicsContext context) {
        context.drawImage(imageBaril, x, y, w, h);
        drawHitBox(context);
    }

    protected void drawHitBox(GraphicsContext context) {
        if (Input.isPressed(KeyCode.D)) {
            context.setStroke(Color.WHITE);
            context.strokeRect(x, y, w, h);
        }
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
