package ca.mcgill.ecse.coolsupplies.view;

//import javax.swing.table.TableColumn;
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
/*
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

  /*
   * @author Dimitri Christopoulos
   */
  @FXML
  public void initialize() {

    itemList = FXCollections.observableArrayList();


    item_name_column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
    item_price_column.setCellValueFactory(data -> new SimpleStringProperty(""+data.getValue().getPrice()));
    
    addButtonsColumn();

    itemList.addAll(CoolSuppliesFeatureSet3Controller.getItems());

    item_table_view.setItems(itemList);
    

  }

  /*
   * @author Dimitri Christopoulos
   */
  @FXML
  private void addButtonsColumn() {
    item_options_column.setCellFactory(col -> new TableCell<>() {
      private final Button updateButton = new Button("Update");
      private final Button deleteButton = new Button("Delete");
      private final HBox buttons = new HBox(10, updateButton, deleteButton);
  
      {
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

  /*
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

  /*
   * @author Dimitri Christopoulos
   */
  // Clear text when user clicks text field
  @FXML
  private void itemNameTextClick() {
    new_item_name_text_field.setText("");
  }

  /*
   * @author Dimitri Christopoulos
   */
  // Clear text when user clicks text field
  @FXML
  private void priceTextClick() {
    new_price_text_field.setText("");
  }
  


  /*
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
    
    // actions
    newName.setOnMouseClicked(a -> newName.setText(""));
    newPrice.setOnMouseClicked(a -> newPrice.setText(""));
    saveButton.setOnAction(a -> {

    // textt from labels
    String newNameString = newName.getText();
    String newPriceString = newPrice.getText();

    // add updated name as new, remove old
    String addMessage = CoolSuppliesFeatureSet3Controller.updateItem(oldItem.getName(), newNameString, Integer.parseInt(newPriceString));
    try {
      if (addMessage.isEmpty()) {
        errorUpdate.setText(addMessage);
        newName.setText("New name");
        newPrice.setText("New price");
        dialog.close();
      }
      else {
        errorUpdate.setText(addMessage);
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

  /*
   * @Author Dimitri Christopoulos
   */
  /*private void paymentWindow(TOOrder pendingOrder) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Label totalCost = new Label(""+pendingOrder.getPrice());
    TextField authCode = new TextField("Authorization Code");
    Button payButton = new Button("Pay");
    Button cancelButton = new Button("Cancel");
    Label errorUpdate = new Label("");
    
    // actions
    authCode.setOnMouseClicked(a -> authCode.setText(""));
    payButton.setOnAction(a -> {

      // textt from labels
      String inputAuthCodeString = authCode.getText();

      try {
        String payMessage = Iteration3Controller.payForOrder(pendingOrder.getNumber(), inputAuthCodeString);
          // Success
          if (payMessage.isEmpty()) {
            errorUpdate.setText(payMessage);
            parentOrders.remove(pendingOrder);
            dialog.close();
          }
          // Error
          else {
            errorUpdate.setText(payMessage);
          }
      } 
      catch (Exception e) {
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
    dialogPane.getChildren().addAll(totalCost, authCode, errorUpdate, payButton, cancelButton);
    Scene dialogScene = new Scene(dialogPane);
    dialog.setScene(dialogScene);
    dialog.setTitle("Pay Order");
    dialog.show();
  }

  private void latePaymentWindow(TOOrder latePendingOrder) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Label totalCost = new Label(""+latePendingOrder.getPrice());
    TextField authCode = new TextField("Authorization Code");
    TextField lateAuthCode = new TextField("Late Authorization Code");
    Button payButton = new Button("Pay");
    Button cancelButton = new Button("Cancel");
    Label errorUpdate = new Label("");
    
    // actions
    authCode.setOnMouseClicked(a -> authCode.setText(""));
    payButton.setOnAction(a -> {

      // textt from labels
      String inputAuthCodeString = authCode.getText();
      String inputLateAuthCodeString = lateAuthCode.getText();

      try {
          String payPenaltyMessage = Iteration3Controller.payPenaltyForOrder(latePendingOrder.getNumber(), inputLateAuthCodeString, inputAuthCodeString);
          if (payPenaltyMessage.isEmpty()) {
            errorUpdate.setText(payPenaltyMessage);
            parentOrders.remove(latePendingOrder);
          }
          else {
            errorUpdate.setText(payPenaltyMessage);
          }
      } 
      catch (Exception e) {
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
    dialogPane.getChildren().addAll(totalCost, authCode, errorUpdate, payButton, cancelButton);
    Scene dialogScene = new Scene(dialogPane);
    dialog.setScene(dialogScene);
    dialog.setTitle("Pay Order");
    dialog.show();
  }*/


}
