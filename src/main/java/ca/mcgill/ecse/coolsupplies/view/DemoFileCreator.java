package ca.mcgill.ecse.coolsupplies.view;

import java.sql.Date;
import java.util.Calendar;

import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.model.Order;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.SchoolAdmin;
import ca.mcgill.ecse.coolsupplies.model.Student;

public class DemoFileCreator {
	public static void createDemoData() {
		// root
		CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

		// admin
		new SchoolAdmin("admin@cool.ca", "Pwd#", coolSupplies);
		
		// grades
		Grade g5 = new Grade("5th", coolSupplies);
		Grade g6 = new Grade("6th", coolSupplies);
		Grade g7 = new Grade("7th", coolSupplies);
		
		// parents
		Parent pAnna = new Parent("mom@gmail.com", "1234", "Anna", 1234567, coolSupplies);
		Parent pBob = new Parent("dad@gmail.com", "4321", "Bob", 7654321, coolSupplies);
		
		// students
		Student sJeremy = new Student("Jeremy", coolSupplies, g5);
		sJeremy.setParent(pAnna);
		Student sJane = new Student("Jane", coolSupplies, g6);
		sJane.setParent(pAnna);
		new Student("Luke", coolSupplies, g7);
		Student sJanice = new Student("Janice", coolSupplies, g7);
		sJanice.setParent(pBob);

		// items
		Item notebook = new Item("notebook", 10, coolSupplies);
		Item pen = new Item("pen", 4, coolSupplies);
		Item calculator = new Item("calculator", 60, coolSupplies);
		Item ruler = new Item("ruler", 8, coolSupplies);
		
		// bundles
		GradeBundle B7 = new GradeBundle("B7", 10, coolSupplies, g7);
		B7.addBundleItem(2, PurchaseLevel.Mandatory, coolSupplies, notebook);
		B7.addBundleItem(3, PurchaseLevel.Optional, coolSupplies, pen);
		GradeBundle B6 = new GradeBundle("B6", 30, coolSupplies, g6);
		B6.addBundleItem(1, PurchaseLevel.Recommended, coolSupplies, notebook);
		B6.addBundleItem(1, PurchaseLevel.Optional, coolSupplies, pen);
		B6.addBundleItem(1, PurchaseLevel.Mandatory, coolSupplies, calculator);
		GradeBundle B5 = new GradeBundle("B5", 20, coolSupplies, g5);
		B5.addBundleItem(1, PurchaseLevel.Optional, coolSupplies, notebook);
		B5.addBundleItem(1, PurchaseLevel.Recommended, coolSupplies, ruler);
		
		// orders
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.NOVEMBER, 5, 0, 0, 0);
		Order o1 = new Order(1, new Date(calendar.getTimeInMillis()), PurchaseLevel.Mandatory, pAnna, sJane, coolSupplies);
		o1.addOrderItem(1, coolSupplies, B6);
		o1.addOrderItem(3, coolSupplies, pen);
		new Order(2, new Date(calendar.getTimeInMillis()), PurchaseLevel.Optional, pAnna, sJeremy, coolSupplies);
		new Order(3, new Date(calendar.getTimeInMillis()), PurchaseLevel.Recommended, pBob, sJanice, coolSupplies);
	}
}
