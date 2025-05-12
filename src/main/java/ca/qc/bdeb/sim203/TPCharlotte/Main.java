package ca.qc.bdeb.sim203.TPCharlotte;
/**
 * Jeu de Charlotte La Barbotte
 *
 * @author Loic Desrochers-Girard et Sebastian Crete
 * @since 29-11-2023
 */

import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.Input;
import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.Niveau;
import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.Partie;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons.Ennemis;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main {
    public static double HEIGHT = 520;
    public static double WIDTH = 900;
    public static double TAILLE_NIVEAU = WIDTH * 8;
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    GraphicsContext context = canvas.getGraphicsContext2D();
    AnimationTimer timer;
    Partie partie = new Partie();

    public static void main(String[] args) {
        GUI.launch(GUI.class, args);
    }


}
