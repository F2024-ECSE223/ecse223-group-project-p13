package ca.mcgill.ecse.coolsupplies.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Imports

import ca.mcgill.ecse.coolsupplies.model.*;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;

// Feature Set 3

// TODO: JavaDoc

public class CoolSuppliesFeatureSet3Controller {

  private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  public static String addItem(String name, int price) {

    // Validate Price
    if (price < 0) {
      return "The price must be greater than or equal to 0.";
    }

    // Validate name - Name must not be empty
    if (name.equals("")) {
      return "The name must not be empty.";
    }

    // Validate name - name cannot already be in system
    Item item = (Item) Item.getWithName(name);
    if (item != null) {
      return "The name must be unique.";
    }

    // Add new item - input is valid
    Item newItem = new Item(name, price, coolSupplies);
    coolSupplies.addItem(newItem);

    return "";  // Success message
    
  }

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
    
    return "";
  }

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
    else return "";
  }

  public static TOItem getItem(String name) {

    Item item = (Item) Item.getWithName(name);
    TOItem to_item = new TOItem(item.getName(), item.getPrice());
    return to_item;

  }

  // returns all items
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
