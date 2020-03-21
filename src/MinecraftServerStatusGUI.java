import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MinecraftServerStatusGUI extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Minecraft-Server Status");

        Label sourceLbl = new Label("Hostname or ip: ");
        sourceLbl.setId("headerLbl");
        TextField host = new TextField();
        Button start = new Button("Go!");
        HBox inputBox = new HBox(sourceLbl, host, start);

        Label state = new Label("Server state: ");
        Label stateLbl = new Label("state");
        HBox stateBox = new HBox(state, stateLbl);

        Label ip = new Label("Server-ip: ");
        Label ipLbl = new Label("ip");
        HBox ipBox = new HBox(ip, ipLbl);

        Label port = new Label("Server-port: ");
        Label portLbl = new Label("port");
        HBox portBox = new HBox(port, portLbl);

        Label ping = new Label("Ping enabled: ");
        Label pingLbl = new Label("ping");
        HBox pingBox = new HBox(ping, pingLbl);

        Label query = new Label("Query enabled: ");
        Label queryLbl = new Label("query");
        HBox queryBox = new HBox(query, queryLbl);

        Label srv = new Label("Using srv record: ");
        Label srvLbl = new Label("srv");
        HBox srvBox = new HBox(srv, srvLbl);

        Label queryMismatch = new Label("Query mismatch: ");
        Label queryMismatchLbl = new Label("query mismatch");
        HBox queryMismatchBox = new HBox(queryMismatch, queryMismatchLbl);

        Label motdAnimated = new Label("MOTD animated: ");
        Label motdAnimatedLbl = new Label("motd animated");
        HBox motdAnimatedBox = new HBox(motdAnimated, motdAnimatedLbl);

        Label motd = new Label("MOTD: ");
        Label motdLbl = new Label("motd");
        HBox motdBox = new HBox(motd, motdLbl);

        Label nrPlayers = new Label("Nr of online players: ");
        Label nrPlayersLbl = new Label("# players");
        HBox nrPlayerBox = new HBox(nrPlayers, nrPlayersLbl);

        Label players = new Label("Online players: ");
        Label playersLbl = new Label("players");
        HBox playersBox = new HBox(players, playersLbl);

        Label version = new Label("Server version: ");
        Label versionLbl = new Label("version");
        HBox versionBox = new HBox(version, versionLbl);

        Hyperlink sourceCode = new Hyperlink("Program and sourcecode here");
        sourceCode.setOnAction(eventLink -> getHostServices().showDocument("https://github.com/fabio-anzola/MinecraftServerStatus"));
        HBox srcCodeBox = new HBox(sourceCode);
        srcCodeBox.setAlignment(Pos.CENTER);

        VBox vertical = new VBox(inputBox, stateBox, ipBox, portBox, pingBox, queryBox, srvBox, queryMismatchBox, motdAnimatedBox, motdBox, nrPlayerBox, playersBox, versionBox, srcCodeBox);
        vertical.setSpacing(20);

        Scene scene = new Scene(vertical, 500, 550);
        stage.setScene(scene);
        scene.getStylesheets().add("style.css");
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto&display=swap");

        EventHandler<ActionEvent> init = event -> {
            MinecraftServerStatus connection = new MinecraftServerStatus(host.getText(), "resources");
            try {
                connection.downloadJson();
                connection.parseData();
                connection.saveToText();
                connection.saveIcon();
                stateLbl.setText(connection.data[0]);
                ipLbl.setText(connection.data[1]);
                portLbl.setText(connection.data[2]);
                pingLbl.setText(connection.data[3]);
                queryLbl.setText(connection.data[4]);
                srvLbl.setText(connection.data[5]);
                queryMismatchLbl.setText(connection.data[6]);
                motdAnimatedLbl.setText(connection.data[7]);
                motdLbl.setText(connection.data[8]);
                nrPlayersLbl.setText(connection.data[9]);
                playersLbl.setText(connection.data[10]);
                versionLbl.setText(connection.data[11]);
            }
            catch (IOException e) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(stage);
                Text errorDisplay = new Text("Sorry, something went wrong. \n Please check your input or report a bug!");
                Button exit = new Button("Ok");
                errorDisplay.setTextAlignment(TextAlignment.CENTER);
                VBox screen = new VBox(errorDisplay, exit);
                screen.setAlignment(Pos.CENTER);
                Scene dialogScene = new Scene(screen, 250, 100);
                dialog.setScene(dialogScene);
                dialog.show();

                exit.setOnAction(exitEvent -> dialog.close());
            }
        };

        start.setOnAction(init);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
