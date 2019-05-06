package metalMVC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MetalController Class
 *
 * Manipulation of GUI through MetalView, execution of algorithms with and saving data into MetalModel
 */
class MetalController {

    // The MetalController needs to interact with both the MetalModel and MetalView
    private MetalView mView;
    private MetalModel mModel;


    /**
     * MetalController constructor
     *
     * @param model MetalModel for data container
     * @param view MetalView for updating view
     */
    MetalController(MetalModel model, MetalView view) {

        mModel = model;
        mView  = view;

        // Adding listeners to the view
        view.addNextBtnListener(new NextBtnListener());
        view.addPreviousBtnListener(new PreviousBtnListener());

    }


    // Next button class implementing the ActionListener for navigation
    class NextBtnListener implements ActionListener {
        // When event happens, tries to save data in MetalModel and then updates MetalView to the next card
        public void actionPerformed(ActionEvent e) {
            boolean saved;
            try {
                if (mView.getCardIdentifier().equalsIgnoreCase("Card 1")) {
                    saved = mModel.saveFirstCard(mView);
                    if (saved) {
                        mView.cards.next(mView.panelCards);
                    } else
                        mView.showError("INSERT DATA");
                }
                else if (mView.getCardIdentifier().equalsIgnoreCase("Card 2")) {
                    saved = mModel.saveSecondCard(mView);
                    if (saved) {
                        mView.cards.next(mView.panelCards);
                    } else
                        mView.showError("INSERT DATA");
                }
                else if (mView.getCardIdentifier().equalsIgnoreCase("Card 3")) {
                    saved = mModel.saveThirdCard(mView);
                    if (saved) {
                        System.out.println("Chosen project path: " + mModel.getProjectPath());

                        // Execute algorithms with data from MetalModel
                        boolean executed = mModel.execute(mView);
                        if (executed) {
                            JOptionPane.showMessageDialog(mView,"Algorithm executed!\n\n" +
                                    "Computing time took: " + Long.toString(mModel.getMetalSolution().getComputingTime()) + " ms\n" +
                                    "MetalSolution: " + mModel.getMetalSolution().getSolutionVariables().toString() +
                                    "\nfor variables: " + mModel.getNameOfVariables()
                            );
                        }
                    } else
                        mView.showError("INSERT DATA");
                }

            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    // Previous button class implementing the ActionListener for navigation
    class PreviousBtnListener implements ActionListener {
        // When event happens, updates MetalView to the previous card
        public void actionPerformed(ActionEvent e) {
            try {
                mView.cards.previous(mView.panelCards);
                mView.updateViewPanelNav();

            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

}
