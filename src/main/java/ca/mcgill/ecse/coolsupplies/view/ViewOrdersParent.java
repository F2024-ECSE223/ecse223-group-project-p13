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
            final Button penaltyButton = new Button("Pay");
            final Button cancelButton = new Button("Cancel");

            payButton.setOnAction(event -> {
                // open pay stage
            });

            penaltyButton.setOnAction(event -> {
                //open penalty stage
            });

            cancelButton.setOnAction(event -> {
                //cancel order method called here
            });
        }
   }
//    private void addOrder(){
//        CoolSuppliesFxmlView.newWindow("");
//    }



}
