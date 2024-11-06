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
