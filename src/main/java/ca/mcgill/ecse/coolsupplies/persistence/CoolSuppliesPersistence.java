/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.persistence;

// line 4 "../../../../../CoolSuppliesPersistence.ump"
public class CoolSuppliesPersistence
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CoolSuppliesPersistence Attributes
  private static String filename;
  private static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.coolsupplies");

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CoolSuppliesPersistence()
  {
    filename = "data.json";
  }

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

  /**
   * Path to the data file
   */
  public String getFilename()
  {
    return filename;
  }

  public void delete()
  {}


  /**
   * Load data from JSON file
   */
  // line 14 "../../../../../CoolSuppliesPersistence.ump"
   static  CoolSuppliesPersistence load(){
    // Implementation will be provided in Java
    return null;
  }


  /**
   * Save data to JSON file
   */
  // line 20 "../../../../../CoolSuppliesPersistence.ump"
  public void save(){
    // Implementation will be provided in Java
  }


  public String toString()
  {
    return super.toString() + "["+
            "filename" + ":" + getFilename()+ "]";
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 7 "../../../../../CoolSuppliesPersistence.ump"
  static CoolSuppliesPersistence instance ;

  
}