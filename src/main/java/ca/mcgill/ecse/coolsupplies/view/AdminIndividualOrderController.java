package ca.mcgill.ecse.coolsupplies.view;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import ca.mcgill.ecse.coolsupplies.controller.Iteration3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
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
  private TableColumn<TOOrder,String> items_col;
  @FXML
  private TableColumn<TOOrder,String> bundle_col;
  @FXML
  private TableColumn<TOOrder,String> discount_col;
  @FXML
  private TableColumn<TOOrder,String> price_col;
  @FXML
  private TableColumn<TOOrder,String> total_col;


  @FXML
  public void initialize(String orderNumber) {
    date_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));

    // Load order info from controller
    dateList.add(Iteration3Controller.viewOrder(orderNumber));

    // Set the items for the table
    first_row.setItems(dateList);
  }
}