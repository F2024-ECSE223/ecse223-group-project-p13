package ca.mcgill.ecse.coolsupplies.application;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;
import ca.mcgill.ecse.coolsupplies.view.CoolSuppliesFxmlView;
import ca.mcgill.ecse.coolsupplies.view.DemoFileCreator;
import javafx.application.Application;

public class CoolSuppliesApplication {

  private static CoolSupplies coolSupplies;
  public static Boolean isLight = true;

  public static void main(String[] args) {
    clearAppData();
    DemoFileCreator.createDemoData();
    Application.launch(CoolSuppliesFxmlView.class, args);
  }

  public static CoolSupplies getCoolSupplies() {
    if (coolSupplies == null) {
      // these attributes are default, you should set them later with the setters
      coolSupplies = CoolSuppliesPersistence.load();
    }
    return coolSupplies;
  }

  private static void clearAppData() {
    File data = new File("app.data");   

    if (data.exists()) {
      try {
        PrintWriter writer = new PrintWriter(data);
        writer.write("");
        writer.close();
        System.out.println("NOTICE: Cleared app data");
      } catch (IOException e) {
        System.out.println("ERROR: Unable to clear app data");
      }
    }
  }
}
