package ca.mcgill.ecse.coolsupplies.view;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;

import java.awt.event.ActionEvent;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;

public class UpdateParentView {

    @FXML
    private ComboBox<String> emailUpdateParent;
    @FXML
    private PasswordField passwordUpdateParent;

    @FXML
    private TextField phoneUpdateParent;

    @FXML
    private TextField nameUpdateParent;

    @FXML
    private Label errorMessageUpdateParent;

    @FXML
    private Button buttonSaveUpdateParent;

    @FXML
    public void initialize(){
        emailUpdateParent.setItems(FXCollections.observableArrayList(getParentEmails()));
    }

    private List<String> getParentEmails(){
        List<String> parentEmails = new ArrayList<>();
        for (TOParent parent: CoolSuppliesFeatureSet1Controller.getParents()){
            parentEmails.add(parent.getEmail());
        }
        return parentEmails;
    }

    // ADD A CHECK FOR OLD PASSWORD TO SEE IF RIGHT PARENT 
    @FXML
    public void saveButtonClickedUpdateParent(javafx.event.ActionEvent event)
    {
        String email = emailUpdateParent.getValue();
        String name = nameUpdateParent.getText();
        String phoneNumber = phoneUpdateParent.getText();
        int phone = Integer.parseInt(phoneNumber);
        String password = passwordUpdateParent.getText();

        String message = CoolSuppliesFeatureSet1Controller.updateParent(email, password, name,phone);
        errorMessageUpdateParent.setText(message);
        
        emailUpdateParent.getSelectionModel().clearSelection();;
        nameUpdateParent.setText("");
        phoneUpdateParent.setText("");
        passwordUpdateParent.setText("");
    }

}
