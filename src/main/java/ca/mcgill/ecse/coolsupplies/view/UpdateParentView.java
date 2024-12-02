package ca.mcgill.ecse.coolsupplies.view;

import java.util.ArrayList;
import java.util.List;
import atlantafx.base.theme.Styles;
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
    private Label infoPasswordUpdateParent;

     /**
   * This method initializes the Update Parent page.
   * 
   * @author Lune Letailleur
   * @param none
   * @return void
   **/

    @FXML
    public void initialize(){
        buttonSaveUpdateParent.getStyleClass().add(Styles.SUCCESS);
        emailUpdateParent.setItems(FXCollections.observableArrayList(getParentEmails()));
        infoEmailUpdateParent.setItems(FXCollections.observableArrayList(getParentEmails()));

        infoEmailUpdateParent.setOnAction((e) ->  {
            String email = infoEmailUpdateParent.getSelectionModel().getSelectedItem();

            this.selectedParent = CoolSuppliesFeatureSet1Controller.getParent(email);

            if (this.selectedParent != null) {
                infoNameUpdateParent.setText(this.selectedParent.getName());
                infoPhoneUpdateParent.setText(Integer.toString(this.selectedParent.getPhoneNumber()));
                infoPasswordUpdateParent.setText(this.selectedParent.getPassword());
            }
        });
    }

     /**
   * This method gets the list of Parent Emails.
   * 
   * @author Lune Letailleur
   * @param none
   * @return List<String> parent emails, a list of each parent's email.
   **/

    private List<String> getParentEmails(){
        List<String> parentEmails = new ArrayList<>();
        for (TOParent parent: CoolSuppliesFeatureSet1Controller.getParents()){
            parentEmails.add(parent.getEmail());
        }
        return parentEmails;
    }

     /**
   * This method implements the svaing action of the Save button.
   * 
   * @author Lune Letailleur
   * @param ActionEvent event which represents the on going event.
   * @return void
   **/

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

            if (message.isEmpty()){
                emailUpdateParent.getSelectionModel().clearSelection();;
                nameUpdateParent.setText("");
                phoneUpdateParent.setText("");
                passwordUpdateParent.setText("");
                oldPasswordUpdateParent.setText("");
            }
        } else {
            errorMessageUpdateParent.setText("This email is not yours.");
        }

    }

}
