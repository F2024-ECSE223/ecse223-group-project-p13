package ca.mcgill.ecse.coolsupplies.view;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;

import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;

public class DeleteParentView {
    @FXML
    private TableView <TOParent> tableViewRemoveParent;

    @FXML
    private TableColumn <TOParent, String> parentEmailRemoveParent;

    @FXML
    private TableColumn <TOParent, Void> removeRemoveParent;

    @FXML
    private Label errorMessageRemoveParent;

    private ObservableList<TOParent> parentList = FXCollections.observableArrayList();
    @FXML
    public void initialize ()
    {
        // need to initialize the grid
        // need to initialize the number of rows we need in our table (ie. nb of parents in the list)
        parentEmailRemoveParent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        addRemoveButton(); // need to update by adding the "remove" button
        updateTable(); // need to update the table with the list of parents
    }

    private void updateTable(){
        // Need to update the list if parents in the table when go in the page
        parentList.setAll(CoolSuppliesFeatureSet1Controller.getParents());
        tableViewRemoveParent.setItems(parentList);

    }

    private void addRemoveButton(){
        // need to add a Remove button for each (set id and OnAction)
        removeRemoveParent.setCellFactory(column -> new TableCell<>(){
            private final Button removeButtonRemoveParent = new Button("Remove");
            {
                removeButtonRemoveParent.setOnAction(event -> {
                    TOParent parent = getTableView().getItems().get(getIndex());
                    DeleteClickedDeleteParent(parent.getEmail());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty){
                    setGraphic(null);
                } else {
                    setGraphic(removeButtonRemoveParent);
                }
            }
        });
        // need a protected void UpdateItem () ?????
    }


    public void DeleteClickedDeleteParent (String email)
    {
        String message = CoolSuppliesFeatureSet1Controller.deleteParent(email);
        errorMessageRemoveParent.setText(message);

        parentList.removeIf(parent -> parent.getEmail().equals(email));

    }

}
