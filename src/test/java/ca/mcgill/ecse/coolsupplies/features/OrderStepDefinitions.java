
package ca.mcgill.ecse.coolsupplies.features;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Map;
import java.sql.Date;

import ca.mcgill.ecse.coolsupplies.model.*;
import ca.mcgill.ecse.coolsupplies.controller.*;

/* Helper Imports */
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


/* JUnit Imports */
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class OrderStepDefinitions {
  private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
  private String errString = "";
  private List<TOOrder> transferOrders = new ArrayList<>();
  private List<TOOrderItem> transferOrderItems = new ArrayList<>();
  private TOOrder transferOrder = null;


  @Given("the following parent entities exist in the system")
  public void the_following_parent_entities_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List[E], List[List[E]], List[Map[K,V]], Map[K,V] or
    // Map[K, List[V]]. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.

    List<Map<String,String >> entities = dataTable.asMaps();

    for (var entity:entities){
      String aEmail = entity.get("email");
      String aPassword = entity.get("password");
      String aName = entity.get("name");
      int aPhoneNumber = Integer.parseInt(entity.get("phoneNumber"));
      coolSupplies.addParent(aEmail, aPassword, aName, aPhoneNumber);
    }
  }

  /**
   * @author Lune Letailleur
   */
  @Given("the following grade entities exist in the system")
  public void the_following_grade_entities_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List[E], List[List[E]], List[Map[K,V]], Map[K,V] or
    // Map[K, List[V]]. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    List<Map<String, String>> entities = dataTable.asMaps();

    for (var entity : entities) {
      String level = entity.get("level");
      new Grade(level, coolSupplies);
    }
  }

  /**
   * @author Lune Letailleur
   */
  @Given("the following student entities exist in the system")
  public void the_following_student_entities_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
       List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      String name = row.get("name");
      String gradeLevel = row.get("gradeLevel");
      Grade studentGrade;

      if (Grade.hasWithLevel(gradeLevel)) { // Checking if grade exists
        studentGrade = Grade.getWithLevel(gradeLevel);
      } else {
        studentGrade = new Grade(gradeLevel, coolSupplies);
        coolSupplies.addGrade(studentGrade);
      }

      coolSupplies.addStudent(name, studentGrade);
    }
  }

  /**
   * @author Lune Letailleur
   */
  @Given("the following student entities exist for a parent in the system")
  public void the_following_student_entities_exist_for_a_parent_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List[E], List[List[E]], List[Map[K,V]], Map[K,V] or
    // Map[K, List[V]]. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    List<Map<String, String>> entities = dataTable.asMaps();

    for (var entity : entities) {
      String name = entity.get("name");
      String email = entity.get("parentEmail");

      Parent parentOfStudent = (Parent) User.getWithEmail(email);
      Student student = Student.getWithName(name);

      student.setParent(parentOfStudent);
    }
  }

  /**
   * @author Lune Letailleur
   */
  @Given("the following item entities exist in the system")
  public void the_following_item_entities_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List[E], List[List[E]], List[Map[K,V]], Map[K,V] or
    // Map[K, List[V]]. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    List<Map<String, String>> entities = dataTable.asMaps();

    for (var entity : entities) {
      String name = entity.get("name");
      int price = Integer.parseInt(entity.get("price"));
      coolSupplies.addItem(name, price);
    }
  }

  /**
   * @author Lune Letailleur
   */
  @Given("the following grade bundle entities exist in the system")
  public void the_following_grade_bundle_entities_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<List<String>> gradeBundles = dataTable.asLists(String.class);

    // Skip index zero since it contains column headers
    for (int i = 1; i < gradeBundles.size(); i++) {
      List<String> curData = gradeBundles.get(i);
      String name = curData.get(0);
      int discount = Integer.parseInt(curData.get(1));
      Grade grade = Grade.getWithLevel(curData.get(2));
      new GradeBundle(name, discount, coolSupplies, grade);
    }
  }

  /**
   * @author Edouard Dupont
   */
  @Given("the following bundle item entities exist in the system")
  public void the_following_bundle_item_entities_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> row : rows) {
        int quantity = Integer.parseInt(row.get("quantity"));
        String levelStr = row.get("level");
        String gradeBundleName = row.get("gradeBundleName");
        String itemName = row.get("itemName");

        BundleItem.PurchaseLevel level = BundleItem.PurchaseLevel.valueOf(levelStr);

        // Find the GradeBundle by name
        GradeBundle gradeBundle = null;
        for (GradeBundle gb : coolSupplies.getBundles()) {
            if (gb.getName().equals(gradeBundleName)) {
                gradeBundle = gb;
                break;
            }
        }

        // Find the Item by name
        Item item = null;
        for (Item i : coolSupplies.getItems()) {
            if (i.getName().equals(itemName)) {
                item = i;
                break;
            }
        }

        // Add the BundleItem to the system
        new BundleItem(quantity, level, coolSupplies, gradeBundle, item);
      }
  }

  /**
   * @author Edouard Dupont
   */
  @Given("the following order entities exist in the system")
  public void the_following_order_entities_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : rows) {
        int number = Integer.parseInt(row.get("number"));
        Date date = Date.valueOf(row.get("date"));
        String levelStr = row.get("level");
        String parentEmail = row.get("parentEmail");
        String studentName = row.get("studentName");

        // Convert level string to PurchaseLevel enum
        BundleItem.PurchaseLevel level = BundleItem.PurchaseLevel.valueOf(levelStr);

        // Find the Parent by email
        Parent parent = null;
        for (Parent p : coolSupplies.getParents()) {
            if (p.getEmail().equals(parentEmail)) {
                parent = p;
                break;
            }
        }

        // Find the Student by name
        Student student = null;
        for (Student s : coolSupplies.getStudents()) {
            if (s.getName().equals(studentName)) {
                student = s;
                break;
            }
        }

        // Create the Order
        new Order(number, date, level, parent, student, coolSupplies);
    }
  }

  /**
   * @author Edouard Dupont
   */
  @Given("the following order item entities exist in the system")
  public void the_following_order_item_entities_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> row : rows) {
        int quantity = Integer.parseInt(row.get("quantity"));
        int orderNumber = Integer.parseInt(row.get("orderNumber"));
        String itemName = row.get("itemName");

        // Find the Order by number
        Order order = Order.getWithNumber(orderNumber);

        // Determine if the item is a GradeBundle or an Item
        InventoryItem inventoryItem = null;

        // Find the Item by name
        for (Item item : coolSupplies.getItems()) {
            if (item.getName().equals(itemName)) {
                inventoryItem = item;
                break;
            }
        }

        if (inventoryItem == null) {
          for (GradeBundle bundle : coolSupplies.getBundles()) {
            if (bundle.getName().equals(itemName)) {
              inventoryItem = bundle;
              break;
            }
          }
        }
        // Create the OrderItem
        new OrderItem(quantity, coolSupplies, order, inventoryItem);
    }
  }

  /**
   * @author Edouard Dupont
   */
  @Given("the order {string} is marked as {string}")
  public void the_order_is_marked_as(String orderID, String status) {
    int orderNumber = Integer.parseInt(orderID);

    // Retrieve the order
    Order order = Order.getWithNumber(orderNumber);

    // Set the Order status
    switch (status) {
        case "Started":
            // Default status; do nothing
            break;
        case "Paid":
            if (order.getStatusFullName().equals("Started")) {
                order.pay();
            }
            break;
        case "Penalized":
            if (order.getStatusFullName().equals("Started")) {
                order.startSchoolYear();
            }
            break;
        case "Prepared":
            if (order.getStatusFullName().equals("Started")) {
              order.pay();
              order.startSchoolYear();
            }

            if (order.getStatusFullName().equals("Paid")) {
              order.startSchoolYear();
            }

            if (order.getStatusFullName().equals("Penalized")) {
              order.payForEverything();
            }
            break;
        case "PickedUp":
          if (order.getStatusFullName().equals("Started")) {
            order.pay();
            order.startSchoolYear();
          }

          if (order.getStatusFullName().equals("Paid")) {
            order.startSchoolYear();
          }

          if (order.getStatusFullName().equals("Penalized")) {
            order.payForEverything();
          }

          if (order.getStatusFullName().equals("Prepared")) {
            order.receiveOrder();
          }

          System.out.println(order.getStatusFullName());

          break;
    }
  }

  /**
   * @author Edouard Dupont
   */
  @When("the parent attempts to update an order with number {string} to purchase level {string} and student with name {string}")
  public void the_parent_attempts_to_update_an_order_with_number_to_purchase_level_and_student_with_name(
      String orderID, String purLevel, String stuName) {
    callController(Iteration3Controller.updateOrder(purLevel, stuName, orderID));
  }

  /**
   * @author Edouard Dupont
   */
  @When("the parent attempts to add an item {string} with quantity {string} to the order {string}")
  public void the_parent_attempts_to_add_an_item_with_quantity_to_the_order(String itemName,
      String itemQty, String orderNum) {
    callController(Iteration3Controller.addItem(itemName, itemQty, orderNum));
  }


  /**
   * @author Trevor Piltch 
   */
  @When("the parent attempts to update an item {string} with quantity {string} in the order {string}")
  public void the_parent_attempts_to_update_an_item_with_quantity_in_the_order(String item,
      String quantity, String order) {
     callController(Iteration3Controller.updateOrderQuantity(item, quantity, order));
  }

  
  /**
   * @author Trevor Piltch
   */
  @When("the parent attempts to delete an item {string} from the order {string}")
  public void the_parent_attempts_to_delete_an_item_from_the_order(String item, String order) {
    callController(Iteration3Controller.deleteItem(item, order));
  }


  /**
   * @author Trevor Piltch
   */
  @When("the parent attempts to get from the system the order with number {string}")
  public void the_parent_attempts_to_get_from_the_system_the_order_with_number(String order) {
    transferOrder = Iteration3Controller.viewOrder(order);
  }

  /**
   * @author Trevor Piltch
   */
  @When("the parent attempts to cancel the order {string}")
  public void the_parent_attempts_to_cancel_the_order(String order) {
    callController(Iteration3Controller.cancelOrder(order));
  }

 
  /**
   * @author Trevor Piltch
   */
  @When("the parent attempts to pay for the order {string} with authorization code {string}")
  public void the_parent_attempts_to_pay_for_the_order_with_authorization_code(String order,
      String authoCode) {
    callController(Iteration3Controller.payForOrder(order, authoCode));
  }

  /**
   * @author Kenny-Alexander Joseph
   */
  @When("the admin attempts to start a school year for the order {string}")
  public void the_admin_attempts_to_start_a_school_year_for_the_order(String string) {
    callController(Iteration3Controller.startSchoolYear(string));
  }

  /**
   * @author Kenny-Alexander Joseph
   */  
  @When("the parent attempts to pay penalty for the order {string} with penalty authorization code {string} and authorization code {string}")
  public void the_parent_attempts_to_pay_penalty_for_the_order_with_penalty_authorization_code_and_authorization_code(
      String string, String string2, String string3) {
    callController(Iteration3Controller.payPenaltyForOrder(string, string2, string3));
  }

  /**
   * @author Kenny-Alexander Joseph
   */
  @When("the student attempts to pickup the order {string}")
  public void the_student_attempts_to_pickup_the_order(String string) {
    callController(Iteration3Controller.pickUpOrder(string));
  }

  /**
   * @author Kenny-Alexander Joseph
   */
  @When("the school admin attempts to get from the system all orders")
  public void the_school_admin_attempts_to_get_from_the_system_all_orders() {
    for (Order order : coolSupplies.getOrders()) {
      Iteration3Controller.viewOrder(String.valueOf(order.getNumber()));
    }
  }

  /**
   * @author Kenny-Alexander Joseph
   */
  @Then("the order {string} shall contain penalty authorization code {string}")
  public void the_order_shall_contain_penalty_authorization_code(String string, String string2) {
    Order order = Order.getWithNumber(Integer.parseInt(string));
    assertNotNull("Expected order with number "+string+ " to exist", order);
    String penaltyCode = order.getPenaltyAuthorizationCode();
    assertEquals(penaltyCode, string2);
  }

  /**
   * @author Kenny-Alexander Joseph
   */
  @Then("the order {string} shall not contain penalty authorization code {string}")
  public void the_order_shall_not_contain_penalty_authorization_code(String string,
      String string2) {
    Order order = Order.getWithNumber(Integer.parseInt(string));
    assertNotNull("Expected order with number "+string+ " to exist", order);
    String penaltyCode = order.getPenaltyAuthorizationCode();
    assertNotEquals("Expected order with number "+string+" to not have penalty authorization code "+string2,penaltyCode,string2);
  }


  /**
   * @author Clara Dupuis
   */
  @Then("the order {string} shall not contain authorization code {string}")
  public void the_order_shall_not_contain_authorization_code(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    int orderId = Integer.parseInt(string);
    Order particularOrder = Order.getWithNumber(orderId);

    assertNotNull("Expected order with number "+ orderId+ " to exist", particularOrder);

    String authorizationCode = particularOrder.getAuthorizationCode();

    assertNotEquals("Expected authorization code to be " + string2, string2, authorizationCode);

  }

  /**
   * @author Clara Dupuis
   */
  @Then("the order {string} shall not exist in the system")
  public void the_order_shall_not_exist_in_the_system(String string) {
    // Write code here that turns the phrase above into concrete actions

    int orderId= Integer.parseInt(string);
    boolean orderExists = Order.hasWithNumber(orderId);

    assertFalse("Expected order with number " + string +  "not to exist", orderExists);


}
  /**
   * @author Clara Dupuis
   */
  @Then("the order {string} shall contain authorization code {string}")
  public void the_order_shall_contain_authorization_code(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    int orderId = Integer.parseInt(string);
    Order particularOrder = Order.getWithNumber(orderId);

    assertNotNull("Expected order with number "+ orderId+ " to exist", particularOrder);

    String authorizationCode = particularOrder.getAuthorizationCode();

    assertEquals("Expected authorization code to be " + string2, string2, authorizationCode);

  }

  /**
   * @author Clara Dupuis
   */
  @Then("the order {string} shall contain {string} item")
  public void the_order_shall_contain_item(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    int orderId = Integer.parseInt(string);
    Order particularOrder = Order.getWithNumber(orderId);

    assertNotNull("Expected order with number "+ orderId+ " to exist", particularOrder);

    List<OrderItem> listOfItems = particularOrder.getOrderItems();

    assertTrue("Expected 1 item was " + listOfItems.size(), listOfItems.size() == 1);
  }

  /**
   * @author Clara Dupuis
   */
  @Then("the order {string} shall not contain {string}")
  public void the_order_shall_not_contain(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    int orderId = Integer.parseInt(string);
    Order particularOrder = Order.getWithNumber(orderId);

    assertNotNull("Expected order with number "+ orderId+ " to exist", particularOrder);

    List<OrderItem> ListOfItems = particularOrder.getOrderItems();
    boolean itemExists = false;
  }

  /**
   * @author Nil Akkurt
   */
  @Then("the number of order items in the system shall be {string}")
  public void the_number_of_order_items_in_the_system_shall_be(String string) {
    int itemsInSystem = CoolSuppliesApplication.getCoolSupplies().numberOfOrderItems();
    int numOfItems= Integer.parseInt(string);
    assertEquals("Expected"+ numOfItems + "order items, found" + itemsInSystem, numOfItems, itemsInSystem);
  }

  /**
   * @author Nil Akkurt
   */
  @Then("the order {string} shall contain {string} items")
  public void the_order_shall_contain_items(String string, String string2) {
    int orderNum = Integer.parseInt(string);
    int numOfItems = Integer.parseInt(string2);
    Order myOrder = Order.getWithNumber(orderNum);
    assertNotNull("Expected order"+ orderNum + "to exist.", myOrder);
    int actNumOfItems = myOrder.numberOfOrderItems();
    assertEquals("Expected"+ numOfItems + "order items in order" + orderNum + ", found" + actNumOfItems,
            numOfItems, actNumOfItems);
  }

  /**
   * @author Nil Akkurt
   */
  @Then("the order {string} shall not contain {string} with quantity {string}")
  public void the_order_shall_not_contain_with_quantity(String string, String string2,
                                                        String string3) {
    int wrongQuantity = Integer.parseInt(string3);
    int orderNum = Integer.parseInt(string);
    Order myOrder = Order.getWithNumber(orderNum);
    assertNotNull("Expected order "+ orderNum + " to exist.", myOrder);
    int itemQuantity = -100;
    List<OrderItem> itemsInOrder = myOrder.getOrderItems();

    for(OrderItem item: itemsInOrder){
      if(string2.equals(item.getItem().getName())){   //expected item to exist?
        itemQuantity = item.getQuantity();
      }
    }
    assertNotEquals("The quantity of item " + string2 + " should not be "+ wrongQuantity, itemQuantity, wrongQuantity);

  }

  /**
   * @author Nil Akkurt
   */
  @Then("the order {string} shall contain {string} with quantity {string}")
  public void the_order_shall_contain_with_quantity(String string, String string2, String string3) {
    int expQuantity = Integer.parseInt(string3);
    int orderNum = Integer.parseInt(string);
    int itemQuantity = 0;
    Order myOrder = Order.getWithNumber(orderNum);
    assertNotNull("Expected order"+ orderNum + "to exist.", myOrder);
    List<OrderItem> itemsInOrder = myOrder.getOrderItems();
    for(OrderItem item: itemsInOrder){
      if(string2.equals(item.getItem().getName())){
        itemQuantity = item.getQuantity();
      }
    }
    assertEquals("Expected" + expQuantity + "amount of" + string2 + "in order" + orderNum + ", found" +
            itemQuantity, expQuantity, itemQuantity);
  }


  /**
   * @author Nil Akkurt
   */
  @Then("the order {string} shall be marked as {string}")
  public void the_order_shall_be_marked_as(String string, String string2) {
    int orderNum = Integer.parseInt(string);
    Order myOrder = Order.getWithNumber(orderNum);
    assertNotNull("Expected order "+ orderNum + " to exist. ", myOrder);
    String orderStatus = myOrder.getStatusFullName();
    assertEquals("Expected order " + orderNum + " to be marked as " + string2 + " found " + orderStatus, string2, orderStatus);

  }


  /**
   * @author Nil Akkurt
   */
  @Then("the number of orders in the system shall be {string}")
  public void the_number_of_orders_in_the_system_shall_be(String string) {
    int numOfOrders = Integer.parseInt(string);
    int ordersInSystem = CoolSuppliesApplication.getCoolSupplies().numberOfOrders();
    assertEquals("Expected"+ numOfOrders + "orders, but found" + ordersInSystem, numOfOrders, ordersInSystem);
  }

  /*
   * @author Dimitri Christopoulos
   */
  @Then("the order {string} shall contain level {string} and student {string}")
  public void the_order_shall_contain_level_and_student(String string, String string2,
      String string3) {
   
    // Obtain the order object
    int orderNumber = Integer.parseInt(string);


    List<Order> orderList = coolSupplies.getOrders();
    Order orderInSystem = null;

    // Find order from the order in the system
    for (Order orderFromList : orderList) {
      if (orderFromList.getNumber() == orderNumber) {
        orderInSystem = orderFromList;
      }
    }

    // Check if the order was found
    assertNotNull("Order with number " + orderNumber + " does not exist", orderInSystem);
  
    // Update the level
    BundleItem.PurchaseLevel level = BundleItem.PurchaseLevel.valueOf(string2);
    assertEquals("Expected order to have level " + level, level, orderInSystem.getLevel());

    // Set the student name to the order
    Student student = Student.getWithName(string3);
    assertNotNull("Expected student with name " + string3, student);

    assertEquals("Expected order to be assigned to student " + string3, student, orderInSystem.getStudent());
      
  }

  /*
   * @author Dimitri Christopoulos
   */
  @Given("order {string} is marked as {string}")
  public void order_is_marked_as(String string, String string2) {
    
    // Parse string into integer, then create new Order object

    Order orderInSystem = null;

    List<Order> orders = coolSupplies.getOrders();
    for (Order order : orders) {
      if (order.getNumber() == Integer.parseInt(string)) {
        orderInSystem = order;
      }
    }
    assertNotNull("Order not found: " + string, orderInSystem);
  }

  /*
   * @author Dimitri Christopoulos
   */
  @Then("the error {string} shall be raised")
  public void the_error_shall_be_raised(String string) {
    assertTrue("The expected error ** " + string + " ** was not raised. Found: " + errString, errString.contains(string));
  }

  /*
   * @author Dimitri Christopoulos
   */
  @Then("the following order entities shall be presented")
  public void the_following_order_entities_shall_be_presented(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List[E], List[List[E]], List[Map[K,V]], Map[K,V] or
    // Map[K, List[V]]. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
  
    List<Map<String, String>> entities = dataTable.asMaps();

    for (Map<String, String> entity : entities) {
      String parentEmail = entity.get("parentEmail");
      String orderNumber = entity.get("number");
      String date = entity.get("date");
      String level = entity.get("level");
      String studentName = entity.get("studentName");
      String status = entity.get("status");
      String authorizationCode = entity.get("authorizationCode");
      String penaltyAuthorizationCode = entity.get("penaltyAuthorizationCode");
      String totalPrice = entity.get("totalPrice");

      for (TOOrder transferOrder : transferOrders) {
        if (!(parentEmail.equals(transferOrder.getParentEmail()) && 
        orderNumber.equals(transferOrder.getNumber()) && 
        date.equals(transferOrder.getDate()) && 
        level.equals(transferOrder.getLevel()) && 
        studentName.equals(transferOrder.getStudentName()) && 
        status.equals(transferOrder.getStatus()) && 
        authorizationCode.equals(transferOrder.getAuthCode()) && 
        penaltyAuthorizationCode.equals(transferOrder.getPenaltyAuthCode()) && totalPrice.equals(String.valueOf(transferOrder.getPrice())))) {
          throw new AssertionError("Order entity not found.");
        }
      }

    }
  }

  /*
   * @author Dimitri Christopoulos
   */
  @Then("the following order items shall be presented for the order with number {string}")
  public void the_following_order_items_shall_be_presented_for_the_order_with_number(String string,
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List[E], List[List[E]], List[Map[K,V]], Map[K,V] or
    // Map[K, List[V]]. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    
    int orderNumber = Integer.parseInt(string);

    // Find order in system
    List<Order> orderList = coolSupplies.getOrders();
    Order order = null;
    for (Order orderFromList : orderList) {
      if (orderFromList.getNumber() == orderNumber) {
        order = orderFromList;
      }
    }
    assertNotNull("Order with order number " + orderNumber + " not found", order);  // Error if not found

    // Get map of order items to be validated
    List<Map<String, String>> orderItemsInDatatable = dataTable.asMaps();

    for (Map<String, String> orderItem : orderItemsInDatatable) {
      String quantity = orderItem.get("quantity");
      String itemName = orderItem.get("itemName");
      String gradeBundleName = orderItem.get("gradeBundleName");
      String price = orderItem.get("price");
      String discount = orderItem.get("discount");

      for (TOOrderItem transferOrderItem : transferOrderItems) {
        if (!(quantity.equals(transferOrderItem.getQuantity()) &&
         itemName.equals(transferOrderItem.getName()) && gradeBundleName.equals(transferOrderItem.getGradeBundle()) &&
          price.equals(transferOrderItem.getPrice()) && discount.equals(transferOrderItem.getPrice()))) {
            throw new AssertionError("Order item not found");
          }
      }
    }
  }

  /*
   * @author Dimitri Christopoulos
   */
  @Then("no order entities shall be presented")
  public void no_order_entities_shall_be_presented() {
    assertNull("Expected no order entities", transferOrder);
  }

  /* Helper methods */
  private void callController(String result) {
    if (!result.isEmpty()) {
      errString += result;
      System.out.println("Error thrown: " + result);
    }
  }
}
