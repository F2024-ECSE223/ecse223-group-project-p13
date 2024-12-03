package ca.mcgill.ecse.coolsupplies.view;

import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import java.util.List;
import atlantafx.base.theme.Styles;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewBundleView {
    public StackPane mainContent;

    @FXML
    private Button newBundles;

    @FXML
    private TableView<TOGradeBundle> bundlesTable;

    @FXML
    private TableColumn<TOGradeBundle, String> bundleName;

    @FXML
    private TableColumn<TOGradeBundle, String> bundleGrade;

    @FXML
    private TableColumn<TOGradeBundle, String> discount;

    @FXML
    private TableColumn<TOGradeBundle, Void> editButton;

    @FXML
    private Label errorLabel = new Label("null");

    private ObservableList<TOGradeBundle> bundleList = FXCollections.observableArrayList();

    /**
    * @author Edouard Dupont
    * 
    * This method initializes the view by setting up table columns, adding action buttons,
    * refreshing the table, and styling the "new bundle" button.
    * 
    * @return void
    */
    @FXML
    public void initialize() {
        bundleName.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        bundleGrade.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getGradeLevel()));
        discount.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().getDiscount())));

        addActionButtonToTable();

        refreshTable();

        this.newBundles.getStyleClass().add(Styles.SUCCESS);
    }

    /**
    * @author Edouard Dupont
    * 
    * Opens a new window to add a bundle. Passes the current ViewBundleView instance 
    * to the AddBundleController for context.
    * 
    * @param event The action event triggered by the user clicking the "new bundle" button.
    * @return void
    */
    @FXML
    public void newBundle(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/AddBundle.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            AddBundleController controller = loader.getController();

            // **Pass the ViewBundleView reference to AddBundleController**
            controller.initData(this);

            // Show the window
            Stage stage = new Stage();
            stage.setTitle("Add a New Bundle");
            stage.setScene(new Scene(root));
            stage.show();

            clearError();
        } catch (Exception e) {
            displayError("Failed to open the new bundle page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
    * @author Edouard Dupont
    * 
    * Adds an "Edit" button to each row in the table. Clicking the button opens a new
    * window to edit the selected bundle.
    * 
    * @return void
    */
    private void addActionButtonToTable() {
        editButton.setCellFactory(param -> new TableCell<TOGradeBundle, Void>() {
        private final Button editButton = new Button("Edit");
        {
            editButton.setPrefWidth(80);
            editButton.setPrefHeight(30);
            editButton.getStyleClass().add(Styles.ACCENT);
            editButton.getStyleClass().add(Styles.BUTTON_OUTLINED);

            // Configure edit button
            editButton.setOnAction(event -> {
                TOGradeBundle bundle = getTableView().getItems().get(getIndex());
                try {
                    // Load the FXML file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/EditBundle.fxml"));
                    Parent root = loader.load();

                    // Get the controller instance
                    EditBundleView controller = loader.getController();

                    // Pass the selected bundle to the controller
                    controller.initData(bundle);

                    // Create a new stage and show the scene
                    Stage stage = new Stage();
                    stage.setTitle("Edit a Bundle");
                    stage.setScene(new Scene(root));
                    stage.show();

                    // **Add listener to refresh the table when the window is closed**
                    stage.setOnHidden(e -> refreshTable());

                    clearError();
                } catch (Exception e) {
                    displayError("Failed to open the edit bundle page: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(editButton);
            }
        }
    });  
}

    /**
    * @author Edouard Dupont
    * 
    * Displays an error message in the error label.
    * 
    * @param message The error message to be displayed.
    * @return void
    */
    private void displayError(String message) {
        errorLabel.setText(message);
    }

    /**
    * @author Edouard Dupont
    * 
    * Clears the error message from the error label.
    * 
    * @return void
    */
    private void clearError() {
        errorLabel.setText("");
    }

    /**
    * @author Edouard Dupont
    * 
    * Refreshes the bundle table by retrieving all bundles, updating discounts where necessary,
    * and ensuring the table displays the latest data.
    * 
    * @return void
    */
    public void refreshTable() {
        // Get all bundles
        List<TOGradeBundle> allBundles = CoolSuppliesFeatureSet4Controller.getBundles();

        // Iterate over each bundle
        for (TOGradeBundle bundle : allBundles) {
            // Get the number of items in the bundle
            List<TOBundleItem> bundleItems =
                    CoolSuppliesFeatureSet5Controller.getBundleItems(bundle.getName());
            int nbItems = 0;

            for (TOBundleItem item : bundleItems) {
                nbItems += item.getQuantity();
            }
            // If the bundle has fewer than 2 items and discount is not zero
            if (nbItems < 2 && bundle.getDiscount() != 0) {
                // Set the discount to zero
                String result = CoolSuppliesFeatureSet4Controller.updateBundle(bundle.getName(),
                        bundle.getName(), 0, bundle.getGradeLevel());
                if (!result.isEmpty()) {
                    // Handle any error messages
                    displayError("Error updating bundle discount: " + result);
                }
            }
        }

        // After updating discounts, refresh the bundle list
        bundleList.setAll(CoolSuppliesFeatureSet4Controller.getBundles());
        bundlesTable.setItems(bundleList);
        bundlesTable.refresh();
    }
}

