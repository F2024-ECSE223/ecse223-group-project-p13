/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.controller;

// line 57 "../../../../../CoolSuppliesTransferObjects.ump"
public class TOOrderItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOOrderItem Attributes
  private String quantity;
  private String name;
  private String gradeBundle;
  private String price;
  private String discount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOOrderItem(String aQuantity, String aName, String aGradeBundle, String aPrice, String aDiscount)
  {
    quantity = aQuantity;
    name = aName;
    gradeBundle = aGradeBundle;
    price = aPrice;
    discount = aDiscount;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getQuantity()
  {
    return quantity;
  }

  public String getName()
  {
    return name;
  }

  public String getGradeBundle()
  {
    return gradeBundle;
  }

  public String getPrice()
  {
    return price;
  }

  public String getDiscount()
  {
    return discount;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "," +
            "name" + ":" + getName()+ "," +
            "gradeBundle" + ":" + getGradeBundle()+ "," +
            "price" + ":" + getPrice()+ "," +
            "discount" + ":" + getDiscount()+ "]";
  }
}