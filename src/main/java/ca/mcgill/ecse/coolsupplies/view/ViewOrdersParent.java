package ca.mcgill.ecse.coolsupplies.view;

import javafx.fxml.FXML;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.*;
import ca.mcgill.ecse.coolsupplies.controller.*;
import ca.mcgill.ecse.coolsupplies.view.*;
import javafx.scene.control.Label;
public class ViewOrdersParent {
    @FXML
    private ScrollPane ordersScroll;
    @FXML
    private TableView<String> ordersTable;
    @FXML
    private TableColumn<String, String> orderNo;
    @FXML
    private TableColumn<String, String> orderStudent;
    @FXML
    private TableColumn<String, String> dateOrdered;
    @FXML
    private TableColumn<String, String> orderStatus;
    @FXML
    private TableColumn<String, Void> actionColumn;
    @FXML
    private Button addOrder;

    @FXML
    private Label errorLabel;

    TOParent myParent = UpdateParentView.getSelectedParent();

    @FXML
    private void initialize() {
        orderNo.setCellValueFactory(new PropertyValueFactory<>("number"));
        orderStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        dateOrdered.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<TOOrder> ordersInSystem = FXCollections.observableArrayList(Iteration3Controller.viewAllOrders());
        ObservableList<TOOrder> parentOrders = FXCollections.observableArrayList();




        for (TOOrder order : ordersInSystem) {
            //if (order.getParentEmail().isEquals(parentEmail))
                parentOrders.add(order);
        }
        //ordersTable.setItems(parentOrders);

        actionColumn.setCellFactory(col -> new TableCell<>()) {
            final Button payButton = new Button("Pay");
            final Button cancelButton = new Button("Cancel");

            payButton.setOnAction(event -> {
                TOOrder myOrder = getTableView().getItems().get(getIndex());
                String myStatus = myOrder.getStatus();
                if(myStatus.equals("Started")){
                    //CoolSuppliesFxmlView.newWindow("");
                }
                else if (myStatus.equals("Penalized")){
                    //CoolSuppliesFxmlView.newWindow("");
                }
                else{
                    errorLabel.setText("Cannot pay for this order.");
                }

            });


            cancelButton.setOnAction(event -> {
                TOOrder myOrder = getTableView().getItems().get(getIndex());
                String attemptCancel = Iteration3Controller.cancelOrder(myOrder);
                errorLabel.setText(attemptCancel);
            });
        }
   }
//    private void addOrder(){
//        CoolSuppliesFxmlView.newWindow("");
//    }



}
