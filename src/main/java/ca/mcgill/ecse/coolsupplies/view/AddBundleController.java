package ca.mcgill.ecse.coolsupplies.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import java.util.ArrayList;
import java.util.List;

public class AddBundleController {

    @FXML
    private TextField bundleNameText;

    @FXML
    private ComboBox<String> gradeOptions;

    @FXML
    private TextField discountValue;

    @FXML
    private Button nextButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        try {
            List<TOGrade> gradesInSystem = CoolSuppliesFeatureSet7Controller.getGrades();
            List<String> gradeLevels = new ArrayList<>();
            for (TOGrade grade : gradesInSystem) {
                gradeLevels.add(grade.getLevel());
            }
            gradeOptions.getItems().addAll(gradeLevels);
            clearError();
        } catch (Exception e) {
            displayError("Failed to load grades: " + e.getMessage());
        }
    }

    @FXML
    private void moveToNextPage(ActionEvent event) {
        try {
            CoolSuppliesFxmlView.newWindow("EditBundle.fxml", "Edit a Bundle");
            // clearError();
        } catch (Exception e) {
            // displayError("Failed to open the edit bundle page: " + e.getMessage());
        }

        //Find a way to close other window when clicking next
    }

    private void displayError(String message) {
        errorLabel.setText(message);
    }

    private void clearError() {
        errorLabel.setText("");
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

//onAction="#moveToNextPage"