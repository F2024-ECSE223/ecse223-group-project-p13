package ca.mcgill.ecse.coolsupplies.view;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet6Controller;
import ca.mcgill.ecse.coolsupplies.controller.Iteration3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class OrderView {
  private StackPane mainContent;
  private ObservableList<TOOrder> orders;
  private StringProperty parentEmail = new SimpleStringProperty("");
  private String selectedStudent = "";
  private String orderLevel = "";

  public OrderView(StackPane mainContent) {
    this.mainContent = mainContent;
    VBox view = new VBox();
    view.setPadding(new Insets(16, 16, 16, 16));

    ListView<TOOrder> orderList = createOrderList();

    VBox newOrder = createAddOrder();

    Text title = new Text("Order History");
    title.setFont(CoolSuppliesFxmlView.title2);

    Text title2 = new Text("Add Order");
    title2.setFont(CoolSuppliesFxmlView.title2);

    view.getChildren().addAll(title, orderList, title2, newOrder);
    mainContent.getChildren().add(view);
  }

  private VBox createAddOrder() {
    VBox content = new VBox(16);

    HBox selection = new HBox(16);
    selection.setAlignment(Pos.CENTER_LEFT);
    Text label1 = new Text("Parent: ");
    Text label2 = new Text("Student: ");

    selection.getChildren().addAll(label1, selectParent(), label2, selectStudent(), createOrderLevel());

    TextField orderID = new TextField("");
    orderID.setPromptText("Order number");

    content.getChildren().addAll(selection, orderID);

    return content;
  }

  private ListView<TOOrder> createOrderList() {
    orders = FXCollections.observableList(Iteration3Controller.viewAllOrders());

    ListView<TOOrder> listView = new ListView<>(orders);

    listView.setCellFactory(l -> new ListCell<TOOrder>() {
      @Override
      protected void updateItem(TOOrder order, boolean empty) {
        super.updateItem(order, empty);

        if (empty || order == null) {
          setText(null);
        } else {
          HBox cell = new HBox();
          cell.setAlignment(Pos.CENTER_LEFT);
          Button cancelOrder = new Button("Cancel");
          cancelOrder.setOnAction(e -> {
            // TODO: Add cancel workflow
          });

          Button updateOrder = new Button("Update");
          updateOrder.setOnAction(e -> {
            // TODO: Add update workflow
          });

          Text label = new Text(order.getNumber());

          Region spacer = new Region();
          HBox.setHgrow(spacer, Priority.ALWAYS);

          cell.getChildren().addAll(label, spacer, cancelOrder, updateOrder);

          setGraphic(cell);
        }
      }
    });


    return listView;
  }

  // MARK: Helper methods

  private HBox selectParent() {
    HBox header = new HBox();

    ObservableList<TOParent> parents =
        FXCollections.observableList(CoolSuppliesFeatureSet1Controller.getParents());
    ComboBox<TOParent> box = new ComboBox<>(parents);
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
    optional.setUserData("optional");

    hbox.getChildren().addAll(mandatory, recommended, optional);

    group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        this.orderLevel = newValue.toString();
      }
    });

    return hbox;
  } 
}
