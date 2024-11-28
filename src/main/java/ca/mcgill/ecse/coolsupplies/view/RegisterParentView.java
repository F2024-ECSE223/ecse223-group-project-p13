package ca.mcgill.ecse.coolsupplies.view;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;

import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.event.ActionEvent;

public class RegisterParentView {
    @FXML
    private TextField emailRegisterParent;

    @FXML
    private TextField nameRegisterParent;

    @FXML
    private TextField phoneRegisterParent;

    @FXML
    private PasswordField passwordRegisterParent;

    @FXML
    private Button buttonSaveRegisterParent;

    @FXML
    private Label errorMessageRegisterParent;

    @FXML
    public void saveButtonClickedRegisterParent (ActionEvent event)
    {
        String email = emailRegisterParent.getText();
        String phoneNumberString = phoneRegisterParent.getText();
        int phoneNumber = Integer.parseInt(phoneNumberString);
        String name = nameRegisterParent.getText();
        String password = passwordRegisterParent.getText();

        String message = CoolSuppliesFeatureSet1Controller.addParent(email, password, name, phoneNumber);
        errorMessageRegisterParent.setText(message);

        // NEED TO UPDATE THE LIST OF PARENTS ON OTHER PAGE (dans le code d affichage)
    }

}
