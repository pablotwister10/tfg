package metalMVC;

public class Metal {

    // Create model, view, and controller.  They are created once here and passed to the parts that need them so there
    // is only one copy of each.

    public static void main(String[] args) {

        MetalModel      model      = new MetalModel();
        MetalView       view       = new MetalView(model);
        MetalController controller = new MetalController(model, view);

        view.setVisible(true);

    }

}
