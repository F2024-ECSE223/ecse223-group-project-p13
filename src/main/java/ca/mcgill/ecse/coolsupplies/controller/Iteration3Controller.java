package ca.mcgill.ecse.coolsupplies.controller;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;

public class Iteration3Controller {
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  public static String updateOrder(String newLevel, String student) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String addItem(String item, String quantity, String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String updateOrderQuantity(String item, String quantity) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String deleteItem(String item) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String payForOrder(int orderId) { //is this right?
    Order order = Order.getWithId(orderId); // Assuming a static method to get an order by ID
        if (order == null) {
            throw new Exception("Order not found with ID: " + orderId);
        }
 
 
        if (order.getStatusFullName().equals("Pending")) {
            try {
                order.pay(); // Trigger the state machine transition
            } catch (Exception e) {
                throw new Exception("Unable to process payment: " + e.getMessage());
            }
        } else {
            throw new Exception("Order cannot be paid in its current state: " + order.getStatusFullName());
        }
  }
 

  public static String payPenaltyForOrder() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String cancelOrder() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String viewAllOrders() {
    return coolSupplies.getOrders(); // Find the Method to get all orders
  }

  public static String viewOrder(String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String startSchoolYear() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }
}