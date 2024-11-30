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
    private TOParent selectedParent = null;
 
    @FXML
    private ComboBox<String> emailUpdateParent;

    @FXML
    private PasswordField oldPasswordUpdateParent;

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
    private ComboBox<String> infoEmailUpdateParent;

    @FXML
    private Label infoNameUpdateParent;

    @FXML
    private Label infoPhoneUpdateParent;

    @FXML
    public void initialize(){
        emailUpdateParent.setItems(FXCollections.observableArrayList(getParentEmails()));
        infoEmailUpdateParent.setItems(FXCollections.observableArrayList(getParentEmails()));

        infoEmailUpdateParent.setOnAction((e) ->  {
            String email = infoEmailUpdateParent.getSelectionModel().getSelectedItem();

            this.selectedParent = CoolSuppliesFeatureSet1Controller.getParent(email);

            if (this.selectedParent != null) {
                infoNameUpdateParent.setText(this.selectedParent.getName());
                infoPhoneUpdateParent.setText(Integer.toString(this.selectedParent.getPhoneNumber()));
            }
        });
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
        String oldPassword = oldPasswordUpdateParent.getText();
        String email = emailUpdateParent.getValue();
        String name = nameUpdateParent.getText();
        String phoneNumber = phoneUpdateParent.getText();
        int phone = Integer.parseInt(phoneNumber);
        String password = passwordUpdateParent.getText();

        if (oldPassword.equals(CoolSuppliesFeatureSet1Controller.getParent(email).getPassword())){
            String message = CoolSuppliesFeatureSet1Controller.updateParent(email, password, name,phone);
            errorMessageUpdateParent.setText(message);
        } else {
            errorMessageUpdateParent.setText("This email is not yours.");
        }
        
        
        emailUpdateParent.getSelectionModel().clearSelection();;
        nameUpdateParent.setText("");
        phoneUpdateParent.setText("");
        passwordUpdateParent.setText("");
        oldPasswordUpdateParent.setText("");
    }

}
