package ca.mcgill.ecse.coolsupplies.view;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import ca.mcgill.ecse.coolsupplies.controller.TOItem;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class StudentManagementView {
  @FXML
  private TextField nameInput;

  @FXML
  private ComboBox<String> gradeInput;

  @FXML
  private Button addButton;

  @FXML
  private TableView<TOStudent> tableView;

  @FXML 
  private TableColumn<TOStudent, String> columnName;

  @FXML 
  private TableColumn<TOStudent, String> columnGrade;

  @FXML 
  private TableColumn<TOStudent, Void> columnActions;
  
   @FXML
  private Label errorLabel;

  private ObservableList<TOStudent> studentList = FXCollections.observableArrayList();
  private int editingRowIndex = -1;

  @FXML
  public void initialize(){
     gradeInput.setItems(FXCollections.observableArrayList(getGradeLevels()));

    // Configure table columns
    columnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    columnGrade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGradeLevel()));

    // Add action buttons to the table
    addActionButtonsToTable();

    // Load initial data into the table
    refreshTable();
}

private List<String> getGradeLevels() {
    List<String> gradeLevels = new ArrayList<>();
    for (TOGrade grade : CoolSuppliesFeatureSet7Controller.getGrades()) {
        gradeLevels.add(grade.getLevel());
    }
    return gradeLevels;
}


  @FXML
  private void AddStudent(ActionEvent AddStudent){
      String name = nameInput.getText();
      String grade = gradeInput.getValue();
      String result = CoolSuppliesFeatureSet2Controller.addStudent(name, grade);
      if (result.isEmpty()){
          errorLabel.setText("");
          refreshTable();
          nameInput.clear();
          gradeInput.getSelectionModel().clearSelection();
      } else {
          errorLabel.setText(result);
      }
  }

    
  private void addActionButtonsToTable() {
    columnActions.setCellFactory(param -> new TableCell<>() {
        private final Button deleteButton = new Button("Delete");
        private final Button updateButton = new Button("Update");
        private final Button saveButton = new Button("Save");
        private final TextField newNameInput = new TextField("");
        private final ComboBox<String> newGradeInput = new ComboBox<>();
        private final HBox buttons = new HBox(10, updateButton, deleteButton); // Default buttons
        private final HBox saveFunction = new HBox(10, saveButton);

        {
            deleteButton.setPrefWidth(80);
            deleteButton.setPrefHeight(30);
            updateButton.setPrefWidth(80);
            updateButton.setPrefHeight(30);
            saveButton.setPrefWidth(80);
            saveButton.setPrefHeight(30);

            // Configure delete button
            deleteButton.setOnAction(event -> {
                TOStudent student = getTableView().getItems().get(getIndex());
                deleteStudent(student.getName());
            });

            // Configure update button
            updateButton.setOnAction(event -> {
                TOStudent student = getTableView().getItems().get(getIndex());
                makeUpdateWindow(student);
            });

            // Configure save button
            saveButton.setOnAction(event -> {
                TOStudent TOstudent = getTableView().getItems().get(getIndex());
            
                newGradeInput.setItems(FXCollections.observableArrayList(getGradeLevels()));

                String NewName = newNameInput.getText();
                String NewGrade = newGradeInput.getValue();
                String result = CoolSuppliesFeatureSet2Controller.updateStudent(TOstudent.getName(), NewName, NewGrade);
                if (result.isEmpty()){
                    errorLabel.setText("");
                    refreshTable();
                    nameInput.clear();
                    gradeInput.getSelectionModel().clearSelection();
                } else {
                    errorLabel.setText(result);
                }
            

                // Exit edit mode
                editingRowIndex = -1;

                // Refresh the table to update the graphics
                getTableView().refresh();
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(buttons);
            }
        }
      
    });
}

private void makeUpdateWindow(TOStudent oldStudent) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    TextField newName = new TextField("New name");
    ComboBox<String> newGrade = new ComboBox<>();
    Button saveButton = new Button("Save");
    Button cancelButton = new Button("Cancel");
    Label errorUpdate = new Label("");
    
    newGrade.setItems(FXCollections.observableArrayList(getGradeLevels()));
    // actions
    newName.setOnMouseClicked(a -> newName.setText(""));
    
    saveButton.setOnAction(a -> {

    // textt from labels
    String newNameString = newName.getText();
    String newGradeLevl = newGrade.getValue();

    // add updated name as new, remove old
    
    String result = CoolSuppliesFeatureSet2Controller.updateStudent(oldStudent.getName(),newNameString, newGradeLevl);
    try {
      if (result.isEmpty()) {
        errorUpdate.setText("");
        TOStudent new_student = new TOStudent(newNameString, newGradeLevl);
        studentList.add(new_student);
        studentList.remove(oldStudent);
        oldStudent.delete();
        dialog.close();
      }
      else {
        errorUpdate.setText(result);
      }
    } catch (Exception e) {
      errorUpdate.setText(""+e);
    }
    
    
    });
    
    cancelButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10;
    
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(newName, newGrade, errorUpdate, saveButton, cancelButton);
    Scene dialogScene = new Scene(dialogPane);
    dialog.setScene(dialogScene);
    dialog.setTitle("Update Student");
    dialog.show();
  }





    private void deleteStudent(String name) {
        // Call the backend function and display the error or success message
        String result = CoolSuppliesFeatureSet2Controller.deleteStudent(name);
        if (result.isEmpty()) {
            errorLabel.setText("");
            refreshTable();
        } else {
            errorLabel.setText(result); // Use the error message from the backend
        }
    }

   
    private void refreshTable() {
      studentList.setAll(CoolSuppliesFeatureSet2Controller.getStudents());
      tableView.setItems(studentList);
  }
}
