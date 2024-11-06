package ca.mcgill.ecse.coolsupplies.controller;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;

public class Iteration3Controller {
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  public static String updateOrder(String newLevel, String student) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  /**
   * @author Clara Dupuis
   * @param item
   * @param quantity
   * @param orderNumber
   * @return
   */
  public static String addItem(String item, String quantity, String orderNumber) {
   int itemQuantity;
   int orderNum;
    try {
       itemQuantity = Integer.parseInt(quantity);
       orderNum = Integer.parseInt(orderNumber);
    }catch (NumberFormatException e) {
      return "The quantity number and the order number must be valid integers.";
    }

    if (Order.getWithNumber(orderNum) == null){
      return "Order not found in the system";
    }


    if (InventoryItem.hasWithName(item)){
      try {
        OrderItem itemOrdered = new OrderItem(itemQuantity, coolSupplies, Order.getWithNumber(orderNum), InventoryItem.getWithName(item));

        if (Order.getWithNumber(orderNum).addOrderItem(itemOrdered)){
          return "";
        } else {
          return "Item was not added to the order.";
        }

      } catch (RuntimeException e) {

        switch (e.getMessage()) {
          case "Unable to create orderItem due to coolSupplies. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html":
            return "The orderItem was nor created due to coolSupplies.";
          case "Unable to create orderItem due to order. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html":
            return "The orderItem was not created due to the order.";
          case "Unable to create orderItem due to item. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html":
            return "The orderItem was not created due to the item.";
          default :
            return "The orderItem was not created due to an unexpected error: "+ e.getMessage();
        }
      }


    } else return "The Item was not found in the system";

  }

  public static String updateOrderQuantity(String item, String quantity) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String deleteItem(String item) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String payForOrder() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String payPenaltyForOrder() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String cancelOrder() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String viewAllOrders() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  /**
   * @author Clara Dupuis
   * @param orderNumber
   * @return
   */
  public static String viewOrder(String orderNumber) {
    int orderNum;

    try {
      orderNum = Integer.parseInt(orderNumber);
    } catch (NumberFormatException e){
      return "The order number must be a valid integer.";
    }

    if (Order.getWithNumber(orderNum)== null) return "The Order could not be found in the system.";

    else return Order.getWithNumber(orderNum).toString();

  }


  public static String startSchoolYear() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }
}