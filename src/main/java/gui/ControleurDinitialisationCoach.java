package gui;

import entites.Coach;
import entites.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceCoach;
import utilitaires.SessionUtilisateur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class ControleurDinitialisationCoach implements Initializable {

    @FXML
    private Button Bconfirmer;

    @FXML
    private ChoiceBox<String> CBSexe;

    @FXML
    private DatePicker DPDate_de_naissance;

    @FXML
    private TextArea TADescription;

    @FXML
    private TextField TFnom;

    @FXML
    private TextField TFprenom;

    @FXML
    private Label titre;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CBSexe.getItems().add("homme");
        CBSexe.getItems().add("femme");
    }

    @FXML
    void confirmerInitialisationCoach(ActionEvent event) {
        String nom = TFnom.getText();
        String prenom = TFprenom.getText();
        String sexe = CBSexe.getValue();
        String description = TADescription.getText();
        Date date_de_naissance = DPDate_de_naissance.getValue() == null ? null : Date.from(DPDate_de_naissance.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());


        if( nom.isEmpty() || prenom.isEmpty() || sexe.isEmpty() || description.isEmpty() || date_de_naissance == null)
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
            ServiceCoach.ajouter(utilisateur, description, sexe, date_de_naissance );

            Coach coach = ServiceCoach.obtenir(utilisateur);
            coach.setNom(nom);
            coach.setPrenom(prenom);
            coach.setType_utilisateur("coach");
            ServiceCoach.mettreAJour(coach);

            SessionUtilisateur.getInstance().setUtilisateurActuel(coach);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        Stage stage = (Stage)  titre.getScene().getWindow();


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
