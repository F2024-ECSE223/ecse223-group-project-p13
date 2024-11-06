package ca.mcgill.ecse.coolsupplies.controller;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
import java.util.List;

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
    Order order = Order.getWithNumber(orderId);
    if (order == null) {
        throw new Exception("Order not found with ID: " + orderId); //check message
    }

    if (order.getStatusFullName().equals("Pending")) { //should this be in update order?
        try {
            order.pay(); // Trigger the state machine transition (how do I do that?)
        } catch (Exception e) {
            throw new Exception("Unable to process payment: " + e.getMessage());
        }
    } else {
        throw new Exception("Order cannot be paid in its current state: " + order.getStatusFullName()); //bruh...
    }
  }
 

  public static String payPenaltyForOrder() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String cancelOrder() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static List<Order> viewAllOrders() {
    return coolSupplies.getOrders(); 
  }

  public static String viewOrder(String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String startSchoolYear() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }
}