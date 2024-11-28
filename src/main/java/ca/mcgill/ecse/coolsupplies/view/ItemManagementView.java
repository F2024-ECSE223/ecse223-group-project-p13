package ca.mcgill.ecse.coolsupplies.view;

import java.util.List;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGradeBundle;
import ca.mcgill.ecse.coolsupplies.controller.TOItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ItemManagementView {

  @FXML
  private TableView<TOGradeBundle> BundleTable;

  @FXML
  private TableView<TOItem> ItemTable;
  

  @FXML
  private Spinner<Integer> BundleQuantity;

  @FXML
  private TableColumn<TOItem, String> BundleItems;

  @FXML
  private TableColumn<TOItem, String> BundlePrice;

  @FXML
  private TableColumn<TOGradeBundle, Integer> BundleDiscount;

  @FXML
  private TableColumn<TOItem, String> columnName;

  @FXML
  private TableColumn<TOItem, String> columnPrice;

  @FXML
  private TableColumn<TOItem, Void> columnQuantity;

  @FXML
  private Button ViewOrder;

  
  private ObservableList<TOItem> availableItemsList = FXCollections.observableArrayList();
  private Map<TOItem, Integer> orderMap = new HashMap<>();
}