package combine;

public class TabToPDF { // doesn't work, use controller for now

	public static void main(String args[]) {

		// TabToPDFController ttp = new TabToPDFController(); //should I make
		// non-static?

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TabToPDFView.createAndShowGUI();
			}
		});

		// TabToPDFController.run();

	}

}
