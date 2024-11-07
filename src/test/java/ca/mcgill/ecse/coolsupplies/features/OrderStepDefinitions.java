package ca.mcgill.ecse.coolsupplies.features;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.BundleItem;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.InventoryItem;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.model.Order;
import ca.mcgill.ecse.coolsupplies.model.OrderItem;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.Student;
import ca.mcgill.ecse.coolsupplies.model.User;
import io.cucumber.core.backend.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class OrderStepDefinitions {
  private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
  private String errString = "";

  /**
   * This test attemps to verify that the given parent entities exist in the system.
   * @author Lune Letailleur
   * @param dataTable represents the parent entities we wish to exist in the system
   * @return void
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

    List<Map<String, String, String, String >> entities = dataTable.asMaps();

    for (var entity:entities){
      String aEmail = entity.get("email");
      String aPassword = entity.get("password");
      String aName = entity.get("name");
      int aPhoneNumber = (int) entity.get("phoneNumber");
      coolSupplies.addParent(aEmail, aPassword, aName, aPhoneNumber);
    }
  }

  /**
   * This test attemps to verify that the given grade entities exist in the system.
   * @author Lune Letailleur
   * @param dataTable represents the grade entities we wish to exist in the system
   * @return void
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
   * This test attemps to verify that the given student entities exist in the system.
   * @author Lune Letailleur
   * @param dataTable represents the student entities we wish to exist in the system
   * @return void
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
      Grade gradeLevel = (Grade) entity.get("gradeLevel");
      coolSupplies.addStudent(name, gradeLevel);
    }
  }

  /**
   * This test attemps to verify that the given student entities exist for a parent in the system.
   * @author Lune Letailleur
   * @param dataTable represents the student entities we wish to exist in the system
   * @return void
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
   * This test attemps to verify that the given item entities exist in the system.
   * @author Lune Letailleur
   * @param dataTable represents the item entities we wish to exist in the system
   * @return void
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
      int price = (int) entity.get("price");
      coolSupplies.addItem(name, price);
    }
  }

  /**
   * This test attemps to verify that the given grade bundle entities exist in the system.
   * @author Lune Letailleur
   * @param dataTable represents the grade bundle entities we wish to exist in the system
   * @return void
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

  // MARK: Edouard
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
    throw new io.cucumber.java.PendingException();
  }

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
    throw new io.cucumber.java.PendingException();
  }

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
    throw new io.cucumber.java.PendingException();
  }

  @Given("the order {string} is marked as {string}")
  public void the_order_is_marked_as(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the parent attempts to update an order with number {string} to purchase level {string} and student with name {string}")
  public void the_parent_attempts_to_update_an_order_with_number_to_purchase_level_and_student_with_name(
      String string, String string2, String string3) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the parent attempts to add an item {string} with quantity {string} to the order {string}")
  public void the_parent_attempts_to_add_an_item_with_quantity_to_the_order(String string,
      String string2, String string3) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
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
    PurchaseLevel level = PurchaseLevel.valueOf(string2);
    orderInSystem.setLevel(level);
    assertEquals("Expected order to have level " + level, level, orderInSystem.getLevel());

    // Set the student name to the order
    Student student = Student.getWithName(string3);
    assertNotNull("Expected student with name " + string3, student);

    specificOrder.setStudent(student);
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

    orderInSystem.setStatus(Status.Started);
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

      // Get order from system, check if it exists and check order number
      List<Order> orderList = coolSupplies.getOrders();
      Order order = null;
      for (Order orderFromList : orderList) {
        if (orderFromList.getNumber() == Integer.parseInt(orderNumber)) {
          order = orderFromList;
        }
      }

      assertNotNull("Expected order with order number " + orderNumber + " to exist", order);  // Error if not found
      assertEquals("Expected order number " + orderNumber, orderNumber, order.getNumber());

      // Check date
      assertEquals("Expected date: " + date, date, order.getDate());

      // Check email
      assertEquals("Expected the email " + parentEmail, parentEmail, order.getParent().getEmail());

      // Check if level is correct
      PurchaseLevel expectedLevel = PurchaseLevel.valueOf(level);
      assertEquals("Expected order to have level " + level, expectedLevel, order.getLevel());

      // Check if student is correct
      Student student = Student.getWithName(studentName);
      assertEquals("Expected order to have student " + studentName, student, order.getStudent());

      // Check status
      assertEquals("Expected the order to have status: " + status, status, order.getStatusFullName());
      
      // Check auhtorization code
      assertEquals("Expected authorization code "+ authorizationCode, authorizationCode, order.getAuthorizationCode());

      // Check late authorization code
      assertEquals("Expected penalty authorization code "+ penaltyAuthorizationCode, penaltyAuthorizationCode, order.getPenaltyAuthorizationCode());

      // Check price
      int priceInSystem = 0;

      // Look over all order items
      for (OrderItem orderItem : order.getOrderItems()) {

        int quantityOfOrderItem = orderItem.getQuantity();  // Number of orderItem

        // Turn order item into invenotry item 
        InventoryItem inventoryItem = orderItem.getItem();

        // Inventory item is either an (Individual) Item or a GradeBundle

        // inventoryItem is an Item
        if (inventoryItem instanceof Item) {

          Item item = (Item) inventoryItem;
          int priceOfItems = item.getPrice() * quantityOfOrderItem;
          priceInSystem += priceOfItems;

        }

        // inventoryItem is a GradeBundle
        else if (inventoryItem instanceof GradeBundle) {

          int priceOfBundle = 0;
          PurchaseLevel purchaseLevel = order.getLevel();  // Purchase level selected in Order
          

          GradeBundle gradeBundle = (GradeBundle) inventoryItem;
          int discount = gradeBundle.getDiscount();

          // Items contained in the bundle, includes items of all levels
          List<BundleItem> itemsInBundle = gradeBundle.getBundleItems();


          int numOfIterations = 0; // Used to count if discount should be applied (discount only if 2 or more distinct items)

          // Loop over all bundle items, only want the ones that are equal to or higher in priority to the selected level
          for (BundleItem bundleItem : itemsInBundle) {

            int quantity = bundleItem.getQuantity();
            int priceOfBundleItem = 0;

            // Only include the bundle items with the appropriate level
            if (purchaseLevel.compareTo(bundleItem.getLevel()) >= 0) {  // enum PurchaseLevel {Mandatory, Recommended, Optional}, Optional has the highest value
              Item itemInBundle = bundleItem.getItem();
              priceOfBundleItem = itemInBundle.getPrice();
            }
            
            priceOfBundle += (quantity * priceOfBundleItem);

            numOfIterations++;
          }

          // Apply discount if 2 or more distinct items are bought from the bundle
          if (numOfIterations > 1) {
            priceOfBundle = priceOfBundle * ((1-discount)/100);
          }
          
          // Multiply by the quantity of Bundles ordered
          priceOfBundle *= quantityOfOrderItem;

          // Update total price
          priceInSystem += priceOfBundle;

        }
        
      }
      assertEquals("Expected price " + totalPrice, totalPrice, priceInSystem);
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
    int orderNumberInSystem = Integer.parseInt(string);
    Order orderInSystem = Order.getWithNumber(orderNumberInSystem);
    List<OrderItem> orderItemsInSystem = orderInSystem.getOrderItems();
    assertNotNull("Expected orderr to be in system", orderInSystem);

    List<Map<String, String>> orderItems = dataTable.asMaps();

    for (Map<String, String> orderItem : orderItems) {
      String quantity = orderItem.get("quantity");
      assertEquals("Expected quantity of order to be " + quantity, quantity, orderInSystem.getQuantity());
      String itemName = orderItem.get("itemName");
      String gradeBundleName = orderItem.get("gradeBundleName");
      String price = orderItem.get("price");
      String discount = orderItem.get("discount");
    
    }
  }

  /*
   * @author Dimitri Christopoulos
   */
  @Then("no order entities shall be presented")
  public void no_order_entities_shall_be_presented() {
    
    assertTrue("Expected no order entities", coolSupplies.getOrders().isEmpty());
  }

  /* Helper methods */
  private void callController(String result) {
    if (!result.isEmpty()) {
      errString += result;
    }
  }
}
