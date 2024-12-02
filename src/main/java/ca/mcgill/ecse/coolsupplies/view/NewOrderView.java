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
  private StringProperty parentEmail = new SimpleStringProperty("");
  private String selectedStudent = "";
  private String orderLevel = "";
  private StringProperty errMsg = new SimpleStringProperty("");

  public void createAddOrder() {
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

    selection.getChildren().addAll(selectParent(), selectStudent(), datePicker);

    TextField orderID = new TextField("");
    orderID.setPromptText("Order number");


    Button add = new Button("Add");
    add.getStyleClass().add(Styles.SUCCESS);

    add.setOnAction((e) -> {
      try {
        int orderNum = Integer.parseInt(orderID.getText());
       errMsgText.setText(CoolSuppliesFeatureSet6Controller.startOrder(orderNum,
            java.sql.Date.valueOf(datePicker.getValue()), orderLevel, parentEmail.get(),
            selectedStudent));

        dialog.hide();
      } catch (NumberFormatException err) {
        errMsgText.setText("Please enter a number");
      }
    });


    content.getChildren().addAll(errMsgText, selection, createOrderLevel(), orderID, add);

    dialog.setScene(new Scene(content));

    dialog.show();
  }

  // MARK: Helper methods

  private HBox selectParent() {
    HBox header = new HBox();

    ObservableList<TOParent> parents =
        FXCollections.observableList(CoolSuppliesFeatureSet1Controller.getParents());
    ComboBox<TOParent> box = new ComboBox<>(parents);
    box.setPromptText("Parent");
    box.setCellFactory(lv -> new ListCell<TOParent>() {
      @Override
      protected void updateItem(TOParent parent, boolean empty) {
        super.updateItem(parent, empty);
        setText(empty ? null : parent.getEmail());
      }
    });

    box.setButtonCell(new ListCell<TOParent>() {
      @Override
      protected void updateItem(TOParent parent, boolean empty) {
        super.updateItem(parent, empty);
        setText(empty ? null : parent.getEmail());
      }
    });

    box.setOnAction((e) -> {
      TOParent selected = box.getSelectionModel().getSelectedItem();

      if (selected != null) {
        this.parentEmail.set(selected.getEmail());
      }
    });

    header.getChildren().addAll(box);

    return header;
  }

  private ComboBox<TOStudent> selectStudent() {
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

    parentEmail.addListener((observable, oldValue, newValue) -> {
      if (newValue != null && !newValue.isEmpty()) {
        ObservableList<TOStudent> students = FXCollections.observableArrayList(
            CoolSuppliesFeatureSet6Controller.getStudentsOfParent(newValue));
        studentBox.setItems(students);
      }
    });

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