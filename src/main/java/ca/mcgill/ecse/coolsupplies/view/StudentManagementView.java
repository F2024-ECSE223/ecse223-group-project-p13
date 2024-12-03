package ca.mcgill.ecse.coolsupplies.view;

import java.util.ArrayList;
import java.util.List;
import atlantafx.base.theme.Styles;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet6Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
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
    private TableColumn<TOStudent, String> parent;
   @FXML

  private Label errorLabel;

  private ObservableList<TOStudent> studentList = FXCollections.observableArrayList();
  

  
    /**this method initializes the view by setting the columns and adds the buttons to the table 
     * @author Clara Dupuis
     * @param none
     * @return void
     */
    
  @FXML
  public void initialize(){
    addButton.getStyleClass().add(Styles.SUCCESS);
    gradeInput.setItems(FXCollections.observableArrayList(getGradeLevels()));

    columnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    columnGrade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGradeLevel()));
    parent.setCellValueFactory(cellData -> {
        String parentName = getParentOfStudent(cellData.getValue().getName());
        return new SimpleStringProperty(parentName.isEmpty() ? "" : parentName);
    });

    addActionButtonsToTable();

 
    refreshTable();
}

/**
 * This method gets a list of all of the grades in the student
 * @author Clara Dupuis
 * @return List of strings representing the grades
 */

private List<String> getGradeLevels() {
    List<String> gradeLevels = new ArrayList<>();
    for (TOGrade grade : CoolSuppliesFeatureSet7Controller.getGrades()) {
        gradeLevels.add(grade.getLevel());
    }
    return gradeLevels;
}

/**
 * This methods adds a student with the name and grade level inputted by the userto the list of students when the add button is pressed 
 * @author Clara Dupuis
 * @param AddStudent an OnAction event of the add button 
 */
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

 
  /**
   * This methods adds the update and delete buttons to the table
   * @author Clara Dupuis
   * @parameters: none
   */
  private void addActionButtonsToTable() {
    columnActions.setCellFactory(param -> new TableCell<>() {
        private final Button deleteButton = new Button("Remove");
        private final Button updateButton = new Button("Update");
        private final HBox buttons = new HBox(10, updateButton, deleteButton); // Default buttons

        {
            deleteButton.setPrefWidth(80);
            deleteButton.setPrefHeight(30);
            updateButton.setPrefWidth(80);
            updateButton.setPrefHeight(30);

            // Configure delete button
            deleteButton.setOnAction(event -> {
                TOStudent student = getTableView().getItems().get(getIndex());
                deleteStudent(student.getName());
            });
            deleteButton.getStyleClass().add(Styles.DANGER);

            // Configure update button
            updateButton.setOnAction(event -> {
                TOStudent student = getTableView().getItems().get(getIndex());
                makeUpdateWindow(student);
            });
            updateButton.getStyleClass().add(Styles.ACCENT);
            updateButton.getStyleClass().add(Styles.BUTTON_OUTLINED);
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

/**
 * This method created a window that pops up when the update button is pressed 
 * @author Clara Dupuis
 * @param oldStudent
 */

private void makeUpdateWindow(TOStudent oldStudent) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

   
    
    TextField newName = new TextField(oldStudent.getName());
    ComboBox<String> newGrade = new ComboBox<>();
    Button saveButton = new Button("Save");
    Button cancelButton = new Button("Cancel");
    Label errorUpdate = new Label("");
    
    newGrade.setItems(FXCollections.observableArrayList(getGradeLevels()));

    newGrade.getSelectionModel().select(getGradeLevels().indexOf(oldStudent.getGradeLevel()));
    
    
    newName.setOnMouseClicked(a -> newName.setText(""));
    
    saveButton.setOnAction(a -> {

  
    String newNameString = newName.getText();
    String newGradeLevl = newGrade.getValue();

    
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


/**
 * this method cancels a student and is called when the delete button is pressed
 * @author Clara Dupuis
 * @param: a string name that represents the student that should be deleted
 **/
private void deleteStudent(String name) {
       
    String result = CoolSuppliesFeatureSet2Controller.deleteStudent(name);
    if (result.isEmpty()) {
        errorLabel.setText("");
        refreshTable();
    } else {
        errorLabel.setText(result); 
    }
}

   /**
    * this methods refreshes the table so that it auto refreshes when a student is added, updated or removed
    * @author Clara Dupuis
    *@param none
    */
    private void refreshTable() {
      studentList.setAll(CoolSuppliesFeatureSet2Controller.getStudents());
      tableView.setItems(studentList);
  }


  /**
   * @author Clara Dupuis
   * @param studentName a string representing the name of the student
   * @return the parent's email (string) or an empty string if the parent could not be found 
   */
  private String getParentOfStudent(String studentName){

    List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();

    for (TOParent parent : parents){
        TOStudent associatedStudent = CoolSuppliesFeatureSet6Controller.getStudentOfParent(studentName, parent.getEmail());
        if (associatedStudent != null){
            return parent.getEmail();
        }
    }
    return "";


    }


    


  
}


