package ca.mcgill.ecse.coolsupplies.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;

import java.awt.event.ActionEvent;
import java.util.Objects;

public class UpdateAdminView {

    @FXML
    private Button buttonSaveUpdateAdmin;

    @FXML
    private PasswordField newPasswordUpdateAdmin;

    @FXML
    private Label errorMessage;


    @FXML
    public void saveButtonClickedUpdateAdmin (ActionEvent event){
       String  newPassword = newPasswordUpdateAdmin.getText();
       String message = CoolSuppliesFeatureSet1Controller.updateAdmin(newPassword);
       errorMessage.setText(message);


       // ADD ERROR LABEL
    }

}
