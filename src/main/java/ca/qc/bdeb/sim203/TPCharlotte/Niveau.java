package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Niveau {
    private Background bg;
    private static ArrayList<Image> images = new ArrayList<>();

    public Niveau() {
        var rnd = new Random();
        bg = new Background(new BackgroundFill(Color.hsb(rnd.nextDouble(190, 271), 0.84,
                1.0), null, null));
    }

    public static void creerImages() {
        for (int i = 1; i <= 6; i++) {
            images.add(new Image("code/decor" + i + ".png"));
        }
    }

    public Background getBg() {
        return bg;
    }
}
