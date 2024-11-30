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
   private Label errorLabel = new Label("null");


   private ObservableList<TOGradeBundle> bundleList = FXCollections.observableArrayList();


   @FXML
   public void initialize() {


       // Set up the table columns
       bundleName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
       bundleGrade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGradeLevel()));


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


   }


   private void addActionButtonsToTable() {
       // columnActions.setCellFactory(param -> new TableCell<>() {
       //     private final Button deleteButton = new Button("Delete");
       //     private final Button updateButton = new Button("Update");
       //     private final Button saveButton = new Button("Save");
       //     private final HBox buttons = new HBox(10, updateButton, deleteButton); // Default
       //                                                                            // buttons
       //     private final HBox saveFunction = new HBox(10, saveButton);


       //     {
       //         deleteButton.setPrefWidth(80);
       //         deleteButton.setPrefHeight(30);
       //         updateButton.setPrefWidth(80);
       //         updateButton.setPrefHeight(30);
       //         saveButton.setPrefWidth(80);
       //         saveButton.setPrefHeight(30);


       //         // Configure delete button
       //         deleteButton.setOnAction(event -> {
       //             TOStudent student = (TOStudent) getTableView().getItems().get(getIndex());
       //             // deleteStudent(student.getName());
       //         });


       //         // Configure update button
       //         updateButton.setOnAction(event -> {
       //             TOStudent student = (TOStudent) getTableView().getItems().get(getIndex());
       //             // enableEditMode(student, getIndex());


       //             // Replace "Update" button with "Save" button
       //             // buttons.getChildren().clear();
       //             // buttons.getChildren().addAll(saveButton, deleteButton);


       //             // Force refresh
       //             // getTableView().refresh();
       //             setGraphic(saveFunction);
       //         });




       //         // Configure save button
       //         saveButton.setOnAction(event -> {
       //             TOStudent student = (TOStudent) getTableView().getItems().get(getIndex());


       //             // Fetch edited values
       //             // String newName = columnName.getCellObservableValue(getIndex()).getValue();
       //             // String newGrade = columnGrade.getCellObservableValue(getIndex()).getValue();


       //             // updateStudent(student.getName(), newName, newGrade);


       //             // Restore "Update" button after saving
       //             buttons.getChildren().clear();
       //             buttons.getChildren().addAll(updateButton, deleteButton);


       //             // Force refresh
       //             getTableView().refresh();
       //             setGraphic(buttons);
       //         });
       //     }


       // });
   }


   private void openBundleDetail(TOGradeBundle bundle) {
       //When
      
       // try {
       //     FXMLLoader loader = new FXMLLoader(getClass().getResource("EditBundle.fxml"));
       //     Parent root = loader.load();


       //     // Pass the bundle to the controller
       //     EditBundleView controller = loader.getController();
       //     controller.initData(bundle);


       //     Stage stage = new Stage();
       //     stage.setScene(new Scene(root));
       //     stage.setTitle("Edit Bundle");
       //     stage.show();


       //     clearError();
       // } catch (IOException e) {
       //     displayError("Failed to open the bundle detail page: " + e.getMessage());
       // }
   }


   // private void openNewBundlePage() {
   //     try {
   //         FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBundle.fxml"));
   //         Parent root = loader.load();
  
   //         Stage stage = new Stage();
   //         stage.setScene(new Scene(root));
   //         stage.setTitle("Add New Bundle");
   //         stage.show();
   //     } catch (IOException e) {
   //         displayError("Failed to open the new bundle page: " + e.getMessage());
   //     }




       // try {
       //     FXMLLoader loader = new FXMLLoader();
       //     loader.setLocation(this.getClass().getResource("AddBundle.fxml"));
       //     loader.setClassLoader(this.getClass().getClassLoader());


       //     var root = (Pane) loader.load();


       //     mainContent.getChildren().add(root);


           // Stage stage = new Stage();
           // stage.setScene(new Scene(root));
           // stage.setTitle("Add New Bundle");
           // stage.show();
       // } catch (IOException e) {
       //     throw new RuntimeException(e); // Let the calling method handle it
       // }
   // }


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

