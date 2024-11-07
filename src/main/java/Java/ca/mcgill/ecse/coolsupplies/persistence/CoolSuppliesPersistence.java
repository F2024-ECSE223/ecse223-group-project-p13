/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.persistence;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;

// line 4 "../../../../../../CoolSuppliesPersistence.ump"
public class CoolSuppliesPersistence
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CoolSuppliesPersistence Attributes
  private String filename;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CoolSuppliesPersistence()
  {
    filename = "app.json";
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

  public String getFilename()
  {
    return filename;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "filename" + ":" + getFilename()+ "]";
  }
}