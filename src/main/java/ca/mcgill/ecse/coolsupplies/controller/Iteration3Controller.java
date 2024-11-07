package ca.mcgill.ecse.coolsupplies.controller;

/* Project Imports */
import java.util.List;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.model.Order;
import ca.mcgill.ecse.coolsupplies.model.OrderItem;

public class Iteration3Controller {
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  public static String updateOrder(String newLevel, String student, String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String addItem(String item, String quantity, String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  /*
   * @author Dimitri Christopoulos
   */
  public static String updateOrderQuantity(String item, String quantity, String orderNumber) {

    // Obtain the order object
    int orderNumberInt = Integer.parseInt(orderNumber);
    List<Order> orderList = coolSupplies.getOrders();
    Order order = null;

    // Find order from the orders in the system
    for (Order orderFromList : orderList) {
      if (orderFromList.getNumber() == orderNumberInt) {
        order = orderFromList;
      }
    }

    // Error if orderNumber is not in system
    if (order == null) {
	    return "Item " + item + " does not exist in the order " + orderNumber + ".";
    }

    // Check if item is in the system
    Item itemObject = null;
    List<Item> itemList = coolSupplies.getItems();
    for (Item itemFromList : itemList) {
      if (itemFromList.getName().equals(item)) {
        itemObject = itemFromList;
      }
    }

    // Error if item is not in system
    if (itemObject == null) {
      return "Item" + item + " does not exist.";
    }

    for (OrderItem orderItem : order.getOrderItems()) {

      // Successfully update item
      if ((orderItem.getItem().getName()).equals(item)) {
        orderItem.setQuantity(Integer.parseInt(quantity));
        return "";
      }
      // Item not found in system
      else {
        return "Item " + item + " does not exist in the order " + orderNumber + ".";
      }
    }

    // Quantity must be greater than 0
    if (Integer.parseInt(quantity) < 1) {
      return "Quantity must be greater than 0.";
    }

    // Test if order exists, by checking if orderNumber is in the system
    Order orderExists = null;
    for (Order orderFromList : orderList) {
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

  public static String viewOrder(String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String startSchoolYear() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }
}
