package gui.backOffice;

import entities.Exercice;
import entities.Regime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.ExerciceService;
import services.RegimeService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherRegime implements Initializable {

    @FXML
    private HBox collectBtn2;
    @FXML
    private HBox commandsBtn1;
    @FXML
    private Pane content_area;
    @FXML
    private HBox dashboardBtn;
    @FXML
    private ImageView dashboardIcon;
    @FXML
    private Label dashboardText;
    @FXML
    private Button delete;
    @FXML
    private HBox fundrisingBtn;
    @FXML
    private ListView<Regime> listRegimes;
    @FXML
    private HBox navBarLogout;
    @FXML
    private HBox productsBtn;
    @FXML
    private Button regimes;
    @FXML
    private Button show;
    @FXML
    private HBox sideBarLogout;
    @FXML
    private HBox usersBtn;
    @FXML
    private Label usersText;
    @FXML
    private Button utilisateurs;

    RegimeService regimeService = new RegimeService();
    ExerciceService exerciceService = new ExerciceService();

    @FXML
    void regimes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backOffice/AfficherRegime.fxml"));
            Parent root = loader.load();


            if (root != null) {
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Error: Loading AfficherRegime.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
        // When a cell is clicked and the regime has no exercises, open the add exercise view.
        listRegimes.setOnMouseClicked(event -> {
            Regime selectedRegime = listRegimes.getSelectionModel().getSelectedItem();
            if (selectedRegime != null) {
                if (selectedRegime.getExercices() == null || selectedRegime.getExercices().isEmpty()) {
                    openAddExerciceView(selectedRegime);
                }
            }
        });
    }

    public void refreshTable() {
        ObservableList<Regime> regimeList;
        try {
            regimeList = FXCollections.observableArrayList(regimeService.readAll());
        } catch (SQLException ex) {
            regimeList = FXCollections.observableArrayList();
            System.out.println("Erreur lors de la récupération des régimes: " + ex.getMessage());
            ex.printStackTrace();
        }

            listRegimes.setCellFactory(new Callback<ListView<Regime>, ListCell<Regime>>() {
                @Override
                public ListCell<Regime> call(ListView<Regime> listView) {
                    return new ListCell<Regime>() {
                        private final VBox vbox = new VBox();
                        private final Label nomLabel = new Label();
                        // This label will display additional details when the cell is expanded.
                        private final Label detailsLabel = new Label();
                        private boolean expanded = false;

                        {
                            // Initially, only show the regime name.
                            vbox.getChildren().add(nomLabel);
                            setOnMouseClicked(event -> {
                                if (!isEmpty()) {
                                    expanded = !expanded;
                                    updateDisplay();
                                }
                            });
                        }

                    private void updateDisplay() {
                        Regime regime = getItem();
                        if (regime == null) return;

                        if (expanded) {
                            StringBuilder details = new StringBuilder();
                            //details.append("\nDescription: ").append(regime.getDescription());
                            //details.append("\nType: ").append(regime.getTypeRegime());
                            details.append("\nCoach: ").append(regime.getCoach() != null ? regime.getCoach().toString() : "Non défini");

                            // Update the exercises list.
                            try {
                                List<Exercice> exercices = exerciceService.getExercisesForRegime(regime.getId());
                                regime.setExercices(exercices);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            List<Exercice> exercices = regime.getExercices();
                            if (exercices == null || exercices.isEmpty()) {
                                details.append("\nAucun exercice associé");
                            } else {
                                details.append("\nExercices:");
                                for (Exercice e : exercices) {
                                    details.append("\n- ").append(e.getNom())
                                            .append(" (").append(e.getCaloriesBrulees())
                                            .append(" cal/").append(e.getDuree()).append(" min)");
                                }
                            }
                            detailsLabel.setText(details.toString());
                            if (!vbox.getChildren().contains(detailsLabel)) {
                                vbox.getChildren().add(detailsLabel);
                            }
                            setStyle("-fx-background-color: green;");
                        } else {
                            vbox.getChildren().remove(detailsLabel);
                            setStyle("");
                        }
                    }

                    @Override
                    protected void updateItem(Regime regime, boolean empty) {
                        super.updateItem(regime, empty);
                        if (empty || regime == null) {
                            setGraphic(null);
                        } else {
                            nomLabel.setText("Nom : " + regime.getNom() + "\n"+" Description: "+regime.getDescription()+"\n"+" Type: "+regime.getTypeRegime());
                            // Refresh the exercises for this regime.
                            try {
                                List<Exercice> exercices = exerciceService.getExercisesForRegime(regime.getId());
                                regime.setExercices(exercices);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            updateDisplay();
                            setGraphic(vbox);
                        }
                    }
                };
            }
        });
        listRegimes.setItems(regimeList);
    }

    // Opens the interface for adding exercises for the given regime.
    private void openAddExerciceView(Regime regime) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backOffice/CrrerExercice.fxml"));
            Parent root = loader.load();
            // The exercise creation controller must have a method setRegime(Regime regime)
            CreerExercice controller = loader.getController();
            controller.setRegime(regime);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter des Exercices");
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ouverture de l'ajout d'exercices : " + e.getMessage());
        }
    }

    @FXML
    void addRegime(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backOffice/CrrerRegime.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Créer un Régime");
            stage.setScene(new Scene(root, 800, 500));
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void addExercice(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backOffice/CrrerExercice.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Créer un Régime");
            stage.setScene(new Scene(root, 650, 700));
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteRegime(ActionEvent event) {
        Regime selectedRegime = listRegimes.getSelectionModel().getSelectedItem();
        if (selectedRegime == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un régime à supprimer.");
            alert.showAndWait();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation de suppression");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce régime et ses exercices associés ?");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    List<Exercice> exercices = exerciceService.getExercisesForRegime(selectedRegime.getId());
                    for (Exercice e : exercices) {
                        exerciceService.delete(e);
                    }
                    regimeService.delete(selectedRegime);
                    refreshTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText("Erreur lors de la suppression");
                    errorAlert.setContentText("Une erreur s'est produite lors de la suppression du régime.");
                    errorAlert.showAndWait();
                }
            }
        });
    }

    // Opens an update form for the selected regime.
    public void updateRegime(ActionEvent actionEvent) {
        Regime selectedRegime = listRegimes.getSelectionModel().getSelectedItem();
        if (selectedRegime == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un régime à mettre à jour.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backOffice/UpdateRegime.fxml"));
            Parent root = loader.load();
            UpdateRegime controller = loader.getController();
            controller.setRegime(selectedRegime);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Mettre à jour le Régime");
            stage.show();

            // Close the current window:
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
