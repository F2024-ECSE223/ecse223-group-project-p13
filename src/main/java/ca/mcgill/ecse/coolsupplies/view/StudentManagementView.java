package ca.mcgill.ecse.coolsupplies.view;


import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;




public class StudentManagementView {
  @FXML
  private TextField nameInput;

  @FXML
  private ComboBox<String> gradeInputCombo;

  @FXML
  private Button addButton;

  @FXML
  private Button searchButton;

  @FXML 
  private TextField searchStudent;

  @FXML 
  private TableColumn<TOStudent, String> nameColumn;

  @FXML 
  private TableColumn<TOStudent, String> gradeColumn;

  @FXML 
  private TableColumn<TOStudent, Void> actionColumn;
  
  @FXML
  private Label errorLabel;

  @FXML
  public void addStudent(){
    String name = nameInput.getText();
    String gradeLevel = gradeInputCombo.getValue();

    //try catch block ?


    if (name==null||name.isEmpty()||gradeLevel == null){
      errorLabel.setText("the name or grade is not valid.");
      return;
    }

    Grade grade = Grade.getWithLevel(gradeLevel);
    new Student(name, CoolSuppliesApplication.getCoolSupplies(), grade);

    //add the student to a list of students 

    nameInput.clear();
    gradeInputCombo.getSelectionModel().clearSelection();
    errorLabel.setText("");
  }

  public void seachStudent(){
    String name = searchStudent.getText();

    if (name.isEmpty()){
      //show noting ? show all student?
    }
    //find the student in the list of students with the "name"
  }

  public void addActionButtons(){
    actionColumn.setCellFactory(param -> new TableCell<>(){
      private Button deleteButton = new Button("Delete");
      {
        deleteButton.setOnAction(event -> {
          //delete the student from fron end and backend 
          TOStudent student = getTableView().getItems().get(getIndex());
          //remove from UI
          Student particularStudent = Student.getWithName(student.getName());
          particularStudent.delete();
        
        });

      }
    });
  }

}
