package ca.mcgill.ecse.coolsupplies.controller;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;

/* Util Imports */
import java.util.List;
import java.util.ArrayList;

public class Iteration3Controller {
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * This method allows the user to update an order by assigning a new level and student.
   * 
   * @author Lune Letailleur
   * @param newLevel is a string representing the new level of the order
   * @param student is a string representing the student for the order
   * @param orderNumber is a string representing the number of the order
   * @return an empty string if the update was successful or an error message
   */
  public static String updateOrder(String newLevel, String studentName, String orderNumber) {
    int orderNumberInt = Integer.parseInt(orderNumber);

    // checking that the level exist
    if (!(newLevel.equals("Mandatory") || newLevel.equals("Recommended")
        || newLevel.equals("Optional"))) {
      return "Purchase level " + newLevel + " does not exist.";
    }

    BundleItem.PurchaseLevel level = BundleItem.PurchaseLevel.valueOf(newLevel);

    // checking if the order exists
    Order order = Order.getWithNumber(orderNumberInt);
    if (order == null) {
      return "Order " + orderNumberInt + " does not exist";
    }

    if (!order.getStatus().equals(Order.Status.Started)) {
      return "Cannot update a " + formatState(order) + " order";
    }

    Student student2 = Student.getWithName(studentName);

    if (student2 == null) {
      return "Student " + studentName + " does not exist.";
    }

    if (!order.updateOrder(level, student2)) {
      return "Student " + studentName + " is not a child of the parent "
          + order.getParent().getEmail() + ".";
    }

    // autosave
    try {
      CoolSuppliesPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

  /**
   * This method allows the user to add an item to a started order
   * 
   * @author Clara Dupuis
   * @param item is a string representing the name of the item
   * @param quantity is a string representing the quantity of the item we want to add to the order
   * @param orderNumber is a string representing the number of the order
   * @return en empty string if the item was added successfully or an error message if not
   */
  public static String addItem(String item, String quantity, String orderNumber) {

    int itemQuantity = Integer.parseInt(quantity);
    int orderNum = Integer.parseInt(orderNumber);
    // Verify that the order exists in the system
    Order order = Order.getWithNumber(orderNum);

    if (order == null) {
      return "Order " + orderNumber + " does not exist.";
    }

    // cannot add an item to a paid, penalized, prepared or picked up order
    if (!order.getStatus().equals(Order.Status.Started)) {
      return "Cannot add items to a " + formatState(order) + " order.";
    }
    // Check that the quantity is bigger than 0
    if (itemQuantity <= 0)
      return "Quantity must be greater than 0.";

    // Verify that the item exists
    if (InventoryItem.hasWithName(item)) {
      for (OrderItem orderItem : order.getOrderItems()) {
        if (orderItem.getItem().equals(InventoryItem.getWithName(item))) {
          return "Item " + item + " already exists in the order " + orderNumber + ".";
        }
      }

      // Add the item to the order
      if (order.addItem(InventoryItem.getWithName(item), itemQuantity)) {
        // autosave
        try {
          CoolSuppliesPersistence.save();
        } catch (RuntimeException e) {
          return e.getMessage();
        }
        return "";
      } else {
        return "Item could not be added to the order.";
      }

    } else
      return "Item " + item + " does not exist.";
  }

  /*
   * @author Dimitri Christopoulos
   */
  public static String updateOrderQuantity(String item, String quantity, String orderNumber) {

    // Obtain the order object
    int orderNumberInt = Integer.parseInt(orderNumber);
    Order order = Order.getWithNumber(orderNumberInt);

    // Error if orderNumber is not in system
    if (order == null) {
      return "Order " + orderNumber + " does not exist";
    }

    // Check if item is in the system
    InventoryItem itemObject = null;
    for (OrderItem orItem : coolSupplies.getOrderItems()) {
      if (orItem.getItem().getName().equals(item)) {
        itemObject = orItem.getItem();
      }
    }

    for (GradeBundle bundle : coolSupplies.getBundles()) {
      if (bundle.getName().equals(item)) {
        itemObject = bundle;
      }
    }

    for (Item iItem : coolSupplies.getItems()) {
      if (iItem.getName().equals(item)) {
        itemObject = iItem;
      }
    }

    // Error if item is not in system
    if (itemObject == null) {
      return "Item " + item + " does not exist.";
    }

    // Quantity must be greater than 0
    if (Integer.parseInt(quantity) < 1) {
      return "Quantity must be greater than 0.";
    }

    InventoryItem itemToUpdate = null;
    OrderItem orItemToUpdate = null;

    for (OrderItem orderItem : order.getOrderItems()) {
      // Successfully update item
      if ((orderItem.getItem().getName()).equals(item)) {
        itemToUpdate = orderItem.getItem();
        orItemToUpdate = orderItem;
      }
    }

    if (itemToUpdate == null) {
      return "Item " + item + " does not exist in the order " + orderNumber + ".";
    }

    // Test if order exists, by checking if orderNumber is in the system
    Order orderExists = null;
    for (Order orderFromList : coolSupplies.getOrders()) {
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

        // autosave
        try {
          CoolSuppliesPersistence.save();
        } catch (RuntimeException e) {
          return e.getMessage();
        }
        return "";
      }
    }
    return "Item " + item + " does not exist.";
  }

  /**
   * Deletes an item from a specific order if the entered item is valid, exists in the order, the
   * order exists, and is still in the Started status. If not, returns the appropriate error message
   * 
   * @author Nil Akkurt
   * @param item
   * @param orderNumber
   * @return String
   */
  public static String deleteItem(String item, String orderNumber) {
    int orderNum = Integer.parseInt(orderNumber);
    Order myOrder = Order.getWithNumber(orderNum);
    if (myOrder == null) {
      return "Order " + orderNumber + " does not exist";
    }
    List<OrderItem> items = myOrder.getOrderItems();
    InventoryItem itemToDel = null;
    OrderItem orItemToDel = null;
    List<OrderItem> itemsInSystem = CoolSuppliesApplication.getCoolSupplies().getOrderItems();
    List<GradeBundle> bundlesInSystem = CoolSuppliesApplication.getCoolSupplies().getBundles();

    InventoryItem inItem = null; // checking if the inputted item is an item in the system
    for (OrderItem orItem : itemsInSystem) {
      if (orItem.getItem().getName().equals(item)) {
        inItem = orItem.getItem();
      }
    }

    for (GradeBundle bundle : bundlesInSystem) {
      if (bundle.getName().equals(item)) {
        inItem = bundle;
      }
    }

    for (Item iItem : coolSupplies.getItems()) {
      if (iItem.getName().equals(item)) {
        inItem = iItem;
      }
    }

    if (inItem == null) {
      return "Item " + item + " does not exist.";
    }

    for (OrderItem myItem : items) {
      String itemName = myItem.getItem().getName();
      if (item.equals(itemName)) {
        itemToDel = myItem.getItem();
        orItemToDel = myItem;
      }
    }
    if (itemToDel == null) {
      return "Item " + item + " does not exist in the order " + orderNumber + ".";
    }

    if (!myOrder.getStatus().equals(Order.Status.Started)) {
      return "Cannot delete items from a " + formatState(myOrder) + " order";
    }

    orItemToDel.delete();

    // autosave
    try {
      CoolSuppliesPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

  /**
   * @author Edouard Dupont
   */
  public static String payForOrder(String orderNumber, String authCode) {
    // Validate the authorization code
    if (authCode == null || authCode.trim().isEmpty()) {
      return "Authorization code is invalid";
    }

    Order order = Order.getWithNumber(Integer.parseInt(orderNumber));

    if (order == null) {
      return "Order " + orderNumber + " does not exist";
    }

    if (!order.hasOrderItems()) {
      return "Order " + orderNumber + " has no items";
    }

    if (order.getStatus() == Order.Status.Paid) {
      return "The order is already paid";
    }

    if (order.getStatus() == Order.Status.PickedUp) {
      return "Cannot pay for a picked up order";
    }

    if (order.getStatus() != Order.Status.Started) {
      return "Cannot pay for a " + order.getStatusFullName().toLowerCase() + " order";
    }

    order.pay(authCode);

    // autosave
    try {
      CoolSuppliesPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

  /**
   * @auther Trevor Piltch
   * @param orderNumber - The number of the order to pay for in the system
   * @param authCode - The authorization code of the payment
   * @param penaltyAuthCode - The autorization code of the penalty
   * 
   *        Pays for the penalty for the order.
   */
  public static String payPenaltyForOrder(String orderNumber, String penaltyAuthCode,
      String authCode) {
    Integer num = Integer.parseInt(orderNumber);
    Order order = Order.getWithNumber(num);

    if (order == null) {
      return "Order " + orderNumber + " does not exist";
    }

    if (order.getStatus() == Order.Status.PickedUp) {
      return "Cannot pay penalty for a picked up order.";
    }

    if (order.getStatus() != Order.Status.Penalized) {
      return "Cannot pay penalty for a " + order.getStatusFullName().toLowerCase() + " order";
    }

    if (authCode.isEmpty()) {
      return "Authorization code is invalid";
    }

    if (penaltyAuthCode.isEmpty()) {
      return "Penalty authorization code is invalid";
    }

    order.payPenalty(authCode, penaltyAuthCode);

    // autosave
    try {
      CoolSuppliesPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

  /**
   * @author Kenny-Alexander Joseph
   */
  public static String pickUpOrder(String orderNumber) {
    int orderNum = Integer.parseInt(orderNumber);

    Order order = Order.getWithNumber(orderNum);
    if (order == null) {
      return "Order " + orderNum + " does not exist";
    }

    // Check the order's state
    String state = order.getStatusFullName().toLowerCase();
    if (state.equals("pickedup")) {
      return "The order is already picked up";
    }
    if (!state.equals("prepared")) {
      return "Cannot pickup a " + state + " order";
    }

    order.pickup();

    // autosave
    try {
      CoolSuppliesPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

  /**
   * @author Trevor Piltch
   * @param orderNumber
   * @return
   */
  public static String cancelOrder(String orderNumber) {
    Order order = Order.getWithNumber(Integer.parseInt(orderNumber));

    if (order == null) {
      return "Order " + orderNumber + " does not exist";
    }

    if (order.getStatus() == Order.Status.PickedUp) {
      return "Cannot cancel a picked up order";
    }

    if (order.getStatus() != Order.Status.Started && order.getStatus() != Order.Status.Paid) {
      return "Cannot cancel a " + order.getStatusFullName().toLowerCase() + " order";
    }

    order.delete();

    // autosave
    try {
      CoolSuppliesPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

  /**
   * @author Edouard Dupont
   */
  public static List<TOOrder> viewAllOrders() {
    List<Order> orders = coolSupplies.getOrders();
    List<TOOrder> toOrders = new ArrayList<>();

    for (Order order : orders) {
      toOrders.add(viewOrder(Integer.toString(order.getNumber())));
    }

    return toOrders;
  }

  /**
   * This method allows the user to view all the details of an order.
   * 
   * @author Clara Dupuis
   * @param orderNumber is a string representing the number of the order that we want to view
   * @return a TOOrder object
   */
  public static TOOrder viewOrder(String orderNumber) {

    int orderNum = Integer.parseInt(orderNumber);

    Order particularOrder = Order.getWithNumber(orderNum);

    if (particularOrder == null) {
      return null;
    }

    String studentName = particularOrder.getStudent().getName();
    String parentName = particularOrder.getParent().getEmail();
    String date = particularOrder.getDate().toString();
    String OrderNumber = Integer.toString(particularOrder.getNumber());
    String authorizationCode = particularOrder.getAuthorizationCode();
    String penaltyAuthorizationCode = particularOrder.getPenaltyAuthorizationCode();
    String statusString = particularOrder.getStatusFullName();
    String levelString = particularOrder.getLevel().toString();



    double totalPrice = 0;
    List<TOOrderItem> orderItemList = new ArrayList<>();

    for (OrderItem orderItem : particularOrder.getOrderItems()) {
      int quantityOrdered = orderItem.getQuantity();
      InventoryItem inventoryItem = orderItem.getItem();

      String itemQuantityStr = String.valueOf(quantityOrdered);

      String itemName = "";
      String gradeBundleStr = "";
      String itemPriceStr = "";
      String discountStr = "0";

      double itemTotalPrice = 0;

      if (inventoryItem instanceof Item) {
        Item item = (Item) inventoryItem;

        itemName = item.getName();
        int itemPrice = item.getPrice();
        int priceOfItem = item.getPrice() * quantityOrdered;
        totalPrice += priceOfItem;
        itemTotalPrice = priceOfItem;
        itemPriceStr = String.valueOf(priceOfItem);
      }

      else if (inventoryItem instanceof GradeBundle) {

        GradeBundle gradeBundle = (GradeBundle) inventoryItem;

        itemName = gradeBundle.getName();
        gradeBundleStr = gradeBundle.getName();
        int discount = gradeBundle.getDiscount();
        discountStr = String.valueOf(discount);

        double priceOfBundle = 0;

        BundleItem.PurchaseLevel purchaseLevel = particularOrder.getLevel();

        List<BundleItem> itemsInBundle = gradeBundle.getBundleItems();


        int numberOfIterations = 0; // used to count if discount should be applied

        for (BundleItem bundleItem : itemsInBundle) {

          int quantityInBundle = bundleItem.getQuantity();;
          int priceOfBundleItem = 0;

          if (purchaseLevel.compareTo(bundleItem.getLevel()) >= 0) {
            Item itemInBundle = bundleItem.getItem();
            priceOfBundleItem = itemInBundle.getPrice();
          }

          priceOfBundle += (quantityInBundle * priceOfBundleItem);

          numberOfIterations += 1;
        }

        if (numberOfIterations > 1) {
          priceOfBundle = priceOfBundle * ((double) (1 - discount/100.0));
        }

        priceOfBundle *= quantityOrdered;
        itemTotalPrice = priceOfBundle;
        itemPriceStr = String.valueOf(priceOfBundle);

        totalPrice += priceOfBundle;
      }

      TOOrderItem toOrderItem =
          new TOOrderItem(itemQuantityStr, itemName, gradeBundleStr, itemPriceStr, discountStr);

      orderItemList.add(toOrderItem);

    }

    return new TOOrder(parentName, studentName, statusString, orderNumber, date, levelString,
        authorizationCode, penaltyAuthorizationCode, totalPrice,
        orderItemList.toArray(new TOOrderItem[0]));
  }


  /**
   * This method starts a school year by changing the status of an order
   * 
   * @author Clara Dupuis
   * @param orderNumber is a string representing the number of the order
   * @return an empty string if the school year was started successfully or an error message
   */
  public static String startSchoolYear(String orderNumber) {

    int orderNum = Integer.parseInt(orderNumber);
    Order particularOrder = Order.getWithNumber(orderNum);

    // verify that the order exists in the system
    if (particularOrder == null) {
      return "Order " + orderNumber + " does not exist.";
    }

    // Start a school year if the order is started or if it is paid
    if (particularOrder.getStatusFullName().equals("Started")
        || particularOrder.getStatusFullName().equals("Paid")) {
      if (particularOrder.startSchoolYear()) {
        // if the order is started it becomes penalized
        // if the order is paid it becomes prepared

        // autosave
        try {
          CoolSuppliesPersistence.save();
        } catch (RuntimeException e) {
          return e.getMessage();
        }
        return "";
      } else
        return "The School year could not be started.";

      // The school year cannot be started if order is penalized, pickedUp or prepared --> school
      // year has already started
    } else if (particularOrder.getStatusFullName().equals("Penalized")
        || particularOrder.getStatusFullName().equals("PickedUp")
        || particularOrder.getStatusFullName().equals("Prepared")) {
      return "The school year has already been started.";

      // this case should never happen
    } else {
      return "The order status is neither Started, Penalized, Prepared, Paid or PickedUp.";
    }

  }

  /* Helper Methods */
  private static double calculatePrice(Order order) {
    // TODO: Implement
    return 0;
  }

  /**
   * @author Trevor Piltch
   * @param Order - Order to convert Converts from an Order to TOOrder
   */
  private static TOOrder convertOrder(Order order) {
    List<TOOrderItem> toItems = new ArrayList<>();
    for (OrderItem item : order.getOrderItems()) {
      if (item.getItem() instanceof GradeBundle) {
        Item iItem = (Item) item.getItem();

        TOOrderItem toItem =
            new TOOrderItem(Integer.toString(item.getQuantity()), item.getItem().getName(),
                ((GradeBundle) item.getItem()).toString(), Integer.toString(iItem.getPrice()),
                Integer.toString(((GradeBundle) item.getItem()).getDiscount()));

        toItems.add(toItem);
      }
    }

    TOOrderItem[] toItemsArray = new TOOrderItem[toItems.size()];

    return new TOOrder(order.getParent().getEmail(), order.getStudent().getName(),
        order.getStatus().toString(), Integer.toString(order.getNumber()),
        order.getDate().toString(), order.getLevel().toString(), order.getAuthorizationCode(),
        order.getPenaltyAuthorizationCode(), calculatePrice(order), toItems.toArray(toItemsArray));
  }

  private static String formatState(Order order) {
    switch (order.getStatus()) {
      case PickedUp:
        return "picked up";
      default:
        return order.getStatusFullName().toLowerCase();
    }
  }
}

