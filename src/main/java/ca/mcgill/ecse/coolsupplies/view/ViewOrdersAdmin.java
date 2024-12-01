package ca.mcgill.ecse.coolsupplies.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
public class ViewOrdersAdmin {
    @FXML
    private ScrollPane ordersScroll;

    @FXML
    private TableView<TOOrder> ordersTable;

    @FXML
    private TableColumn<TOOrder, String> orderNo;
    @FXML
    private TableColumn<TOOrder, String> orderParent;
    @FXML
    private TableColumn<TOOrder, String> dateOrdered;
    @FXML
    private TableColumn<TOOrder, String> orderStatus;
    @FXML
    private TableColumn<String, Void> buttonColumn;

    @FXML
    private Label errorLabel;

    @FXML
    private ObservableList<TOOrder> ordersInSystem = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        orderNo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumber()));
        orderParent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getParentEmail()));
        dateOrdered.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        orderStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        ordersTable.setItems(ordersInSystem);
        addingButtons();
        refresh();
    }

    @FXML
    private void addingButtons(){
        buttonColumn.setCellFactory(col -> new TableCell<>()){
            final Button cancelButton = new Button("Cancel");
            final Button editOrder = new Button("Edit");
            final Button pickUp = new Button("Pick Up");
            {
                cancelButton.setOnAction(event -> {
                    TOOrder myOrder = getTableView().getItems().get(getIndex());
                    String attemptCancel = Iteration3Controller.cancelOrder(myOrder);
                    errorLabel.setText(attemptCancel);

                });

                editOrder.setOnAction(event -> {
                    TOOrder myOrder = getTableView().getItems().get(getIndex());
                    String myStatus = myOrder.getStatus();
                    if(!myStatus.equals("Started")){
                        errorLabel.setText("You cannot edit this order.");
                    }
                    else{
                        CoolSuppliesFxmlView.newWindow("EditBundle.fxml", "Edit a bundle");
                    }

                });

                pickUp.setOnAction(event -> {
                    TOOrder myOrder = getTableView().getItems().get(getIndex());
                    String myStatus = myOrder.getStatus();
                    if(!myStatus.equals("Paid")){
                        errorLabel.setText("You cannot pick up this order.");
                    }
                });
            }
        }

    }



    private void refresh(){
        ordersInSystem.setAll(Iteration3Controller.viewAllOrders());
        ordersTable.setItems(ordersInSystem);
    }
    @FXML
    private void viewOrder(ActionEvent event) {
        try{
            CoolSuppliesFxmlView.newWindow("viewindividualorder.fxml", "View an individual order");
            clearError();
        }
        catch(Exception e){
            setErrorMessage("Cannot open order details:" + e.getMessage());
        }
    }

    private void clearError() {errorLabel.setText("");
    }
    private void setErrorMessage(String message) {
        errorLabel.setText(message);
    }

}
