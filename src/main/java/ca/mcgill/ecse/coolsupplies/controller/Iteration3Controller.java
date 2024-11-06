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

  /**
   * @auther Trevor Piltch
   * @param orderNumber - The number of the order to pay for in the system
   * @param authCode - The authorization code of the payment
   * @param panaltyAuthCode - The autorization code of the penalty
   * 
   * Pays for the penalty for the order.
   */
  public static String payPenaltyForOrder(String orderNumber, String authCode, String penaltyAuthCode) {
    Integer num = Integer.parseInt(orderNumber);
    Order order = Order.getWithNumber(num);

    if (order == null) {
      return "Order " + orderNumber + " does not exist";
    }

    if (order.getStatus() != Order.Status.Penalized) {
      return "Cannot pay penalty for a " + order.getStatus() + " order";
    }

    if (authCode.isEmpty()) {
      return "Authorization code is invalid.";
    }

    if (penaltyAuthCode.isEmpty()) {
      return "Penalty authorization code is invalid";
    }

    order.setPenaltyAuthorizationCode(penaltyAuthCode);

    return "";
  }

  public static String pickUpOrder(String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String cancelOrder(String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  /**
   * @author Trevor Piltch
   *  
   * Returns a list of orders. 
   * **NOTE** I opted not to use a transfer object here for multiple reasons:
   * 1. In the definition of viewAllOrders, it specifies basically all the fields of the object 
   * 2. Creating a new transfer object for the order with the same fields is duplicating code and further increasing the complexity of the system
  */
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
