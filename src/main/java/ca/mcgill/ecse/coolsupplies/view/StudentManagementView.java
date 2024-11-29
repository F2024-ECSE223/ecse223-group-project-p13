package ca.mcgill.ecse.coolsupplies.view;


import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
          errorLabel.setText("Student added successfully.");
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
        private final HBox buttons = new HBox(10, updateButton, deleteButton); // Default buttons

        {
            // Configure delete button
            deleteButton.setOnAction(event -> {
                TOStudent student = getTableView().getItems().get(getIndex());
                deleteStudent(student.getName());
            });

            // Configure update button
            updateButton.setOnAction(event -> {
                TOStudent student = getTableView().getItems().get(getIndex());
                enableEditMode(getIndex());

                // Replace "Update" button with "Save" button
                buttons.getChildren().setAll(saveButton, deleteButton);
                setGraphic(buttons); // Explicitly update the graphic
            });

            // Configure save button
            saveButton.setOnAction(event -> {
                TOStudent student = getTableView().getItems().get(getIndex());

                // Fetch edited values
                String newName = columnName.getCellObservableValue(getIndex()).getValue();
                String newGrade = columnGrade.getCellObservableValue(getIndex()).getValue();

                if (newName == null || newName.trim().isEmpty() || newGrade == null || newGrade.trim().isEmpty()) {
                    errorLabel.setText("Name and grade cannot be empty.");
                    return;
                }

                updateStudent(student.getName(), newName, newGrade);

                // Restore "Update" button after saving
                buttons.getChildren().setAll(updateButton, deleteButton);
                setGraphic(buttons); // Explicitly update the graphic

                // Disable edit mode
                //disableEditMode();
            });

            // Set the default buttons (Update + Delete)
            setGraphic(buttons);
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
  
private void enableEditMode(int rowIndex) {
  editingRowIndex = rowIndex; // Track the row being edited

  tableView.setEditable(true); // Enable editing on the table

  // Allow editing only for the specific row in the "Name" column
  columnName.setCellFactory(param -> new TextFieldTableCell<>() {
      @Override
      public void startEdit() {
          if (getIndex() == editingRowIndex) { // Allow editing only for the specified row
              super.startEdit();
          }
      }

      @Override
      public void updateItem(String item, boolean empty) {
          super.updateItem(item, empty);
          if (getIndex() == editingRowIndex) {
              setEditable(true);
          } else {
              setEditable(false);
          }
      }
  });

  // Allow editing only for the specific row in the "Grade" column
  columnGrade.setCellFactory(param -> new ComboBoxTableCell<>(FXCollections.observableArrayList(getGradeLevels())) {
      @Override
      public void startEdit() {
          if (getIndex() == editingRowIndex) { // Allow editing only for the specified row
              super.startEdit();
          }
      }

      @Override
      public void updateItem(String item, boolean empty) {
          super.updateItem(item, empty);
          if (getIndex() == editingRowIndex) {
              setEditable(true);
          } else {
              setEditable(false);
          }
      }
  });

  // Notify the user about edit mode
  errorLabel.setText("Edit mode enabled for row " + (rowIndex + 1) + ". Click 'Save' to apply changes.");
}


            
    private void updateStudent(String currentName, String newName, String newGrade) {
     
        String result = CoolSuppliesFeatureSet2Controller.updateStudent(currentName, newName, newGrade);
        if (result.isEmpty()) {
            errorLabel.setText("Student updated successfully.");
            refreshTable();
        } else {
            errorLabel.setText(result); // Display error from the controller
        }
    }

    private void deleteStudent(String name) {
        // Call the backend function and display the error or success message
        String result = CoolSuppliesFeatureSet2Controller.deleteStudent(name);
        if (result.isEmpty()) {
            errorLabel.setText("Student deleted successfully.");
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

