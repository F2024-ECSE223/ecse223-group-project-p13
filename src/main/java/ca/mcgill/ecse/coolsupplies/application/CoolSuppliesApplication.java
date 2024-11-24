package ca.mcgill.ecse.coolsupplies.application;

import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;
import ca.mcgill.ecse.coolsupplies.view.CoolSuppliesFxmlView;
import javafx.application.Application;

public class CoolSuppliesApplication {

  private static CoolSupplies coolSupplies;

  public static void main(String[] args) {
    Application.launch(CoolSuppliesFxmlView.class, args);
  }

  public static CoolSupplies getCoolSupplies() {
    if (coolSupplies == null) {
      // these attributes are default, you should set them later with the setters
      coolSupplies = CoolSuppliesPersistence.load();
    }
    return coolSupplies;
  }

}
