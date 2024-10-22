package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.coolsupplies.model.BundleItem;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.Item;

public class CoolSuppliesFeatureSet5Controller {

  public static String addBundleItem(int quantity, String level, String itemName,
  String bundleName) {
    /**
    * @author Edouard Dupont
    * @param quantity: The quantity of the item to add.
    * @param level: The purchase level (Mandatory, Recommended, Optional).
    * @param itemName: The name of the item.
    * @param bundleName: The name of the grade bundle.
    * @return A message indicating success or the reason for failure.
    * 
    * This method adds a new BundleItem to the specified GradeBundle.
    */
      
    if (quantity <= 0) {
      return "Quantity must be greater than zero";
    }

    // Convert the level string to PurchaseLevel enum
    PurchaseLevel purchaseLevel;
    try {
      purchaseLevel = PurchaseLevel.valueOf(level);
    } catch (IllegalArgumentException e) {
      return "Invalid purchase level";
    }

    CoolSupplies cs = CoolSuppliesApplication.getCoolSupplies();

    // Find the GradeBundle with the specified name
    GradeBundle bundle = null;
    for (GradeBundle b : cs.getBundles()) {
      if (b.getName().equals(bundleName)) {
        bundle = b;
        break;
      }
    }
    
    if (bundle == null) {
      return "Bundle not found";
    }

    // Find the Item with the specified name
    Item item = null;
    for (Item i : cs.getItems()) {
      if (i.getName().equals(itemName)) {
        item = i;
          break;
      }
    }
    if (item == null) {
      return "Item not found";
    }

    // Check if the BundleItem already exists
    for (BundleItem bi : cs.getBundleItems()) {
      if (bi.getBundle().equals(bundle) && bi.getItem().equals(item)) {
        return "BundleItem already exists";
      }
    }

    // Create new BundleItem
    try {
      new BundleItem(quantity, purchaseLevel, cs, bundle, item);
        return "BundleItem successfully added";
    } catch (RuntimeException e) {
        return e.getMessage();
    }

  }

  public static String updateBundleItem(String itemName, String bundleName, int newQuantity,
      String newLevel) {
    /**
    * @author Edouard Dupont
    * @param itemName: The name of the item.
    * @param bundleName: The name of the grade bundle.
    * @param newQuantity: The new quantity to set.
    * @param newLevel: The new purchase level to set.
    * @return A message indicating success or the reason for failure.
    * 
    * This method updates the quantity and purchase level of an existing BundleItem.
    */
    
    if (newQuantity <= 0) {
      return "Quantity must be greater than zero";
    }

    // Convert the level string to PurchaseLevel enum
    PurchaseLevel purchaseLevel;
    try {
        purchaseLevel = PurchaseLevel.valueOf(newLevel);
    } catch (IllegalArgumentException e) {
        return "Invalid purchase level";
    }

    CoolSupplies cs = CoolSuppliesApplication.getCoolSupplies();

    // Find the GradeBundle
    GradeBundle bundle = null;
    for (GradeBundle b : cs.getBundles()) {
        if (b.getName().equals(bundleName)) {
            bundle = b;
            break;
        }
    }
    if (bundle == null) {
        return "Bundle not found";
    }

    // Find the Item
    Item item = null;
    for (Item i : cs.getItems()) {
        if (i.getName().equals(itemName)) {
            item = i;
            break;
        }
    }
    if (item == null) {
        return "Item not found";
    }

    // Find the BundleItem
    BundleItem bundleItem = null;
    for (BundleItem bi : cs.getBundleItems()) {
        if (bi.getBundle().equals(bundle) && bi.getItem().equals(item)) {
            bundleItem = bi;
            break;
        }
    }
    if (bundleItem == null) {
        return "BundleItem not found";
    }

    // Update the bundleItem
    bundleItem.setQuantity(newQuantity);
    bundleItem.setLevel(purchaseLevel);
    return "BundleItem successfully updated";

      
    }

  public static String deleteBundleItem(String itemName, String bundleName) {
    /**
    * @author Edouard Dupont
    * @param itemName: The name of the item.
    * @param bundleName: The name of the grade bundle.
    * @return A message indicating success or the reason for failure.
    * 
    * This method deletes an existing BundleItem from the system.
    */

    CoolSupplies cs = CoolSuppliesApplication.getCoolSupplies();

    // Find the GradeBundle
    GradeBundle bundle = null;
    for (GradeBundle b : cs.getBundles()) {
        if (b.getName().equals(bundleName)) {
            bundle = b;
            break;
        }
    }
    if (bundle == null) {
        return "Bundle not found";
    }

    // Find the Item
    Item item = null;
    for (Item i : cs.getItems()) {
      if (i.getName().equals(itemName)) {
        item = i;
        break;
      }
    }
    if (item == null) {
      return "Item not found";
    }

    // Find the BundleItem
    BundleItem bundleItem = null;
    for (BundleItem bi : cs.getBundleItems()) {
      if (bi.getBundle().equals(bundle) && bi.getItem().equals(item)) {
        bundleItem = bi;
        break;
      }
    }
    if (bundleItem == null) {
      return "BundleItem not found";
    }

    // Delete the BundleItem
    bundleItem.delete();
    return "BundleItem successfully deleted";
  }

  public static TOBundleItem getBundleItem(String itemName, String bundleName) {
    /**
    * @author Edouard Dupont
    * @param itemName   The name of the item.
    * @param bundleName The name of the grade bundle.
    * @return A TOBundleItem object representing the bundle item, or null if not found.
    * 
    * This method retrieves a specific BundleItem as a transfer object.
    */
    
    CoolSupplies cs = CoolSuppliesApplication.getCoolSupplies();

    // Find the GradeBundle
    GradeBundle bundle = null;
    for (GradeBundle b : cs.getBundles()) {
      if (b.getName().equals(bundleName)) {
        bundle = b;
        break;
      }
    }
    if (bundle == null) {
      return null;
    }

    // Find the Item
    Item item = null;
    for (Item i : cs.getItems()) {
      if (i.getName().equals(itemName)) {
        item = i;
        break;
      }
    }
    if (item == null) {
      return null;
    }

    // Find the BundleItem
    BundleItem bundleItem = null;
    for (BundleItem bi : cs.getBundleItems()) {
      if (bi.getBundle().equals(bundle) && bi.getItem().equals(item)) {
        bundleItem = bi;
        break;
      }
    }
    if (bundleItem == null) {
      return null;
    }

    // Create and return the TOBundleItem
    return new TOBundleItem(bundleItem.getQuantity(),bundleItem.getLevel().toString(),itemName,bundleName);
  }

  // returns all bundle items of a bundle
  public static List<TOBundleItem> getBundleItems(String bundleName) {
    /**
    * @author Edouard Dupont
    * @param bundleName The name of the grade bundle.
    * @return A list of TOBundleItem objects for the specified bundle.
    * 
    * This method retrieves all BundleItems associated with a specific GradeBundle.
    */

    CoolSupplies cs = CoolSuppliesApplication.getCoolSupplies();
    List<TOBundleItem> toBundleItems = new ArrayList<>();

    // Find the GradeBundle
    GradeBundle bundle = null;
    for (GradeBundle b : cs.getBundles()) {
      if (b.getName().equals(bundleName)) {
        bundle = b;
        break;
      }
    }
    if (bundle == null) {
      return toBundleItems; // Return empty list if bundle not found
    }

    // Iterate through BundleItems and create TOBundleItems
    for (BundleItem bi : bundle.getBundleItems()) {
      String itemName = bi.getItem().getName();
      TOBundleItem toBundleItem = new TOBundleItem(bi.getQuantity(), bi.getLevel().toString(), itemName, bundleName);
      toBundleItems.add(toBundleItem);
     }

     return toBundleItems;

  }

}
