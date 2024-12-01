package ca.mcgill.ecse.coolsupplies.view;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
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
            //clearError();
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


    private void addBundle() {
        String bundleName = bundleNameText.getText();
        String discount = discountValue.getText();
        String grade = gradeOptions.getValue();
        int discountInt = 0;
        clearError();
        try {
            discountInt = Integer.parseInt(discount);
        } catch (Exception e) {
            displayError("Discount value must be an integer." + e.getMessage());
        }

        String attemptAddBundle = CoolSuppliesFeatureSet4Controller.addBundle(bundleName, discountInt, grade);
        if (!attemptAddBundle.trim().isEmpty()) {
            displayError(attemptAddBundle);
        }
    }

    @FXML
    private void nextButton(ActionEvent event) {
        addBundle();
        moveToNextPage(event);
    }
}
