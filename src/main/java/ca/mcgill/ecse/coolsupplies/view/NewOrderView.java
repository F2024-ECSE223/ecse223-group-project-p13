package ca.mcgill.ecse.coolsupplies.view;

import java.time.LocalDate;
import atlantafx.base.theme.Styles;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet6Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewOrderView {
  public String selectedStudent = "";
  public String orderLevel = "";
  public StringProperty errMsg = new SimpleStringProperty("");

  public Button add = new Button("Add");
  public DatePicker datePicker = new DatePicker();
  public TextField orderID = new TextField("");
  public Label errMsgText = new Label("");

  /**
   * @author Trevor Piltch
   * @param parentEmail - The parent for which we are adding an order
   * @return The view to create a new order as a VBox
   */
  public VBox createAddOrder(String parentEmail) {
    VBox content = new VBox(16);
    content.setPadding(new Insets(16, 16, 16, 16));

    HBox selection = new HBox(16);
    selection.setAlignment(Pos.CENTER_LEFT);

    errMsgText.getStyleClass().add(Styles.DANGER);

    datePicker.setPrefWidth(150);
    datePicker.setValue(LocalDate.now());
    datePicker.valueProperty().addListener((obserable, oldValue, newValue) -> {
      if (newValue == null) {
        datePicker.setValue(LocalDate.now());
      }
    });

    selection.getChildren().addAll(selectStudent(parentEmail), datePicker);

    orderID.setPromptText("Order number");

    add.getStyleClass().add(Styles.SUCCESS);

    content.getChildren().addAll(errMsgText, selection, createOrderLevel(), orderID, add);

    return content;
  }

  // MARK: Helper methods
  /**
   * @author Trevor Piltch
   * @param parentEmail - The parent from which we are selecting the student
   * @return A drop down menu of the students for the given parent
   */
  private ComboBox<TOStudent> selectStudent(String parentEmail) {
    ComboBox<TOStudent> studentBox = new ComboBox<>();
    studentBox.setPromptText("Student");
    studentBox.setCellFactory(lv -> new ListCell<TOStudent>() {
      @Override 
      protected void updateItem(TOStudent student, boolean empty) {
        super.updateItem(student, empty);
        setText(empty ? null : student.getName());
      }
    });

    studentBox.setButtonCell(new ListCell<TOStudent>() {
      @Override 
      protected void updateItem(TOStudent student, boolean empty) {
        super.updateItem(student, empty);
        setText(empty ? null : student.getName());
      }
    });

    studentBox.setOnAction((e) -> {
      TOStudent selected = studentBox.getSelectionModel().getSelectedItem();

      if (selected != null) {
        this.selectedStudent = selected.getName();
      }
    });

      ObservableList<TOStudent> students = FXCollections.observableArrayList(
            CoolSuppliesFeatureSet6Controller.getStudentsOfParent(parentEmail));
        studentBox.setItems(students);

    return studentBox;
  }

  /**
   * @author Trevor Piltch
   * @return The three levels as buttons in an HBox
   * @brief Creates three radio buttons for the order levels in an HBox
   */
  private HBox createOrderLevel() {
    HBox hbox = new HBox(16);
    hbox.setAlignment(Pos.CENTER_LEFT);

    ToggleGroup group = new ToggleGroup();

    RadioButton mandatory = new RadioButton("Mandatory");
    mandatory.setToggleGroup(group);
    mandatory.setUserData("Mandatory");

    RadioButton recommended = new RadioButton("Recommended");
    recommended.setToggleGroup(group);
    recommended.setUserData("Recommended");

    RadioButton optional = new RadioButton("Optional");
    optional.setToggleGroup(group);
    optional.setUserData("Optional");

    hbox.getChildren().addAll(mandatory, recommended, optional);

    group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        this.orderLevel = newValue.getUserData().toString();
      }
    });

    return hbox;
  } 
}
