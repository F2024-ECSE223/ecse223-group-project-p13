package ca.mcgill.ecse.coolsupplies.controller;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;

/* Util Types */
import java.util.List;
import java.util.ArrayList;

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
    String state = order.getStatusFullName(); 
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

    return "";
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
  public static List<TOOrder> viewAllOrders() {
    List<Order> orders = coolSupplies.getOrders();
    List<TOOrder> toOrders = new ArrayList<>();

    for (Order order : orders) {
      toOrders.add(convertOrder(order));
    }

    return toOrders;
  }

  public static String viewOrder(String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String startSchoolYear() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  /* Helper Methods */ 
  private static double calculatePrice(Order order) {
    // TODO: Implement
    return 0;
  }

  /**
   * @author Trevor Piltch
   * @param Order  - Order to convert
   * Converts from an Order to TOOrder
   */
  private static TOOrder convertOrder(Order order) {
     List<TOOrderItem> toItems = new ArrayList<>();
    for (OrderItem item : order.getOrderItems()) {
      if (item.getItem() instanceof GradeBundle) {
        Item iItem = (Item) item.getItem();

        TOOrderItem toItem = new TOOrderItem(Integer.toString(item.getQuantity()), item.getItem().getName(), ((GradeBundle) item.getItem()).toString(), Integer.toString(iItem.getPrice()), Integer.toString(((GradeBundle) item.getItem()).getDiscount()));

        toItems.add(toItem);
      }
    }

      TOOrderItem[] toItemsArray = new TOOrderItem[toItems.size()];
 
      return new TOOrder(
        order.getParent().getEmail(), 
        order.getStudent().getName(), 
        order.getStatus().toString(), 
        Integer.toString(order.getNumber()),
        order.getDate().toString(), 
        order.getLevel().toString(), 
        order.getAuthorizationCode(), 
        order.getPenaltyAuthorizationCode(),
        calculatePrice(order),
        toItems.toArray(toItemsArray)
      );
  }
}

