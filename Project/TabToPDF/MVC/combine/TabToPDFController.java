//need to: make constructor for controller (public controller) blah look at MVC example below, get it to accept all button actions, set model and controller

package combine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabToPDFController implements ActionListener {

	public static void run() {

		TabToPDFView.createAndShowGUI();

	}

	public static void main(String args[]) {

		// TabToPDFModel.createPreview();

		// TabToPDFView.createAndShowGUI();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	// @Override
	// public void actionPerformed(ActionEvent e)

	// TODO Auto-generated method stub

}

/*
 * 
 * 
 * public static void main(String[] args) { // this goes into the run java file
 * 
 * TabToPDF Controller c = new Controller(); 
 * TabToPDFModel m = Model.getInstance(); 
 * TabToPDFView v = new TabToPDFView(c); 
 * c.setModel(m); 
 * c.setView(v); 
 * v.setVisible(true); 
 * } 
 * }
 * 
 * import java.awt.event.ActionEvent; import java.awt.event.ActionListener;
 * 
 * 
 * public class Controller implements ActionListener {
 * 
 * private TabToPDFModel model; private TabToPDFView view;
 * 
 * public Controller() { this.model = null; this.view = null; }
 * 
 * 
 * public void setModel(TabToPDFModel model) { this.model = model; }
 * 
 * 
 * public void setView(TabToPDFView view) { this.view = view; }
 * 
 * @Override 
 * public void actionPerformed(ActionEvent event) { 
 * String command = event.getActionCommand(); 
 * if (command.equals(TabToPDFView.SELECT_FILE)) {
 * this.model.doshit; 
 * this.view.doshit; 
 * } 
 * else if
 * (command.equals(TabToPDFView.CONVERT)) { 
 * this.model.doshit; 
 * this.view.doshit;
 * } }
 * 
 * 
 * }
 */

