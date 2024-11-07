package ca.mcgill.ecse.coolsupplies.controller;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;

import java.util.ArrayList;
import java.util.List;

public class Iteration3Controller {
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  public static String updateOrder(String newLevel, String student, String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
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


      //cannot add an item to a paid, penalized, prepared or picked up order
      if (Order.getWithNumber(orderNum).getStatusFullName().equals("Paid")) {
          return "Cannot add items to a paid order.";
      } else if (Order.getWithNumber(orderNum).getStatusFullName().equals("Penalized")) {
          return "Cannot add items to a penalized order.";
      } else if (Order.getWithNumber(orderNum).getStatusFullName().equals("PickedUp")) {
          return "Cannot add items to a picked up order.";
      } else if (Order.getWithNumber(orderNum).getStatusFullName().equals("Prepared")) {
          return "Cannot add items to a prepared order.";

          //can only add an item to an order with status "Started"
      }else{

          //Check that the quantity is bigger than 0
          if (itemQuantity <= 0) return "Quantity must be greater than 0.";

          //Verify that the order exists in the system
          if (Order.getWithNumber(orderNum) == null) {
              return "Order " + orderNumber + "does not exist.";
          }

          //Verify that the item exists
          if (InventoryItem.hasWithName(item)) {

                  //create a new ItemOrdered for the item we want to add
                  OrderItem itemOrdered = new OrderItem(itemQuantity, coolSupplies, Order.getWithNumber(orderNum), InventoryItem.getWithName(item));

                  //Check that the item is not already present in the order
                  for (OrderItem itemAlreadyInOrder : Order.getWithNumber(orderNum).getOrderItems()) {
                      if (itemOrdered.equals(itemAlreadyInOrder)) {
                          return "Item " + item + "already exists in the order " + orderNumber;
                      }
                  }

                  //Add the item to the order
                  if (Order.getWithNumber(orderNum).addOrderItem(itemOrdered)) {
                      return "";
                  } else {
                      return "Item could not be added to the order.";
                  }

          } else return "Item " + item + "does not exist.";

      }
  }

  public static String updateOrderQuantity(String item, String quantity, String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String deleteItem(String item, String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String payForOrder(String orderNumber, String authCode) { 
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String payPenaltyForOrder(String orderNumber, String authCode, String penaltyAuthCode) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String pickUpOrder(String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String cancelOrder(String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String viewAllOrders() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  /**
   * This method allows the user to view all the details of an order.
   * @author Clara Dupuis
   * @param orderNumber is a string representing the number of the order that we want to view
   * @return a string describing the order or an empty string if the order could not be viewed
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
          return "Order " + orderNumber + "does not exist.";
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
}
