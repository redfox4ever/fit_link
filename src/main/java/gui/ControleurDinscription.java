package gui;

import entites.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import services.ServiceUtilisateur;

import java.io.IOException;
import java.sql.SQLException;

public class ControleurDinscription {

    @FXML
    private Button BSeConnecter;

    @FXML
    private Button BSinscrire;

    @FXML
    private ImageView IVSlider;

    @FXML
    private PasswordField TFConfirmeMotDePasse;

    @FXML
    private TextField TFEmail;

    @FXML
    private PasswordField TFMotDePasse;

    @FXML
    void navigerConnexion(ActionEvent event) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/PageDeConnexion.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage)  TFEmail.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/PageDeConnexion.css").toExternalForm());
        stage.setScene(scene);

    }


    @FXML
    void sInscrire(ActionEvent event) {

        String email = TFEmail.getText();
        String motDePasse = TFMotDePasse.getText();
        String motDePasseConfirmation = TFConfirmeMotDePasse.getText();


        if ( email.isEmpty() || motDePasse.isEmpty() || motDePasseConfirmation.isEmpty() )
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Les champs de l'email et du mot de passe ne doivent etre pas vides!");
            alert.showAndWait();
            return;
        }

        if ( !motDePasse.equals(motDePasseConfirmation))

        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Le mot de passe doit être le même!");
            alert.showAndWait();
            return;
        }

        try {
            Utilisateur utilisateur = ServiceUtilisateur.obtenir(email, motDePasse);

            if (utilisateur != null)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Avertissement");
                alert.setHeaderText(null);
                alert.setContentText("L'email est déjà utilisé!");
                alert.showAndWait();
                return;
            }

            ServiceUtilisateur.ajouter(email, BCrypt.hashpw(motDePasse, BCrypt.gensalt(12)));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Félicitation");
            alert.setHeaderText(null);
            alert.setContentText("Vous êtes inscrit avec succès!");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    }


}

