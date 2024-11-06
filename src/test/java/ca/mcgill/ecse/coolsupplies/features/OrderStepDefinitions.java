package ca.mcgill.ecse.coolsupplies.features;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import ca.mcgill.ecse.coolsupplies.model.*;

/* Helper Imports */
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


/* JUnit Imports */
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class OrderStepDefinitions {
  private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
  private String errString = "";

  /**
   * @author Lune Letailleur
   */
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
      coolSupplies.addGrade(level);
    }
  }

  /**
   * @author Lune Letailleur
   */
  @Given("the following student entities exist in the system")
  public void the_following_student_entities_exist_in_the_system(
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
      Grade gradeLevel = new Grade(entity.get("gradeLevel"), coolSupplies);
      coolSupplies.addStudent(name, gradeLevel);
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
      int discount = (int) entity.get("discount");
      Grade gradeLevel = (Grade) entity.get("gradeLevel");
      coolSupplies.addBundle(name, discount ,gradeLevel);
    }
  }

  /**
   * This test XXX
   * @author Edouard Dupont
   * @param dataTable represents the grade entities we wish to exist in the system
   * @return void
   */
  @Given("the following bundle item entities exist in the system")
  public void the_following_bundle_item_entities_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List[E], List[List[E]], List[Map[K,V]], Map[K,V] or
    // Map[K, List[V]]. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.

    List<Map<String, String>> bundleItems = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> item : bundleItems) {
        // Assume that `BundleItem` is a model entity and `addBundleItem` is a method in the model layer
        String id = item.get("id");
        String name = item.get("name");
        int quantity = Integer.parseInt(item.get("quantity"));
        // Call the method to create the bundle item entity
        BundleItem bundleItem = new BundleItem(id, name, quantity);
        addBundleItem(bundleItem); // Add item to model layer (is this in model?)
    }
  }

  /**
   * This test XXX
   * @author Edouard Dupont
   * @param dataTable represents the grade entities we wish to exist in the system
   * @return void
   */
  @Given("the following order entities exist in the system")
  public void the_following_order_entities_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List[E], List[List[E]], List[Map[K,V]], Map[K,V] or
    // Map[K, List[V]]. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    
    List<Map<String, String>> orders = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> orderData : orders) {
        String orderId = orderData.get("orderId");
        String status = orderData.get("status");
        // Create the order entity and set the required fields
        Order order = new Order(orderId, status);
        model.addOrder(order); // Add order to model layer
    }
  }

  /**
   * This test XXX
   * @author Edouard Dupont
   * @param dataTable represents the grade entities we wish to exist in the system
   * @return void
   */
  @Given("the following order item entities exist in the system")
  public void the_following_order_item_entities_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List[E], List[List[E]], List[Map[K,V]], Map[K,V] or
    // Map[K, List[V]]. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    
    List<Map<String, String>> orderItems = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> itemData : orderItems) {
        String itemId = itemData.get("itemId");
        String orderId = itemData.get("orderId");
        int quantity = Integer.parseInt(itemData.get("quantity"));
        // Assuming an OrderItem model exists with respective fields
        OrderItem orderItem = new OrderItem(itemId, orderId, quantity);
        model.addOrderItem(orderItem); // Add item to model layer
    }
  }

  /**
   * This test XXX
   * @author Edouard Dupont
   * @param string XXX
   * @param string2 XXX
   * @return void
   */
  @Given("the order {string} is marked as {string}")
  public void the_order_is_marked_as(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    
    Order order = model.getOrderById(string); // Retrieve order from model layer
    if (order != null) {
        order.setStatus(string2); // Set the order's status (find it...)
    } else {
        throw new IllegalArgumentException("Order not found: " + string);
    }
  }

  /**
   * This test XXX
   * @author Edouard Dupont
   * @param string XXX
   * @param string2 XXX
   * @param string3 XXX
   * @return void
   */
  @When("the parent attempts to update an order with number {string} to purchase level {string} and student with name {string}")
  public void the_parent_attempts_to_update_an_order_with_number_to_purchase_level_and_student_with_name(
      String string, String string2, String string3) {
    // Write code here that turns the phrase above into concrete actions
    OrderController.updateOrder(string, string2, string3);
  }

  /**
   * This test XXX
   * @author Edouard Dupont
   * @param string XXX
   * @param string2 XXX
   * @param string3 XXX
   * @return void
   */
  @When("the parent attempts to add an item {string} with quantity {string} to the order {string}")
  public void the_parent_attempts_to_add_an_item_with_quantity_to_the_order(String string,
      String string2, String string3) {
    // Write code here that turns the phrase above into concrete actions
    
    int quantity = Integer.parseInt(quantityStr);
    OrderController.addItemToOrder(orderNumber, itemName, quantity); // Assumes `addItemToOrder` in the controller
  }

  // MARK: Trevor
  /** 
   * @author Trevor Piltch
   */
  @When("the parent attempts to update an item {string} with quantity {string} in the order {string}")
  public void the_parent_attempts_to_update_an_item_with_quantity_in_the_order(String item,
      String quantity, String order) {
    // TODO: add controller call
    // callController(<CONTROLLER>.updateItem(item, quentity, order));
  }
  /**
   * @author Trevor Piltch 
   */
  @When("the parent attempts to delete an item {string} from the order {string}")
  public void the_parent_attempts_to_delete_an_item_from_the_order(String item, String order) {
    // TODO: add controller call
    // callController(<CONTROLLER>.deleteItem(item, order));
  }

  /**
   * @author Trevor Piltch
   */
  @When("the parent attempts to get from the system the order with number {string}")
  public void the_parent_attempts_to_get_from_the_system_the_order_with_number(String order) {
    // TODO: add controller call
    // callController(<CONTROLLER>.getOrder(order));
  }


  /**
   * @author Trevor Piltch
   */
  @When("the parent attempts to cancel the order {string}")
  public void the_parent_attempts_to_cancel_the_order(String order) {
    // TODO: add controller call
    // callController(<CONTROLLER>.cancelOrder(order));
  }

  /**
   * @author Trevor Piltch 
   */
  @When("the parent attempts to pay for the order {string} with authorization code {string}")
  public void the_parent_attempts_to_pay_for_the_order_with_authorization_code(String order,
      String authoCode) {
    // TODO: add controller call
    // callController(<CONTROLLER>.payForOrder(order, authCode));
  }

  /**
   * @author Kenny-Alexander Joseph
   * @param string order number
   * @return void
   */
  @When("the admin attempts to start a school year for the order {string}")
  public void the_admin_attempts_to_start_a_school_year_for_the_order(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();

      //need to make controller api for start school year(10. start school year - clara)

  }

  /**
   * @author Kenny-Alexander Joseph
   * @param string order number in string format
   * @param string2 penalty authorization code in string format
   * @param string3 authorization code in string format
   * @return void
   */  
  @When("the parent attempts to pay penalty for the order {string} with penalty authorization code {string} and authorization code {string}")
  public void the_parent_attempts_to_pay_penalty_for_the_order_with_penalty_authorization_code_and_authorization_code(
      String string, String string2, String string3) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();

    //need to make controller api for order payment(6. pay penalty for order - trev)

  }

  /**
   * @author Kenny-Alexander Joseph
   * @param string order number
   * @return void
   */
  @When("the student attempts to pickup the order {string}")
  public void the_student_attempts_to_pickup_the_order(String string) {
    // Write code here that turns the phrase above into concrete actions
    
    //not sure about this one, is collected order deleted from system?
    //"When a student picks up their supplies, it is noted in the application."
    Order order = Order.getWithNumber(Integer.parseInt(string));
    assertNotNull("Expected order with number "+string+ " to exist",order);
    assertNotNull("Expected order with number "+string+ " to be paid",order.getAuthorizationCode);
    
    //need controller method to check current year to see if order should have late penalty applied
    
  }

  /**
   * @author Kenny-Alexander Joseph
   * @return void
   */
  @When("the school admin attempts to get from the system all orders")
  public void the_school_admin_attempts_to_get_from_the_system_all_orders() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();

    //need to make controller api for view all orders(9. view all orders - dupes)

  }

  /**
   * @author Kenny-Alexander Joseph
   * @param string order number in string format
   * @param string2 penalthy authorization code in string format
   * @return void
   */
  @Then("the order {string} shall contain penalty authorization code {string}")
  public void the_order_shall_contain_penalty_authorization_code(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    Order order = Order.getWithNumber(Integer.parseInt(string));
    assertNotNull("Expected order with number "+string+ " to exist",order);

    String penaltyCode = order.getPenaltyAuthorizationCode();
    assertEquals("Expected order with number "+string+" to have penalty authorization code "+string2,penaltyCode,string2);
  }

  /**
   * @author Kenny-Alexander Joseph
   * @param string order number in string format
   * @param string2 penalthy authorization code in string format
   * @return void
   */
  @Then("the order {string} shall not contain penalty authorization code {string}")
  public void the_order_shall_not_contain_penalty_authorization_code(String string,
      String string2) {
    // Write code here that turns the phrase above into concrete actions
    Order order = Order.getWithNumber(Integer.parseInt(string));
    assertNotNull("Expected order with number "+string+ " to exist",order);

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

    int orderId = Integer.parseInt(string);
    Order particularOrder = Order.getWithNumber(orderId);

    assertNotNull("Expected order with number "+ orderId+ " to exist", particularOrder);

    List<OrderItem> ListOfItems = particularOrder.getOrderItems();
    boolean itemExists = false;

    for (OrderItem item : ListOfItems){
      if (string2.equals(item.getItem().getName())){
        itemExists=true;
        break;
      }
    }
    assertTrue("Expected order to contain item " +string2, itemExists);

  }

  /**
   * @author Clara Dupuis
   */
  @Then("the order {string} shall not contain {string}")
  public void the_order_shall_not_contain(String string, String string2) {
    int orderId = Integer.parseInt(string);
    Order particularOrder = Order.getWithNumber(orderId);

    assertNotNull("Expected order with number "+ orderId+ " to exist", particularOrder);

    List<OrderItem> ListOfItems = particularOrder.getOrderItems();
    boolean itemExists = false;

    for (OrderItem item : ListOfItems){
      if (string2.equals(item.getItem().getName())){
        itemExists=true;
        break;
      }
    }
    assertFalse("Expected order to contain item " +string2, itemExists);

  }



  @Then("the number of order items in the system shall be {string}")
  public void the_number_of_order_items_in_the_system_shall_be(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the order {string} shall contain {string} items")
  public void the_order_shall_contain_items(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the order {string} shall not contain {string} with quantity {string}")
  public void the_order_shall_not_contain_with_quantity(String string, String string2,
      String string3) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }


  @Then("the order {string} shall contain {string} with quantity {string}")
  public void the_order_shall_contain_with_quantity(String string, String string2, String string3) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }


  @Then("the order {string} shall be marked as {string}")
  public void the_order_shall_be_marked_as(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }


  @Then("the number of orders in the system shall be {string}")
  public void the_number_of_orders_in_the_system_shall_be(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the order {string} shall contain level {string} and student {string}")
  public void the_order_shall_contain_level_and_student(String string, String string2,
      String string3) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Given("order {string} is marked as {string}")
  public void order_is_marked_as(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the error {string} shall be raised")
  public void the_error_shall_be_raised(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

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
    throw new io.cucumber.java.PendingException();
  }

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
    throw new io.cucumber.java.PendingException();
  }

  @Then("no order entities shall be presented")
  public void no_order_entities_shall_be_presented() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  /* Helper methods */
  private void callController(String result) {
    if (!result.isEmpty()) {
      errString += result;
    }
  }
}
