package ca.mcgill.ecse.coolsupplies.controller;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
import java.util.List;

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

  /**
   * @author Edouard Dupont
   */
  public static String payForOrder(String orderNumber, String authCode) { 
    // Validate the authorization code
    if (authCode == null || authCode.trim().isEmpty()) {
      return "Authorization code is invalid";
  }

  // Parse the order number
  int orderNum;
  try {
      orderNum = Integer.parseInt(orderNumber);
  } catch (NumberFormatException e) {
      return "Order number is invalid";
  }

  // Retrieve the order
  Order order = Order.getWithNumber(orderNum);
  if (order == null) {
      return "Order " + orderNumber + " does not exist";
  }

  // Check the order's state
  String state = order.getStatusFullName(); // Assuming this method exists
  if (!state.equals("Started")) {
      switch (state) {
          case "Paid":
              return "The order is already paid";
          case "Penalized":
              return "Cannot pay for a penalized order";
          case "Prepared":
              return "Cannot pay for a prepared order";
          case "PickedUp":
              return "Cannot pay for a picked up order";
          default:
              return "Cannot pay for the order in its current state";
      }
  }

  // Check if the order has items
  if (order.numberOfOrderItems() == 0) {
      return "Order " + orderNumber + " has no items";
  }

  // Perform the payment
  try {
      order.setAuthorizationCode(authCode);
      order.payForOrder(); // Assuming this triggers the state transition to "Paid"
  } catch (Exception e) {
      // Handle exceptions from the state machine
      return "Unable to process payment: " + e.getMessage();
  }

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

  /**
   * @author Edouard Dupont
   */
  public static List<Order> viewAllOrders() {
    return coolSupplies.getOrders(); 
  }

  public static String viewOrder(String orderNumber) {
    return coolSupplies.getOrder(orderNumber);
  }

  public static String startSchoolYear() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }
}

