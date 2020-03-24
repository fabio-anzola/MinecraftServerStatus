import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MinecraftServerStatusGUI extends Application {

    @Override
    public void start(Stage stage) {
        //stage setup
        stage.setTitle("Minecraft-Server Status");
        stage.setMinHeight(570);
        stage.setMaxHeight(570);
        stage.setMinWidth(500);
        stage.setMaxWidth(1000);

        //input (label, field, button)
        Label sourceLbl = new Label("Hostname or ip: ");
        sourceLbl.setId("headerLbl");
        TextField host = new TextField();
        Button start = new Button("Go!");
        HBox inputBox = new HBox(sourceLbl, host, start);

        //Server-state section
        Label state = new Label("Server state: ");
        Label stateLbl = new Label("");
        HBox stateBox = new HBox(state, stateLbl);

        //Server-ip section
        Label ip = new Label("Server-ip: ");
        Label ipLbl = new Label("ip");
        HBox ipBox = new HBox(ip, ipLbl);

        //Server-port section
        Label port = new Label("Server-port: ");
        Label portLbl = new Label("");
        HBox portBox = new HBox(port, portLbl);

        //Server-ping-enabled section
        Label ping = new Label("Ping enabled: ");
        Label pingLbl = new Label("");
        HBox pingBox = new HBox(ping, pingLbl);

        //Server-query-enabled section
        Label query = new Label("Query enabled: ");
        Label queryLbl = new Label("");
        HBox queryBox = new HBox(query, queryLbl);

        //Server-srv-record section
        Label srv = new Label("Using srv record: ");
        Label srvLbl = new Label("srv");
        HBox srvBox = new HBox(srv, srvLbl);

        //Server-query-mismatch section
        Label queryMismatch = new Label("Query mismatch: ");
        Label queryMismatchLbl = new Label("");
        HBox queryMismatchBox = new HBox(queryMismatch, queryMismatchLbl);

        //Sever-animated-motd section
        Label motdAnimated = new Label("MOTD animated: ");
        Label motdAnimatedLbl = new Label("");
        HBox motdAnimatedBox = new HBox(motdAnimated, motdAnimatedLbl);

        //Server-motd section
        Label motd = new Label("MOTD: ");
        Label motdLbl = new Label("");
        HBox motdBox = new HBox(motd, motdLbl);

        //Server-nr-of-online-players section
        Label nrPlayers = new Label("Nr of online players: ");
        Label nrPlayersLbl = new Label("");
        HBox nrPlayerBox = new HBox(nrPlayers, nrPlayersLbl);

        //Server-online-players section
        Label players = new Label("Online players: ");
        Label playersLbl = new Label("");
        HBox playersBox = new HBox(players, playersLbl);

        //Server-version section
        Label version = new Label("Server version: ");
        Label versionLbl = new Label("");
        HBox versionBox = new HBox(version, versionLbl);

        //Program-source section
        Hyperlink sourceCode = new Hyperlink("Program and sourcecode here");
        sourceCode.setOnAction(eventLink -> getHostServices().showDocument("https://github.com/fabio-anzola/MinecraftServerStatus"));
        HBox srcCodeBox = new HBox(sourceCode);
        srcCodeBox.setAlignment(Pos.CENTER);

        //Main vertical Box
        VBox vertical = new VBox(inputBox, stateBox, ipBox, portBox, pingBox, queryBox, srvBox, queryMismatchBox, motdAnimatedBox, motdBox, nrPlayerBox, playersBox, versionBox, srcCodeBox);
        vertical.setSpacing(20);

        //Scene setup
        Scene scene = new Scene(vertical, 500, 550);
        stage.setScene(scene);

        //Stylesheets
        scene.getStylesheets().add("style.css");
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto&display=swap");

        //If GO button is pressed
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

        //if ENTER key is pressed
        scene.setOnKeyPressed(eventKey -> {
            if (eventKey.getCode() == KeyCode.ENTER) {
                start.fire();
            }
        });

        //map action (event) to GO button
        start.setOnAction(init);

        //final show
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
