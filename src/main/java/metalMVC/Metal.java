package metalMVC;

import org.jfree.ui.RefineryUtilities;

/**
 * Metal Class
 *
 * Contains the main method and associates MetalModel, MetalView and MetalController objects
 */
public class Metal {

    // Create model, view, and controller.  They are created once here and passed to the parts that need them so there
    // is only one copy of each.
    public static void main(String[] args) {

        MetalModel      model      = new MetalModel();
        MetalView       view       = new MetalView();
        MetalController controller = new MetalController(model, view);

        // Setting view of GUI
        view.setVisible(true);
        // Centering the GUI
        RefineryUtilities.centerFrameOnScreen(view);

    }

}
