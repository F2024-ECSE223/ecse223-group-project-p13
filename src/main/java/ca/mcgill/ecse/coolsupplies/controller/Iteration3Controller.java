package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import javax.management.RuntimeErrorException;
/* Project Imports */
import java.util.List;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
import java.util.*;

/* Util Imports */
import java.util.List;

import java.util.ArrayList;
import java.util.List;

/* Util Types */
import java.util.List;
import java.util.ArrayList;

public class Iteration3Controller {
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * This method allows the user to update an order by assigning a new level and student.
   * @author Lune Letailleur
   * @param newLevel is a string representing the new level of the order
   * @param student is a string representing the student for the order
   * @param orderNumber is a string representing the number of the order
   * @return an empty string if the update was successful or an error message
   */
  public static String updateOrder(String newLevel, String student, String orderNumber) {

    int orderNumberInt = Integer.parseInt(orderNumber);

    // checking that the level exist
    if(!(newLevel.equals("Mandatory") || newLevel.equals("Recommended") || newLevel.equals("Optional"))){
      return "Purchase level " + newLevel + " does not exist.";
    }

    BundleItem.PurchaseLevel level = BundleItem.PurchaseLevel.valueOf(newLevel);
    // checking if the order exists
    if (Order.getWithNumber(orderNumberInt) == null){
      return "Order " + orderNumberInt + " does not exist";
    }

    // checking for the status
    if(Order.getWithNumber(orderNumberInt).getStatusFullName().equals("Paid")){
      return "Cannot update a paid order";
    } else if (Order.getWithNumber(orderNumberInt).getStatusFullName().equals("Penalized")){
      return "Cannot update a penalized order";
    } else if (Order.getWithNumber(orderNumberInt).getStatusFullName().equals("Prepared")) {
      return "Cannot update a prepared order";
    } else if (Order.getWithNumber(orderNumberInt).getStatusFullName().equals("PickedUp")) {
      return "Cannot update a picked up order";
    }

   // checking that the student exists
    Boolean doesStudentExists = false;
    for (Student studentEntity : coolSupplies.getStudents()){
      if (studentEntity.getName().equals(student)){
        doesStudentExists = true;
      }
    }

    if (!doesStudentExists){
      return "Student " + student + " does not exist.";
    }

   //checking if student of parent
    Boolean isStudentOfParent = false;
    for (Student studentOfParent : Order.getWithNumber(orderNumberInt).getParent().getStudents()){
      if (studentOfParent.getName().equals(student)){
        isStudentOfParent = true;
        Order.getWithNumber(orderNumberInt).setStudent(studentOfParent);
      }
    }
    if (!isStudentOfParent){
      return "Student " + student + " is not a child of the parent " + Order.getWithNumber(orderNumberInt).getParent().getEmail() + ".";
    }

 

    Order.getWithNumber(orderNumberInt).setLevel(level);
    return "";

  }

  /**
   * This method allows the user to add an item to a started order
   * @author Clara Dupuis
   * @param item is a string representing the name of the item
   * @param quantity is a string representing the quantity of the item we want to add to the order
   * @param orderNumber is a string representing the number of the order
   * @return en empty string if the item was added successfully or an error message if not
   */
  public static String addItem(String item, String quantity, String orderNumber) {

      int itemQuantity = Integer.parseInt(quantity);
      int orderNum = Integer.parseInt(orderNumber);
      //Verify that the order exists in the system
      if (Order.getWithNumber(orderNum) == null) {
          return "Order " + orderNumber + " does not exist.";
      }

      //cannot add an item to a paid, penalized, prepared or picked up order
      if (Order.getWithNumber(orderNum).getStatusFullName().equals("Paid")) {
          return "Cannot add items to a paid order.";
      } else if (Order.getWithNumber(orderNum).getStatusFullName().equals("Penalized")) {
          return "Cannot add items to a penalized order.";
      } else if (Order.getWithNumber(orderNum).getStatusFullName().equals("PickedUp")) {
          return "Cannot add items to a picked up order.";
      } else if (Order.getWithNumber(orderNum).getStatusFullName().equals("Prepared")) {
          return "Cannot add items to a prepared order.";
      } else {
          //Check that the quantity is bigger than 0
          if (itemQuantity <= 0) return "Quantity must be greater than 0.";

          //Verify that the item exists
          if (InventoryItem.hasWithName(item)) {
            for (OrderItem orderItem: Order.getWithNumber(orderNum).getOrderItems()) {
              if (orderItem.getItem().equals(InventoryItem.getWithName(item))) {
                return "Item " + item + " already exists in the order " + orderNumber + ".";
              }
            }
                  //create a new ItemOrdered for the item we want to add
                  OrderItem itemOrdered = new OrderItem(itemQuantity, coolSupplies, Order.getWithNumber(orderNum), InventoryItem.getWithName(item));

                  //Add the item to the order
                  if (Order.getWithNumber(orderNum).addOrderItem(itemOrdered)) {
                      return "";
                  } else {
                      return "Item could not be added to the order.";
                  }

          } else return "Item " + item + " does not exist.";

      }
  }

  /*
   * @author Dimitri Christopoulos
   */
  public static String updateOrderQuantity(String item, String quantity, String orderNumber) {

    // Obtain the order object
    int orderNumberInt = Integer.parseInt(orderNumber);
    Order order = Order.getWithNumber(orderNumberInt);

    // Error if orderNumber is not in system
    if (order == null) {
	    return "Order " + orderNumber + " does not exist";
    }

    // Check if item is in the system
    InventoryItem itemObject = null;
    for (OrderItem orItem: coolSupplies.getOrderItems()) {
      if (orItem.getItem().getName().equals(item)) {
        itemObject = orItem.getItem();
      }
    }

    for (GradeBundle bundle : coolSupplies.getBundles()) {
      if (bundle.getName().equals(item)) {
        itemObject = bundle;
      }
    }

    for (Item iItem : coolSupplies.getItems()) {
      if (iItem.getName().equals(item)) {
        itemObject = iItem;
      }
    }

    // Error if item is not in system
    if (itemObject == null) {
      return "Item " + item + " does not exist.";
    }

    // Quantity must be greater than 0
    if (Integer.parseInt(quantity) < 1) {
      return "Quantity must be greater than 0.";
    }

    InventoryItem itemToUpdate = null;
    OrderItem orItemToUpdate = null;

    for (OrderItem orderItem : order.getOrderItems()) {
      // Successfully update item
      if ((orderItem.getItem().getName()).equals(item)) {
        itemToUpdate = orderItem.getItem();
        orItemToUpdate = orderItem;
      }
    }

    if (itemToUpdate == null) {
      return "Item " + item + " does not exist in the order " + orderNumber + ".";
    }

    // Test if order exists, by checking if orderNumber is in the system
    Order orderExists = null;
    for (Order orderFromList : coolSupplies.getOrders()) {
      if (orderFromList.getNumber() == orderNumberInt) {
        orderExists = orderFromList;
      }
    }
    if (orderExists == null) {
      return "Order " + orderNumber + " does not exist";
    }

    // Error if status is not started
    switch (order.getStatus()) {
        case Paid:
            return "Cannot update items in a paid order";
        case Penalized:
            return "Cannot update items in a penalized order";
        case Prepared:
            return "Cannot update items in a prepared order";
        case PickedUp:
            return "Cannot update items in a picked up order";
        default:
            break;
    }
    
    for (OrderItem orderItem : order.getOrderItems()) {
      // Successfully update item
      if ((orderItem.getItem().getName()).equals(item)) {
        orderItem.setQuantity(Integer.parseInt(quantity));
        return "";
      }
    }
    return "Item " + item + " does not exist.";
  }

  /**
   * Deletes an item from a specific order if the entered item is valid, exists in the order,
   * the order exists, and is still in the Started status. If not, returns the appropriate error message
   * @author Nil Akkurt
   * @param item
   * @param orderNumber
   * @return String
   */
  public static String deleteItem(String item, String orderNumber) {
    int orderNum = Integer.parseInt(orderNumber);
    Order myOrder = Order.getWithNumber(orderNum);
    if(myOrder == null){
      return "Order " + orderNumber + " does not exist";
    }
    List<OrderItem> items = myOrder.getOrderItems();
    InventoryItem itemToDel = null;
    OrderItem orItemToDel = null;
    List<OrderItem> itemsInSystem = CoolSuppliesApplication.getCoolSupplies().getOrderItems();
    List<GradeBundle> bundlesInSystem = CoolSuppliesApplication.getCoolSupplies().getBundles();

    InventoryItem inItem = null;     //checking if the inputted item is an item in the system
    String thing  = "";
    for(OrderItem orItem: itemsInSystem) {
      if (orItem.getItem().getName().equals(item)) {
        inItem = orItem.getItem();
      }
      thing += orItem.getItem().getName() + "\n";
    }

    for(GradeBundle bundle: bundlesInSystem){
      if(bundle.getName().equals(item)){
        inItem = bundle;
      }
    }

    for (Item iItem : coolSupplies.getItems()) {
      if (iItem.getName().equals(item)) {
        inItem = iItem;
      }
    }

    if(inItem == null) {
      return "Item " + item + " does not exist.";
    }

    for(OrderItem myItem: items){
      String itemName = myItem.getItem().getName();
      if(item.equals(itemName)){
        itemToDel = myItem.getItem();
        orItemToDel = myItem;
      }
    }
    if(itemToDel == null){
      return "Item " + item +" does not exist in the order " + orderNumber + ".";
    }

    
    if(myOrder.getStatusFullName().equals("Paid")){
      return "Cannot delete items from a paid order";
    }
    if(myOrder.getStatusFullName().equals("Penalized")){
      return "Cannot delete items from a penalized order";
    }
    if(myOrder.getStatusFullName().equals("Prepared")){
      return "Cannot delete items from a prepared order";
    }
    if(myOrder.getStatusFullName().equals("PickedUp")){
      return "Cannot delete items from a picked up order";
    }
    orItemToDel.delete();
    return "";
  }

  /**
   * @author Edouard Dupont
   */
  public static String payForOrder(String orderNumber, String authCode) { 
    // Validate the authorization code
    if (authCode == null || authCode.trim().isEmpty()) {
      return "Authorization code is invalid";
    }

    Order order = Order.getWithNumber(Integer.parseInt(orderNumber));

    if (order == null) {
      return "Order " + orderNumber + " does not exist";
    }

    if (!order.hasOrderItems()) {
      return "Order " + orderNumber + " has no items";
    }

    if (order.getStatus() == Order.Status.Paid) {
      return "The order is already paid";
    }

    if (order.getStatus() == Order.Status.PickedUp) {
      return "Cannot pay for a picked up order";
    }

    if (order.getStatus() != Order.Status.Started) {
      return "Cannot pay for a " + order.getStatusFullName().toLowerCase() + " order";
    }

    order.pay();
    order.setAuthorizationCode(authCode);

    return "";
  }

  /**
   * @auther Trevor Piltch
   * @param orderNumber - The number of the order to pay for in the system
   * @param authCode - The authorization code of the payment
   * @param penaltyAuthCode - The autorization code of the penalty
   * 
   * Pays for the penalty for the order.
   */
  public static String payPenaltyForOrder(String orderNumber, String penaltyAuthCode, String authCode) {
    Integer num = Integer.parseInt(orderNumber);
    Order order = Order.getWithNumber(num);

    if (order == null) {
      return "Order " + orderNumber + " does not exist";
    }

    if (order.getStatus() == Order.Status.PickedUp) {
      return "Cannot pay penalty for a picked up order.";
    }

    if (order.getStatus() != Order.Status.Penalized) {
      return "Cannot pay penalty for a " + order.getStatusFullName().toLowerCase() + " order";
    }

    if (authCode.isEmpty()) {
      return "Authorization code is invalid";
    }

    if (penaltyAuthCode.isEmpty()) {
      return "Penalty authorization code is invalid";
    }

    order.setPenaltyAuthorizationCode(penaltyAuthCode);
    order.setAuthorizationCode(authCode);
    order.payForEverything();

    return "";
  }

  /**
   * @author Kenny-Alexander Joseph
   */  
  public static String pickUpOrder(String orderNumber){
    int orderNum = Integer.parseInt(orderNumber);

    Order order = Order.getWithNumber(orderNum);
    if (order == null) {
      return "Order " + orderNum + " does not exist";
   }

    // Check the order's state
    String state = order.getStatusFullName().toLowerCase(); 
    if (state.equals("pickedup")) {
      return "The order is already picked up";
    }
    if (!state.equals("prepared")) {
      return "Cannot pickup a " + state + " order";
    }

    order.receiveOrder();
    return "";
  }

  /**
   * @author Trevor Piltch
   * @param orderNumber
   * @return
   */
  public static String cancelOrder(String orderNumber) {
    Order order = Order.getWithNumber(Integer.parseInt(orderNumber));

    if (order == null) {
      return "Order " + orderNumber + " does not exist";
    }

    if (order.getStatus() == Order.Status.PickedUp) {
      return "Cannot cancel a picked up order";
    }

    if (order.getStatus() != Order.Status.Started && order.getStatus() != Order.Status.Paid) {
      return "Cannot cancel a " + order.getStatusFullName().toLowerCase() + " order";
    }

    order.delete();

    return "";
  }

  /**
   * @author Edouard Dupont
   */
  public static List<TOOrder> viewAllOrders() {
    List<Order> orders = coolSupplies.getOrders();
    List<TOOrder> toOrders = new ArrayList<>();

    for (Order order : orders) {
      toOrders.add(convertOrder(order));
    }

    return toOrders;
  }

/**
   * This method allows the user to view all the details of an order.
   * @author Clara Dupuis
   * @param orderNumber is a string representing the number of the order that we want to view
   * @return a TOOrder object
   */
  public static TOOrder viewOrder(String orderNumber) {

    int orderNum = Integer.parseInt(orderNumber);

    Order particularOrder = Order.getWithNumber(orderNum);

    if (particularOrder == null){
        return null;
    }

    String studentName = particularOrder.getStudent().getName();
    String parentName = particularOrder.getParent().getName();
    String date = particularOrder.getDate().toString();
    String OrderNumber = Integer.toString(particularOrder.getNumber());
    String authorizationCode = particularOrder.getAuthorizationCode();
    String penaltyAuthorizationCode = particularOrder.getPenaltyAuthorizationCode();
    String statusString = particularOrder.getStatusFullName();
    String levelString = particularOrder.getLevel().toString();

    

  double totalPrice = 0;
  List<TOOrderItem> orderItemList = new ArrayList<>();

  for (OrderItem orderItem: particularOrder.getOrderItems()) {
      int quantityOrdered = orderItem.getQuantity();
      InventoryItem inventoryItem = orderItem.getItem();

      String itemQuantityStr = String.valueOf(quantityOrdered);

      String itemName = "";
      String gradeBundleStr = "";
      String itemPriceStr = "";
      String discountStr = "0";

      double itemTotalPrice = 0;

      if (inventoryItem instanceof Item) {
          Item item = (Item) inventoryItem;

          itemName = item.getName();
          int itemPrice = item.getPrice();
          int priceOfItem = item.getPrice() * quantityOrdered;
          totalPrice += priceOfItem;
          itemTotalPrice = priceOfItem;
          itemPriceStr = String.valueOf(priceOfItem);
      }

      else if (inventoryItem instanceof GradeBundle) {

          GradeBundle gradeBundle = (GradeBundle) inventoryItem;

          itemName = gradeBundle.getName();
          gradeBundleStr = gradeBundle.getName();
          int discount = gradeBundle.getDiscount();
          discountStr = String.valueOf(discount);

          double priceOfBundle = 0;

          BundleItem.PurchaseLevel purchaseLevel = particularOrder.getLevel();

          List<BundleItem> itemsInBundle = gradeBundle.getBundleItems();


          int numberOfIterations = 0; //used to count if discount should be applied

          for (BundleItem bundleItem : itemsInBundle){

              int quantityInBundle = bundleItem.getQuantity();;
              int priceOfBundleItem = 0;

              if (purchaseLevel.compareTo(bundleItem.getLevel()) >= 0){
                  Item itemInBundle = bundleItem.getItem();
                  priceOfBundleItem = itemInBundle.getPrice();
              }

              priceOfBundle += (quantityInBundle * priceOfBundleItem);

              numberOfIterations+=1;
          }

          if (numberOfIterations>1){
              priceOfBundle = priceOfBundle*((double) (1-discount)/100);
          }

          priceOfBundle *=quantityOrdered;
          itemTotalPrice = priceOfBundle;
          itemPriceStr = String.valueOf(priceOfBundle);

          totalPrice += priceOfBundle;
      }

      TOOrderItem toOrderItem = new TOOrderItem(itemQuantityStr, itemName, gradeBundleStr, itemPriceStr, discountStr);

      orderItemList.add(toOrderItem);

  }

   return new TOOrder(parentName, studentName, statusString, orderNumber, date, levelString, authorizationCode, penaltyAuthorizationCode, totalPrice, orderItemList.toArray(new TOOrderItem[0]));
}

   
    /**
     * This method starts a school year by changing the status of an order
     * @author Clara Dupuis
     * @param orderNumber is a string representing the number of the order
     * @return an empty string if the school year was started successfully or an error message
     */
  public static String startSchoolYear(String orderNumber) {

      int orderNum = Integer.parseInt(orderNumber);
      Order particularOrder = Order.getWithNumber(orderNum);

      //verify that the order exists in the system
      if (particularOrder == null) {
          return "Order " + orderNumber + " does not exist.";
      }

      //Start a school year if the order is started or if it is paid
      if (particularOrder.getStatusFullName().equals("Started") || particularOrder.getStatusFullName().equals("Paid")) {
          if (particularOrder.startSchoolYear()) {
              //if the order is started it becomes penalized
              //if the order is paid it becomes prepared
              return "";
          }
          else return "The School year could not be started.";

          //The school year cannot be started if order is penalized, pickedUp or prepared --> school year has already started
      } else if (particularOrder.getStatusFullName().equals("Penalized") || particularOrder.getStatusFullName().equals("PickedUp") || particularOrder.getStatusFullName().equals("Prepared")) {
          return "The school year has already been started.";

        // this case should never happen
      } else {
          return "The order status is neither Started, Penalized, Prepared, Paid or PickedUp.";
      }

  }

  /* Helper Methods */ 
  private static double calculatePrice(Order order) {
    // TODO: Implement
    return 0;
  }

  /**
   * @author Trevor Piltch
   * @param Order  - Order to convert
   * Converts from an Order to TOOrder
   */
  private static TOOrder convertOrder(Order order) {
     List<TOOrderItem> toItems = new ArrayList<>();
    for (OrderItem item : order.getOrderItems()) {
      if (item.getItem() instanceof GradeBundle) {
        Item iItem = (Item) item.getItem();

        TOOrderItem toItem = new TOOrderItem(Integer.toString(item.getQuantity()), item.getItem().getName(), ((GradeBundle) item.getItem()).toString(), Integer.toString(iItem.getPrice()), Integer.toString(((GradeBundle) item.getItem()).getDiscount()));

        toItems.add(toItem);
      }
    }

      TOOrderItem[] toItemsArray = new TOOrderItem[toItems.size()];
 
      return new TOOrder(
        order.getParent().getEmail(), 
        order.getStudent().getName(), 
        order.getStatus().toString(), 
        Integer.toString(order.getNumber()),
        order.getDate().toString(), 
        order.getLevel().toString(), 
        order.getAuthorizationCode(), 
        order.getPenaltyAuthorizationCode(),
        calculatePrice(order),
        toItems.toArray(toItemsArray)
      );
  }
}

