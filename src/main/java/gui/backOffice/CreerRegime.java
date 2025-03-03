package gui.backOffice;

import entities.Regime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import services.RegimeService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CreerRegime implements Initializable {

    @FXML
    private TextField nomField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField typeField;

    @FXML
    private ComboBox<Integer> coachComboBox;

    @FXML
    private Button ajouterButton;

    private final RegimeService regimeService = new RegimeService();



    @FXML
    void ajouterRegime(ActionEvent event) {
        String nom = nomField.getText();
        String description = descriptionField.getText();
        String type = typeField.getText();
        Integer refCoach = coachComboBox.getValue();

        if (nom.isEmpty() || description.isEmpty() || type.isEmpty() || refCoach == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs !", Alert.AlertType.ERROR);
            return;
        }

        Regime regime = new Regime(nom, description, type);
        regime.setRefCoach(refCoach);

        try {
            regimeService.create(regime);
            showAlert("Succès", "Régime ajouté avec succès !", Alert.AlertType.INFORMATION);
            clearFields();
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible d'ajouter le régime : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nomField.clear();
        descriptionField.clear();
        typeField.clear();
        coachComboBox.setValue(null);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Charger les coachs disponibles dans le ComboBox
        List<Integer> coachIds = regimeService.getAllCoachIds();
        coachComboBox.getItems().addAll(coachIds);

        // Appliquer le style CSS
        if (ajouterButton.getScene() != null) {
            ajouterButton.getScene().getStylesheets().add(getClass().getResource("/styles/style1.css").toExternalForm());
        }
    }
    @FXML
     public void goToRegimes(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backOffice/AfficherRegime.fxml"));
            Parent root = loader.load();
            // Check if the loading was successful
            if (root != null) {
                // Create a new scene with the loaded view
                Scene scene = new Scene(root);

                // Get the main stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Error: Loading Afficher.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
