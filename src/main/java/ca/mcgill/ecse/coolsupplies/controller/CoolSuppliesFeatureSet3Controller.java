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
  private static List<Item> items;

  public static String addItem(String name, int price) {
    
    
    try {

      // Create new item
      Item new_item = new Item(name, price, coolSupplies);
      coolSupplies.addItem(new_item);
    } 

    catch (Exception e) {  // TODO: Fix return strings

      //Check if new_item already exists
      if (e.getMessage().equals("change this message")) {
        return "Item already exists";
      }

      // Other errors (invalid arguments)
      else {
        return "Invalid argument(s)";
      }
    }

    return "";  // Success message
    
  }

  public static String updateItem(String name, String newName, int newPrice) {
    
    // TODO: Handle errors
    // Get Item then set new name and new price
    Item item = (Item) Item.getWithName(name);
    item.setName(newName);
    item.setPrice(newPrice);
    return "";
  }

  public static String deleteItem(String name) {

    // Check if list of items is empty
    if (coolSupplies.hasItems()) {

      // If list is not empty, get item and delete it
      Item item = (Item) Item.getWithName(name);
      item.delete();

      return "";
    }
    else return "Item list is empty";
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
