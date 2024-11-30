package ca.mcgill.ecse.coolsupplies.view;

//import javax.swing.table.TableColumn;
import javafx.scene.control.TableColumn;

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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;

public class ItemViewController {

  @FXML
  private TextField new_item_name_text_field; 

  @FXML
  private TextField new_price_text_field;

  @FXML
  private Button add_item_button;

  @FXML
  private TableView<TOItem> item_table_view;

  @FXML
  private TableColumn<TOItem, String> item_name_column;

  @FXML
  private TableColumn<TOItem, String> item_price_column;  // TODO: Integer for price??

  @FXML
  private TableColumn<TOItem, Void> item_options_column;

  private ObservableList<TOItem> itemList;

  @FXML
  public void initialize() {

    itemList = FXCollections.observableArrayList();


    item_name_column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
    item_price_column.setCellValueFactory(data -> new SimpleStringProperty(""+data.getValue().getPrice()));
    //item_options_column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getShift()));

  }

  @FXML
  public void addItemClicked(ActionEvent event) {

    String newName = new_item_name_text_field.getText();
    String newPrice = new_price_text_field.getText();

    if (newName == null || newName.trim().isEmpty()) {
      showError("Please input a valid item name");
    } 
    else if (newPrice == null || newPrice.trim().isEmpty()) {
      showError("Please input a valid item price");
    }
    String addMessage = CoolSuppliesFeatureSet3Controller.addItem(newName, Integer.parseInt(newPrice));
    if (addMessage.isEmpty()) {
      new_item_name_text_field.setText("");
      new_price_text_field.setText("");
    }
    else {
      showError(addMessage);
    }
  }


  // Helper methods

  // Error popup from tutorial
  private  static void makePopupWindow(String title, String message) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    //Text text = new Text(message);
    Button okButton = new Button("OK");
    okButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10; // inner padding/spacing
    int outerPadding = 100; // outer padding
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    //dialogPane.getChildren().addAll(text, okButton);
    Scene dialogScene = new Scene(dialogPane, outerPadding + 5 * message.length(), outerPadding);
    dialog.setScene(dialogScene);
    dialog.setTitle(title);
    dialog.show();
  }

  private static void showError(String message) {
    makePopupWindow("Error", message);
  }


  
  
}
