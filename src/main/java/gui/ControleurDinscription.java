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
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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


            //send email

            String apiUrl = "https://hook.eu2.make.com/cqdthydg94knhett4huus3xgs5jtu5bu";
            String emailJsonString = "{\n" +
                    "  \"subject\": \"Félicitations ! Votre compte a été créé avec succès\",\n" +
                    "  \"recipient\": \"" + email + "\",\n" +
                    "  \"body\": \"Bonjour " + email + ",\\n\\n" +
                    "Félicitations ! Votre compte a été créé avec succès. Vous pouvez dès à présent vous connecter à notre plateforme et finaliser la configuration de votre compte.\\n\\n" +
                    "Nous vous invitons à vous rendre sur notre site pour personnaliser vos informations et commencer à profiter de nos services.\\n\\n" +
                    "Si vous avez des questions ou avez besoin d’aide, n’hésitez pas à nous contacter.\\n\\n" +
                    "Cordialement,\\n" +
                    "fitLink\"\n" +
                    "}";

            System.out.println("JSON to be sent: " + emailJsonString);

            // Create a URL object
            URL url = new URL(apiUrl);

            // Open a connection to the API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true); // Enable writing to the body of the request

            // Write the JSON input to the body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = emailJsonString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}

