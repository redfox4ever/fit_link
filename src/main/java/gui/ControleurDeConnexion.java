package gui;

import entites.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import services.ServiceUtilisateur;
import utilitaires.Captcha;
import utilitaires.Navigator;
import utilitaires.SessionUtilisateur;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControleurDeConnexion  implements Initializable {

    @FXML
    private Button BSeConnecter;

    @FXML
    private ImageView IVSlider;

    @FXML
    private TextField TFEmail;

    @FXML
    private PasswordField TFMotDePasse;

    @FXML
    private Hyperlink HLInscription;

    String captchaAnswer;

    @FXML
    private TextField TFCaptchaResponse;

    @FXML
    private ImageView captchaImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Generate a CAPTCHA image
        Captcha captcha = new Captcha();

        // Convert BufferedImage to JavaFX Image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(captcha.image, "png", baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Image image = new Image(bais);

        // Store the correct answer for later validation
        captchaAnswer = captcha.answer;
        System.out.println(captchaAnswer);
        captchaImage.setImage(image);
    }

    @FXML
    void connecter(ActionEvent event)
    {
        String email = TFEmail.getText();
        String motDePasse = TFMotDePasse.getText();
        String captchaResponse = TFCaptchaResponse.getText();
        System.out.println(captchaResponse + "  " + captchaAnswer);
        if (!Objects.equals(captchaResponse, captchaAnswer))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Captcha est incorrect!");
            alert.showAndWait();
            return;
        }
        if ( email.isEmpty() || motDePasse.isEmpty() )
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Les champs de l'email et du mot de passe ne doivent etre pas vides!");
            alert.showAndWait();

        }

        else {

            Utilisateur utilisateur = null;
            try {


                utilisateur = ServiceUtilisateur.obtenir(email);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (!BCrypt.checkpw(motDePasse, utilisateur.getMot_de_passe())) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Avertissement");
                    alert.setHeaderText(null);
                    alert.setContentText("Le compte n'existe pas!");
                    alert.showAndWait();

                } else {

                System.out.println(utilisateur.getType_utilisateur());
                SessionUtilisateur session = SessionUtilisateur.getInstance();
                session.setUtilisateurActuel(utilisateur);
                Stage stage = (Stage) TFEmail.getScene().getWindow();

                String type = session.getUtilisateurActuel().getType_utilisateur();

                if (Objects.equals(type, "coach") ) {
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
                        stage.setTitle("Gestion des Régimes");
                        stage.setScene(scene);
                        //primaryStage.show();
                    } catch (Exception e) {
                        System.err.println("Erreur lors du chargement de l'interface : " + e.getMessage());
                        e.printStackTrace();
                    }

                } else if (Objects.equals(type, "client")) {

                } else if (Objects.equals(type,"proprietaire_salle")) {

                } else if (Objects.equals(type,"administrateur")) {

                } else {

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PageDinitialisationUtilisateur.fxml"));
                        Parent root = loader.load();

                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("/PageDinitialisation.css").toExternalForm());
                        stage.setScene(scene);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
        }
    }

    public void navigerInscription()
    {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/PageDinscription.fxml"));
            Stage stage = (Stage)  TFEmail.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/PageDinscription.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
