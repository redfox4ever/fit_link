package gui;
import entites.ProprietaireSalle;
import entites.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceProprietaireSalle;
import utilitaires.SessionUtilisateur;

import java.io.IOException;
import java.sql.SQLException;

public class ControleurDinitialisationProprietaire {

    public Label titre;
    @FXML
    private Button Bconfirmer;

    @FXML
    private TextField TFnom;

    @FXML
    private TextField TFprenom;


    @FXML
    void confirmerInitialisationProprietaire(ActionEvent event) {

        String nom = TFnom.getText();
        String prenom = TFprenom.getText();


        if( nom.isEmpty() || prenom.isEmpty() )
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Toutes les informations doivent être incluses!");
            alert.showAndWait();
            return;
        }
        Utilisateur utilisateur = SessionUtilisateur.getInstance().getUtilisateurActuel();

        try {
            ServiceProprietaireSalle.ajouter(utilisateur);

            ProprietaireSalle proprietaireSalle = ServiceProprietaireSalle.obtenir(utilisateur);
            proprietaireSalle.setNom(TFnom.getText());
            proprietaireSalle.setPrenom(TFprenom.getText());
            proprietaireSalle.setType_utilisateur("proprietaire_salle");
            ServiceProprietaireSalle.mettreAJour(proprietaireSalle);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //TODO: Navigation interface proprietaire
        Parent root = null;
        Scene scene = new Scene(root);
        Stage stage = (Stage)  titre.getScene().getWindow();
        stage.setScene(scene);

    }

    @FXML
    void navigerInitialisationUtilisateur(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/PageDinitialisationUtilisateur.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage)  titre.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/PageDinitialisation.css").toExternalForm());
        stage.setScene(scene);

    }

}
