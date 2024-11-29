package ca.mcgill.ecse.coolsupplies.view;

import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ViewBundleView {

    @FXML
    private Button newBundles;

    @FXML
    private TableView<TOGradeBundle> bundlesTable;

    @FXML
    private TableColumn<TOGradeBundle, String> bundleName;

    @FXML
    private TableColumn<TOGradeBundle, String> bundleGrade;

    @FXML
    private Label errorLabel;

    private ObservableList<TOGradeBundle> bundleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        // Set up the table columns
        bundleName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        bundleGrade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGradeLevel()));

        // Load initial data into the table
        refreshTable();

        // Load data into the table
        // try {
        //     bundleList.addAll(getAllBundles());
        //     bundlesTable.setItems(bundleList);
        // } catch (Exception e) {
        //     displayError("Failed to load bundles: " + e.getMessage());
        // }

        // Add listener for row double-clicks
        bundlesTable.setRowFactory(tv -> {
            TableRow<TOGradeBundle> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    TOGradeBundle clickedBundle = row.getItem();
                    openBundleDetail(clickedBundle);
                }
            });
            return row;
        });
    }

    // @FXML
    public void newBundle(ActionEvent event) {
        try {
            openNewBundlePage();
            clearError();
        } catch (Exception e) {
            displayError("Failed to open the new bundle page: " + e.getMessage());
        }
    }

    private void openBundleDetail(TOGradeBundle bundle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditBundle.fxml"));
            Parent root = loader.load();

            // Pass the bundle to the controller
            EditBundleView controller = loader.getController();
            controller.initData(bundle);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Bundle");
            stage.show();

            clearError();
        } catch (IOException e) {
            displayError("Failed to open the bundle detail page: " + e.getMessage());
        }
    }

    private void openNewBundlePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBundle.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Bundle");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e); // Let the calling method handle it
        }
    }

    // private List<TOGradeBundle> getAllBundles() {
    //     return CoolSuppliesFeatureSet4Controller.getBundles();
    // }

   // Method to display error messages
    private void displayError(String message) {
        errorLabel.setText(message);
    }

    //Method to clear error messages
    private void clearError() {
        errorLabel.setText("");
    }

    private void refreshTable() {
        bundleList.setAll(CoolSuppliesFeatureSet4Controller.getBundles());
        bundlesTable.setItems(bundleList);
    }
}