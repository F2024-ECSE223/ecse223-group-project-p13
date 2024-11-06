package ca.mcgill.ecse.coolsupplies.controller;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;

public class Iteration3Controller {
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  public static String updateOrder(String newLevel, String student, String orderNumber) {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  /**
   * @author Clara Dupuis
   * @param item is a string representing the name of the item
   * @param quantity is a string representing the quantity of the item we want to add to the order
   * @param orderNumber is a string representing the number of the order
   * @return en empty string if the item was added successfully or an error message if not
   */
  public static String addItem(String item, String quantity, String orderNumber) {


      int itemQuantity = Integer.parseInt(quantity);
      int orderNum = Integer.parseInt(orderNumber);

      if (Order.getWithNumber(orderNum).getStatusFullName().equals("Paid")) {
          return "Cannot add items to a paid order.";
      } else if (Order.getWithNumber(orderNum).getStatusFullName().equals("Penalized")) {
          return "Cannot add items to a penalized order.";
      } else if (Order.getWithNumber(orderNum).getStatusFullName().equals("PickedUp")) {
          return "Cannot add items to a picked up order.";
      } else if (Order.getWithNumber(orderNum).getStatusFullName().equals("Prepared")) {
          return "Cannot add items to a prepared order.";

          //} else if (Order.getWithNumber(orderNum).getStatusFullName().equals("Started")) {
      }else{

          if (itemQuantity <= 0) return "Quantity must be greater than 0.";


          if (Order.getWithNumber(orderNum) == null) {
              return "Order " + orderNumber + "does not exist.";
          }


          if (InventoryItem.hasWithName(item)) {
              try {
                  OrderItem itemOrdered = new OrderItem(itemQuantity, coolSupplies, Order.getWithNumber(orderNum), InventoryItem.getWithName(item));

                  for (OrderItem itemAlreadyInOrder : Order.getWithNumber(orderNum).getOrderItems()) {
                      if (itemOrdered.equals(itemAlreadyInOrder)) {
                          return "Item " + item + "already exists in the order " + orderNumber;
                      }
                  }

                  if (Order.getWithNumber(orderNum).addOrderItem(itemOrdered)) {
                      return "";
                  } else {
                      return "Item was not added to the order.";
                  }

              } catch (RuntimeException e) {
                    //idk if I need to catch these exceptions
                  switch (e.getMessage()) {
                      case "Unable to create orderItem due to coolSupplies. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html":
                          return "The orderItem was nor created due to coolSupplies.";
                      case "Unable to create orderItem due to order. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html":
                          return "The orderItem was not created due to the order.";
                      case "Unable to create orderItem due to item. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html":
                          return "The orderItem was not created due to the item.";
                      default:
                          return "The orderItem was not created due to an unexpected error: " + e.getMessage();
                  }
              }


          } else return "Item " + item + "does not exist.";

      }
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

  public static String viewAllOrders() {
    throw new UnsupportedOperationException("Not Implemented yet.");
  }

  /**
   * @author Clara Dupuis
   * @param orderNumber is a string representing the number of the order that we want to view
   * @return a string describing the order or an error message
   */
  public static String viewOrder(String orderNumber) {
    int orderNum;

    try {
      orderNum = Integer.parseInt(orderNumber);
    } catch (NumberFormatException e){
      return "";
    }

    if (Order.getWithNumber(orderNum)== null) return "";

    else return Order.getWithNumber(orderNum).toString();
  }

    /**
     * @author Clara Dupuis
     * @param orderNumber is a string representing the number of the order
     * @return an empty string if the school year was started successfully or an error message
     */
  public static String startSchoolYear(String orderNumber) {

      int orderNum = Integer.parseInt(orderNumber);
      Order particularOrder = Order.getWithNumber(orderNum);

      if (particularOrder == null) {
          return "Order " + orderNumber + "does not exist.";
      }

      if (particularOrder.getStatusFullName().equals("Started") || particularOrder.getStatusFullName().equals("Paid")) {
          particularOrder.startSchoolYear();
          return "";
      } else if (particularOrder.getStatusFullName().equals("Penalized") || particularOrder.getStatusFullName().equals("PickedUp") || particularOrder.getStatusFullName().equals("Prepared")) {
          return "The school year has already been started.";

      } else {
          return "The order status is neither Started, Penalized, Prepared, Paid or PickedUp.";
      }

  }
}
