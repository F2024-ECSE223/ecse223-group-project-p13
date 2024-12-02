package ca.mcgill.ecse.coolsupplies.view;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import ca.mcgill.ecse.coolsupplies.controller.Iteration3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOBundleItem;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet5Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.controller.TOOrderItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class ParentIndividualOrderController {
  @FXML
  private TableView<TOOrder> first_row;
  @FXML
  private TableView<TOOrder> second_row;
  @FXML
  private TableView<TOOrderItem> item_table;
  @FXML
  private TableView<TOBundleItem> bundle_table;
  @FXML
  private TableColumn<TOOrder, String> date_col;
  private ObservableList<TOOrder> dateList = FXCollections.observableArrayList();
  @FXML
  private TableColumn<TOOrder, String> level_col;
  @FXML
  private TableColumn<TOOrder, String> parent_col;
  @FXML
  private TableColumn<TOOrder, String> student_col;
  @FXML
  private TableColumn<TOOrder, String> status_col;
  @FXML
  private TableColumn<TOOrder, String> auth_col;
  @FXML
  private TableColumn<TOOrder, String> penalty_col;
  @FXML
  private Label orderTitle;
  @FXML
  private TableColumn<TOOrderItem, String> name_col;
  private ObservableList<TOOrderItem> individualItemList = FXCollections.observableArrayList();
  @FXML
  private TableColumn<TOOrderItem, String> price_col;
  @FXML
  private Label bundleTitle;
  @FXML
  private TableColumn<TOBundleItem, String> bundleName_col;
  private ObservableList<TOBundleItem> bundleItemList = FXCollections.observableArrayList();
  @FXML
  private TableColumn<TOBundleItem, String> bundleQuantity_col;
  @FXML
  private TableColumn<TOBundleItem, String> bundleLevel_col;
  @FXML
  private TableColumn<TOOrder, String> total_col;
  private Button editOrder;
  private boolean bundleWasSet = false;


  @FXML
  //public void initialize(String orderNumber) {
  public void initialize() {
    TOOrder order = ViewOrdersParent.getOrder();
   

    date_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
    level_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLevel()));
    parent_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getParentEmail()));
    student_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudentName()));
    status_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
    auth_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthCode()));
    penalty_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPenaltyAuthCode()));
    orderTitle.setText("Order Number: "+order.getNumber());
    
    name_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    price_col.setCellValueFactory(cellData -> new SimpleStringProperty("$"+String.valueOf(cellData.getValue().getPrice())));

    bundleName_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemName()));
    bundleQuantity_col.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
    bundleLevel_col.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getLevel())));
    
    total_col.setCellValueFactory(cellData -> new SimpleStringProperty("$"+String.valueOf(cellData.getValue().getPrice())));

    // Load order info from controller
    dateList.add(order);
    
    for (TOOrderItem orderitem : order.getItems()) {
      if (orderitem.getGradeBundle() == null || orderitem.getGradeBundle().isEmpty()) individualItemList.add(orderitem);
      else {
        if (!bundleWasSet) {
          bundleTitle.setText("Bundle: "+orderitem.getGradeBundle()+"    Discount: $"+orderitem.getDiscount());
          bundleWasSet = true;
        }
        bundleItemList.addAll(CoolSuppliesFeatureSet5Controller.getBundleItems(orderitem.getGradeBundle()));
    }
  }

    // Set the items for the table
    first_row.setItems(dateList);
    second_row.setItems(dateList);
    item_table.setItems(individualItemList);
    bundle_table.setItems(bundleItemList);
  }
}