package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilitaires.Navigator;
import utilitaires.SessionUtilisateur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControleurDinitialisationUtilisateur implements Initializable {


    @FXML
    private Label titre;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if( SessionUtilisateur.getInstance().getUtilisateurActuel().getType_utilisateur() != null )
        {
            System.out.println("l'initialisation n'est pas necessaire");
        }


    }


    @FXML
    void navigationInitialisationClient(MouseEvent event) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/PageDinitialisationClient.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage)  titre.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/PageDinitialisation.css").toExternalForm());
        stage.setScene(scene);

    }


    @FXML
    void navigationInittialisationCoach(MouseEvent event) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/PageDinitialisationCoach.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage)  titre.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/PageDinitialisation.css").toExternalForm());
        stage.setScene(scene);

    }

    @FXML
    void navigationInittialisationProprietaire(MouseEvent event) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/PageDinitialisationProprietaire.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) titre.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/PageDinitialisation.css").toExternalForm());
        stage.setScene(scene);

    }

}



