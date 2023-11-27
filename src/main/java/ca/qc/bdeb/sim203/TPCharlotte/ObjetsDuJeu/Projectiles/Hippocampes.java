package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles;

import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.Input;
import javafx.scene.image.Image;


public class Hippocampes extends Projectiles{
    private double amplitudeSinus;
    private double periode = (Input.rnd.nextDouble(1, 4)) * 1000;
    private double posYinitial;
    private double tempsDeLaCreation;
    public Hippocampes(double posX, double posY) {
        super(posX, posY);
        posYinitial = posY;
        tempsDeLaCreation = System.currentTimeMillis();
        w = 20;
        h = 36;
        vx = 500;
        image = new Image("code/hippocampe.png");
        amplitudeSinus = Input.rnd.nextDouble(30, 61);
        int signe = Input.rnd.nextInt(2);
        if (signe == 1){
            amplitudeSinus = -amplitudeSinus;
        }
    }
    @Override
    public void updatePhysique(double deltaTime) {
        super.updatePhysique(deltaTime);
        double t = System.currentTimeMillis()-tempsDeLaCreation;
        y = amplitudeSinus * Math.sin((2 * Math.PI * t) / periode) + posYinitial;
    }
}
