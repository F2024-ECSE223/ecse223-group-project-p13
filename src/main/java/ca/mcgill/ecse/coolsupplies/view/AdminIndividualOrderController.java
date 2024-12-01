package ca.mcgill.ecse.coolsupplies.view;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import ca.mcgill.ecse.coolsupplies.controller.Iteration3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.controller.TOOrderItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.beans.property.SimpleStringProperty;

public class AdminIndividualOrderController {
  @FXML
  private TableView<TOOrder> first_row;
  @FXML
  private TableView<TOOrder> second_row;
  @FXML
  private TableView<TOOrder> third_row;
  @FXML
  private TableColumn<TOOrder,String> date_col;
  private ObservableList<TOOrder> dateList = FXCollections.observableArrayList();
  @FXML
  private TableColumn<TOOrder,String> level_col;
  @FXML
  private TableColumn<TOOrder,String> parent_col;
  @FXML
  private TableColumn<TOOrder,String> student_col;
  @FXML
  private TableColumn<TOOrder,String> status_col;
  @FXML
  private TableColumn<TOOrder,String> auth_col;
  @FXML
  private TableColumn<TOOrder,String> penalty_col;
  @FXML
  private TableColumn<TOOrder,String> order_col;
  @FXML
  private TableColumn<TOOrderItem,String> name_col;
  private ObservableList<TOOrderItem> itemList = FXCollections.observableArrayList();
  @FXML
  private TableColumn<TOOrderItem,String> price_col;
  @FXML
  private TableColumn<TOOrderItem,String> bundleName_col;
  @FXML
  private TableColumn<TOOrder,String> discount_col;
  @FXML
  private TableColumn<TOOrder,String> bundlePrice_col;
  @FXML
  private TableColumn<TOOrder,String> total_col;


  @FXML
  //public void initialize(String orderNumber) {
  public void initialize() {
    TOOrder order = Iteration3Controller.viewAllOrders().get(1);

    date_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
    level_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLevel()));
    parent_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getParentEmail()));
    student_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudentName()));
    status_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
    auth_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthCode()));
    penalty_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPenaltyAuthCode()));
    order_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumber()));
    name_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    price_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice()));
    bundleName_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGradeBundle()));
    //discount_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get));
    //bundlePrice_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
    total_col.setCellValueFactory(cellData -> new SimpleStringProperty("$"+String.valueOf(cellData.getValue().getPrice())));

    // Load order info from controller
    dateList.add(Iteration3Controller.viewOrder(order.getNumber()));
    itemList.addAll(Iteration3Controller.viewOrder(order.getNumber()).getItems());
    

    // Set the items for the table
    first_row.setItems(dateList);
    second_row.setItems(dateList);
    third_row.setItems(dateList);
  }
}