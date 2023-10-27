package ca.qc.bdeb.sim203.TPCharlotte;

import java.awt.*;
import java.lang.reflect.Array;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;

public class Ennemis {


    private Image imageEnnemi;
    private ArrayList<String> poissonsEnnemis = new ArrayList<>();


    public Ennemis() {
        poissonsEnnemis.add("code/poisson1.png");
        poissonsEnnemis.add("code/poisson2.png");
        poissonsEnnemis.add("code/poisson3.png");
        poissonsEnnemis.add("code/poisson4.png");
        poissonsEnnemis.add("code/poisson5.png");
        Random r = new Random();
        int randomIndex = r.nextInt(poissonsEnnemis.size());
        String urlImage = poissonsEnnemis.get(randomIndex);
        imageEnnemi = new Image(urlImage);
    }
    public Image getImageEnnemi() {
        return imageEnnemi;
    }

}
