package ca.mcgill.ecse.coolsupplies.view;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import java.util.Optional;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextInputDialog;

public class GradeManagerView {
  @FXML
  private TableView<TOGrade> grade_pane;
  @FXML
  private TableColumn<TOGrade, String> grade_col;
  @FXML
  private TableColumn<TOGrade, Void> options_col;
  @FXML
  private TextField addGradeNameTextField;
  @FXML
  private Button addGradeNameButton;

  private ObservableList<TOGrade> gradeList = FXCollections.observableArrayList();
  
  @FXML
  public void initialize() {
    grade_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLevel()));

    Callback<TableColumn<TOGrade, Void>, TableCell<TOGrade, Void>> cellFactory = new Callback<>() {
      @Override
      public TableCell<TOGrade, Void> call(final TableColumn<TOGrade, Void> param) {
        final TableCell<TOGrade, Void> cell = new TableCell<>() {
          private final Button deleteButton = new Button("Delete");
          private final Button updateButton = new Button("Update");
          private final HBox buttonBox = new HBox(10, updateButton, deleteButton);

          {
            deleteButton.setOnAction((ActionEvent event) -> {
              TOGrade grade = getTableView().getItems().get(getIndex());
              CoolSuppliesFeatureSet7Controller.deleteGrade(grade.getLevel());
              gradeList.remove(grade);
            });

            updateButton.setOnAction((ActionEvent event) -> {
              TOGrade grade = getTableView().getItems().get(getIndex());
              
              TextInputDialog dialog = new TextInputDialog(grade.getLevel());
              dialog.setTitle("Update Grade");
              dialog.setHeaderText("Update Grade Name");
              dialog.setContentText("Please enter the new grade name:");
              //if we want to change picture: dialog.getDialogPane().setGraphic(new ImageView(new Image("icon.png")));

              Optional<String> result = dialog.showAndWait();
              result.ifPresent(newGrade -> {
                //call controller
                CoolSuppliesFeatureSet7Controller.updateGrade(grade.getLevel(),newGrade);
                //delete old gradeTO
                gradeList.remove(grade);
                //add new gradeTO
                TOGrade newGradeTO = new TOGrade(newGrade);
                gradeList.add(newGradeTO);


                //figure out error handling here


              });              
            });
          }

          @Override
          public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setGraphic(null);
            } else {
              setGraphic(buttonBox);
            }
          }
        };
        return cell;
      }
    };

    // Load options
    options_col.setCellFactory(cellFactory);

    // Load grades from controller
    gradeList.addAll(CoolSuppliesFeatureSet7Controller.getGrades());

    // Set the items for the table
    grade_pane.setItems(gradeList);
    };

  // Event Listener on Button[#addGradeNameButton].onAction
  @FXML
  public void addGradeNameButton(ActionEvent event) {
    String grade = addGradeNameTextField.getText();

    //figure out error handling here


    CoolSuppliesFeatureSet7Controller.addGrade(grade);
    TOGrade newGrade = new TOGrade(grade);
    gradeList.add(newGrade);
    addGradeNameTextField.clear();
  }

}