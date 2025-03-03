package gui;

import entites.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import services.ServiceUtilisateur;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;

public class ControleurPasswordRecover {

    @FXML
    private Button BCheckEmail;

    @FXML
    private ImageView IVSlider;

    @FXML
    private TextField TFEmail;

    @FXML
    private VBox contentArea;

    @FXML
    void checkEmail(ActionEvent event) {
        String email = TFEmail.getText();
        try {
            Utilisateur utilisateur = ServiceUtilisateur.obtenir(email);
            if (utilisateur == null)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Avertissement");
                alert.setHeaderText(null);
                alert.setContentText("L'email' n'existe pas!");
                alert.showAndWait();

            }
            else {
                String verificationCode = generateVerificationCode();


                //Sending verification Code
                try {
                    sendVerificationCode(email, verificationCode);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                contentArea.getChildren().clear();
                TextField verificationCodeField = new TextField();
                verificationCodeField.setPromptText("Enter verification code");
                verificationCodeField.setPrefWidth(249);
                verificationCodeField.setPrefHeight(32);
                verificationCodeField.setStyle("-fx-background-color: #1b1b1e; -fx-text-fill: white; -fx-border-width: 2; -fx-border-color: #404043; -fx-border-radius: 5; -fx-background-radius: 5;");

                Button verifyButton = new Button("Verifier Code");
                verifyButton.setPrefWidth(249);
                verifyButton.setPrefHeight(32);
                verifyButton.setStyle("-fx-background-color: #32974A; -fx-text-fill: white; -fx-border-radius: 12; -fx-padding: 10; -fx-font-size: 16px;");


                verifyButton.setOnAction(e -> {
                    String code = verificationCodeField.getText();



                    if (code.equals(verificationCode)) { // Replace "expectedCode" with actual logic
                        contentArea.getChildren().clear();

                        // Create new TextField for old password
                        TextField passwordField = new TextField();
                        passwordField.setPromptText("Entrer mot de passe");
                        passwordField.setPrefWidth(249); // Set appropriate layout
                        passwordField.setPrefHeight(32); // Set appropriate layout
                        passwordField.setStyle("-fx-background-color: #1b1b1e; -fx-text-fill: white; -fx-border-width: 2; -fx-border-color: #404043; -fx-border-radius: 5; -fx-background-radius: 5;");


                        // Create new TextField for new password
                        TextField passwordOfConfirmationField = new TextField();
                        passwordOfConfirmationField.setPromptText("Veifier mot de passe");
                        passwordOfConfirmationField.setPrefWidth(249); // Set appropriate layout
                        passwordOfConfirmationField.setPrefHeight(32); // Set appropriate layout
                        passwordOfConfirmationField.setStyle("-fx-background-color: #1b1b1e; -fx-text-fill: white; -fx-border-width: 2; -fx-border-color: #404043; -fx-border-radius: 5; -fx-background-radius: 5;");


                        // Create new Button to update password
                        Button updateButton = new Button("Update Password");
                        updateButton.setPrefWidth(249);
                        updateButton.setPrefHeight(32);
                        updateButton.setStyle("-fx-background-color: #32974A; -fx-text-fill: white; -fx-border-radius: 12; -fx-padding: 10; -fx-font-size: 16px;");


                        // Add event handler for the update button
                        updateButton.setOnAction(ev -> {
                            String password = passwordField.getText();
                            String passwordOfConfirmation = passwordOfConfirmationField.getText();
                            // Add your password update logic here
                            // For example, you can call a method from ServiceUtilisateur to update the password

                            if(!password.equals(passwordOfConfirmation)) {

                                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                                successAlert.setTitle("Erreur");
                                successAlert.setHeaderText(null);
                                successAlert.setContentText("Les mots de passe ne correspondent pas!");
                                successAlert.showAndWait();
                                return;
                            }

                            utilisateur.setMot_de_passe(BCrypt.hashpw(password, BCrypt.gensalt(12)));

                            try {

                                if (ServiceUtilisateur.mettreAJour(utilisateur) != null) {
                                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                                    successAlert.setTitle("Succès");
                                    successAlert.setHeaderText(null);
                                    successAlert.setContentText("Mot de passe mis à jour avec succès!");
                                    successAlert.showAndWait();

                                    navigerConnexion();
                                } else {
                                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                    errorAlert.setTitle("Erreur");
                                    errorAlert.setHeaderText(null);
                                    errorAlert.setContentText("Échec de la mise à jour du mot de passe!");
                                    errorAlert.showAndWait();
                                }
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                        contentArea.getChildren().addAll(passwordField, passwordOfConfirmationField, updateButton);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText(null);
                        alert.setContentText("Code de vérification invalide!");
                        alert.showAndWait();
                    }
                });


                contentArea.getChildren().addAll(verificationCodeField, verifyButton);
            }




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    private String generateVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10)); // Append a random digit (0-9)
        }
        return code.toString();
    }

    private void sendVerificationCode(String email, String verificationCode) throws IOException {
        //send email

        String apiUrl = "https://hook.eu2.make.com/anxdvf60623fcxrqbtl1fsm7nvc3f6xw";
        String emailJsonString = "{\n" +
                "  \"subject\": \"Réinitialisation de votre mot de passe\",\n" +
                "  \"recipient\": \"" + email + "\",\n" +
                "  \"body\": \"Bonjour " + email + ",\\n\\n" +
                "Nous avons reçu une demande de réinitialisation de votre mot de passe.\\n\\n" +
                "Votre code de vérification est : " + verificationCode + "\\n\\n" +
                "Veuillez saisir ce code sur notre plateforme pour continuer la procédure de réinitialisation.\\n\\n" +
                "Si vous n'avez pas fait cette demande, ignorez simplement cet email.\\n\\n" +
                "Cordialement,\\n" +
                "fitLink\"\n" +
                "}";

        System.out.println("JSON to be sent: " + emailJsonString);

        // Create a URL object
        URL url = null;
        try {
            url = new URL(apiUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

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
    }

    void navigerConnexion() {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/PageDeConnexion.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage)  contentArea.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/PageDeConnexion.css").toExternalForm());
        stage.setScene(scene);

    }

}