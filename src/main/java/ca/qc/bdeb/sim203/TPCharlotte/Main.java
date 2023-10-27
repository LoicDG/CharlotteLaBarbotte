package ca.qc.bdeb.sim203.TPCharlotte;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Charlotte la Barbotte");
        stage.getIcons().add(new Image("code/charlotte.png"));
        stage.setResizable(false);
//Scene 1 : La page titre
        ImageView logo = new ImageView(new Image("code/logo.png"));
        logo.setPreserveRatio(true);
        logo.setFitWidth(500);
        Button jouer = new Button("Jouer");
        Button infos = new Button("Infos");
        HBox boutons = new HBox(jouer, infos);
        boutons.setAlignment(Pos.CENTER);

        VBox rootTitre = new VBox(logo, boutons);
        rootTitre.setAlignment(Pos.CENTER);

        Scene titre = new Scene(rootTitre,900,590);
        rootTitre.setStyle("-fx-background-color: #2A7FFF;");

        stage.setScene(titre);

        //Scene 3 : Infos
        Text nomDuJeu = new Text("Charlotte la Barbotte");
        nomDuJeu.setFont(Font.font(30));

        //Events!!! Jsp si on devrait mettre tous les events aussi pr que ce soit
        //plus organise mais lowkey idc, c juste pour la note so cmm idk ce qui
        //est le plus clean


        stage.show();
    }
}
