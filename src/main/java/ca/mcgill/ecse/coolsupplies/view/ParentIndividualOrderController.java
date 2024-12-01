package ca.mcgill.ecse.coolsupplies.view;

import javafx.scene.control.TableView;
import javafx.util.Callback;
import javafx.scene.control.TableColumn;
import ca.mcgill.ecse.coolsupplies.controller.Iteration3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.controller.TOOrderItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ParentIndividualOrderController {
  @FXML
  private TableView<TOOrder> first_row;
  @FXML
  private TableView<TOOrder> second_row;
  @FXML
  private TableView<TOOrderItem> item_table;
  @FXML
  private TableView<TOOrderItem> bundle_table;
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
  private TableColumn<TOOrder, String> order_col;
  @FXML
  private TableColumn<TOOrderItem, String> name_col;
  private ObservableList<TOOrderItem> individualItemList = FXCollections.observableArrayList();
  @FXML
  private TableColumn<TOOrderItem, String> bundleName_col;
  private ObservableList<TOOrderItem> bundleItemList = FXCollections.observableArrayList();
  @FXML
  private TableColumn<TOOrderItem, Void> viewBundleContent_col;
  @FXML
  private TableColumn<TOOrderItem, Void> viewItemContent_col;
  @FXML
  private TableColumn<TOOrderItem, String> discount_col;
  @FXML
  private TableColumn<TOOrderItem, String> bundlePrice_col;
  @FXML
  private TableColumn<TOOrder, String> total_col;


  @FXML
  //public void initialize(String orderNumber) {
  public void initialize() {
    TOOrder order = Iteration3Controller.viewAllOrders().get(0);
    System.out.println(order.getPrice());
    System.out.println(order.hasItems());

    date_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
    level_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLevel()));
    parent_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getParentEmail()));
    student_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudentName()));
    status_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
    auth_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthCode()));
    penalty_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPenaltyAuthCode()));
    order_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumber()));
    
    name_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    bundleName_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGradeBundle()));
    discount_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDiscount()));
    bundlePrice_col.setCellValueFactory(cellData -> new SimpleStringProperty("$"+String.valueOf(cellData.getValue().getPrice())));
    //bundlePrice_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice()));
    
    total_col.setCellValueFactory(cellData -> new SimpleStringProperty("$"+String.valueOf(cellData.getValue().getPrice())));

    viewBundleContent_col.setCellFactory(new Callback<>() {
      @Override
      public TableCell<TOOrderItem, Void> call(final TableColumn<TOOrderItem, Void> param) {
        final TableCell<TOOrderItem, Void> cell = new TableCell<>() {
          private final Button viewButton = new Button("View");
    
          {
            viewButton.setOnAction(menuDisplay -> {
              TOOrderItem orderitem = getTableView().getItems().get(getIndex());
              Stage display = new Stage();
              // display.initModality(Modality.APPLICATION_MODAL);
              // display.setTitle("Bundle Content");

              // TableView<TOOrderItem> content_table = new TableView<>();
              // ObservableList<TOOrderItem> content = FXCollections.observableArrayList();

              // TableColumn<TOOrderItem, String> itemNameCol = new TableColumn<>("Item Name");
              // TableColumn<TOOrderItem, String> itemPriceCol = new TableColumn<>("Price");
              // TableColumn<TOOrderItem, String> itemQuantityCol = new TableColumn<>("Quantity");
              // itemNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
              // itemPriceCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice()));
              // itemQuantityCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuantity()));

              // content_table.getColumns().addAll(itemNameCol, itemPriceCol, itemQuantityCol);
              // content_table.setItems(content);

              // Vbox vbox = new Vbox(new Label("Bundle Content"),display);
              // display.setScene(new Scene(vbox));
              // display.showAndWait();
            });
          }
    
          @Override
          public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setGraphic(null);
            } else {
              setGraphic(viewButton);
            }
          }
        };
        return cell;
      }
    });

    viewItemContent_col.setCellFactory(new Callback<>() {
      @Override
      public TableCell<TOOrderItem, Void> call(final TableColumn<TOOrderItem, Void> param) {
        final TableCell<TOOrderItem, Void> cell = new TableCell<>() {
          private final Button viewButton = new Button("View");
    
          {
            viewButton.setOnAction(menuDisplay -> {
              TOOrderItem orderitem = getTableView().getItems().get(getIndex());
              Stage display = new Stage();
              // display.initModality(Modality.APPLICATION_MODAL);
              // display.setTitle("Bundle Content");

              // TableView<TOOrderItem> content_table = new TableView<>();
              // ObservableList<TOOrderItem> content = FXCollections.observableArrayList();

              // TableColumn<TOOrderItem, String> itemNameCol = new TableColumn<>("Item Name");
              // TableColumn<TOOrderItem, String> itemPriceCol = new TableColumn<>("Price");
              // TableColumn<TOOrderItem, String> itemQuantityCol = new TableColumn<>("Quantity");
              // itemNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
              // itemPriceCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice()));
              // itemQuantityCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuantity()));

              // content_table.getColumns().addAll(itemNameCol, itemPriceCol, itemQuantityCol);
              // content_table.setItems(content);

              // Vbox vbox = new Vbox(new Label("Bundle Content"),display);
              // display.setScene(new Scene(vbox));
              // display.showAndWait();
            });
          }
    
          @Override
          public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setGraphic(null);
            } else {
              setGraphic(viewButton);
            }
          }
        };
        return cell;
      }
    });
    
    // Load order info from controller
    dateList.add(order);
    
    for (TOOrderItem orderitem : order.getItems()) {
      if (orderitem.getGradeBundle() == null || orderitem.getGradeBundle().isEmpty()) individualItemList.add(orderitem);
      else bundleItemList.add(orderitem);
    }

    // Set the items for the table
    first_row.setItems(dateList);
    second_row.setItems(dateList);
    item_table.setItems(individualItemList);
    bundle_table.setItems(bundleItemList);
  }
}
