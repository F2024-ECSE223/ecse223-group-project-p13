package ca.mcgill.ecse.coolsupplies.view;

import ca.mcgill.ecse.coolsupplies.controller.TOGradeBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditBundleView {

    @FXML
    private Label bundleNameLabel;

    @FXML
    private TextField gradeLevelField;

    private TOGradeBundle bundle;

    public void initData(TOGradeBundle bundle) {
        this.bundle = bundle;
        bundleNameLabel.setText(bundle.getName());
        gradeLevelField.setText(bundle.getGradeLevel());
        // Initialize other UI components with bundle data
    }

    //saveButton
    // Other methods for handling events, saving changes, etc.
}
