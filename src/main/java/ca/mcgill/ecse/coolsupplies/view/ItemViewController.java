package ca.mcgill.ecse.coolsupplies.view;

import javax.swing.table.TableColumn;

import ca.mcgill.ecse.coolsupplies.controller.TOItem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ItemViewController {

  @FXML
  private TextField item_item_name_label; 

  @FXML
  private TextField item_price_label;

  @FXML
  private Button item_add_item_button;

  @FXML
  private TableView<TOItem> item_table_view;

  @FXML
  private TableColumn item_item_column;

  @FXML
  private TableColumn item_price_column;

  @FXML
  private TableColumn item_options_column;

  private ObservableList<TOItem> itemList;

  public void start() {
    
  }

  
  
}
