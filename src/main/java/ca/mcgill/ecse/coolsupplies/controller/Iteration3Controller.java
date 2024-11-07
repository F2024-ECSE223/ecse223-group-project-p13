package ca.mcgill.ecse.coolsupplies.controller;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;

public class Iteration3Controller {
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  public static String updateOrder(String newLevel, String student, String orderNumber) {

    int orderNumberInt = Integer.parseInt(orderNumber);
    BundleItem.PurchaseLevel level = BundleItem.PurchaseLevel.valueOf(newLevel); //NOT SURE

    // checking for the status
    if(Order.getWithNumber(orderNumberInt).getStatusFullName().equals("Paid")){
      return "Cannot update a paid order";
    } else if (Order.getWithNumber(orderNumberInt).getStatusFullName().equals("Penalized")){
      return "Cannot update a penalized order";
    } else if (Order.getWithNumber(orderNumberInt).getStatusFullName().equals("Prepared")) {
      return "Cannot update a prepared order";
    } else if (Order.getWithNumber(orderNumberInt).getStatusFullName().equals("PickedUp")) {
      return "Cannot update a picked up order";
    }

    // checking that the student exists
    Boolean doesStudentExists = false;
    for (Student studentEntity : coolSupplies.getStudents()){
      if (studentEntity.toString().equals(student)){
        doesStudentExists = true;
      }
    }

    if (!doesStudentExists){
      return "Student" + student + "does not exist.";
    }


    // checking if the order exists
    if (Order.getWithNumber(orderNumberInt) == null){
      return "Order" + orderNumberInt + "does not exist";
    }

    // checking that the level exist
    if(!(newLevel.equals("Mandatory") || newLevel.equals("Recommended") || newLevel.equals("Optional"))){
      return "Purchase level" + newLevel + "does not exist.";
    }

    //checking if student of parent
    Boolean isStudentOfParent = false;
    for (Student studentOfParent : Order.getWithNumber(orderNumberInt).getParent().getStudents()){
      if (studentOfParent.toString().equals(student)){
        isStudentOfParent = true;
        Order.getWithNumber(orderNumberInt).setStudent(studentOfParent);
      }
    }
    if (!isStudentOfParent){
      return "Student" + student + "is not a child of the parent" + Order.getWithNumber(orderNumberInt).getParent().getEmail() + ".";
    }

    Order.getWithNumber(orderNumberInt).setLevel(level);
    return ""; // empty ????

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
