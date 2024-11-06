package ca.mcgill.ecse.coolsupplies.controller;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;

/* Util Imports */
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
    Integer num = Integer.parseInt(orderNumber);
    Order order = Order.getWithNumber(num);

    if (order == null) {
      return "Order " + orderNumber + " does not exist";
    }

    /* 
    if (order.getStatus() != Prepared) {
      return "Cannot pay penalty for a " + order.getStatus() + " order"
    }
    */

    if (authCode.isEmpty()) {
      return "Authorization code is invalid.";
    }

    if (penaltyAuthCode.isEmpty()) {
      return "Penalty authorization code is invalid";
    }

    /*
     * order.payPenalty(authCode, panaltyCode)
     */

    return "";
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

  public static String viewOrder(String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String startSchoolYear() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }
}
