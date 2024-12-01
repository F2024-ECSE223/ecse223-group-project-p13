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
import javafx.scene.control.ComboBox;
import java.util.List;
import java.util.ArrayList;

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
    private TableColumn<String, Void> actionColumn;
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
        parents.getItems().addAll(parentEmails);

        parents.setOnAction(event -> {
            String selectedEmail = parents.getValue();
            fetchOrders(selectedEmail);
        });


        actionColumn.setCellFactory(col -> new TableCell<>() {
            Button payButton = new Button("Pay");
            final Button cancelButton = new Button("Cancel");

            payButton.setOnAction((e) -> {
                TOOrder myOrder = ordersTable.getSelectionModel().getSelectedItem();
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
        });
   }

   private void fetchOrders(String email){
        for(TOOrder order : ordersInSystem){
            if(order.getParentEmail().equals(email)){
                parentOrders.add(order);
            }
        }
        ordersTable.setAll(parentOrders);
   }

}
