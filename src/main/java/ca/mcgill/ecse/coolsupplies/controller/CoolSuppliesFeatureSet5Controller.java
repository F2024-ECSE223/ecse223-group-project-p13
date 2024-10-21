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
     * @param int quantity: XXX
     * @param String level: XXX
     * @param String itemName: XXX
     * @param String bundleName: XXX
     * This method BLAHHH
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
     * @param String itemName: XXX
     * @param String bundleName: XXX
     * @param int newQuantity: XXX
     * @param String newLevel: XXX
     * This method BLAHHH
     */


      
    }

  public static String deleteBundleItem(String itemName, String bundleName) {
    /**
     * @author Edouard Dupont
     * @param String itemName: XXX
     * @param String bundleName: XXX
     * This method BLAHHH
     */

     BundleItem.getBundleItem(itemName, bundleName).delete();
  }

  public static TOBundleItem getBundleItem(String itemName, String bundleName) {
    /**
     * @author Edouard Dupont
     * @param String itemName: XXX
     * @param String bundleName: XXX
     * This method BLAHHH
     */
    
    return new TOBundleItem(1,level, itemName, bundleName);
  }

  // returns all bundle items of a bundle
  public static List<TOBundleItem> getBundleItems(String bundleName) {
    /**
     * @author Edouard Dupont
     * @param String bundleName: XXX
     * This method BLAHHH
     */

    List<TOBundleItem> bundleItems = new ArrayList<TOBundleItem>();
    //for items in bundle name, add item to bundleitem FIND WHAT TO DO HERE
    return bundleItems;

  }

}
