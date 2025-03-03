package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger l'interface graphique depuis le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backOffice/AfficherRegime.fxml"));
            Parent root = loader.load();

            // Vérifier si le chargement s'est bien passé
            if (root != null) {
                System.out.println("Interface chargée avec succès !");
            } else {
                System.err.println("Erreur : le fichier FXML n'a pas été chargé correctement.");
            }

            // Créer une scène et l'afficher
            Scene scene = new Scene(root);
            primaryStage.setTitle("Gestion des Régimes");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'interface : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
