package ca.mcgill.ecse.coolsupplies.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.*;
import ca.mcgill.ecse.coolsupplies.controller.*;
import ca.mcgill.ecse.coolsupplies.model.Order;
import ca.mcgill.ecse.coolsupplies.view.*;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import java.util.List;
import atlantafx.base.theme.Styles;
import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Modality;
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

    private  List<TOParent> parentsInSystem= new ArrayList<>();
    private  List<String> parentEmails = new ArrayList<>();
    private ObservableList<TOOrder> ordersInSystem = FXCollections.observableArrayList(Iteration3Controller.viewAllOrders());
    private ObservableList<TOOrder> parentOrders =  FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        orderNo.setCellValueFactory(new PropertyValueFactory<>("number"));
        orderStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        dateOrdered.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        parentsInSystem = CoolSuppliesFeatureSet1Controller.getParents();
    
        for(TOParent parent : parentsInSystem){
            parentEmails.add(parent.getEmail());
        }

        parents.setItems(FXCollections.observableArrayList(parentEmails));

        parents.setOnAction(event -> {
            String selectedEmail = parents.getValue();
            fetchOrders(selectedEmail);
        });

        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button payButton = new Button("Pay");
            private final Button cancelButton = new Button("Cancel");
            private final HBox buttons = new HBox(10, payButton, cancelButton);

          {
            payButton.setOnAction(event -> {
              TOOrder myOrder = getTableView().getItems().get(getIndex());

              String myStatus = myOrder.getStatus();
              if (myStatus.equals("Started")) {
                paymentWindow(myOrder);
              } else if (myStatus.equals("Penalized")) {
                latePaymentWindow(myOrder);
              } else {
                errorLabel.setText("Cannot pay for this order.");
              }
            });

            cancelButton.setOnAction(event -> {
                TOOrder myOrder = getTableView().getItems().get(getIndex());
                String attemptCancel = Iteration3Controller.cancelOrder(myOrder.getNumber());
                errorLabel.setText(attemptCancel);
            });
          }

            @Override
            protected void updateItem(Void item, boolean empty) {
              super.updateItem(item, empty);

              if (empty) {
                setGraphic(null);
              }
              else {
                setGraphic(buttons);
              }
            }
        });


          addOrder.getStyleClass().add(Styles.SUCCESS);
          addOrder.setOnAction((e) -> {
            addNewOrder();
          });
   }

   /*
   * @author Dimitri Christopoulos
   */
  private void paymentWindow(TOOrder pendingOrder) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Label totalCost = new Label(""+pendingOrder.getPrice());
    TextField authCode = new TextField("Authorization Code");
    Button payButton = new Button("Pay");
    Button cancelButton = new Button("Cancel");
    Label errorUpdate = new Label("");
    
    // actions
    authCode.setOnMouseClicked(a -> authCode.setText(""));
    payButton.setOnAction(a -> {

      // textt from labels
      String inputAuthCodeString = authCode.getText();

      try {
        String payMessage = Iteration3Controller.payForOrder(pendingOrder.getNumber(), inputAuthCodeString);
          // Success
          if (payMessage.isEmpty()) {
            errorUpdate.setText(payMessage);
            parentOrders.remove(pendingOrder);
            dialog.close();
          }
          // Error
          else {
            errorUpdate.setText(payMessage);
          }
      } 
      catch (Exception e) {
        errorUpdate.setText(""+e);
      }

    });

    cancelButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10;
    int outerPadding = 100;
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
    Label totalCost = new Label(""+latePendingOrder.getPrice());
    TextField authCode = new TextField("Authorization Code");
    TextField lateAuthCode = new TextField("Late Authorization Code");
    Button payButton = new Button("Pay");
    Button cancelButton = new Button("Cancel");
    Label errorUpdate = new Label("");
    
    // actions
    authCode.setOnMouseClicked(a -> authCode.setText(""));
    payButton.setOnAction(a -> {

      // textt from labels
      String inputAuthCodeString = authCode.getText();
      String inputLateAuthCodeString = lateAuthCode.getText();


      try {
          String payPenaltyMessage = Iteration3Controller.payPenaltyForOrder(latePendingOrder.getNumber(), inputLateAuthCodeString, inputAuthCodeString);
          if (payPenaltyMessage.isEmpty()) {
            errorUpdate.setText(payPenaltyMessage);
            parentOrders.remove(latePendingOrder);
          }
          else {
            errorUpdate.setText(payPenaltyMessage);
          }
      } 
      catch (Exception e) {
        errorUpdate.setText(""+e);
      }

    });

    cancelButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10;
    int outerPadding = 100;
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(totalCost, authCode, errorUpdate, payButton, cancelButton);
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
    new NewOrderView().createAddOrder();
  }
}
