package ca.mcgill.ecse.coolsupplies.persistence;

import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;

class CoolSuppliesPersistence {
  static String filename = "app.json";
  static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.coolsupplies");


  public static CoolSupplies load() {
    var coolsupplies = (CoolSupplies) serializer.deserialize(filename);

    if(coolsupplies == null) coolsupplies = new CoolSupplies();
    else coolsupplies.reinitialize();
    return coolsupplies;
  }

  public static void save() {
    save(CoolSuppliesApplication.getCoolSupplies());
  }

  public static void save(CoolSupplies coolsupplies) {
    serializer.serialize(coolsupplies, filename);
  }
}
