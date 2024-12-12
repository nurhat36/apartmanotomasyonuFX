package com.mycompany.apartmanotomasyonufxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        // FXML yükleniyor
        Parent rootNode = loadFXML(fxml);

        // Yeni bir StackPane oluştur
        StackPane root = new StackPane(rootNode);

        // Yeni bir Stage oluştur (Açılan pencere)
        Stage newStage = new Stage();
        newStage.setTitle(fxml);
        newStage.setScene(new Scene(root)); // Yeni sahneyi oluştur ve ata

        // Stage'i ekrana ortala ve göster
        newStage.centerOnScreen();
        newStage.show();
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/mycompany/apartmanotomasyonufxml/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        SQLHelper sql = new SQLHelper();
        launch();// // JavaFX uygulamasını başlatır. Bu, `start` metodunu çağırır.
    }

}