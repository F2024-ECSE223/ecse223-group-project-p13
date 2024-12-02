package ca.mcgill.ecse.coolsupplies.view;

import java.time.LocalDate;
import java.time.ZoneId;
import atlantafx.base.theme.Styles;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet6Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewOrderView {
  private String selectedStudent = "";
  private String orderLevel = "";
  private StringProperty errMsg = new SimpleStringProperty("");

  public void createAddOrder(String parentEmail) {
    Stage dialog = new Stage();
    dialog.setTitle("New Order");

    VBox content = new VBox(16);
    content.setPadding(new Insets(16, 16, 16, 16));

    HBox selection = new HBox(16);
    selection.setAlignment(Pos.CENTER_LEFT);

    Label errMsgText = new Label("");
    errMsgText.getStyleClass().add(Styles.DANGER);

    DatePicker datePicker = new DatePicker();
    datePicker.setPrefWidth(150);
    datePicker.setValue(LocalDate.now());
    datePicker.valueProperty().addListener((obserable, oldValue, newValue) -> {
      if (newValue == null) {
        datePicker.setValue(LocalDate.now());
      }
    });

    selection.getChildren().addAll(selectStudent(parentEmail), datePicker);

    TextField orderID = new TextField("");
    orderID.setPromptText("Order number");


    Button add = new Button("Add");
    add.getStyleClass().add(Styles.SUCCESS);

    add.setOnAction((e) -> {
      try {
        int orderNum = Integer.parseInt(orderID.getText());
        String result =CoolSuppliesFeatureSet6Controller.startOrder(orderNum,
        java.sql.Date.valueOf(datePicker.getValue()), orderLevel, parentEmail,
        selectedStudent);
      
          if (result == null || result.isEmpty()){
            dialog.hide();
          }
          else {
            errMsgText.setText(result);
          }
       
       
        
        
      } catch (NumberFormatException err) {
        errMsgText.setText("Please enter a number");
      }
    });


    content.getChildren().addAll(errMsgText, selection, createOrderLevel(), orderID, add);

    dialog.setScene(new Scene(content));

    dialog.show();
  }

  // MARK: Helper methods
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
