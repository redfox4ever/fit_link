package gui.backOffice;

import entities.Exercice;
import entities.Niveau;
import entities.Regime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ExerciceService;
import services.RegimeService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CreerExercice implements Initializable {

    @FXML
    private TextField nomField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField caloriesField;

    @FXML
    private TextField dureeField;

    @FXML
    private ComboBox<Niveau> niveauComboBox;

    @FXML
    private TextField typeField;

    @FXML
    private ComboBox<Integer> regimeComboBox;

    @FXML
    private Button ajouterButton;

    private final ExerciceService exerciceService = new ExerciceService();
    private final RegimeService regimeService = new RegimeService();
    @FXML
    private Label regimeIdLabel;

    // The regime passed from the previous view
    private Regime regime;

    // This method will be called by the previous screen to pass the regime
    public void setRegime(Regime regime) {
        this.regime = regime;
        // You can now use regime.getId() directly to show the ID or for other operations
        if (regimeIdLabel != null) {
            regimeIdLabel.setText("Regime ID: " + regime.getId());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Charger les niveaux de difficulté
        niveauComboBox.getItems().addAll(Niveau.values());

        // Charger les régimes disponibles dans le ComboBox
        List<Regime> regimes;
        try {
            regimes = regimeService.readAll();
            for (Regime r : regimes) {
                regimeComboBox.getItems().add(r.getId());
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de charger les régimes : " + e.getMessage(), Alert.AlertType.ERROR);
        }

        // Appliquer le CSS
        applyCSS();
    }

    // Fonction pour appliquer le fichier CSS
    private void applyCSS() {
        if (ajouterButton.getScene() != null) {
            ajouterButton.getScene().getStylesheets().add(getClass().getResource("/backoffice0/Styles.css").toExternalForm());
        }
    }

    @FXML
    void ajouterExercice(ActionEvent event) {
        try {
            String nom = nomField.getText();
            String description = descriptionField.getText();
            double calories = Double.parseDouble(caloriesField.getText());
            int duree = Integer.parseInt(dureeField.getText());
            Niveau niveau = niveauComboBox.getValue();
            String type = typeField.getText();
            Integer regimeId = regimeComboBox.getValue();

            if (nom.isEmpty() || description.isEmpty() || type.isEmpty() || niveau == null || regimeId == null) {
                showAlert("Erreur", "Veuillez remplir tous les champs !", Alert.AlertType.ERROR);
                return;
            }

            Regime regime = new Regime();
            regime.setId(regimeId);

            Exercice exercice = new Exercice(nom, description, calories, duree, niveau, type, regime);
            exerciceService.create(exercice);
            showAlert("Succès", "Exercice ajouté avec succès !", Alert.AlertType.INFORMATION);
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs numériques valides pour les calories et la durée !", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible d'ajouter l'exercice : " + e.getMessage(), Alert.AlertType.ERROR);
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
        caloriesField.clear();
        dureeField.clear();
        niveauComboBox.setValue(null);
        typeField.clear();
        regimeComboBox.setValue(null);
    }
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
