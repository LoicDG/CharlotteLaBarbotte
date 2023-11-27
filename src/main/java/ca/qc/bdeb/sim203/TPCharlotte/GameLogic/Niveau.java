package ca.qc.bdeb.sim203.TPCharlotte.GameLogic;

import ca.qc.bdeb.sim203.TPCharlotte.Main;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Baril;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons.Charlotte;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons.Ennemis;
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
    private static int nbNiveau = 0;
    private int numNiveau;
    private static ArrayList<Image> images = new ArrayList<>();
    private ArrayList<Ennemis> poissons = new ArrayList<>();
    private long tempsCreationNiveau;
    private long tempsExec;
    private double sinceDespawn = 0;
    private boolean isOver = false;
    private Baril baril; //chaque niveau a 1 baril
    private ArrayList<Decor> decors = new ArrayList<>();
    public Niveau() {
        bg = new Background(new BackgroundFill(Color.hsb(Input.rnd.nextDouble(190, 271), 0.84,
                1.0), null, null));
        nbNiveau++;
        numNiveau = nbNiveau;
        tempsCreationNiveau = System.currentTimeMillis();
        double minRange = Main.TAILLE_NIVEAU/ 5;
        double maxRange = (4*Main.TAILLE_NIVEAU)/5;
        double xBaril = minRange + Input.rnd.nextDouble() * (maxRange - minRange);
        baril = new Baril(xBaril, 0, tempsCreationNiveau);
        double espacement = Input.rnd.nextInt(50, 101);
        double xDecors = espacement;
        while (xDecors < Main.TAILLE_NIVEAU) {
            decors.add(new Decor(xDecors));
            espacement = Input.rnd.nextInt(50, 101) + Decor.getW();
            xDecors += espacement;
        }
    }
    public Baril getBaril() {
        return baril;
    }

    public ArrayList<Decor> getDecors() {
        return decors;
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

    public void setTempsCreationNiveau(long tempsCreationNiveau) {
        this.tempsCreationNiveau = tempsCreationNiveau;
    }

    public ArrayList<Ennemis> getPoissons() {
        return poissons;
    }

    public void spawnEnnemis(Camera cam) {
        int nbPoissons = Input.rnd.nextInt(1, 6);
        if (sinceDespawn >= (0.75 + 1 / Math.sqrt(numNiveau))) {
            for (int i = 0; i < nbPoissons; i++) {
                poissons.add(new Ennemis(cam.getX() + cam.getWidth(),
                        Input.rnd.nextDouble(Main.HEIGHT * 0.2, Main.HEIGHT * 0.81), numNiveau));
            }
            tempsExec = System.currentTimeMillis();
            sinceDespawn = 0;
        } else {
            sinceDespawn = (double) (System.currentTimeMillis() - tempsExec) / 1000;
        }
    }

    public void isPlusLa(double x) {
        for (int i = 0; i < poissons.size(); i++) {
            if (poissons.get(i).getX() + poissons.get(i).getImage().getWidth() < x) {
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

    public void checkFini(Charlotte charlotte) {
        if (charlotte.getX() + charlotte.getW() >= Main.TAILLE_NIVEAU) {
            isOver = true;
        }
    }
}
