package gui.backOffice;

import entities.Coach;
import entities.Regime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.RegimeService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateRegime {
    @FXML
    private TextField nomField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField typeField;
    @FXML
    private ComboBox<Integer> coachComboBox; // This stores coach IDs

    private Regime regime;
    private RegimeService regimeService = new RegimeService();

    // Called from AfficherRegime when the update button is clicked.
    public void setRegime(Regime regime) {
        this.regime = regime;
        nomField.setText(regime.getNom());
        descriptionField.setText(regime.getDescription());
        typeField.setText(regime.getTypeRegime());
        // Only set the ComboBox value if a coach exists.
      coachComboBox.setValue(1);
    }

    @FXML
    private void mettreAJourRegime() {
        // Update the regime object with new values.
        regime.setNom(nomField.getText());
        regime.setDescription(descriptionField.getText());
        regime.setTypeRegime(typeField.getText());
        regime.setRefCoach(1);
        // If a coach is selected, create a new Coach instance with that ID.


        try {
            regimeService.update(regime);
            // Optionally, show a success message or close the update window.
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, display an error alert.
        }
    }

    // Called when the controller is initialized.
    public void initialize(URL location, ResourceBundle resources) {
        // Populate the coachComboBox with coach IDs.
        List<Integer> coachIds = regimeService.getAllCoachIds();
        coachComboBox.getItems().addAll(coachIds);
    }

    // Navigates back to the AfficherRegime view.
    @FXML
    public void goToRegimes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backOffice/AfficherRegime.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage;
            if (event != null) {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            } else {
                stage = (Stage) nomField.getScene().getWindow();
            }
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
