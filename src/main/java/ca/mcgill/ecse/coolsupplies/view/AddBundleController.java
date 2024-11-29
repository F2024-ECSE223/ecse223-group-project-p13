package ca.mcgill.ecse.coolsupplies.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;
import java.util.ArrayList;
import java.lang.Integer;

public class AddBundleController {
    @FXML
    private Label bundleName;
    @FXML
    private Label grade;
    @FXML
    private Label labelAdmin;
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
        List<TOGrade> gradesInSystem = CoolSuppliesFeatureSet7Controller.getGrades();
        List<String> gradeLevels = new ArrayList<>();
        for(TOGrade grade : gradesInSystem){
            gradeLevels.add(grade.getLevel());
        }
        gradeOptions.getItems().addAll(gradeLevels);
    }

    @FXML
    private void moveToNextPage(){  //move over to add items page from the next button

    }
    @FXML
    private void createBundle(){
        String bundleName = bundleNameText.getText();
        String bundleDiscount= discountValue.getText();
        String bundleGrade= gradeOptions.getValue();

        int discountInt = Integer.parseInt(bundleDiscount);

        String tryAddBundle = CoolSuppliesFeatureSet4Controller.addBundle(bundleName, bundleDiscount, bundleGrade);

        if(!tryAddBundle.isEmpty()){
            Alert alert = new Alert();
            alert.setError(tryAddBundle);
        }

    }


}