package ca.mcgill.ecse.coolsupplies.features;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.BundleItem;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.InventoryItem;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.model.Order;
import ca.mcgill.ecse.coolsupplies.model.OrderItem;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.Student;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;
import java.sql.Date;

public class OrderStepDefinitions {
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

  /**
   * @author Edouard Dupont
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
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> row : rows) {
        int quantity = Integer.parseInt(row.get("quantity"));
        String levelStr = row.get("level");
        String gradeBundleName = row.get("gradeBundleName");
        String itemName = row.get("itemName");

        // Convert level string to PurchaseLevel enum
        BundleItem.PurchaseLevel level;
        try {
            level = BundleItem.PurchaseLevel.valueOf(levelStr);
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid purchase level: " + levelStr);
        }

        // Find the GradeBundle by name
        GradeBundle gradeBundle = null;
        for (GradeBundle gb : coolSupplies.getBundles()) {
            if (gb.getName().equals(gradeBundleName)) {
                gradeBundle = gb;
                break;
            }
        }
        if (gradeBundle == null) {
            throw new Exception("GradeBundle not found: " + gradeBundleName);
        }

        // Find the Item by name
        Item item = null;
        for (Item i : coolSupplies.getItems()) {
            if (i.getName().equals(itemName)) {
                item = i;
                break;
            }
        }
        if (item == null) {
            throw new Exception("Item not found: " + itemName);
        }

        // Add the BundleItem to the system
        BundleItem bundleItem = new BundleItem(quantity, level, coolSupplies, gradeBundle, item);
        coolSupplies.addBundleItem(bundleItem);
      }
  }

  /**
   * @author Edouard Dupont
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
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> row : rows) {
        int number = Integer.parseInt(row.get("number"));
        Date date = Date.valueOf(row.get("date"));
        String levelStr = row.get("level");
        String parentEmail = row.get("parentEmail");
        String studentName = row.get("studentName");
        String status = row.get("status");
        String authorizationCode = row.get("authorizationCode");
        String penaltyAuthorizationCode = row.get("penaltyAuthorizationCode");

        // Convert level string to PurchaseLevel enum
        BundleItem.PurchaseLevel level;
        try {
            level = BundleItem.PurchaseLevel.valueOf(levelStr);
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid purchase level: " + levelStr);
        }

        // Find the Parent by email
        Parent parent = null;
        for (Parent p : coolSupplies.getParents()) {
            if (p.getEmail().equals(parentEmail)) {
                parent = p;
                break;
            }
        }
        if (parent == null) {
            throw new Exception("Parent not found: " + parentEmail);
        }

        // Find the Student by name
        Student student = null;
        for (Student s : coolSupplies.getStudents()) {
            if (s.getName().equals(studentName)) {
                student = s;
                break;
            }
        }
        if (student == null) {
            throw new Exception("Student not found: " + studentName);
        }

        // Create the Order
        Order order = new Order(number, date, level, parent, student, coolSupplies);
        order.setAuthorizationCode(authorizationCode);
        order.setPenaltyAuthorizationCode(penaltyAuthorizationCode);

        // Set the Order status
        switch (status) {
            case "Started":
                // Default status; do nothing
                break;
            case "Paid":
                order.pay(); // Assuming this method exists
                break;
            case "Penalized":
                order.startSchoolYear(); // Transition to Penalized
                break;
            case "Prepared":
                order.pay();
                order.startSchoolYear();
                break;
            case "PickedUp":
                order.pay();
                order.startSchoolYear();
                order.receiveOrder();
                break;
            default:
                throw new Exception("Invalid order status: " + status);
        }

        // Add the Order to CoolSupplies
        coolSupplies.addOrder(order);
    }
  }

  /**
   * @author Edouard Dupont
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
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> row : rows) {
        int quantity = Integer.parseInt(row.get("quantity"));
        int orderNumber = Integer.parseInt(row.get("orderNumber"));
        String itemName = row.get("itemName");
        String gradeBundleName = row.get("gradeBundleName");

        // Find the Order by number
        Order order = Order.getWithNumber(orderNumber);
        if (order == null) {
            throw new Exception("Order not found: " + orderNumber);
        }

        // Determine if the item is a GradeBundle or an Item
        InventoryItem inventoryItem = null;

        if (itemName != null && !itemName.isEmpty()) {
            // Find the Item by name
            for (Item item : coolSupplies.getItems()) {
                if (item.getName().equals(itemName)) {
                    inventoryItem = item;
                    break;
                }
            }
            if (inventoryItem == null) {
                throw new Exception("Item not found: " + itemName);
            }
        } else if (gradeBundleName != null && !gradeBundleName.isEmpty()) {
            // Find the GradeBundle by name
            for (GradeBundle bundle : coolSupplies.getBundles()) {
                if (bundle.getName().equals(gradeBundleName)) {
                    inventoryItem = bundle;
                    break;
                }
            }
            if (inventoryItem == null) {
                throw new Exception("GradeBundle not found: " + gradeBundleName);
            }
        } else {
            throw new Exception("Either itemName or gradeBundleName must be provided");
        }

        // Create the OrderItem
        OrderItem orderItem = new OrderItem(quantity, coolSupplies, order, inventoryItem);

        // Add the OrderItem to CoolSupplies
        coolSupplies.addOrderItem(orderItem);
    }
  }

  /**
   * @author Edouard Dupont
   */
  @Given("the order {string} is marked as {string}")
  public void the_order_is_marked_as(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    int orderNumber = Integer.parseInt(string);

    // Retrieve the order
    Order order = Order.getWithNumber(orderNumber);
    if (order == null) {
        throw new Exception("Order not found: " + orderNumber);
    }

    // Set the Order status
    switch (string2) {
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
            if (!order.getStatusFullName().equals("Prepared")) {
                order.pay();
                order.startSchoolYear();
            }
            break;
        case "PickedUp":
            if (!order.getStatusFullName().equals("PickedUp")) {
                order.pay();
                order.startSchoolYear();
                order.receiveOrder();
            }
            break;
        default:
            throw new Exception("Invalid order status: " + string2);
    }
  }

  /**
   * @author Edouard Dupont
   */
  @When("the parent attempts to update an order with number {string} to purchase level {string} and student with name {string}")
  public void the_parent_attempts_to_update_an_order_with_number_to_purchase_level_and_student_with_name(
      String string, String string2, String string3) {
    // Write code here that turns the phrase above into concrete actions

      int orderNumber = Integer.parseInt(string);
  
      // Retrieve the order
      Order order = Order.getWithNumber(orderNumber);
      if (order == null) {
          throw new Exception("Order not found: " + orderNumber);
      }
  
      // Convert level string to PurchaseLevel enum
      BundleItem.PurchaseLevel level;
      try {
          level = BundleItem.PurchaseLevel.valueOf(string2);
      } catch (IllegalArgumentException e) {
          throw new Exception("Invalid purchase level: " + string2);
      }
  
      // Find the Student by name
      Student student = null;
      for (Student s : order.getParent().getStudents()) {
          if (s.getName().equals(string3)) {
              student = s;
              break;
          }
      }
      if (student == null) {
          throw new Exception("Student not found: " + string3);
      }
  
      // Update the order
      order.setLevel(level);
      order.setStudent(student);
  }

  /**
   * @author Edouard Dupont
   */
  @When("the parent attempts to add an item {string} with quantity {string} to the order {string}")
  public void the_parent_attempts_to_add_an_item_with_quantity_to_the_order(String string,
      String string2, String string3) {
    // Write code here that turns the phrase above into concrete actions
    int quantity = Integer.parseInt(string2);
    int orderNumber = Integer.parseInt(string3);

    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    // Retrieve the order
    Order order = Order.getWithNumber(orderNumber);
    if (order == null) {
        throw new Exception("Order not found: " + orderNumber);
    }

    // Find the Item by name
    InventoryItem inventoryItem = null;

    // Check if itemName corresponds to an Item
    for (Item item : coolSupplies.getItems()) {
        if (item.getName().equals(string)) {
            inventoryItem = item;
            break;
        }
    }

    // If not found, check if itemName corresponds to a GradeBundle
    if (inventoryItem == null) {
        for (GradeBundle bundle : coolSupplies.getBundles()) {
            if (bundle.getName().equals(string)) {
                inventoryItem = bundle;
                break;
            }
        }
    }

    if (inventoryItem == null) {
        throw new Exception("Item or Bundle not found: " + string);
    }

    // Create and add the OrderItem
    OrderItem orderItem = new OrderItem(quantity, coolSupplies, order, inventoryItem);
    coolSupplies.addOrderItem(orderItem);
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
    // throw new io.cucumbser.java.PendingException();
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

}
