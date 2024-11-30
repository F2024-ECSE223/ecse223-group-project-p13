package ca.mcgill.ecse.coolsupplies.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;

public class AddBundleController {
    @FXML
    private Label bundleName;
    @FXML
    private Label grade;
    @FXML
    private TextField bundleNameText;
    @FXML
    private ComboBox<String> gradeOptions;
    @FXML
    private Label discount;
    @FXML
    private TextField discountValue;
    @FXML
    private Button nextButton;
    @FXML
    private void initialize(){
        // List<TOGrade> gradesInSystem = CoolSuppliesFeatureSet7Controller.getGrades();
        // List<String> gradeLevels = new ArrayList<>();
        // for(TOGrade grade : gradesInSystem){
        //     gradeLevels.add(grade.getLevel());
        // }
        // gradeOptions.getItems().addAll(gradeLevels);
    }

    @FXML
    private void moveToNextPage(ActionEvent event){  //move over to add items page from the next button
        try {
            CoolSuppliesFxmlView.newWindow("EditBundle.fxml", "Edit a Bundle");
            // clearError();
        } catch (Exception e) {
            // displayError("Failed to open the edit bundle page: " + e.getMessage());
        }
    }
    // @FXML
    // private void createBundle(){
    //     String bundleName = bundleNameText.getText();
    //     String bundleDiscount= discountValue.getText();
    //     String bundleGrade= gradeOptions.getValue();
    //     try {
    //         int discountInt = Integer.parseInt(bundleDiscount);
    //         String tryAddBundle = CoolSuppliesFeatureSet4Controller.addBundle(bundleName, discountInt, bundleGrade);
    //     }
    //     catch(NumberFormatException e) {
    //         Alert alert = new Alert(AlertType.ERROR);
    //         alert.setContentText("Discount must be a number");
    //     }

    // }


}