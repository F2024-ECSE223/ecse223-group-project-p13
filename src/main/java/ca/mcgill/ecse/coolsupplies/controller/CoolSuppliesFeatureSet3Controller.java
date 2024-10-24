package ca.mcgill.ecse.coolsupplies.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Imports

import ca.mcgill.ecse.coolsupplies.model.*;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;

// Feature Set 3

public class CoolSuppliesFeatureSet3Controller {

  private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * Add item to the system.
   * 
   * @param name name of item
   * @param price price of item
   * @return empty string if successful or an error message for unsuccessful submission
   * @author Dimitri Christopoulos
   */
  public static String addItem(String name, int price) {

    // Validate name - name cannot already be in system
    Item item = (Item) Item.getWithName(name);
    if (item != null) {
      return "The name must be unique.";
    }

    // Validate name - Name must not be empty
    if (name.equals("")) {
      return "The name must not be empty.";
    }

    // Validate Price
    if (price < 0) {
      return "The price must be greater than or equal to 0.";
    }

    // Add new item - input is valid
    Item newItem = new Item(name, price, coolSupplies);
    coolSupplies.addItem(newItem);

    return "";  // Success message
    
  }

  /**
   * Updates an existing item with a new name and new price.
   * 
   * @param name old name of item
   * @param newName new name of item
   * @param newPrice new price of item
   * @return empty string if successful or an error message for unsuccessful submission
   * @author Dimitri Christopoulos
   */
  public static String updateItem(String name, String newName, int newPrice) {

    // Validate input - item must exist
    Item item = (Item) Item.getWithName(name);
    if (item == null) {
      return "The item does not exist.";
    }

    // Validate input - newName must not be empty.
    if (newName.equals("")) {
      return "The name must not be empty.";
    }

    // Validate Price
    if (newPrice < 0) {
      return "The price must be greater than or equal to 0.";
    }

    // Validate newName - Make sure newName is not already in system
    Item newNameItem = (Item) Item.getWithName(newName);
    if (newNameItem != null) {
      return "The name must be unique.";
    }

    // Set new name and new price
    item.setName(newName);
    item.setPrice(newPrice);
    
    return "";  // Success message
  }

  /**
   * Delete an existing item from the system
   * 
   * @param name name of item to be deleted
   * @return empty string if successful or an error message for unsuccessful submission
   * @author Dimitri Christopoulos
   */
  public static String deleteItem(String name) {

    // Check if list of items is empty
    if (coolSupplies.hasItems()) {

      Item item = (Item) Item.getWithName(name);

      // Check if item exists
      if (item == null) {
        return "The item does not exist.";
      }

      // If list is not empty, get item and delete it
      item.delete();

      return "";
    }
    else return "";  // Success message
  }

  /**
   * Get an item by name and return it as a transfer object
   * @param name name of item
   * @return tranfer item of item object
   * @author empty string if successful or an error message for unsuccessful submission
   * @author Dimitri Christopoulos
   */
  public static TOItem getItem(String name) {

    Item item = (Item) Item.getWithName(name);

    // If item not found, return null
    if (item == null) {
      return null;
    }

    TOItem to_item = new TOItem(item.getName(), item.getPrice());
    return to_item;

  }

  // returns all items
  /**
   * Get all the items in the system as a List of transfer objects.
   * 
   * @return list of Item transfer objects.
   * @author empty string if successful or an error message for unsuccessful submission
   * @author Dimitri Christopoulos
   */
  public static List<TOItem> getItems() {

    List<TOItem> toitem_list = new ArrayList<>();  // Tranfer object item list
    List<Item> item_list = coolSupplies.getItems();  // List of current objects

    // Iterate over current objects and add them to the new list by creating TOItem objects
    for (Item item : item_list) {
      toitem_list.add(getItem(item.getName()));
    }
    return toitem_list;
  }
}
