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
import javafx.stage.Stage;

public class ItemManagementView {

    @FXML private TableView<BundleItemEntry> TableViewBundles;
    @FXML private TableView<ItemEntry> TableViewItems;
    @FXML private TableColumn<BundleItemEntry, String> BundleItems;
    @FXML private TableColumn<BundleItemEntry, String> BundlePrice;
    @FXML private TableColumn<BundleItemEntry, String> BundleDiscount;
    @FXML private TableColumn<BundleItemEntry, Integer> BundleAmount;

    @FXML private Spinner<Integer> BundleQuantity;
    @FXML private TableColumn<ItemEntry, String> columnName;
    @FXML private TableColumn<ItemEntry, String> columnPrice;
    @FXML private TableColumn<ItemEntry, Integer> columnQuantity;
    @FXML private RadioButton optionalButton;
    @FXML private RadioButton recommendedButton;
    @FXML private RadioButton mandatoryButton;
    @FXML private ComboBox<String> gradeInput;
    @FXML private ComboBox<String> BundleChoice;

    @FXML private Label errorLabel;

    @FXML private Button Save; // Declare the Save button

    private ToggleGroup levelToggleGroup;

    private ObservableList<ItemEntry> itemEntries;
    private ObservableList<BundleItemEntry> bundleItemEntries;

    private TOGradeBundle bundle;
    private List<TOGradeBundle> allBundles;

    private String studentGrade;
    private String orderLevel;
    private String orderNumber;

    private List<TOStudent> ListParents;

    private int bundleQuantity = 0; 

    /**
     * this method sets the parameters of the UI
     * @author Clara Dupuis
     * @param studentGrade a string that represents the name of the student 
     * @param orderLevel a string that represents the order Level
     * @param orderNumber a string that represents the order id
     */
    public void setOrderParameters(String studentGrade, String orderLevel, String orderNumber) {
        this.studentGrade = studentGrade;
        this.orderLevel = orderLevel;
        this.orderNumber = orderNumber;
    }

    /**
     * this method initializes the view by setting the tables and filling them with the items and the appropriate bundle. It also initializes the radio buttons, the student and the bundle drop down menus
     * @author Clara Dupuis
     */
    @FXML
    public void initialize() {

      
        TOOrder order = ViewOrdersParent.getOrder();
        String parentEmail = order.getParentEmail();
        ListParents = CoolSuppliesFeatureSet6Controller.getStudentsOfParent(parentEmail);

        if (ListParents.isEmpty()) {
            errorLabel.setText("No students found for the parent.");
            return;
        }

   
        List<String> studentNames = ListParents.stream()
                                      .map(TOStudent::getName)
                                      .collect(Collectors.toList());
        gradeInput.setItems(FXCollections.observableArrayList(studentNames));

  
        gradeInput.setValue(order.getStudentName());

        TOStudent student = ListParents.stream()
                                .filter(s -> s.getName().equals(order.getStudentName()))
                                .findFirst()
                                .orElse(null);

        if (student == null) {
            errorLabel.setText("Student not found.");
            return;
        }

        setOrderParameters(student.getGradeLevel(), order.getLevel(), order.getNumber());

        if (orderLevel == null || orderNumber == null) {
            errorLabel.setText("Order parameters are not set.");
            return;
        }

        
        levelToggleGroup = new ToggleGroup();
        mandatoryButton.setToggleGroup(levelToggleGroup);
        recommendedButton.setToggleGroup(levelToggleGroup);
        optionalButton.setToggleGroup(levelToggleGroup);

        if ("Mandatory".equalsIgnoreCase(orderLevel)) {
            mandatoryButton.setSelected(true);
        } else if ("Recommended".equalsIgnoreCase(orderLevel)) {
            recommendedButton.setSelected(true);
        } else if ("Optional".equalsIgnoreCase(orderLevel)) {
            optionalButton.setSelected(true);
        }

        allBundles = CoolSuppliesFeatureSet4Controller.getBundles();

    
        List<String> bundleNames = allBundles.stream()
                                  .map(TOGradeBundle::getName)
                                  .collect(Collectors.toList());

        BundleChoice.setItems(FXCollections.observableArrayList(bundleNames));

        setupTablesAndSpinners();

        if (!bundleNames.isEmpty()) {
            BundleChoice.setValue(bundleNames.get(0));
       
            bundle = allBundles.get(0);
            initializeQuantities();
          
            updateBundle();
        }

        BundleChoice.valueProperty().addListener((obs, oldBundleName, newBundleName) -> {
            if (newBundleName != null && !newBundleName.equals(oldBundleName)) {
              
                bundle = allBundles.stream()
                                   .filter(b -> b.getName().equals(newBundleName))
                                   .findFirst()
                                   .orElse(null);

                if (bundle == null) {
                    errorLabel.setText("Selected bundle not found.");
               
                    bundleItemEntries.clear();
                    TableViewBundles.setItems(bundleItemEntries);
                } else {
                    errorLabel.setText("");
                    initializeQuantities();
                    updateBundle();
                }
            }
        });

        addListeners();

        levelToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selectedButton = (RadioButton) newToggle;
                String newOrderLevel = selectedButton.getText();
                handleOrderLevelChange(newOrderLevel);
            }
        });
    }

    /**
     * This method adds listeners to get the grade when the student is selected 
     * @author Clara Dupuis
     */
    private void addListeners() {
        gradeInput.valueProperty().addListener((obs, oldStudent, newStudent) -> {
            if (newStudent != null && !newStudent.equals(oldStudent)) {
                String result = Iteration3Controller.updateOrder(orderLevel, newStudent, orderNumber);
                if (!result.isEmpty()) {
                    errorLabel.setText(result);
                    gradeInput.setValue(oldStudent);
                } else {
                    errorLabel.setText("");
                    TOStudent newStudentObj = ListParents.stream()
                                              .filter(s -> s.getName().equals(newStudent))
                                              .findFirst()
                                              .orElse(null);
                    if (newStudentObj != null) {
                        studentGrade = newStudentObj.getGradeLevel();
                    }
                }
            }
        });
    }

    /**
     * this method updates the bundle according to the bundle inputed in the drop down menu
     * @author Clara Dupuis
     */
    private void updateBundle() {
        if (bundle == null) {
            errorLabel.setText("No bundle selected.");
            bundleItemEntries = FXCollections.observableArrayList();
            TableViewBundles.setItems(bundleItemEntries);
            return;
        } else {
            errorLabel.setText("");
        }

        // Load bundle items
        loadBundleItems();

        // Set the spinner to the current bundle quantity
   
        BundleQuantity.getValueFactory().setValue(bundleQuantity);
        BundleQuantity.setVisible(!bundleItemEntries.isEmpty());
    }

    /**
     * This method sets ups the tables and the spinners used to add items/bundles to the order
     * @author Clara Dupuis
     */

    private void setupTablesAndSpinners() {
     
        BundleItems.setCellValueFactory(cellData -> cellData.getValue().itemName());
        BundlePrice.setCellValueFactory(cellData -> cellData.getValue().itemPrice());
        BundleDiscount.setCellValueFactory(cellData -> cellData.getValue().discountBundle());
        BundleAmount.setCellValueFactory(cellData -> cellData.getValue().amount().asObject());

     
        columnName.setCellValueFactory(cellData -> cellData.getValue().name());
        columnPrice.setCellValueFactory(cellData -> cellData.getValue().price());

        addSpinner();

        loadItems();

    
        BundleQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0));
        BundleQuantity.valueProperty().addListener((obs, oldValue, newValue) -> {
            String result;

            if (newValue > 0) {
              
                result = Iteration3Controller.addItem(bundle.getName(), String.valueOf(newValue), orderNumber);
                if (!result.isEmpty()) {
                    result = Iteration3Controller.updateOrderQuantity(bundle.getName(), String.valueOf(newValue), orderNumber);
                }
                bundleQuantity = newValue;
            } else {
                result = Iteration3Controller.deleteItem(bundle.getName(), orderNumber);
                bundleQuantity = 0;
            }

            if (result.isEmpty()) {
                errorLabel.setText("");
            } else {
                errorLabel.setText(result);
            }
        });
    }

    /**
     * This method loads the items of the bundle according to the bundle selected in the drop down menu
     * @author Clara Dupuis
     */

    private void loadBundleItems() {
        bundleItemEntries = FXCollections.observableArrayList();

        if (bundle == null) {
    
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

                    int amountPerBundle = bundleItem.getQuantity(); 

                    bundleItemEntries.add(new BundleItemEntry(itemName, itemPrice, discount, amountPerBundle));
                }
            }
        }

        TableViewBundles.setItems(bundleItemEntries);
    }

    /**
     * This method verifys what items to display in the bundle table according to the order level inputted in the radio button
     * @author Clara Dupuis
     * @param itemLevel
     * @param orderLevel
     * @return boolean to determine if item is in bundle or not 
     */

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

    /**
     * This method loads all of the items available to purchase in the application
     * @author Clara Dupuis
     */
    private void loadItems() {
        itemEntries = FXCollections.observableArrayList();

        List<TOItem> allItems = CoolSuppliesFeatureSet3Controller.getItems();

        for (TOItem item : allItems) {
            itemEntries.add(new ItemEntry(item.getName(), String.valueOf(item.getPrice()), 0));
        }

        TableViewItems.setItems(itemEntries);
    }

    /**
     * this method initializes the values in the spinners to be those of the number of items already in the order
     * @author Clara Dupuis
     */
    private void initializeQuantities() {
 
        TOOrder currentOrder = Iteration3Controller.viewOrder(orderNumber);

        if (currentOrder == null) {
            errorLabel.setText("Order " + orderNumber + " does not exist.");
            return;
        }

        gradeInput.setValue(currentOrder.getStudentName());
        if ("Mandatory".equalsIgnoreCase(orderLevel)) {
            mandatoryButton.setSelected(true);
        } else if ("Recommended".equalsIgnoreCase(orderLevel)) {
            recommendedButton.setSelected(true);
        } else if ("Optional".equalsIgnoreCase(orderLevel)) {
            optionalButton.setSelected(true);
        }

        List<TOOrderItem> orderItems = currentOrder.getItems();

        for (ItemEntry itemEntry : itemEntries) {
            boolean foundInOrder = false;
            for (TOOrderItem orderItem : orderItems) {
                if (orderItem.getName().equals(itemEntry.getName())) {
                    int qty = Integer.parseInt(orderItem.getQuantity());
                    itemEntry.setQuantity(qty);
                    itemEntry.setInOrder(true);
                    foundInOrder = true;
                    break;
                }
            }
            if (!foundInOrder) {
                itemEntry.setQuantity(0);
                itemEntry.setInOrder(false);
            }
        }

        TableViewItems.refresh();

        bundleQuantity = 0;
        for (TOOrderItem orderItem : orderItems) {
            if (orderItem.getName().equals(bundle.getName())) {
                bundleQuantity = Integer.parseInt(orderItem.getQuantity());
                break;
            }
        }

        BundleQuantity.getValueFactory().setValue(bundleQuantity);
    }

    /**
     * this method adds spinners in the table with all of the items 
     * @author Clara Dupuis 
     */
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
                       
                            result = Iteration3Controller.addItem(itemEntry.getName(), String.valueOf(newValue), orderNumber);
                            if (result.isEmpty()) {
                                itemEntry.setInOrder(true);
                                itemEntry.setQuantity(newValue);
                                errorLabel.setText("");
                            } else {
                                errorLabel.setText(result);
                            }
                        } else {
                            
                            result = Iteration3Controller.updateOrderQuantity(itemEntry.getName(), String.valueOf(newValue), orderNumber);
                            if (result.isEmpty()) {
                                itemEntry.setQuantity(newValue);
                                errorLabel.setText("");
                            } else {
                                errorLabel.setText(result);
                            }
                        }
                    } else {
                    
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
                    if (spinner.getValueFactory() == null) {
                        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, value == null ? 0 : value));
                    }
                    spinner.getValueFactory().setValue(value == null ? 0 : value);
                    setGraphic(spinner);
                }
            }
        });

        columnQuantity.setCellValueFactory(cellData -> cellData.getValue().quantity().asObject());
    }

  
    public static class BundleItemEntry {
        private SimpleStringProperty itemName;
        private SimpleStringProperty itemPrice;
        private SimpleStringProperty discount;
        private SimpleIntegerProperty amount;

        /**
         * this method sets the parameters for the BundleItemEntry class
         * @author
         * @param itemName a string representing the name of the student
         * @param itemPrice a string representing tge price of the item
         * @param discount a string representing the discount of the bundle
         * @param amount a string representing the number of items bought
         */

        public BundleItemEntry(String itemName, String itemPrice, String discount, int amount) {
            this.itemName = new SimpleStringProperty(itemName);
            this.itemPrice = new SimpleStringProperty(itemPrice);
            this.discount = new SimpleStringProperty(discount);
            this.amount = new SimpleIntegerProperty(amount);
        }

        /**
         * This methods gets the name of the item in the bundle
         * @author Clara Dupuis
         * @return string representing the name of the item
         */
        public String getItemName() { 
            return itemName.get(); }

        /**
         * This method sets the name of an item in the bundle 
         * @author Clara Dupuis
         * @param itemName a string representing the name that the item is given
         */
        public void setItemName(String itemName) { 
            this.itemName.set(itemName); }
        
        /**
         * This method returns a simpleStringProperty representing the name of the item in the bundle
         * @author Clara Dupuis
         * @return a SimpleStringProperty for the name of the item
         */
        public SimpleStringProperty itemName() { return itemName; }

        /**
         * This method returns a String representing the price of the item in the bundle
         * @author Clara Dupuis
         * @return a String for the price of the item
         */
        public String getItemPrice() { return itemPrice.get(); }

        /**
         * This method sets the price of the item 
         * @author Clara Dupuis
         */
        public void setItemPrice(String itemPrice) { this.itemPrice.set(itemPrice); }

        /**
         * This method returns a simpleStringProperty representing the price of the item in the bundle
         * @author Clara Dupuis
         * @return a SimpleStringProperty for the price of the item
         */
        public SimpleStringProperty itemPrice() { return itemPrice; }

        /**
         * This method returns a String representing the discount of the item in the bundle
         * @author Clara Dupuis
         * @return a String for the discount of the item
         */
        public String getDiscount() { return discount.get(); }

        /**
         * This method sets the discount of the item in the bundle
         * @author Clara Dupuis
         */
        public void setDiscount(String discount) { this.discount.set(discount); }

        /**
         * This method returns a simpleStringProperty representing the discount of the item in the bundle
         * @author Clara Dupuis
         * @return a SimpleStringProperty for the discount of the item
         */
        public SimpleStringProperty discountBundle() { return discount; }

        /**
         * This method returns a integer representing the number of items ordering
         * @author Clara Dupuis
         * @return an integer for the number of items ordering 
         */
        public int getAmount() { return amount.get(); }

        /**
         * This method sets the amount of items ordering
         * @author Clara Dupuis
         */
        public void setAmount(int amount) { this.amount.set(amount); }

       /**
         * This method returns a simpleStringProperty representing the amount of the item in the bundle
         * @author Clara Dupuis
         * @return a SimpleStringProperty for the amount of the item
         */
        public SimpleIntegerProperty amount() { return amount; }
    }

    public static class ItemEntry {
        private SimpleStringProperty name;
        private SimpleStringProperty price;
        private SimpleIntegerProperty quantity;
        private boolean isInOrder;


        /** 
         * his method sets the parameters for the ItemEntry class
         * @author Clara Dupuis
         * @param name a string representing the name of the item
         * @param price a string representing the price of the item
         * @param quantity an integer representing the number of that item that is ordered
         */
        public ItemEntry(String name, String price, int quantity) {
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleStringProperty(price);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.isInOrder = quantity > 0;
        }

        /**
         * This method returns the name of the student
         * @author Clara Dupuis
         * @return a string representing the name of the item
         */
        public String getName() { return name.get(); }

        /**
         * This method sets the name of the student 
         * @author Clara Dupuis
         * @param name a string representing the name of the student 
         */
        public void setName(String name) { this.name.set(name); }

        /**
         * This method returns a simpleStringProperty representing the discount of the name of the item
         * @author Clara Dupuis
         * @return SimpleStringProperty representing the name of the item
         */
        public SimpleStringProperty name() { return name; }

        /**
         * This method returns the price of the student
         * @author Clara Dupuis
         * @return a string representing the price of the item
         */
        public String getPrice() { return price.get(); }

        /**
         * This method sets the price of the student 
         * @author Clara Dupuis
         * @param name a string representing the price of the student 
         */
        public void setPrice(String price) { this.price.set(price); }

        /**
         * This method returns a simpleStringProperty representing the price of the item
         * @author Clara Dupuis
         * @return SimpleStringProperty representing the price of the item
         */
        public SimpleStringProperty price() { return price; }

        /**
         * this method returns true if the item is in the order
         * @author Clara Dupuis
         * @return boolean isInOrder
         */
        public boolean isInOrder() {
            return isInOrder;
        }

        /**
         * this method sets the item to be in order
         * @author Clara Dupuis
         */
        public void setInOrder(boolean inOrder) {
            this.isInOrder = inOrder;
        }

        /**
         * this method returns an int representing the number of items in the order 
         * @author Clara Dupuis
         * @return integer representing the quantity of the item
         */
        public int getQuantity() {
            return quantity.get();
        }

        /**
         * this method sets the quantity of the item in the order 
         * @author Clara Dupuis
         * @param quantity
         */
        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }

        /**
         * this method returns a simplestringproperty representing the quantity of the item in the order
         * @author Clara Dupuis
         * @return SimpleStrinIntegerProperty representing quantity
         */
        public SimpleIntegerProperty quantity() {
            return quantity;
        }
    }

    /**
     * this method handles the case where the order level specified is changed 
     * @author Clara Dupuis
     * @param newOrderLevel a string representing the new order level
     */
    private void handleOrderLevelChange(String newOrderLevel) {
        String oldOrderLevel = orderLevel;
        orderLevel = newOrderLevel;
        String result = Iteration3Controller.updateOrder(orderLevel, gradeInput.getValue(), orderNumber);

        if (!result.isEmpty()) {
            errorLabel.setText(result);
            orderLevel = oldOrderLevel;

            levelToggleGroup.getToggles().stream()
                    .filter(toggle -> ((RadioButton) toggle).getText().equalsIgnoreCase(oldOrderLevel))
                    .findFirst()
                    .ifPresent(levelToggleGroup::selectToggle);
        } else {
            
            errorLabel.setText("");

            loadBundleItems();
        }
    }


    /**
     * this method closes the pop up windows when the save button is pressed
     * @author Clara Dupuis
     */
    @FXML
    private void handleSaveButtonAction() {
       
        Stage stage = (Stage) Save.getScene().getWindow();
        stage.close();
    }
}
