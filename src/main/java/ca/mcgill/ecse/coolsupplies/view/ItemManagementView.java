package ca.mcgill.ecse.coolsupplies.view;

import java.util.Collections;
import java.util.List;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;
import ca.mcgill.ecse.coolsupplies.controller.Iteration3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGradeBundle;
import ca.mcgill.ecse.coolsupplies.controller.TOItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ItemManagementView {

    @FXML
    private TableView<TOGradeBundle> BundleTable;

    @FXML
    private TableView<TOItem> ItemTable;

    @FXML
    private Spinner<Integer> BundleQuantity;

    @FXML
    private TableColumn<TOGradeBundle, String> BundleItems;

    @FXML
    private TableColumn<TOGradeBundle, String> BundlePrice;

    @FXML
    private TableColumn<TOGradeBundle, Integer> BundleDiscount;

    @FXML
    private TableColumn<TOItem, String> columnName;

    @FXML
    private TableColumn<TOItem, String> columnPrice;

    @FXML
    private TableColumn<TOItem, Void> columnQuantity;

    @FXML
    private Button viewOrder;

    @FXML
    private Label errorLabel;

    private ObservableList<TOGradeBundle> bundleList = FXCollections.observableArrayList();
    private ObservableList<TOItem> itemList = FXCollections.observableArrayList();

    private String selectedGrade;
    private String selectedLevel;

    /**
     * Set the grade and level of the student.
     */
    public void setStudentInfo(String grade, String level) {
        this.selectedGrade = grade;
        this.selectedLevel = level;
        refreshBundleTable();
        refreshItemTable();
    }

    @FXML
    public void initialize() {
        // Configure table columns
        BundleItems.setCellValueFactory(new PropertyValueFactory<>("name"));
        BundlePrice.setCellValueFactory(new PropertyValueFactory<>("discount")); // Adjust if price exists
        BundleDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Add spinner factory for the quantity column
        columnQuantity.setCellFactory(param -> new TableCell<>() {
            private final Spinner<Integer> spinner = new Spinner<>(0, 100, 0);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(spinner);
                }
            }
        });

        // Initialize spinner for the bundle quantity
        SpinnerValueFactory<Integer> bundleSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        BundleQuantity.setValueFactory(bundleSpinnerFactory);
    }

    private void refreshBundleTable() {
        // Get the bundle for the selected grade and level
        List<TOGradeBundle> bundles = CoolSuppliesFeatureSet4Controller.getBundles();
        TOGradeBundle matchingBundle = bundles.stream()
                .filter(bundle -> bundle.getGradeLevel().equals(selectedGrade))
                .findFirst()
                .orElse(null);

        bundleList.setAll(matchingBundle != null ? Collections.singletonList(matchingBundle) : Collections.emptyList());
        BundleTable.setItems(bundleList);
    }

    private void refreshItemTable() {
        // Get all items
        List<TOItem> items = CoolSuppliesFeatureSet3Controller.getItems();
        itemList.setAll(items);
        ItemTable.setItems(itemList);
    }

    @FXML
    private void handleViewOrder() {
        // Collect bundle quantity
        int bundleQuantity = BundleQuantity.getValue();
        if (bundleQuantity > 0 && !bundleList.isEmpty()) {
            TOGradeBundle selectedBundle = bundleList.get(0);
            String bundleName = selectedBundle.getName();
            Iteration3Controller.addItem(bundleName, String.valueOf(bundleQuantity), "1"); // Order number hardcoded for simplicity
        }

        // Collect item quantities
        for (TOItem item : itemList) {
            int quantity = 0; // Replace with logic to get quantity from the quantity column
            if (quantity > 0) {
                Iteration3Controller.addItem(item.getName(), String.valueOf(quantity), "1");
            }
        }

        errorLabel.setText("Order updated successfully!");
    }
}
