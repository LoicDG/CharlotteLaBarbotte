package ca.qc.bdeb.sim203.TPCharlotte;

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

import java.util.Random;

//arraylist des ennemis que charlotte a pas depasse et ajouter un nouveau parametre a charlotte
public class Main extends Application {
    public static double HEIGHT = 520;
    public static double WIDTH = 900;
    public static double TAILLE_NIVEAU = WIDTH * 8;
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    GraphicsContext context = canvas.getGraphicsContext2D();
    AnimationTimer timer;
    Partie partie = new Partie();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Charlotte la Barbotte");
        stage.getIcons().add(new Image("code/charlotte.png"));
        stage.setResizable(false);
        Niveau.creerImages();
        Ennemis.creerImageEnnemis();

        //region Scène 1, La page titre
        var rootTitre = new VBox();
        rootTitre.setAlignment(Pos.CENTER);
        var titre = new Scene(rootTitre, WIDTH, HEIGHT);
        var logo = new ImageView(new Image("code/logo.png"));
        logo.setPreserveRatio(true);
        logo.setFitWidth(450);
        var jouer = new Button("Jouer!");
        var infos = new Button("Infos");
        var boutons = new HBox(jouer, infos);
        boutons.setAlignment(Pos.CENTER);
        boutons.setSpacing(10);
        rootTitre.getChildren().addAll(logo, boutons);
        rootTitre.setStyle("-fx-background-color: #2A7FFF;");
        rootTitre.setSpacing(10);
        //endregion


        stage.setScene(titre);
        //Scène 2, pour le menu informations
        var ennemiImage = new Image("code/poisson" + Input.rnd.nextInt(1, 6) + ".png");
        var sceneInfos = setScreenInfos(titre, stage, ennemiImage);
        //Scène 3, pour jouer
        var sceneJouer = setScreenJouer(titre, stage, partie);
        Pane paneJouer = (Pane) sceneJouer.getRoot();
        timer = new AnimationTimer() {
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double deltaTime = (now - lastTime) * 1e-9;
                partie.update(deltaTime);
                partie.draw(context);
                paneJouer.setBackground(partie.getBackground());
                if (partie.isPartieFinie() && stage.getScene() == sceneJouer) {
                    retourMenu(titre, stage);
                    return;
                }
                lastTime = now;
            }
        };

        //region Événementiel
        infos.setOnAction(event -> {
            //change l'image à chaque fois
            var image = (ImageView) sceneInfos.getRoot().getChildrenUnmodifiable().get(1);
            image.setImage(new Image(Ennemis.getUrlImage()));
            stage.setScene(sceneInfos);
        });
        jouer.setOnAction(event -> {
            commencerPartie(stage, sceneJouer);
            timer.start();
        });
        titre.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });
        //endregion
        stage.show();
    }

    private void commencerPartie(Stage stage, Scene sceneJouer) {
        if (partie.isPartieFinie()) {
            partie = new Partie();
        }
        stage.setScene(sceneJouer);
    }

    private Scene setScreenInfos(Scene originale, Stage stage, Image poisson) {
        var root = new VBox();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var titre = new Text("Charlotte la Barbotte");
        titre.setFont(Font.font(48));
        root.setStyle("-fx-background-color: #2A7FFF");

        //image poisson
        var imageEnnemi = new ImageView(poisson);
        imageEnnemi.setPreserveRatio(true);
        imageEnnemi.setFitHeight(HEIGHT * 0.4);

        //Collaborateurs
        var collabos = new VBox();
        var par = new Text("Par ");
        par.setFont(Font.font(20));
        var loic = new Text("Loïc Desrochers-Girard");
        loic.setFont(Font.font(32));
        var parLoic = new HBox(par,loic);
        parLoic.setAlignment(Pos.CENTER);
        var et = new Text("et ");
        et.setFont(Font.font(20));
        var sebby = new Text("Sebastian Crete");
        sebby.setFont(Font.font(32));
        var etSebby = new HBox(et,sebby);
        etSebby.setAlignment(Pos.CENTER);
        collabos.getChildren().addAll(parLoic, etSebby);
        collabos.setAlignment(Pos.CENTER);

        //Description du travail
        var sources = new Text("""
                Travail remis à Nicolas Hurtubise et Georges Côté. Graphismes adaptés de https://game-icons.net/ et de
                https://openclipart.org/. Développé dans le cadre du cours 420-203-RE - Développement de programmes\s
                dans un environnement graphique, au Collège de Bois-de-Boulogne.""");

        var boutonRetour = new Button("Retour");
        root.getChildren().addAll(titre, imageEnnemi, collabos, sources, boutonRetour);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        //region Événementiel
        boutonRetour.setOnAction(event -> stage.setScene(originale));
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.setScene(originale);
            }
        });
        //endregion
        return scene;
    }

    private Scene setScreenJouer(Scene originale, Stage stage, Partie partie) {
        var root = new Pane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        root.setBackground(partie.getNiveau().getBg());
        root.getChildren().add(canvas);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                timer.stop();
                retourMenu(originale, stage);
            } else if (event.getCode() == KeyCode.D && Input.isPressed(KeyCode.D)) {
                Input.setKeyPressed(KeyCode.D, false);
            } else {
                Input.setKeyPressed(event.getCode(), true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.D) return;
            Input.setKeyPressed(event.getCode(), false);
        });
        return scene;

    }

    private void retourMenu(Scene originale, Stage stage) {
        Niveau.resetNbNiveau();
        stage.setScene(originale);
        Input.setKeyPressed(KeyCode.D, false);
        partie.setPartieFinie(true);
    }

}
