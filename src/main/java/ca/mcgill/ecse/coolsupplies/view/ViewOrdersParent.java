package ca.mcgill.ecse.coolsupplies.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import atlantafx.base.theme.Styles;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet6Controller;
import ca.mcgill.ecse.coolsupplies.controller.Iteration3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewOrdersParent {
  @FXML
  private ScrollPane ordersScroll;
  @FXML
  private TableView<TOOrder> ordersTable;
  @FXML
  private TableColumn<TOOrder, String> orderNo;
  @FXML
  private TableColumn<TOOrder, String> orderStudent;
  @FXML
  private TableColumn<TOOrder, String> dateOrdered;
  @FXML
  private TableColumn<TOOrder, String> orderStatus;
  @FXML
  private TableColumn<TOOrder, Void> actionColumn;
  @FXML
  private Button addOrder;

  @FXML
  private Label errorLabel;

  @FXML
  private ComboBox<String> parents;

  private List<TOParent> parentsInSystem = new ArrayList<>();
  private List<String> parentEmails = new ArrayList<>();
  private ObservableList<TOOrder> ordersInSystem = FXCollections.observableArrayList(Iteration3Controller.viewAllOrders());
  private ObservableList<TOOrder> parentOrders = FXCollections.observableArrayList();
  private static TOOrder order = null;
  private final Map<String, SimpleStringProperty> statusMap = new HashMap<>();
  private final Map<String, SimpleBooleanProperty> orderChanged = new HashMap<>();

  @FXML
  private void initialize() {
    orderNo.setCellValueFactory(new PropertyValueFactory<>("number"));
    orderStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
    dateOrdered.setCellValueFactory(new PropertyValueFactory<>("date"));
    orderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    parentsInSystem = CoolSuppliesFeatureSet1Controller.getParents();

    for (TOParent parent : parentsInSystem) {
      parentEmails.add(parent.getEmail());
    }

    parents.setItems(FXCollections.observableArrayList(parentEmails));

    parents.setOnAction(event -> {
      String selectedEmail = parents.getValue();
      addOrder.setVisible(true);
      fetchOrders(selectedEmail);
    });

    actionColumn.setCellFactory(param -> new TableCell<>() {
      private final Button payButton = new Button("Pay");
      private final Button cancelButton = new Button("Cancel");
      private final Button viewButton = new Button("View");

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        HBox buttons = new HBox(10, viewButton, payButton, cancelButton);
        buttons.setPadding(new Insets(4, 0, 4, 0));

        if (getIndex() >= 0 && getIndex() < getTableView().getItems().size()) {
          TOOrder order = getTableView().getItems().get(getIndex());
          final SimpleStringProperty status = (statusMap.get(order.getNumber()) != null) ? statusMap.get(order.getNumber()) : new SimpleStringProperty(order.getStatus());
          final SimpleBooleanProperty changed = (orderChanged.get(order.getNumber()) != null) ? orderChanged.get(order.getNumber()) : new SimpleBooleanProperty(false);
          statusMap.put(order.getNumber(), status);
          orderChanged.put(order.getNumber(), changed);
          status.addListener((observable, oldValue, newValue) -> {
            if (!newValue.equalsIgnoreCase("started") && !newValue.equalsIgnoreCase("penalized") || order.getItems().isEmpty()) {
              payButton.setVisible(false);
            }
            if (!newValue.equalsIgnoreCase("started") && !newValue.equalsIgnoreCase("paid")) {
              cancelButton.setVisible(false);
            }
          });
         
          viewButton.getStyleClass().add(Styles.ACCENT);
          viewButton.getStyleClass().add(Styles.BUTTON_OUTLINED);
          payButton.getStyleClass().add(Styles.ACCENT);
          cancelButton.getStyleClass().add(Styles.DANGER);
          payButton.setVisible((order.getStatus().equalsIgnoreCase("started") || order.getStatus().equalsIgnoreCase("penalized")) && order.hasItems());
          cancelButton.setVisible(order.getStatus().equalsIgnoreCase("started")
              || order.getStatus().equalsIgnoreCase("paid"));

          viewButton.setOnAction(event -> {
            ViewOrdersParent.order = getTableView().getItems().get(getIndex());
            CoolSuppliesFxmlView.newWindow("ParentViewIndividualOrder.fxml", "Order");
          });

          cancelButton.setOnAction(event -> {
            TOOrder myOrder = getTableView().getItems().get(getIndex());
            String attemptCancel = Iteration3Controller.cancelOrder(myOrder.getNumber());
            errorLabel.setText(attemptCancel);
            fetchOrders(parents.getSelectionModel().getSelectedItem());
          });

          payButton.setOnAction(event -> {
            TOOrder myOrder = getTableView().getItems().get(getIndex());

            String myStatus = myOrder.getStatus();
            if (myStatus.equals("Started")) {
              paymentWindow(myOrder);
            } else if (myStatus.equals("Penalized")) {
              latePaymentWindow(myOrder);
            } else {
              errorLabel.setText("Cannot pay for this order");
            }
          });
          changed.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
              status.set(order.getNumber());
            }
          });
        }

        if (empty) {
          setGraphic(null);
        } else {
          setGraphic(buttons);
        }


      }
    });

    addOrder.getStyleClass().add(Styles.SUCCESS);
    addOrder.setOnAction((e) -> {
      addNewOrder();
    });
    addOrder.setVisible(false);
}


  /**
   * @author Dimitri Christopoulos
   */
  private void paymentWindow(TOOrder pendingOrder) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Label totalCost = new Label("Total: $" + pendingOrder.getPrice());
    TextField authCode = new TextField("Authorization Code");
    Button payButton = new Button("Pay");
    Button cancelButton = new Button("Cancel");
    Label errorUpdate = new Label("");
    errorUpdate.setTextFill(Color.RED);

    // actions
    authCode.setOnMouseClicked(a -> authCode.setText(""));
    payButton.setOnAction(a -> {

      // textt from labels
      String inputAuthCodeString = authCode.getText();

      try {
        String payMessage =
            Iteration3Controller.payForOrder(pendingOrder.getNumber(), inputAuthCodeString);
        // Success
        if (payMessage.isEmpty()) {
          errorUpdate.setText(payMessage);
          parentOrders.remove(pendingOrder);
          TOOrder paidOrder = Iteration3Controller.viewOrder(pendingOrder.getNumber());
          parentOrders.add(paidOrder);
          orderChanged.get(paidOrder.getNumber()).set(true);
          statusMap.get(paidOrder.getNumber()).set("paid");

          dialog.close();
        }
        // Error
        else {
          errorUpdate.setText(payMessage);
        }
      } catch (Exception e) {
        errorUpdate.setText("" + e);
      }

    });

    cancelButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10;
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(totalCost, authCode, errorUpdate, payButton, cancelButton);
    Scene dialogScene = new Scene(dialogPane);
    dialog.setScene(dialogScene);
    dialog.setTitle("Pay Order");
    dialog.show();
  }

  /*
   * @author Dimitri Christopoulos
   */
  private void latePaymentWindow(TOOrder latePendingOrder) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Label totalCost = new Label("Total: $" + latePendingOrder.getPrice());
    TextField authCode = new TextField("Authorization Code");
    TextField lateAuthCode = new TextField("Late Authorization Code");
    Button payButton = new Button("Pay");
    Button cancelButton = new Button("Cancel");
    Label errorUpdate = new Label("");
    errorUpdate.setTextFill(Color.RED);

    // actions
    authCode.setOnMouseClicked(a -> authCode.setText(""));
    lateAuthCode.setOnMouseClicked(a -> lateAuthCode.setText(""));
    payButton.setOnAction(a -> {

      // textt from labels
      String inputAuthCodeString = authCode.getText();
      String inputLateAuthCodeString = lateAuthCode.getText();


      try {
        String payPenaltyMessage = Iteration3Controller.payPenaltyForOrder(
            latePendingOrder.getNumber(), inputLateAuthCodeString, inputAuthCodeString);
        if (payPenaltyMessage.isEmpty()) {
          errorUpdate.setText(payPenaltyMessage);
          parentOrders.remove(latePendingOrder);
          TOOrder paidOrder = Iteration3Controller.viewOrder(latePendingOrder.getNumber());
          parentOrders.add(paidOrder);
          dialog.close();
        } else {
          errorUpdate.setText(payPenaltyMessage);
        }
      } catch (Exception e) {
        errorUpdate.setText("" + e);
      }

    });

    cancelButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10;
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(totalCost, authCode, lateAuthCode, errorUpdate, payButton,
        cancelButton);
    Scene dialogScene = new Scene(dialogPane);
    dialog.setScene(dialogScene);
    dialog.setTitle("Pay Order");
    dialog.show();
  }

  public void fetchOrders(String email) {
    ordersInSystem = FXCollections.observableArrayList(Iteration3Controller.viewAllOrders());
    parentOrders.clear();

    for (TOOrder order : ordersInSystem) {
      if (order.getParentEmail().equals(email)) {
        parentOrders.add(order);
      }
    }

    ordersTable.setItems(parentOrders);
  }

  private void addNewOrder() {
    Stage dialog = new Stage();
    dialog.setTitle("New Order");

    NewOrderView view = new NewOrderView();

    view.add.setOnAction(e -> {
      try {
        int orderNum = Integer.parseInt(view.orderID.getText());
        String result = CoolSuppliesFeatureSet6Controller.startOrder(orderNum,
            java.sql.Date.valueOf(view.datePicker.getValue()), view.orderLevel,
            parents.getValue(), view.selectedStudent);

        if (result == null || result.isEmpty()) {
          fetchOrders(parents.getValue());
          dialog.hide();
        } else {
          view.errMsgText.setText(result);
        }
      } catch (NumberFormatException err) {
        view.errMsgText.setText("Please enter a number");
      }
    });

    VBox content = view.createAddOrder(parents.getValue());

    dialog.setScene(new Scene(content));

    dialog.show();

  }

  public static TOOrder getOrder() {
    return order;
  }
}
