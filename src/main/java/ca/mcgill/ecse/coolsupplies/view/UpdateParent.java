package ca.mcgill.ecse.coolsupplies.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;

import java.awt.event.ActionEvent;

public class UpdateParent {

    @FXML
    private TextField emailUpdateParent;
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
    public void saveButtonClickedUpdateParent(ActionEvent event)
    {
        String email = emailUpdateParent.getText();
        String name = nameUpdateParent.getText();
        String phoneNumber = phoneUpdateParent.getText();
        int phone = Integer.parseInt(phoneNumber);
        String password = passwordUpdateParent.getText();

        String message = CoolSuppliesFeatureSet1Controller.updateParent(email, password, name,phone);
        if (!message.isEmpty())
        {
            errorMessageUpdateParent.setText(message);
        }
    }

}
