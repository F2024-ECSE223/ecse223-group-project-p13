package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;

public class Iteration3Controller {
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  public static String updateOrder(String newLevel, String student, String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String addItem(String item, String quantity, String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
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

  /**
   * @author Kenny-Alexander Joseph
   */  
  public static String pickUpOrder(String orderNumber){
    // Validate the order number
    int orderNum;
    try {
      orderNum = Integer.parseInt(orderNumber);
    } catch (NumberFormatException e) {
      throw new Exception("Order number is invalid");
    }

    // Retrieve the order
    Order order = Order.getWithNumber(orderNum);
    if (order == null) {
      throw new Exception("Order " + orderNumber + " does not exist");
   }

    // Check the order's state
    String state = order.getStatusFullName(); // Assuming this method exists
    if (state.equals("Started") || state.equals("Paid")) {
      // Perform the cancellation
      try {
        order.cancelOrder(); // Assuming this triggers the state transition to "Cancelled"
        return "Order " + orderNumber + " has been cancelled successfully.";
       } catch (Exception e) {
        throw new Exception("Unable to cancel order: " + e.getMessage());
       }
    } else {
      // Cannot cancel orders in other states
      throw new Exception("Cannot cancel the order in its current state: " + state);
   }
  }

  public static String cancelOrder(String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static List<Order> viewAllOrders() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String viewOrder(String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String startSchoolYear(String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }
}
