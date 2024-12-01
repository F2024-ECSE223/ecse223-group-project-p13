package ca.mcgill.ecse.coolsupplies.view;

import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.layout.HBox;

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
   private TableColumn<TOGradeBundle, Void> editButton;
   //Check if this is right...

   @FXML
   private Label errorLabel = new Label("null");


   private ObservableList<TOGradeBundle> bundleList = FXCollections.observableArrayList();
//    private int editingRowIndex = -1;


   @FXML
   public void initialize() {

    //    Set up the table columns
       bundleName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
       bundleGrade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGradeLevel()));

       //Add action items to table
       addActionButtonToTable();

       // Load initial data into the table
       refreshTable();

   }


   @FXML
   public void newBundle(ActionEvent event) {
       try {
           CoolSuppliesFxmlView.newWindow("AddBundle.fxml", "Add a New Bundle");
           clearError();
       } catch (Exception e) {
           displayError("Failed to open the new bundle page: " + e.getMessage());
       }

       //Add action button to table
       addActionButtonToTable();

   }

   private void addActionButtonToTable() {
    editButton.setCellFactory(param -> new TableCell<TOGradeBundle, Void>() {
        private final Button editButton = new Button("Edit");
        {
            editButton.setPrefWidth(80);
            editButton.setPrefHeight(30);

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



    // editButton.setCellFactory(param -> new TableCell<TOGradeBundle, Void>() {
    //     private final Button editButton = new Button("Edit");
    //     {
    //         editButton.setPrefWidth(80);
    //         editButton.setPrefHeight(30);

    //         // Configure edit button
    //         editButton.setOnAction(event -> {
    //             TOGradeBundle bundle = getTableView().getItems().get(getIndex());
    //             try {
    //                 // Pass the bundle to the edit window or controller
    //                 CoolSuppliesFxmlView.newWindow("EditBundle.fxml", "Edit a Bundle");
    //                 clearError();
    //             } catch (Exception e) {
    //                 displayError("Failed to open the edit bundle page: " + e.getMessage());
    //             }
    //         });
    //     }

    //     @Override
    //     protected void updateItem(Void item, boolean empty) {
    //         super.updateItem(item, empty);
    //         if (empty) {
    //             setGraphic(null);
    //         } else {
    //             setGraphic(editButton);
    //         }
    //     }
    // });
}




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

