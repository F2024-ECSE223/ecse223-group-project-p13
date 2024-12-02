package ca.mcgill.ecse.coolsupplies.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class ViewOrdersAdmin {
  @FXML
  private TableView<TOOrder> ordersTable;

  @FXML
  private TableColumn<TOOrder, String> orderNo;
  @FXML
  private TableColumn<TOOrder, String> orderParent;
  @FXML
  private TableColumn<TOOrder, String> orderStudent;
  @FXML
  private TableColumn<TOOrder, String> dateOrdered;
  @FXML
  private TableColumn<TOOrder, String> orderStatus;
  @FXML
  private TableColumn<TOOrder, Void> actionColumn;

  @FXML
  private Label errorLabel;

  private static TOOrder selectedOrder = null;

  @FXML
  private void initialize() {
    orderNo.setCellValueFactory(new PropertyValueFactory<>("number"));
    orderParent.setCellValueFactory(new PropertyValueFactory<>("parentEmail"));
    dateOrdered.setCellValueFactory(new PropertyValueFactory<>("date"));
    orderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    orderStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));

    fetchOrders();

    actionColumn.setCellFactory(param -> new TableCell<>() {


      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        Button cancelButton = new Button("Cancel");
        Button viewButton = new Button("View");
        HBox buttons = new HBox(10, viewButton, cancelButton);

        cancelButton.setOnAction(event -> {
          TOOrder myOrder = getTableView().getItems().get(getIndex());
          String attemptCancel = Iteration3Controller.cancelOrder(myOrder.getNumber());
          errorLabel.setText(attemptCancel);
          fetchOrders();
        });

        viewButton.setOnAction(event -> {
          ViewOrdersAdmin.selectedOrder = getTableView().getItems().get(getIndex());

          CoolSuppliesFxmlView.newWindow("AdminViewIndividualOrder.fxml", "Order");
        });


        if (empty) {
          setGraphic(null);
        } else {
          setGraphic(buttons);
        }
      }
    });
  }

  private void fetchOrders() {
    ordersTable.setItems(FXCollections.observableArrayList(Iteration3Controller.viewAllOrders()));
  }

  public static TOOrder getOrder() {
    return selectedOrder;
  }
}
