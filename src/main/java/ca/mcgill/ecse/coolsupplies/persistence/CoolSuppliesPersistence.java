/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.persistence;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;

// line 4 "../../../../../CoolSuppliesPersistence.ump"
public class CoolSuppliesPersistence
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CoolSuppliesPersistence Attributes
  private static String filename = "app.json";
  private static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.coolsupplies");


  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFilename(String aFilename)
  {
    boolean wasSet = false;
    filename = aFilename;
    wasSet = true;
    return wasSet;
  }

  public String getFilename()
  {
    return filename;
  }

  public void delete()
  {}

  //Load data from JSON file
  static CoolSupplies load() {
    var coolsupplies = (CoolSupplies) serializer.deserialize(filename);

    //if model cannot be loaded create new empty cool supplies
    if(coolsupplies == null) coolsupplies = new CoolSupplies();
    else coolsupplies.reinitialize();
    return coolsupplies;
  }

  //Save helper function
  static void save() {
    save(CoolSuppliesApplication.getCoolSupplies();)
  }

  // Save data to JSON file
  static void save(CoolSupplies coolsupplies) {
    serializer.serialize(coolsupplies, filename);
  }

  public String toString()
  {
    return super.toString() + "["+
            "filename" + ":" + getFilename()+ "]";
  }
}