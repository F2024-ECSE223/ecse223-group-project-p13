package ca.mcgill.ecse.coolsupplies.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.Callback;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.coolsupplies.controller.*;

public class ItemManagementView {

    @FXML
    private TableView<TOBundleItem> bundleTableView;

    @FXML
    private TableColumn<TOBundleItem, String> BundleItems;

    @FXML
    private TableColumn<TOBundleItem, String> BundlePrice;

    @FXML
    private TableColumn<TOBundleItem, String> BundleDiscount;

    @FXML
    private Spinner<Integer> BundleQuantity;

    @FXML
    private TableView<TOItem> itemsTableView;

    @FXML
    private TableColumn<TOItem, String> columnName;

    @FXML
    private TableColumn<TOItem, String> columnPrice;

    @FXML
    private TableColumn<TOItem, Integer> columnQuantity;

    @FXML
    private Button viewOrder;

    @FXML
    private Label errorLabel;

    // Variables to store passed data
    private String studentName;
    private String orderLevel;

    // ObservableLists for TableViews
    private ObservableList<TOBundleItem> bundleItemsList = FXCollections.observableArrayList();
    private ObservableList<TOItem> itemsList = FXCollections.observableArrayList();

    // Initialize method
    @FXML
    public void initialize() {
        // Initialize TableView columns
        BundleItems.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemName()));
        BundlePrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(getItemPrice(cellData.getValue()))));
        BundleDiscount.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGradeBundleName()));

        // Items TableView columns
        columnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        columnPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        columnQuantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(1).asObject());

        // Initialize Spinner for Bundle Quantity
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        BundleQuantity.setValueFactory(valueFactory);
    }

  
    
    private int getItemPrice(TOBundleItem bundleItem) {
        // Implement logic to get the item price
        // This is a placeholder
        return 0;
    }

    // Method to set student name and order level (called from previous UI)
    public void setStudentInfo(String studentName, String orderLevel) {
        this.studentName = studentName;
        this.orderLevel = orderLevel;

        // Load data based on student info
        loadData();
    }

    // Method to load data into TableViews
    private void loadData() {
        // Get student's grade level
        String gradeLevel = getStudentGradeLevel(studentName);

        if (gradeLevel == null) {
            errorLabel.setText("Student not found.");
            return;
        }

        // Load bundle items
        loadBundleItems(gradeLevel);

        // Load available items
        loadAvailableItems();
    }

    private String getStudentGradeLevel(String studentName) {
        TOStudent student = CoolSuppliesFeatureSet2Controller.getStudent(studentName);
        return student != null ? student.getGradeLevel() : null;
    }

    private void loadBundleItems(String gradeLevel) {
        /* 
        // Get the bundle for the grade
        // TOGradeBundle bundle = CoolSuppliesFeatureSet4Controller.getBundleByGrade(gradeLevel); TODO: @claradupuis getBundleByGrade(string) doesn't exist

        if (bundle == null) {
            errorLabel.setText("No bundle available for the grade.");
            return;
        }

        // Get items in the bundle based on order level
        List<TOBundleItem> bundleItems = getBundleItems(bundle.getName(), orderLevel);

        // Populate the bundle TableView
        bundleItemsList.setAll(bundleItems);
        bundleTableView.setItems(bundleItemsList);
        */
    }

    private List<TOBundleItem> getBundleItems(String bundleName, String orderLevel) {
        // Implement logic to get bundle items based on order level
        // List<TOBundleItem> allBundleItems = CoolSuppliesFeatureSet7Controller.getBundleItems(bundleName); TODO: @claradupuis getBundleItems(string) doesn't exist

        List<TOBundleItem> filteredItems = new ArrayList<>();

        // for (TOBundleItem item : allBundleItems) {
        //     if (purchaseLevelAllows(item.getLevel(), orderLevel)) {
        //         filteredItems.add(item);
        //     }
        // }

        return filteredItems;
    }

    private boolean purchaseLevelAllows(String itemLevel, String orderLevel) {
        // Implement logic to compare itemLevel and orderLevel
        // Assuming levels are "Mandatory", "Recommended", "Optional"

        List<String> levels = List.of("Mandatory", "Recommended", "Optional");

        int itemLevelIndex = levels.indexOf(itemLevel);
        int orderLevelIndex = levels.indexOf(orderLevel);

        return itemLevelIndex <= orderLevelIndex;
    }

    private void loadAvailableItems() {
        /* 
        // Load all available items from the controller
        List<TOItem> items = CoolSuppliesFeatureSet7Controller.getItems(); TODO: @claradupuis getItems() doesn't exist in controller7

        itemsList.setAll(items);
        itemsTableView.setItems(itemsList);
        */
    }

    // Event handler for "View Order" button
    @FXML
    private void viewOrder(ActionEvent event) {
        // Implement navigation to Order View
    }

    // Other methods as needed
}
