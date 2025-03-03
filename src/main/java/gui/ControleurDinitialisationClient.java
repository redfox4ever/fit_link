package gui;

import entites.Client;
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
import services.ServiceClient;
import utilitaires.SessionUtilisateur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class ControleurDinitialisationClient implements Initializable  {

    @FXML
    private Button Bconfirmer;

    @FXML
    private ChoiceBox<String> CBObjectif;

    @FXML
    private ChoiceBox<String> CBSexe;

    @FXML
    private DatePicker DPDate_de_naissance;

    @FXML
    private Spinner<Integer> SPoids;

    @FXML
    private Spinner<Integer> STaille;

    @FXML
    private TextField TFnom;

    @FXML
    private TextField TFprenom;

    @FXML
    private Label titre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactoryTaille =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 250);

        STaille.setValueFactory(valueFactoryTaille);

        SpinnerValueFactory<Integer> valueFactoryPoids =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 250);
        SPoids.setValueFactory(valueFactoryPoids);

        CBSexe.getItems().add("homme");
        CBSexe.getItems().add("femme");

        //
        CBObjectif.getItems().add("perte de poids");
        CBObjectif.getItems().add("gain musculaire");
        CBObjectif.getItems().add("santé et bien-être");

    }

    @FXML
    void confirmerInitialisationClient(ActionEvent event) {
        String nom = TFnom.getText();
        String prenom = TFprenom.getText();
        String sexe = CBSexe.getValue();
        String objectif = CBObjectif.getValue();
        Integer taille = STaille.getValue();
        Integer poids = SPoids.getValue();
        Date date_de_naissance = DPDate_de_naissance.getValue() == null ? null : Date.from(DPDate_de_naissance.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());


        if( nom.isEmpty() || prenom.isEmpty() || sexe.isEmpty() || objectif.isEmpty() || date_de_naissance == null || poids == 0 || taille == 0)
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
            ServiceClient.ajouter(utilisateur, objectif, taille, poids, date_de_naissance, sexe);

            Client client = ServiceClient.obtenir(utilisateur);
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setType_utilisateur("client");
            ServiceClient.mettreAJour(client);

            SessionUtilisateur.getInstance().setUtilisateurActuel(client);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //TODO: Navigation interface Client
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
