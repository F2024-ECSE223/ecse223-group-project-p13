package ca.mcgill.ecse.coolsupplies.features;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.controller.TOOrderItem;
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
  private List<TOOrder> transferOrders = new ArrayList<>();
  private List<TOOrderItem> transferOrderItems = new ArrayList<>();


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
    throw new io.cucumber.java.PendingException();
  }

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
    throw new io.cucumber.java.PendingException();
  }

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
    throw new io.cucumber.java.PendingException();
  }

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
    throw new io.cucumber.java.PendingException();
  }

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
    throw new io.cucumber.java.PendingException();
  }

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
    throw new io.cucumber.java.PendingException();
  }

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

  @When("the parent attempts to update an item {string} with quantity {string} in the order {string}")
  public void the_parent_attempts_to_update_an_item_with_quantity_in_the_order(String string,
      String string2, String string3) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the parent attempts to delete an item {string} from the order {string}")
  public void the_parent_attempts_to_delete_an_item_from_the_order(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the parent attempts to get from the system the order with number {string}")
  public void the_parent_attempts_to_get_from_the_system_the_order_with_number(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }



  @When("the parent attempts to cancel the order {string}")
  public void the_parent_attempts_to_cancel_the_order(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the parent attempts to pay for the order {string} with authorization code {string}")
  public void the_parent_attempts_to_pay_for_the_order_with_authorization_code(String string,
      String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the admin attempts to start a school year for the order {string}")
  public void the_admin_attempts_to_start_a_school_year_for_the_order(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the parent attempts to pay penalty for the order {string} with penalty authorization code {string} and authorization code {string}")
  public void the_parent_attempts_to_pay_penalty_for_the_order_with_penalty_authorization_code_and_authorization_code(
      String string, String string2, String string3) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the student attempts to pickup the order {string}")
  public void the_student_attempts_to_pickup_the_order(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the school admin attempts to get from the system all orders")
  public void the_school_admin_attempts_to_get_from_the_system_all_orders() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the order {string} shall contain penalty authorization code {string}")
  public void the_order_shall_contain_penalty_authorization_code(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the order {string} shall not contain penalty authorization code {string}")
  public void the_order_shall_not_contain_penalty_authorization_code(String string,
      String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }


  @Then("the order {string} shall not contain authorization code {string}")
  public void the_order_shall_not_contain_authorization_code(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }


  @Then("the order {string} shall not exist in the system")
  public void the_order_shall_not_exist_in_the_system(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the order {string} shall contain authorization code {string}")
  public void the_order_shall_contain_authorization_code(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the order {string} shall contain {string} item")
  public void the_order_shall_contain_item(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the order {string} shall not contain {string}")
  public void the_order_shall_not_contain(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
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
    assertTrue("Expected no order entities", coolSupplies.getOrders().isEmpty());
  }

  /* Helper methods */
  private void callController(String result) {
    if (!result.isEmpty()) {
      errString += result;
    }
  }
}
