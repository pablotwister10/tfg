package metalMVC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// MetalController Class for manipulation of GUI and execution of algorithms
class MetalController {

    // The MetalController needs to interact with both the MetalModel and MetalView
    private MetalView mView;
    private MetalModel mModel;


    MetalController(MetalModel model, MetalView view) {

        mModel = model;
        mView  = view;

        // Adding listeners to the view
        view.addNextBtnListener(new NextBtnListener());
        view.addPreviousBtnListener(new PreviousBtnListener());

    }


    class NextBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TODO: BUG WHEN CHANGING ALGORITHMS!!!
            boolean saved;
            try {
                if (mView.getCardIdentifier().equalsIgnoreCase("Card 1")) {
                    saved = mModel.saveFirstCard(mView);
                    if (saved) {
                        mView.cards.next(mView.panelCards);
                    } else
                        mView.showError("INSERT DATA");
                } else if (mView.getCardIdentifier().equalsIgnoreCase("Card 2")) {
                    saved = mModel.saveSecondCard(mView);
                    if (saved) {
                        mView.cards.next(mView.panelCards);
                    } else
                        mView.showError("INSERT DATA");
                } else if (mView.getCardIdentifier().equalsIgnoreCase("Card 3")) {
                    saved = mModel.saveThirdCard(mView);
                    if (saved) {
                        boolean executed = mModel.execute(mView);
                        if (executed) {
                            JOptionPane.showMessageDialog(mView,"Algorithm executed!\n\n" +
                                            "Computing time took: " + Long.toString(mModel.getMetalSolution().getComputingTime()) + " ms\n" +
                                            "MetalSolution: " + mModel.getMetalSolution().getSolutionAlgorithm().toString()
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

    class PreviousBtnListener implements ActionListener {
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