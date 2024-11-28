package ca.mcgill.ecse.coolsupplies.view;


import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;




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

//   private ObservableList<TOStudent> studentList = FXCollections.observableArrayList();

//   @FXML
//   public void initialize(){
//      gradeInputCombo.setItems(FXCollections.observableArrayList(getGradeLevels()));

//     // Configure table columns
//     nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
//     gradeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGradeLevel()));

//     // Add action buttons to the table
//     addActionButtonsToTable();

//     // Load initial data into the table
//     refreshTable();
// }

// private List<String> getGradeLevels() {
//     List<String> gradeLevels = new ArrayList<>();
//     for (TOGrade grade : CoolSuppliesFeatureSet7Controller.getGrades()) {
//         gradeLevels.add(grade.getLevel());
//     }
//     return gradeLevels;
// }


  
  
//     @FXML
//     private void AddStudent(ActionEvent event){
//         String name = nameInput.getText();
//         String grade = gradeInputCombo.getValue();
//         String result = CoolSuppliesFeatureSet2Controller.addStudent(name, grade);
//         if (result.isEmpty()){
//             errorLabel.setText("Student added successfully.");
//             refreshTable();
//             nameInput.clear();
//             gradeInputCombo.getSelectionModel().clearSelection();
//         } else {
//             errorLabel.setText(result);
//         }
//     }

    
//     private void addActionButtonsToTable() {
        

//         actionColumn.setCellFactory(param -> new TableCell<>() {
//             private final Button deleteButton = new Button("Delete");
//             private final Button updateButton = new Button("Update");

//             {
//                 deleteButton.setOnAction(event -> {
//                     TOStudent student = getTableView().getItems().get(getIndex());
//                     deleteStudent(student.getName());
//                 });

//                 updateButton.setOnAction(event -> {
//                     TOStudent student = getTableView().getItems().get(getIndex());
//                     enableEditMode(student, getIndex());
//                 });

//                 HBox buttons = new HBox(5, updateButton, deleteButton);
//                 setGraphic(buttons);
//             }

//             @Override
//             protected void updateItem(Void item, boolean empty) {
//                 super.updateItem(item, empty);
//                 if (empty) {
//                     setGraphic(null);
//                 }
//             }
//         });

//     }

//     private void enableEditMode(TOStudent student, int rowIndex) {
//         nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//         gradeColumn.setCellFactory(param -> new ComboBoxTableCell<>(FXCollections.observableArrayList(getGradeLevels())));
//         actionColumn.setCellFactory(param -> new TableCell<>() {
//             private final Button saveButton = new Button("Save");

//             {
//                 saveButton.setOnAction(event -> {
//                     String newName = nameColumn.getCellData(rowIndex);
//                     String newGrade = gradeColumn.getCellData(rowIndex);
//                     updateStudent(student.getName(), newName, newGrade);
//                 });

//                 setGraphic(saveButton);
//             }

//             @Override
//             protected void updateItem(Void item, boolean empty) {
//                 super.updateItem(item, empty);
//                 if (empty) {
//                     setGraphic(null);
//                 }
//             }
//         });
//     }

//     private void updateStudent(String currentName, String newName, String newGrade) {
//         if (newName.isEmpty() || newGrade.isEmpty()) {
//             errorLabel.setText("Please fill in all fields to update.");
//             return;
//         }

//         String result = CoolSuppliesFeatureSet2Controller.updateStudent(currentName, newName, newGrade);
//         if (result.isEmpty()) {
//             errorLabel.setText("Student updated successfully.");
//             refreshTable();
//         } else {
//             errorLabel.setText(result); // Display error from the controller
//         }
//     }

//     private void deleteStudent(String name) {
//         // Call the backend function and display the error or success message
//         String result = CoolSuppliesFeatureSet2Controller.deleteStudent(name);
//         if (result.isEmpty()) {
//             errorLabel.setText("Student deleted successfully.");
//             refreshTable();
//         } else {
//             errorLabel.setText(result); // Use the error message from the backend
//         }
//     }


//     private void refreshTable() {
//         // Refresh the table by fetching the latest data from the backend
//         studentList.setAll(CoolSuppliesFeatureSet2Controller.getStudents());
//         TableView.setItems(studentList);
//     }
// }
}

