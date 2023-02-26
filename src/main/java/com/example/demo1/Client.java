package com.example.demo1;

import java.io.*;
import java.net.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Client extends Application {
    private TextField textField;
    private TextArea textArea;
    private BufferedReader in;
    private PrintWriter out;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        textField = new TextField();
        textField.setOnAction(e -> {
            out.println(textField.getText());
            textField.setText("");
        });

        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);

        VBox vbox = new VBox(10, textArea, textField);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("Chat TCP");
        stage.show();
    }

    private void run() throws Exception {
        Socket socket = new Socket("localhost", 1234);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String input = in.readLine();
            if (input == null) break;
            Platform.runLater(() -> textArea.appendText(input + "\n"));
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
        Client client = new Client();
        client.run();
    }
}
