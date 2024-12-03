package ca.mcgill.ecse.coolsupplies.view;

import atlantafx.base.theme.Styles;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Controller for the item management page of the admin
 * 
 * @author Dimitri Christopoulos
 */
public class ItemViewController {

  @FXML
  private TextField new_item_name_text_field; 

  @FXML
  private TextField new_price_text_field;

  @FXML
  private Label error;

  @FXML
  private Button add_item_button;

  @FXML
  private TableView<TOItem> item_table_view;

  @FXML
  private TableColumn<TOItem, String> item_name_column;

  @FXML
  private TableColumn<TOItem, String> item_price_column;

  @FXML
  private TableColumn<TOItem, Void> item_options_column;

  private ObservableList<TOItem> itemList;

  /**
   * Sets up the scroll table menu by adding items to the itemList. 
   * Creates the columns for name, priice and calls method to create buttons column.
   * 
   * @param None
   * @return void
   * @author Dimitri Christopoulos
   */
  @FXML
  public void initialize() {

    // Initialize list of items for the sroll table
    itemList = FXCollections.observableArrayList();


    // Add item and price, and buttons ccolumns 
    item_name_column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
    item_price_column.setCellValueFactory(data -> new SimpleStringProperty(""+data.getValue().getPrice()));
    addButtonsColumn();

    // Add items from model to view using controller 
    itemList.addAll(CoolSuppliesFeatureSet3Controller.getItems());
    item_table_view.setItems(itemList);

    add_item_button.getStyleClass().add(Styles.SUCCESS);
  }

  /**
   * Private method to create a third column with two buttons.
   * The first button is to display a popup menu to update the item
   * The second button is to delete the item from the table
   * 
   * @param None
   * @return void
   * @author Dimitri Christopoulos
   */
  @FXML
  private void addButtonsColumn() {
    item_options_column.setCellFactory(col -> new TableCell<>() {
      private final Button updateButton = new Button("Update");
      private final Button deleteButton = new Button("Delete");
      private final HBox buttons = new HBox(10, updateButton, deleteButton);

      {
        buttons.setPadding(new Insets(4, 0, 4, 0));
        updateButton.getStyleClass().add(Styles.BUTTON_OUTLINED);
        updateButton.getStyleClass().add(Styles.ACCENT);
        deleteButton.getStyleClass().add(Styles.DANGER);

          updateButton.setOnAction(event -> {
              TOItem oldItem = getTableView().getItems().get(getIndex());
              makeUpdateWindow(oldItem);
          });
  
          deleteButton.setOnAction(event -> {
              TOItem item = getTableView().getItems().get(getIndex());
              getTableView().getItems().remove(item);
              CoolSuppliesFeatureSet3Controller.deleteItem(item.getName());
          });
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
          super.updateItem(item, empty);
          if (empty) {
              setGraphic(null);
          } else {
              setGraphic(buttons);
          }
      }
    
    });
    
}

  /**
   * When the add item button is pressed, this method is called.
   * Adds item to system and updated the table in the View.
   * 
   * @param event (ActionEvent) action event which is from when the user presses the Add Item button.
   * @return void
   * @author Dimitri Christopoulos
   */
  @FXML
  public void addItemClicked(ActionEvent event) {

    String newName = new_item_name_text_field.getText();
    String newPrice = new_price_text_field.getText();

    
    String addMessage = CoolSuppliesFeatureSet3Controller.addItem(newName, Integer.parseInt(newPrice));
    if (addMessage.isEmpty()) {
      new_item_name_text_field.setText("Item name");
      new_price_text_field.setText("Price");
      error.setText(addMessage);
      TOItem new_item = new TOItem(newName, Integer.parseInt(newPrice));
      itemList.add(new_item);
    }
    else {
      error.setText(addMessage);
    }
  }

  /**
   * When the user presses the new item text field, the text disappears, making it easier for the user to type.
   * 
   * @param None
   * @return void
   * @author Dimitri Christopoulos
   */
  // Clear text when user clicks text field
  @FXML
  private void itemNameTextClick() {
    new_item_name_text_field.setText("");
  }

  /**
   * When the user presses the new price text field, the text disappears, making it easier for the user to type.
   * 
   * @param None
   * @return void
   * @author Dimitri Christopoulos
   */
  // Clear text when user clicks text field
  @FXML
  private void priceTextClick() {
    new_price_text_field.setText("");
  }
  


  /**
   * Privte method used when the user presses the update button.
   * Creates a new window for the user to update the selected itemm.
   * 
   * @param oldItem (TOItem) selected item to be updated
   * @return void
   * @author Dimitri Christopoulos
   */
  private void makeUpdateWindow(TOItem oldItem) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    TextField newName = new TextField("New name");
    TextField newPrice = new TextField("New price");
    Button saveButton = new Button("Save");
    Button cancelButton = new Button("Cancel");
    Label errorUpdate = new Label("");
    errorUpdate.setTextFill(Color.RED);
    saveButton.getStyleClass().add(Styles.SUCCESS);
    cancelButton.getStyleClass().addAll(Styles.DANGER, Styles.BUTTON_OUTLINED);
    
    // actions
    newName.setOnMouseClicked(a -> newName.setText(""));
    newPrice.setOnMouseClicked(a -> newPrice.setText(""));
    saveButton.setOnAction(a -> {

    // textt from labels
    String newNameString = newName.getText();
    String newPriceString = newPrice.getText();

    // add updated name as new, remove old
    String updateMessage = CoolSuppliesFeatureSet3Controller.updateItem(oldItem.getName(), newNameString, Integer.parseInt(newPriceString));
    try {
      if (updateMessage.isEmpty()) {
        errorUpdate.setText(updateMessage);
        newName.setText("New name");
        newPrice.setText("New price");

        itemList.remove(oldItem);
        TOItem updatedItem = new TOItem(newNameString, Integer.parseInt(newPriceString));
        itemList.add(updatedItem);
        
        dialog.close();
        
      }
      else {
        errorUpdate.setText(updateMessage);
      }
    } catch (Exception e) {
      errorUpdate.setText(""+e);
    }
    
    
    });

    cancelButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10;
    int outerPadding = 100;
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(newName, newPrice, errorUpdate, saveButton, cancelButton);
    Scene dialogScene = new Scene(dialogPane);
    dialog.setScene(dialogScene);
    dialog.setTitle("Update Item");
    dialog.show();
  }

}