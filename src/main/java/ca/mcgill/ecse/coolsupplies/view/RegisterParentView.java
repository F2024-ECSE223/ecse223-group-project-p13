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
import atlantafx.base.theme.Styles;

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
    public void initialize() {
        this.buttonSaveRegisterParent.getStyleClass().add(Styles.SUCCESS);
    }
     
     /**
   * This method implements the saving actions when Save is pressed.
   * 
   * @author Lune Letailleur
   * @param ActionEvent event corresponding to teh event happening.
   * @return void
   **/

    @FXML
    public void saveButtonClickedRegisterParent (javafx.event.ActionEvent event)
    {
        String email = emailRegisterParent.getText();
        String phoneNumberString = phoneRegisterParent.getText();
        
        // NECESSARY ? THE PARENT ALWAYS INPUTS NUMBER IN DEMO
        /*if(phoneNumberString.isEmpty()){
            errorMessageRegisterParent.setText("The phone number must not be empty.");
        }*/

        int phoneNumber = Integer.parseInt(phoneNumberString);
        String name = nameRegisterParent.getText();
        String password = passwordRegisterParent.getText();

        String message = CoolSuppliesFeatureSet1Controller.addParent(email, password, name, phoneNumber);
        errorMessageRegisterParent.setText(message);

        // clearing the input fields
        emailRegisterParent.setText("");
        phoneRegisterParent.setText("");
        nameRegisterParent.setText("");
        passwordRegisterParent.setText("");
        // NEED TO UPDATE THE LIST OF PARENTS ON OTHER PAGE (dans le code d affichage)
    }

}
