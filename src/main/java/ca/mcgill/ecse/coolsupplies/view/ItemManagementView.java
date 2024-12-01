package ca.mcgill.ecse.coolsupplies.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @FXML private Label errorLabel;
    @FXML private Button viewOrder;

    private ObservableList<ItemEntry> itemEntries;
    private ObservableList<BundleItemEntry> bundleItemEntries;

    private TOGradeBundle bundle;
    private List<TOBundleItem> bundleItems;
    private Set<String> existingItemNames;

    private String studentGrade;
    private String orderLevel;
    private String orderNumber;

    // Method to set parameters from the previous UI
    public void setOrderParameters(String studentGrade, String orderLevel, String orderNumber) {
        this.studentGrade = studentGrade;
        this.orderLevel = orderLevel;
        this.orderNumber = orderNumber;
    }

    @FXML
    public void initialize() {
        // Ensure order parameters are set
        
        setOrderParameters("6th", "Mandatory", "2");
        if (studentGrade == null || orderLevel == null || orderNumber == null) {
            errorLabel.setText("Order parameters are not set.");
            return;
        }

        List<TOGradeBundle> allBundles = CoolSuppliesFeatureSet4Controller.getBundles();
for (TOGradeBundle b : allBundles) {
    System.out.println("Bundle found: Name=" + b.getName() + ", Grade=" + b.getGradeLevel());
    if (b.getGradeLevel().equals(studentGrade)) {
        bundle = b;
        break;
    }
}

if (bundle == null) {
    System.out.println("No bundle found for grade: " + studentGrade);
    errorLabel.setText("No bundle for grade " + studentGrade);
    return;
} else {
    System.out.println("Selected bundle: " + bundle.getName() + ", Discount=" + bundle.getDiscount());
}
        // Set up the bundle items table
        BundleItems.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
        BundlePrice.setCellValueFactory(cellData -> cellData.getValue().itemPriceProperty());
        BundleDiscount.setCellValueFactory(cellData -> cellData.getValue().discountProperty());

        // Set up the items table
        columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        columnPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());

        // Add spinners
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
        

        // Initialize quantities based on existing order items
        initializeQuantities();
    }

    private void loadBundleItems() {
        bundleItemEntries = FXCollections.observableArrayList();
    
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

        // Get existing items in the order and set quantities in the spinners
        List<TOOrderItem> orderItems = currentOrder.getItems();


        for (ItemEntry itemEntry : itemEntries) {
            for (TOOrderItem orderItem : orderItems) {
                if (orderItem.getName().equals(itemEntry.getName())) {
                    itemEntry.setQuantity(Integer.parseInt(orderItem.getQuantity()));
                    break;
                }
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
                        // If old value was 0, add the item to the order
                        if (oldValue == 0) {
                            errorLabel.setText("Adding item to order: " + itemEntry.getName() + ", Quantity: " + newValue);
                            result = Iteration3Controller.addItem(itemEntry.getName(), String.valueOf(newValue), orderNumber);
                        } else {
                            errorLabel.setText("Updating item quantity in order: " + itemEntry.getName() + ", Quantity: " + newValue);
                            result = Iteration3Controller.updateOrderQuantity(itemEntry.getName(), String.valueOf(newValue), orderNumber);
                        }
                    } else {
                         errorLabel.setText("Removing item from order: " + itemEntry.getName());
                        result = Iteration3Controller.deleteItem(itemEntry.getName(), orderNumber);
                    }
    
                    // Handle result
                    if (result.isEmpty()) {
                        errorLabel.setText(""); // Clear error
                    } else {
                        errorLabel.setText(result); // Display error message
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

        public ItemEntry(String name, String price, int quantity) {
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleStringProperty(price);
            this.quantity = new SimpleIntegerProperty(quantity);
        }

        public String getName() { return name.get(); }
        public void setName(String name) { this.name.set(name); }
        public SimpleStringProperty nameProperty() { return name; }

        public String getPrice() { return price.get(); }
        public void setPrice(String price) { this.price.set(price); }
        public SimpleStringProperty priceProperty() { return price; }

        public int getQuantity() { return quantity.get(); }
        public void setQuantity(int quantity) { this.quantity.set(quantity); }
        public SimpleIntegerProperty quantityProperty() { return quantity; }
    }
}
