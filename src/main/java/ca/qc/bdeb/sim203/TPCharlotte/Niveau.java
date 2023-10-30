package ca.qc.bdeb.sim203.TPCharlotte;

import ca.qc.bdeb.sim203.TPCharlotte.Poissons.Ennemis;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Random;

public class Niveau {
    private Background bg;
    private final double TAILLE_DU_NIVEAU = 8 * 900;
    private static int nbNiveau = 0;
    private int numNiveau;
    private static ArrayList<Image> images = new ArrayList<>();
    private ArrayList<Ennemis> poissons = new ArrayList<>();
    private long tempsCreationNiveau;
    private double tempsExec;
    private boolean isOver = false;

    public Baril getBaril() {
        return baril;
    }

    private Baril baril; //chaque niveau a 1 baril

    public Niveau() {
        var rnd = new Random();
        bg = new Background(new BackgroundFill(Color.hsb(rnd.nextDouble(190, 271), 0.84,
                1.0), null, null));
        nbNiveau++;
        numNiveau = nbNiveau;
        tempsCreationNiveau = System.currentTimeMillis();
        Random random = new Random();
        double minRange = (1 / 5) * TAILLE_DU_NIVEAU;
        double maxRange = (4 / 5) * TAILLE_DU_NIVEAU;
        double xBaril = minRange + random.nextDouble() * (maxRange - minRange);
        baril = new Baril(xBaril, 0, tempsCreationNiveau);

    }

    public int getNumNiveau() {
        return numNiveau;
    }

    public static void resetNbNiveau() {
        nbNiveau = 0;
    }

    public static void creerImages() {
        for (int i = 1; i <= 6; i++) {
            images.add(new Image("code/decor" + i + ".png"));
        }
    }

    public Background getBg() {
        return bg;
    }

    public double getTempsExec() {
        return tempsExec;
    }

    public void setTempsExec(double tempsExec) {
        this.tempsExec = tempsExec;
    }

    public ArrayList<Ennemis> getPoissons() {
        return poissons;
    }

    public double getTempsCreationNiveau() {
        return tempsCreationNiveau;
    }

    public void spawnEnnemis() {
        var random = new Random();
        int nbPoissons = random.nextInt(1, 6);
        for (int i = 0; i < nbPoissons; i++) {
            poissons.add(new Ennemis(Main.WIDTH, random.nextDouble(Main.HEIGHT * 0.2, Main.HEIGHT * 0.81)));
            poissons.get(i).setVx(-(100 * Math.pow(numNiveau, 0.33) + 200));
        }
    }

    public void isPlusLa() {
        for (int i = 0; i < poissons.size(); i++) {
            if (poissons.get(i).getX() + poissons.get(i).getImagePoisson().getWidth() < 0) {
                poissons.remove(poissons.get(i));
                i--;
            }
        }
    }

    public boolean isOver() {
        return isOver;
    }

    public void afficherNumNiveau(GraphicsContext context) {
        if (System.currentTimeMillis() - tempsCreationNiveau <= 5000) {
            context.setFont(Font.font("Comic Sans MS", 72));
            context.fillText("NIVEAU " + numNiveau, Main.WIDTH / 3, Main.HEIGHT / 2);
        }
    }
}
