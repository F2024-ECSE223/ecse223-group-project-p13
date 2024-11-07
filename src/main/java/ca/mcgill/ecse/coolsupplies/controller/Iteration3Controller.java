package ca.mcgill.ecse.coolsupplies.controller;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
import java.util.*;

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

  /**
   * Deletes an item from a specific order if the entered item is valid, exists in the order,
   * the order exists, and is still in the Started status. If not, returns the appropriate error message
   * @author Nil Akkurt
   * @param item
   * @param orderNumber
   * @return String
   */
  public static String deleteItem(String item, String orderNumber) {
    int orderNum = Integer.parseInt(orderNumber);
    Order myOrder = Order.getWithNumber(orderNum);
    List<OrderItem> items = myOrder.getOrderItems();
    InventoryItem itemToDel = null;
    OrderItem orItemToDel = null;
    List<OrderItem> itemsInSystem = CoolSuppliesApplication.getCoolSupplies().getOrderItems();
    InventoryItem inItem = null;
    for(OrderItem orItem: itemsInSystem) {
      if(orItem.getItem().getName().equals(item)){
        inItem = orItem.getItem();
      }
    }
    if(inItem == null) {
      return "Item " + item + " does not exist.";
    }

    for(OrderItem myItem: items){
      String itemName = myItem.getItem().getName();
      if(item.equals(itemName)){
        itemToDel = myItem.getItem();
        orItemToDel = myItem;
      }
    }
    if(itemToDel == null){
      return "Item " + item +" does not exist in order" + orderNumber + ".";
    }

    for
    if(myOrder == null){
      return "Order" + orderNumber + "does not exist";
    }
    if(myOrder.getStatusFullName().equals("Paid")){
      return "Cannot delete items from a paid order";
    }
    if(myOrder.getStatusFullName().equals("Penalized")){
      return "Cannot delete items from a penalized order";
    }
    if(myOrder.getStatusFullName().equals("Prepared")){
      return "Cannot delete items from a prepared order";
    }
    if(myOrder.getStatusFullName().equals("PickedUp")){
      return "Cannot delete items from a picked up order";
    }
    myOrder.removeOrderItem(orItemToDel);
    return "";
  }



  public static String payForOrder(String orderNumber) { 
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  public static String payPenaltyForOrder(String orderNumber) {
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
