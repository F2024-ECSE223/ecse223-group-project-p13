package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;

// Model Import
import ca.mcgill.ecse.coolsupplies.model.*;


public class CoolSuppliesFeatureSet3Controller {

  private static CoolSupplies coolSupplies;
  private static List<Item> items;

  public static String addItem(String name, int price) {
    
    // Create new item
    Item new_item = new Item(name, price, coolSupplies);
    items.add(new_item);

  }

  public static String updateItem(String name, String newName, int newPrice) {
    
    List<Item> items = coolSupplies.getItems();

    for (Item item : items) {
      if (item.getName().equals(name)) {
        item.setName(newName);
        item.setPrice(newPrice);
      }
    }

  }

  public static String deleteItem(String name) {

    List<Item> items = coolSupplies.getItems();

    for (Item item : items) {
      if (item.getName().equals(name)) {
        item = null;
      }
    }
  }

  public static TOItem getItem(String name) {

    List<Item> items = coolSupplies.getItems();

    for (Item item : items) {
      if (item.getName().equals(name)) {
        TOItem to_item = new TOItem(item.getName(), item.getPrice());
        return to_item;
      }
    }
    throw new UnsupportedOperationException("Item not found.");
  }

  // returns all items
  public static List<TOItem> getItems() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

}
