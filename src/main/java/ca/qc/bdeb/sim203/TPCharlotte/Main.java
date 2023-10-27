package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {
    public static double HEIGHT = 590;
    public static double WIDTH = 900;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Charlotte la Barbotte");
        stage.getIcons().add(new Image("code/charlotte.png"));
        stage.setResizable(false);
        Niveau.creerImages();

        //region Scène 1, La page titre
        var rootTitre = new VBox();
        rootTitre.setAlignment(Pos.CENTER);
        var titre = new Scene(rootTitre, WIDTH, HEIGHT);
        var logo = new ImageView(new Image("code/logo.png"));
        logo.setPreserveRatio(true);
        logo.setFitWidth(500);
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
        //Scène pour le menu informations
        var rnd = new Random();
        var ennemiImage = new Image("code/poisson" + rnd.nextInt(1, 6) + ".png");
        var sceneInfos = setScreenInfos(titre, stage, ennemiImage);

        //Scène pour jouer
        Niveau niveau = new Niveau();
        var sceneJouer = setScreenJouer(titre, stage, niveau);

        //region Événementiel
        infos.setOnAction(event -> {
            //change l'image à chaque fois
            var poisson = new Image("code/poisson" + rnd.nextInt(1, 6) + ".png");
            var image = (ImageView) sceneInfos.getRoot().getChildrenUnmodifiable().get(1);
            image.setImage(poisson);
            stage.setScene(sceneInfos);
        });
        jouer.setOnAction(event -> {
            stage.setScene(sceneJouer);
        });

        //endregion


        stage.show();
    }

    private Scene setScreenInfos(Scene originale, Stage stage, Image poisson) {
        var root = new VBox();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var titre = new Text("Charlotte la Barbotte");
        titre.setFont(Font.font(48));
        root.setStyle("-fx-background-color: #2A7FFF;");

        //image poisson
        var imageEnnemi = new ImageView(poisson);
        imageEnnemi.setPreserveRatio(true);
        imageEnnemi.setFitHeight(HEIGHT * 0.4);

        //Collaborateurs
        var collabos = new VBox();
        var loic = new Text("Par Loïc Desrochers-Girard"); //TODO: arranger le style de "Par" et "et" si on veut
        loic.setFont(Font.font(32));
        var sebby = new Text("et Sebastian Crête");
        sebby.setFont(Font.font(32));
        collabos.getChildren().addAll(loic, sebby);
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

    private Scene setScreenJouer(Scene originale, Stage stage, Niveau niveau) {
        var root = new Pane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        var charlotte = new Charlotte(new Image("code/charlotte.png"), WIDTH / 2, HEIGHT / 2);
        var context = canvas.getGraphicsContext2D();
        context.drawImage(charlotte.getImagePoisson(), WIDTH / 2, HEIGHT / 2);
        root.setBackground(niveau.getBg());
        root.getChildren().add(canvas);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.setScene(originale);
            }
        });
        return scene;

    }
}
