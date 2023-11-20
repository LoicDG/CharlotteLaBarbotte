package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class Decor {
    private Image image;
    private double x;
    private static final double y = Main.HEIGHT - 109;
    private static final double w = 80;

    public Decor(double x) {
        var rng = new Random();
        image = new Image("code/decor" + rng.nextInt(1, 7) + ".png");
        this.x = x;
    }

    public static double getY() {
        return y;
    }

    public static double getW() {
        return w;
    }

    public Image getImage() {
        return image;
    }

    public double getX() {
        return x;
    }
}
