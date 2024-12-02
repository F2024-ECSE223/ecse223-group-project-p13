package ca.mcgill.ecse.coolsupplies.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import atlantafx.base.theme.Styles;
import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditBundleView {

    @FXML
    private Label custom; 

    @FXML
    private TextField bundleNameText;

    @FXML
    private ComboBox<String> newGradeOptions;

    @FXML
    private TextField discountValue;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<ItemEntry> itemsTable;

    @FXML
    private TableColumn<ItemEntry, String> itemNameColumn;

    @FXML
    private TableColumn<ItemEntry, Integer> quantityColumn;

    @FXML
    private TableColumn<ItemEntry, String> purchaseLevelColumn;

    private TOGradeBundle bundle;

    private ObservableList<ItemEntry> itemEntries;

    private List<TOBundleItem> bundleItems;

    private Set<String> existingItemNames;

    /**
    * @author Nil Akkurt
    * 
    * Initializes the EditBundleView. Sets up the grade options, initializes table columns, 
    * and styles the delete and save buttons.
    * 
    * @return void
    */
    @FXML
    public void initialize() {
        // Initialize the grade options
        List<String> gradeLevels = getGradeLevels();
        newGradeOptions.setItems(FXCollections.observableArrayList(gradeLevels));

        // Initialize the table columns
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        // Set up custom cell factories
        setupQuantityColumn();
        setupPurchaseLevelColumn();
        deleteButton.getStyleClass().add(Styles.DANGER);
        saveButton.getStyleClass().add(Styles.SUCCESS);
    }

    /**
    * @author Nil Akkurt
    * 
    * Initializes the view with data from the given bundle.
    * 
    * @param bundle The grade bundle to be edited, containing initial data for the UI components.
    * @return void
    */
    public void initData(TOGradeBundle bundle) {
        this.bundle = bundle;
        // Set initial data in the UI components
        bundleNameText.setText(bundle.getName());
        newGradeOptions.setValue(bundle.getGradeLevel());
        discountValue.setText(String.valueOf(bundle.getDiscount()));
        custom.setText("Editing Bundle: " + bundle.getName() + ", Grade: " + bundle.getGradeLevel() + ", Discount: " + bundle.getDiscount());

        // Load items and initialize the table
        loadItems();
    }

    /**
    * @author Edouard Dupont
    * 
    * Loads all available items and prepares the items table with quantities and purchase levels 
    * for the current bundle.
    * 
    * @return void
    */
    private void loadItems() {
        // Fetch all items from your controller
        List<TOItem> allItems = getAllItems();

        // Fetch bundle items for the current bundle
        bundleItems = CoolSuppliesFeatureSet5Controller.getBundleItems(bundle.getName());

        existingItemNames = new HashSet<>();
        for (TOBundleItem bi : bundleItems) {
            existingItemNames.add(bi.getItemName());
        }

        itemEntries = FXCollections.observableArrayList();

        for (TOItem item : allItems) {
            // Check if the item is in the bundle and get its quantity and purchase level
            int quantity = getQuantityForItem(item);
            String purchaseLevel = getPurchaseLevelForItem(item);

            itemEntries.add(new ItemEntry(item.getName(), quantity, purchaseLevel));
        }

        itemsTable.setItems(itemEntries);
    }

    /**
    * @author Edouard Dupont
    * 
    * Retrieves the quantity of the specified item in the bundle.
    * 
    * @param item The item for which the quantity is retrieved.
    * @return The quantity of the specified item in the bundle.
    */
    private int getQuantityForItem(TOItem item) {
        for (TOBundleItem bundleItem : bundleItems) {
            if (bundleItem.getItemName().equals(item.getName())) {
                return bundleItem.getQuantity();
            }
        }
        return 0;
    }

    /**
    * @author Edouard Dupont
    * 
    * Retrieves the purchase level of the specified item in the bundle.
    * 
    * @param item The item for which the purchase level is retrieved.
    * @return The purchase level of the specified item in the bundle.
    */
    private String getPurchaseLevelForItem(TOItem item) {
        for (TOBundleItem bundleItem : bundleItems) {
            if (bundleItem.getItemName().equals(item.getName())) {
                return bundleItem.getLevel();
            }
        }
        return "Optional"; // Default value
    }

    /**
    * @author Edouard Dupont
    * 
    * Sets up the quantity column in the items table with editable spinners for modifying quantities.
    * 
    * @return void
    */
    private void setupQuantityColumn() {
        quantityColumn.setCellFactory(column -> new TableCell<ItemEntry, Integer>() {
            private final Spinner<Integer> spinner = new Spinner<>(0, Integer.MAX_VALUE, 0);

            {
                spinner.setEditable(true);
                spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                    ItemEntry item = getTableView().getItems().get(getIndex());
                    item.setQuantity(newValue);
                });
            }

            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    spinner.getValueFactory().setValue(value);
                    setGraphic(spinner);
                }
            }
        });

        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
    }

    /**
    * @author Edouard Dupont
    * 
    * Sets up the purchase level column in the items table with editable combo boxes for selecting 
    * purchase levels.
    * 
    * @return void
    */
    private void setupPurchaseLevelColumn() {
        purchaseLevelColumn.setCellFactory(column -> new TableCell<ItemEntry, String>() {
            private final ComboBox<String> comboBox = new ComboBox<>();

            {
                comboBox.getItems().addAll("Optional", "Recommended", "Mandatory");
                comboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
                    ItemEntry item = getTableView().getItems().get(getIndex());
                    item.setPurchaseLevel(newValue);
                });
            }

            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    comboBox.setValue(value);
                    setGraphic(comboBox);
                }
            }
        });

        purchaseLevelColumn.setCellValueFactory(cellData -> cellData.getValue().purchaseLevelProperty());
    }

    /**
    * @author Edouard Dupont
    * 
    * Retrieves all available items using the controller.
    * 
    * @return A list of all available items.
    */
    private List<TOItem> getAllItems() {
        return CoolSuppliesFeatureSet3Controller.getItems();
    }

    /**
    * @author Edouard Dupont
    * 
    * Retrieves all available grade levels using the controller.
    * 
    * @return A list of grade levels.
    */
    private List<String> getGradeLevels() {
        List<TOGrade> grades = CoolSuppliesFeatureSet7Controller.getGrades();
        List<String> gradeLevels = new ArrayList<>();
        for (TOGrade grade : grades) {
            gradeLevels.add(grade.getLevel());
        }
        return gradeLevels;
    }

    public static class ItemEntry {
        private final SimpleStringProperty itemName;
        private final SimpleIntegerProperty quantity;
        private final SimpleStringProperty purchaseLevel;

        public ItemEntry(String itemName, int quantity, String purchaseLevel) {
            this.itemName = new SimpleStringProperty(itemName);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.purchaseLevel = new SimpleStringProperty(purchaseLevel);
        }

        public String getItemName() {
            return itemName.get();
        }

        public void setItemName(String itemName) {
            this.itemName.set(itemName);
        }

        public SimpleStringProperty itemNameProperty() {
            return itemName;
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

        public String getPurchaseLevel() {
            return purchaseLevel.get();
        }

        public void setPurchaseLevel(String purchaseLevel) {
            this.purchaseLevel.set(purchaseLevel);
        }

        public SimpleStringProperty purchaseLevelProperty() {
            return purchaseLevel;
        }
    }

    /**
    * @author Nil Akkurt
    * 
    * Saves changes made to the bundle, including updating its details and items, and validating inputs.
    * 
    * @return void
    */
    @FXML
    private void saveChanges() {
        // Gather updated data
    String newBundleName = bundleNameText.getText();
    String newGradeLevel = newGradeOptions.getValue();
    int newDiscount;
    try {
        newDiscount = Integer.parseInt(discountValue.getText());
    } catch (NumberFormatException e) {
        // Handle invalid discount input
        showAlert("Invalid discount value.");
        return;
    }

    // Update the bundle details using your controller methods
    try {
        // Update bundle details
        String updateResult = CoolSuppliesFeatureSet4Controller.updateBundle(bundle.getName(), newBundleName, newDiscount, newGradeLevel);

        if (!updateResult.isEmpty()) {
                showAlert("Failed to update bundle: " + updateResult);
                return;
            }

        // Refresh the bundle and bundle items after update
        bundle = CoolSuppliesFeatureSet4Controller.getBundle(newBundleName);
        bundleItems = CoolSuppliesFeatureSet5Controller.getBundleItems(newBundleName);
        existingItemNames = new HashSet<>();
        for (TOBundleItem bi : bundleItems) {
            existingItemNames.add(bi.getItemName());
        }

        // Update items in the bundle
        for (ItemEntry entry : itemEntries) {
            if (entry.getQuantity() > 0) {
                if (existingItemNames.contains(entry.getItemName())) {
                    // Update item in the bundle
                    String result = CoolSuppliesFeatureSet5Controller.updateBundleItem(entry.getItemName(), newBundleName, entry.getQuantity(), entry.getPurchaseLevel());
                    if (!result.contains("successfully")) {
                        showAlert("Failed to update item: " + result);
                        return;
                    }
                } else {
                    // Add item to the bundle
                    String result = CoolSuppliesFeatureSet5Controller.addBundleItem(entry.getQuantity(), entry.getPurchaseLevel(), entry.getItemName(), newBundleName);
                    if (!result.contains("successfully")) {
                        showAlert("Failed to add item: " + result);
                        return;
                    }
                }
            } else {
                if (existingItemNames.contains(entry.getItemName())) {
                    // Remove item from the bundle
                    String result = CoolSuppliesFeatureSet5Controller.deleteBundleItem(entry.getItemName(), newBundleName);
                    if (!result.contains("successfully")) {
                        showAlert("Failed to delete item: " + result);
                        return;
                    }
                }
                // If quantity is zero and item is not in the bundle, do nothing
            }
        }

        List<TOGradeBundle> allBundles = CoolSuppliesFeatureSet4Controller.getBundles();

    // Iterate over each bundle
    for (TOGradeBundle bundle : allBundles) {
        // Get the number of items in the bundle
        List<TOBundleItem> bundleItems = CoolSuppliesFeatureSet5Controller.getBundleItems(bundle.getName()); 
        int nbItems = 0;

        for(TOBundleItem item : bundleItems){
            nbItems += item.getQuantity();
        }
        // If the bundle has fewer than 2 items and discount is not zero
        if (nbItems < 2 && bundle.getDiscount() != 0) {
            // Set the discount to zero
            String result = CoolSuppliesFeatureSet4Controller.updateBundle(bundle.getName(), bundle.getName(), 0, bundle.getGradeLevel());
            if (!result.isEmpty()) {
                // Handle any error messages
                showAlert("Error updating bundle discount: " + result);
            }
        }
    }
  // Show success message
  showAlert("Bundle updated successfully.");
  // Close the window after saving
  saveButton.getScene().getWindow().hide();

}
       
 catch (Exception e) {
        // Handle exceptions
        showAlert("Failed to update bundle: " + e.getMessage());
    }
    
    }

    /**
    * @author Nil Akkurt
    * 
    * Deletes the currently loaded bundle after user confirmation. Displays success or failure messages.
    * 
    * @return void
    */
    @FXML
    private void deleteBundle() {
        // Confirm deletion
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this bundle?", ButtonType.YES, ButtonType.NO);
    alert.showAndWait();

    if (alert.getResult() == ButtonType.YES) {
        try {
            String result = CoolSuppliesFeatureSet4Controller.deleteBundle(bundle.getName());
            if (!result.isEmpty()) {
                showAlert("Failed to delete bundle: " + result);
                return;
            }
            showAlert("Bundle deleted successfully.");
            // Close the window or navigate away
            deleteButton.getScene().getWindow().hide();
        } catch (Exception e) {
            showAlert("Failed to delete bundle: " + e.getMessage());
        }
    }

        deleteButton.getScene().getWindow().hide();

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


