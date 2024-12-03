package ca.mcgill.ecse.coolsupplies.view;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
        
        int phoneNumber = Integer.parseInt(phoneNumberString);
        String name = nameRegisterParent.getText();
        String password = passwordRegisterParent.getText();

        String message = CoolSuppliesFeatureSet1Controller.addParent(email, password, name, phoneNumber);
        errorMessageRegisterParent.setText(message);
        if (message.isEmpty()){
             // clearing the input fields
            emailRegisterParent.setText("");
            phoneRegisterParent.setText("");
            nameRegisterParent.setText("");
            passwordRegisterParent.setText("");
        }
    }

}
