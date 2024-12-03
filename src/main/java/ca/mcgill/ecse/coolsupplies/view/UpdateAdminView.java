package ca.mcgill.ecse.coolsupplies.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import atlantafx.base.theme.Styles;

public class UpdateAdminView {

    @FXML
    private Button buttonSaveUpdateAdmin;

    @FXML
    private PasswordField newPasswordUpdateAdmin;

    @FXML
    private Label errorMessage;

    /**
     * @author Trevor Piltch
     * @brief Add the green style to the button
     */
    @FXML
    public void initialize() {
      this.buttonSaveUpdateAdmin.getStyleClass().add(Styles.SUCCESS);
    }

     /**
   * This method implements teh svaing action when Save is pressed..
   * 
   * @author Lune Letailleur
   * @param ActionEvent event which represents the ongoing event.
   * @return void
   **/

    @FXML
    public void saveButtonClickedUpdateAdmin (javafx.event.ActionEvent event){
       String  newPassword = newPasswordUpdateAdmin.getText();
       String message = CoolSuppliesFeatureSet1Controller.updateAdmin(newPassword);
       if (message.isEmpty()){
        errorMessage.setText("Password was updated!");
       } else {
        errorMessage.setText(message);
       }
       newPasswordUpdateAdmin.setText(""); // clear user input

    }
}
