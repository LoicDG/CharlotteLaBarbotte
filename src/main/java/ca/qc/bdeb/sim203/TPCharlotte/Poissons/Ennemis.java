package ca.qc.bdeb.sim203.TPCharlotte.Poissons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Random;


public class Ennemis extends Poisson {
    private ImageView hitboxEnnemi = new ImageView();
    private static ArrayList<String> poissonsEnnemis = new ArrayList<>();


    public Ennemis(double x, double y) {
        super(x, y);
        var random = new Random();
        imagePoisson = new Image(getUrlImage());
        hitboxEnnemi.setImage(imagePoisson);
        hitboxEnnemi.setPreserveRatio(true);
        hitboxEnnemi.setFitHeight(random.nextDouble(50, 121));
        ax = -500;
        ay = random.nextDouble(-100, 101);
    }

    public ImageView getImageEnnemi() {
        return hitboxEnnemi;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public static void creerImageEnnemis() {
        poissonsEnnemis.add("code/poisson1.png");
        poissonsEnnemis.add("code/poisson2.png");
        poissonsEnnemis.add("code/poisson3.png");
        poissonsEnnemis.add("code/poisson4.png");
        poissonsEnnemis.add("code/poisson5.png");
    }

    public static String getUrlImage() {
        Random r = new Random();
        int randomIndex = r.nextInt(poissonsEnnemis.size());
        return poissonsEnnemis.get(randomIndex);
    }
}
