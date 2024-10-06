package ca.mcgill.ecse.coolsupplies.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.Item;

public class CoolSuppliesFeatureSet5Controller {

  public static String addBundleItem(int quantity, String level, String itemName,
      String bundleName) {
    //find bundle by name
    GradeBundle bundle = GradeBundle.getBundleName(bundleName);
    if (bundle == null) {
      return "Bundle not found";
    }

    //find item by name
    Item item = Item.getItemName(itemName);
    if (item == null){
      return "Item not found";
    }

    //Add item to bundle
    TOBundleItem bundleitem = new TOBundleItem(quantity, level, itemName, bundleName);
    bundle.addBundleItem(bundleitem);
    return "Bundle Item added successfully";
  }

  public static String updateBundleItem(String itemName, String bundleName, int newQuantity,
      String newLevel) {
    // Find the bundle by name
    GradeBundle bundle = GradeBundle.getBundleName(bundleName);
    if (bundle == null) {
        return "Bundle not found.";
    }

    // Find the item within the bundle
    TOBundleItem bundleItem = bundle.getBundleItemWithName(itemName);
    if (bundleItem == null) {
        return "Item not found in the bundle.";
    }

    // Update quantity and level
    bundleItem.setQuantity(newQuantity);
    bundleItem.setLevel(newLevel);
    return "Bundle item updated successfully.";
  }

  public static String deleteBundleItem(String itemName, String bundleName) {
    // Find the bundle by name
    GradeBundle bundle = GradeBundle.getBundleName(bundleName);
    if (bundle == null) {
        return "Bundle not found.";
    }

    // Find the item within the bundle and remove it
    TOBundleItem bundleItem = bundle.getBundleItemWithName(itemName);
    if (bundleItem == null) {
        return "Item not found in the bundle.";
    }

    bundle.removeBundleItem(bundleItem);
    return "Bundle item removed successfully.";
  }

  public static TOBundleItem getBundleItem(String itemName, String bundleName) {
    // Find the bundle
    GradeBundle bundle = GradeBundle.getBundleName(bundleName);
    if (bundle == null) {
        throw new IllegalArgumentException("Bundle not found.");
    }

    // Find and return the item
    TOBundleItem bundleItem = bundle.getBundleItemWithName(itemName);
    if (bundleItem == null) {
        throw new IllegalArgumentException("Item not found in the bundle.");
    }

    return bundleItem;
  }

  // returns all bundle items of a bundle
  public static List<TOBundleItem> getBundleItems(String bundleName) {
    // Find the bundle
        GradeBundle bundle = GradeBundle.getBundleName(bundleName);
        if (bundle == null) {
            throw new IllegalArgumentException("Bundle not found.");
        }

        return new ArrayList<>(bundle.getBundleItems());

  }

}
