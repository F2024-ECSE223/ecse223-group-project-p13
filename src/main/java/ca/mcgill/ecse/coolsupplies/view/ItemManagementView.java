package ca.mcgill.ecse.coolsupplies.view;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ItemManagementView {

    @FXML private TableView<BundleItemEntry> TableViewBundles;
    @FXML private TableView<ItemEntry> TableViewItems;
    @FXML private TableColumn<BundleItemEntry, String> BundleItems;
    @FXML private TableColumn<BundleItemEntry, String> BundlePrice;
    @FXML private TableColumn<BundleItemEntry, String> BundleDiscount;
    @FXML private Spinner<Integer> BundleQuantity;
    @FXML private TableColumn<ItemEntry, String> columnName;
    @FXML private TableColumn<ItemEntry, String> columnPrice;
    @FXML private TableColumn<ItemEntry, Integer> columnQuantity;
    @FXML private RadioButton optionalButton;
    @FXML private RadioButton recommendedButton;
    @FXML private RadioButton mandatoryButton;
    @FXML private ComboBox<String> gradeInput;

    @FXML private Label errorLabel;

    private ToggleGroup levelToggleGroup;

    private ObservableList<ItemEntry> itemEntries;
    private ObservableList<BundleItemEntry> bundleItemEntries;

    private TOGradeBundle bundle;

    private String studentGrade;
    private String orderLevel;
    private String orderNumber;

    private List<TOStudent> toParentStudents;

    // Method to set parameters from the previous UI
    public void setOrderParameters(String studentGrade, String orderLevel, String orderNumber) {
        this.studentGrade = studentGrade;
        this.orderLevel = orderLevel;
        this.orderNumber = orderNumber;
    }

    @FXML
    public void initialize() {
        // Get the current order
        TOOrder order = ViewOrdersParent.getOrder();

        // Get the parent's email from the order
        String parentEmail = order.getParentEmail();

        // Get the students associated with the parent using CoolSuppliesFeatureSet6Controller
        toParentStudents = CoolSuppliesFeatureSet6Controller.getStudentsOfParent(parentEmail);

        if (toParentStudents.isEmpty()) {
            errorLabel.setText("No students found for the parent.");
            return;
        }

        // Initialize the studentComboBox with the parent's students
        List<String> studentNames = toParentStudents.stream()
                                  .map(TOStudent::getName)
                                  .collect(Collectors.toList());
        gradeInput.setItems(FXCollections.observableArrayList(studentNames));

        // Set the current student in the ComboBox
        gradeInput.setValue(order.getStudentName());

        // Initialize order parameters
        TOStudent student = toParentStudents.stream()
                                .filter(s -> s.getName().equals(order.getStudentName()))
                                .findFirst()
                                .orElse(null);

        if (student == null) {
            errorLabel.setText("Student not found.");
            return;
        }

        setOrderParameters(student.getGradeLevel(), order.getLevel(), order.getNumber());

        if (studentGrade == null || orderLevel == null || orderNumber == null) {
            errorLabel.setText("Order parameters are not set.");
            return;
        }

        // Set up the RadioButtons
        levelToggleGroup = new ToggleGroup();
        mandatoryButton.setToggleGroup(levelToggleGroup);
        recommendedButton.setToggleGroup(levelToggleGroup);
        optionalButton.setToggleGroup(levelToggleGroup);

        // Select the current level
        if ("Mandatory".equalsIgnoreCase(orderLevel)) {
            mandatoryButton.setSelected(true);
        } else if ("Recommended".equalsIgnoreCase(orderLevel)) {
            recommendedButton.setSelected(true);
        } else if ("Optional".equalsIgnoreCase(orderLevel)) {
            optionalButton.setSelected(true);
        }

        // Add listeners for student and level changes
        setupTablesAndSpinners();
        addListeners();

        // Update the bundle and proceed with the rest of the initialization
        updateBundle();

        // Proceed with setting up tables and spinners
        

        // Initialize quantities based on existing order items
        initializeQuantities();

        // Add a listener to respond to changes in the levelToggleGroup
        levelToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selectedButton = (RadioButton) newToggle;
                String newOrderLevel = selectedButton.getText();
                handleOrderLevelChange(newOrderLevel);
            }
        });
    
}


   

private void addListeners() {
    // Listener for student changes
    gradeInput.valueProperty().addListener((obs, oldStudent, newStudent) -> {
        if (newStudent != null && !newStudent.equals(oldStudent)) {
            // Update the order's student in the backend
            String result = Iteration3Controller.updateOrder(orderLevel, newStudent, orderNumber);
            if (!result.isEmpty()) {
                errorLabel.setText(result);
                // Revert to old student
                gradeInput.setValue(oldStudent);
            } else {
                errorLabel.setText("");
                // Update studentGrade and bundle
                TOStudent newStudentObj = toParentStudents.stream()
                                          .filter(s -> s.getName().equals(newStudent))
                                          .findFirst()
                                          .orElse(null);
                if (newStudentObj != null) {
                    studentGrade = newStudentObj.getGradeLevel();
                    updateBundle();
                }
            }
        }
    });
}


    private void updateBundle() {
        // Fetch the bundle for the current grade
        List<TOGradeBundle> allBundles = CoolSuppliesFeatureSet4Controller.getBundles();
        bundle = null;
        for (TOGradeBundle b : allBundles) {
            if (b.getGradeLevel().equals(studentGrade)) {
                bundle = b;
                break;
            }
        }

        if (bundle == null) {
            errorLabel.setText("No bundle for grade " + studentGrade);
            bundleItemEntries = FXCollections.observableArrayList();
            TableViewBundles.setItems(bundleItemEntries);
            return;
        } else {
            errorLabel.setText("");
        }

        // Reload bundle items
        loadBundleItems();

        // Reset bundle quantity spinner
        BundleQuantity.getValueFactory().setValue(0);

        // Re-initialize quantities based on existing order items
        initializeQuantities();
    }

    private void setupTablesAndSpinners() {
        // Set up the bundle items table
        BundleItems.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
        BundlePrice.setCellValueFactory(cellData -> cellData.getValue().itemPriceProperty());
        BundleDiscount.setCellValueFactory(cellData -> cellData.getValue().discountProperty());

        // Set up the items table
        columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        columnPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());

        addSpinner();

        // Load items
        loadItems();

        // Load bundle items
        loadBundleItems();

        // Set up BundleQuantity spinner
        BundleQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0));
        BundleQuantity.valueProperty().addListener((obs, oldValue, newValue) -> {
            String result;

            if (newValue > 0) {
                // Try to add the bundle to the order
                result = Iteration3Controller.addItem(bundle.getName(), String.valueOf(newValue), orderNumber);

                // If the bundle is already in the order, update its quantity instead
                if (!result.isEmpty()) {
                    result = Iteration3Controller.updateOrderQuantity(bundle.getName(), String.valueOf(newValue), orderNumber);
                }
            } else {
                // Remove the bundle from the order
                result = Iteration3Controller.deleteItem(bundle.getName(), orderNumber);
            }

            if (result.isEmpty()) {
                errorLabel.setText(""); // Clear any error messages
            } else {
                errorLabel.setText(result); // Display the error message
            }
        });
    }

    private void loadBundleItems() {
        bundleItemEntries = FXCollections.observableArrayList();

        if (bundle == null) {
            // No bundle to load
            TableViewBundles.setItems(bundleItemEntries);
            return;
        }

        List<TOBundleItem> allBundleItems = CoolSuppliesFeatureSet5Controller.getBundleItems(bundle.getName());

        for (TOBundleItem bundleItem : allBundleItems) {
            String itemPurchaseLevel = bundleItem.getLevel();
            if (isLevelIncluded(itemPurchaseLevel, orderLevel)) {
                String itemName = bundleItem.getItemName();
                TOItem item = CoolSuppliesFeatureSet3Controller.getItem(itemName);

                if (item != null) {
                    String itemPrice = String.valueOf(item.getPrice());
                    String discount = String.valueOf(bundle.getDiscount());
                    bundleItemEntries.add(new BundleItemEntry(itemName, itemPrice, discount));
                }
            }
        }

        TableViewBundles.setItems(bundleItemEntries);
    }

    private boolean isLevelIncluded(String itemLevel, String orderLevel) {
        List<String> levels = Arrays.asList("Mandatory", "Recommended", "Optional");
        int itemLevelIndex = levels.indexOf(itemLevel);
        int orderLevelIndex = levels.indexOf(orderLevel);

        if (itemLevelIndex == -1 || orderLevelIndex == -1) {
            System.out.println("Invalid levels: itemLevel=" + itemLevel + ", orderLevel=" + orderLevel);
            return false;
        }

        return itemLevelIndex <= orderLevelIndex;
    }

    private void loadItems() {
        itemEntries = FXCollections.observableArrayList();

        List<TOItem> allItems = CoolSuppliesFeatureSet3Controller.getItems();

        for (TOItem item : allItems) {
            itemEntries.add(new ItemEntry(item.getName(), String.valueOf(item.getPrice()), 0));
        }

        TableViewItems.setItems(itemEntries);
    }

    private void initializeQuantities() {
        // Fetch the order
        TOOrder currentOrder = Iteration3Controller.viewOrder(orderNumber);

        if (currentOrder == null) {
            errorLabel.setText("Order " + orderNumber + " does not exist.");
            return;
        }

        // Update studentGrade and orderLevel from currentOrder
        studentGrade = currentOrder.getLevel();
        orderLevel = currentOrder.getLevel();

        // Update UI elements
        gradeInput.setValue(currentOrder.getStudentName());
        if ("Mandatory".equalsIgnoreCase(orderLevel)) {
            mandatoryButton.setSelected(true);
        } else if ("Recommended".equalsIgnoreCase(orderLevel)) {
            recommendedButton.setSelected(true);
        } else if ("Optional".equalsIgnoreCase(orderLevel)) {
            optionalButton.setSelected(true);
        }

        // Get existing items in the order and set quantities in the spinners
        List<TOOrderItem> orderItems = currentOrder.getItems();

        for (ItemEntry itemEntry : itemEntries) {
            boolean foundInOrder = false;
            for (TOOrderItem orderItem : orderItems) {
                if (orderItem.getName().equals(itemEntry.getName())) {
                    int qty = Integer.parseInt(orderItem.getQuantity());
                    itemEntry.setQuantity(qty);
                    itemEntry.setInOrder(true); // Mark as in order
                    foundInOrder = true;
                    break;
                }
            }
            if (!foundInOrder) {
                itemEntry.setQuantity(0);
                itemEntry.setInOrder(false); // Not in order
            }
        }

        TableViewItems.refresh();

        // Set the quantity for the bundle
        int bundleQuantity = 0;
        for (TOOrderItem orderItem : orderItems) {
            if (orderItem.getName().equals(bundle.getName())) {
                bundleQuantity = Integer.parseInt(orderItem.getQuantity());
                break;
            }
        }
        BundleQuantity.getValueFactory().setValue(bundleQuantity);
    }

    private void addSpinner() {
        columnQuantity.setCellFactory(column -> new TableCell<ItemEntry, Integer>() {
            private final Spinner<Integer> spinner = new Spinner<>(0, Integer.MAX_VALUE, 0);

            {
                spinner.setEditable(true);
                spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                    ItemEntry itemEntry = getTableView().getItems().get(getIndex());
                    String result;

                    if (newValue > 0) {
                        if (!itemEntry.isInOrder()) {
                            // Item is not in order, add it
                            result = Iteration3Controller.addItem(itemEntry.getName(), String.valueOf(newValue), orderNumber);
                            if (result.isEmpty()) {
                                itemEntry.setInOrder(true);
                                itemEntry.setQuantity(newValue);
                                errorLabel.setText("");
                            } else {
                                errorLabel.setText(result);
                            }
                        } else {
                            // Item is already in order, update quantity
                            result = Iteration3Controller.updateOrderQuantity(itemEntry.getName(), String.valueOf(newValue), orderNumber);
                            if (result.isEmpty()) {
                                itemEntry.setQuantity(newValue);
                                errorLabel.setText("");
                            } else {
                                errorLabel.setText(result);
                            }
                        }
                    } else {
                        // Remove item from order
                        result = Iteration3Controller.deleteItem(itemEntry.getName(), orderNumber);
                        if (result.isEmpty()) {
                            itemEntry.setInOrder(false);
                            itemEntry.setQuantity(0);
                            errorLabel.setText("");
                        } else {
                            errorLabel.setText(result);
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    spinner.getValueFactory().setValue(value == null ? 0 : value);
                    setGraphic(spinner);
                }
            }
        });

        columnQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
    }

    // Inner class for bundle items
    public static class BundleItemEntry {
        private SimpleStringProperty itemName;
        private SimpleStringProperty itemPrice;
        private SimpleStringProperty discount;

        public BundleItemEntry(String itemName, String itemPrice, String discount) {
            this.itemName = new SimpleStringProperty(itemName);
            this.itemPrice = new SimpleStringProperty(itemPrice);
            this.discount = new SimpleStringProperty(discount);
        }

        public String getItemName() { return itemName.get(); }
        public void setItemName(String itemName) { this.itemName.set(itemName); }
        public SimpleStringProperty itemNameProperty() { return itemName; }

        public String getItemPrice() { return itemPrice.get(); }
        public void setItemPrice(String itemPrice) { this.itemPrice.set(itemPrice); }
        public SimpleStringProperty itemPriceProperty() { return itemPrice; }

        public String getDiscount() { return discount.get(); }
        public void setDiscount(String discount) { this.discount.set(discount); }
        public SimpleStringProperty discountProperty() { return discount; }
    }

    // Inner class for items
    public static class ItemEntry {
        private SimpleStringProperty name;
        private SimpleStringProperty price;
        private SimpleIntegerProperty quantity;
        private boolean isInOrder; // New field

        public ItemEntry(String name, String price, int quantity) {
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleStringProperty(price);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.isInOrder = quantity > 0;
        }

        public String getName() { return name.get(); }
        public void setName(String name) { this.name.set(name); }
        public SimpleStringProperty nameProperty() { return name; }

        public String getPrice() { return price.get(); }
        public void setPrice(String price) { this.price.set(price); }
        public SimpleStringProperty priceProperty() { return price; }

        public boolean isInOrder() {
            return isInOrder;
        }

        public void setInOrder(boolean inOrder) {
            this.isInOrder = inOrder;
        }

        public int getQuantity() {
            return quantity.get();
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }

        public SimpleIntegerProperty quantityProperty() {
            return quantity;
        }
    }

    private void handleOrderLevelChange(String newOrderLevel) {
        // Save the old order level in case of rollback
        String oldOrderLevel = orderLevel;
        orderLevel = newOrderLevel;
    
        // Update the order level in the backend
        String result = Iteration3Controller.updateOrder(orderLevel, gradeInput.getValue(), orderNumber);
    
        if (!result.isEmpty()) {
            // If there is an error, revert to the old order level and display the error
            errorLabel.setText(result);
            orderLevel = oldOrderLevel;
    
            // Reset the selected toggle to the previous one
            levelToggleGroup.getToggles().stream()
                    .filter(toggle -> ((RadioButton) toggle).getText().equalsIgnoreCase(oldOrderLevel))
                    .findFirst()
                    .ifPresent(levelToggleGroup::selectToggle);
        } else {
            // Clear any previous error messages
            errorLabel.setText("");
    
            // Reload data based on the new order level
            loadBundleItems();
        }
    }
}
