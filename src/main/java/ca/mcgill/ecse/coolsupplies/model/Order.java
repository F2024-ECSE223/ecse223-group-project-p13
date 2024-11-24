/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import java.util.*;
import java.sql.Date;

// line 1 "../../../../../CoolSuppliesStateMachine.ump"
// line 30 "../../../../../CoolSuppliesPersistence.ump"
// line 42 "../../../../../CoolSupplies.ump"
public class Order
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Order> ordersByNumber = new HashMap<Integer, Order>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private int number;
  private Date date;
  private PurchaseLevel level;
  private String authorizationCode;
  private String penaltyAuthorizationCode;
  private boolean isPickedUp;

  //Order State Machines
  public enum Status { Started, Final, Paid, Penalized, Prepared, PickedUp }
  private Status status;

  //Order Associations
  private Parent parent;
  private Student student;
  private CoolSupplies coolSupplies;
  private List<OrderItem> orderItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(int aNumber, Date aDate, PurchaseLevel aLevel, Parent aParent, Student aStudent, CoolSupplies aCoolSupplies)
  {
    date = aDate;
    level = aLevel;
    authorizationCode = null;
    penaltyAuthorizationCode = null;
    isPickedUp = false;
    if (!setNumber(aNumber))
    {
      throw new RuntimeException("Cannot create due to duplicate number. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddParent = setParent(aParent);
    if (!didAddParent)
    {
      throw new RuntimeException("Unable to create order due to parent. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddStudent = setStudent(aStudent);
    if (!didAddStudent)
    {
      throw new RuntimeException("Unable to create order due to student. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCoolSupplies = setCoolSupplies(aCoolSupplies);
    if (!didAddCoolSupplies)
    {
      throw new RuntimeException("Unable to create order due to coolSupplies. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    orderItems = new ArrayList<OrderItem>();
    setStatus(Status.Started);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumber(int aNumber)
  {
    boolean wasSet = false;
    Integer anOldNumber = getNumber();
    if (anOldNumber != null && anOldNumber.equals(aNumber)) {
      return true;
    }
    if (hasWithNumber(aNumber)) {
      return wasSet;
    }
    number = aNumber;
    wasSet = true;
    if (anOldNumber != null) {
      ordersByNumber.remove(anOldNumber);
    }
    ordersByNumber.put(aNumber, this);
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setLevel(PurchaseLevel aLevel)
  {
    boolean wasSet = false;
    level = aLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setAuthorizationCode(String aAuthorizationCode)
  {
    boolean wasSet = false;
    authorizationCode = aAuthorizationCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setPenaltyAuthorizationCode(String aPenaltyAuthorizationCode)
  {
    boolean wasSet = false;
    penaltyAuthorizationCode = aPenaltyAuthorizationCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsPickedUp(boolean aIsPickedUp)
  {
    boolean wasSet = false;
    isPickedUp = aIsPickedUp;
    wasSet = true;
    return wasSet;
  }

  public int getNumber()
  {
    return number;
  }
  /* Code from template attribute_GetUnique */
  public static Order getWithNumber(int aNumber)
  {
    return ordersByNumber.get(aNumber);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithNumber(int aNumber)
  {
    return getWithNumber(aNumber) != null;
  }

  public Date getDate()
  {
    return date;
  }

  public PurchaseLevel getLevel()
  {
    return level;
  }

  public String getAuthorizationCode()
  {
    return authorizationCode;
  }

  public String getPenaltyAuthorizationCode()
  {
    return penaltyAuthorizationCode;
  }

  public boolean getIsPickedUp()
  {
    return isPickedUp;
  }

  public String getStatusFullName()
  {
    String answer = status.toString();
    return answer;
  }

  public Status getStatus()
  {
    return status;
  }

  public boolean updateOrder(PurchaseLevel level,Student student)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        if (isStudentOfParent(student))
        {
        // line 12 "../../../../../CoolSuppliesStateMachine.ump"
          doUpdateOrder(level, student);
          setStatus(Status.Started);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean addItem(InventoryItem item,int quantity)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        if (isNotItemOfOrderAndQuantityPositive(item,quantity))
        {
        // line 13 "../../../../../CoolSuppliesStateMachine.ump"
          doAddItem(item, quantity);
          setStatus(Status.Started);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean updateItem(OrderItem item,int quantity)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        if (isItemOfOrderAndQuantityPositive(item,quantity))
        {
        // line 16 "../../../../../CoolSuppliesStateMachine.ump"
          doUpdateItem(item, quantity);
          setStatus(Status.Started);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean deleteItem(OrderItem item)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        if (isItemOfOrder(item))
        {
        // line 19 "../../../../../CoolSuppliesStateMachine.ump"
          doDeleteItem(item);
          setStatus(Status.Started);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pay(String authorizationCode)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        if (isNotNullOrEmpty(authorizationCode)&&hasOrderItems())
        {
        // line 20 "../../../../../CoolSuppliesStateMachine.ump"
          doPay(authorizationCode);
          setStatus(Status.Paid);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startSchoolYear()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Paid:
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancel()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        setStatus(Status.Final);
        wasEventProcessed = true;
        break;
      case Paid:
        setStatus(Status.Final);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean payPenalty(String authorizationCode,String penaltyAuthorizationCode)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Penalized:
        if (isNotNullOrEmpty(authorizationCode,penaltyAuthorizationCode))
        {
        // line 30 "../../../../../CoolSuppliesStateMachine.ump"
          doPayPenalty(authorizationCode, penaltyAuthorizationCode);
          setStatus(Status.Prepared);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pickup()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Prepared:
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setStatus(Status aStatus)
  {
    status = aStatus;

    // entry actions and do activities
    switch(status)
    {
      case Final:
        delete();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Parent getParent()
  {
    return parent;
  }
  /* Code from template association_GetOne */
  public Student getStudent()
  {
    return student;
  }
  /* Code from template association_GetOne */
  public CoolSupplies getCoolSupplies()
  {
    return coolSupplies;
  }
  /* Code from template association_GetMany */
  public OrderItem getOrderItem(int index)
  {
    OrderItem aOrderItem = orderItems.get(index);
    return aOrderItem;
  }

  public List<OrderItem> getOrderItems()
  {
    List<OrderItem> newOrderItems = Collections.unmodifiableList(orderItems);
    return newOrderItems;
  }

  public int numberOfOrderItems()
  {
    int number = orderItems.size();
    return number;
  }

  public boolean hasOrderItems()
  {
    boolean has = orderItems.size() > 0;
    return has;
  }

  public int indexOfOrderItem(OrderItem aOrderItem)
  {
    int index = orderItems.indexOf(aOrderItem);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setParent(Parent aParent)
  {
    boolean wasSet = false;
    if (aParent == null)
    {
      return wasSet;
    }

    Parent existingParent = parent;
    parent = aParent;
    if (existingParent != null && !existingParent.equals(aParent))
    {
      existingParent.removeOrder(this);
    }
    parent.addOrder(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setStudent(Student aStudent)
  {
    boolean wasSet = false;
    if (aStudent == null)
    {
      return wasSet;
    }

    Student existingStudent = student;
    student = aStudent;
    if (existingStudent != null && !existingStudent.equals(aStudent))
    {
      existingStudent.removeOrder(this);
    }
    student.addOrder(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCoolSupplies(CoolSupplies aCoolSupplies)
  {
    boolean wasSet = false;
    if (aCoolSupplies == null)
    {
      return wasSet;
    }

    CoolSupplies existingCoolSupplies = coolSupplies;
    coolSupplies = aCoolSupplies;
    if (existingCoolSupplies != null && !existingCoolSupplies.equals(aCoolSupplies))
    {
      existingCoolSupplies.removeOrder(this);
    }
    coolSupplies.addOrder(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrderItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public OrderItem addOrderItem(int aQuantity, CoolSupplies aCoolSupplies, InventoryItem aItem)
  {
    return new OrderItem(aQuantity, aCoolSupplies, this, aItem);
  }

  public boolean addOrderItem(OrderItem aOrderItem)
  {
    boolean wasAdded = false;
    if (orderItems.contains(aOrderItem)) { return false; }
    Order existingOrder = aOrderItem.getOrder();
    boolean isNewOrder = existingOrder != null && !this.equals(existingOrder);
    if (isNewOrder)
    {
      aOrderItem.setOrder(this);
    }
    else
    {
      orderItems.add(aOrderItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrderItem(OrderItem aOrderItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrderItem, as it must always have a order
    if (!this.equals(aOrderItem.getOrder()))
    {
      orderItems.remove(aOrderItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderItemAt(OrderItem aOrderItem, int index)
  {  
    boolean wasAdded = false;
    if(addOrderItem(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderItemAt(OrderItem aOrderItem, int index)
  {
    boolean wasAdded = false;
    if(orderItems.contains(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderItemAt(aOrderItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ordersByNumber.remove(getNumber());
    Parent placeholderParent = parent;
    this.parent = null;
    if(placeholderParent != null)
    {
      placeholderParent.removeOrder(this);
    }
    Student placeholderStudent = student;
    this.student = null;
    if(placeholderStudent != null)
    {
      placeholderStudent.removeOrder(this);
    }
    CoolSupplies placeholderCoolSupplies = coolSupplies;
    this.coolSupplies = null;
    if(placeholderCoolSupplies != null)
    {
      placeholderCoolSupplies.removeOrder(this);
    }
    for(int i=orderItems.size(); i > 0; i--)
    {
      OrderItem aOrderItem = orderItems.get(i - 1);
      aOrderItem.delete();
    }
  }

  // line 42 "../../../../../CoolSuppliesStateMachine.ump"
   private boolean isStudentOfParent(Student student){
    // TODO needs to be completed
    return true;
  }

  // line 47 "../../../../../CoolSuppliesStateMachine.ump"
   private boolean isNotItemOfOrderAndQuantityPositive(InventoryItem item, int quantity){
    // TODO needs to be completed
    return true;
  }

  // line 52 "../../../../../CoolSuppliesStateMachine.ump"
   private boolean isItemOfOrderAndQuantityPositive(OrderItem item, int quantity){
    // TODO needs to be completed
    return true;
  }

  // line 57 "../../../../../CoolSuppliesStateMachine.ump"
   private boolean isItemOfOrder(OrderItem item){
    // TODO needs to be completed
    return true;
  }

  // line 62 "../../../../../CoolSuppliesStateMachine.ump"
   private boolean isNotNullOrEmpty(String code){
    // TODO needs to be completed
    return true;
  }

  // line 67 "../../../../../CoolSuppliesStateMachine.ump"
   private boolean isNotNullOrEmpty(String code1, String code2){
    // TODO needs to be completed
    return true;
  }

  // line 72 "../../../../../CoolSuppliesStateMachine.ump"
   private void doUpdateOrder(PurchaseLevel level, Student student){
    // TODO needs to be completed
  }

  // line 76 "../../../../../CoolSuppliesStateMachine.ump"
   private void doAddItem(InventoryItem item, int quantity){
    // TODO needs to be completed
  }

  // line 80 "../../../../../CoolSuppliesStateMachine.ump"
   private void doUpdateItem(OrderItem item, int quantity){
    // TODO needs to be completed
  }

  // line 84 "../../../../../CoolSuppliesStateMachine.ump"
   private void doDeleteItem(OrderItem item){
    // TODO needs to be completed
  }

  // line 88 "../../../../../CoolSuppliesStateMachine.ump"
   private void doPay(String authorizationCode){
    // TODO needs to be completed
  }

  // line 92 "../../../../../CoolSuppliesStateMachine.ump"
   private void doPayPenalty(String authorizationCode, String penaltyAuthorizationCode){
    // TODO needs to be completed
  }

  // line 32 "../../../../../CoolSuppliesPersistence.ump"
   public static  void reinitializeUniqueNumber(List<Order> orders){
    ordersByNumber.clear();
    for (var order : orders){
      ordersByNumber.put(order.getNumber(),order);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "number" + ":" + getNumber()+ "," +
            "authorizationCode" + ":" + getAuthorizationCode()+ "," +
            "penaltyAuthorizationCode" + ":" + getPenaltyAuthorizationCode()+ "," +
            "isPickedUp" + ":" + getIsPickedUp()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "level" + "=" + (getLevel() != null ? !getLevel().equals(this)  ? getLevel().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "parent = "+(getParent()!=null?Integer.toHexString(System.identityHashCode(getParent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "coolSupplies = "+(getCoolSupplies()!=null?Integer.toHexString(System.identityHashCode(getCoolSupplies())):"null");
  }
}
