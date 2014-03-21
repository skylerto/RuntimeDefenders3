package mvcV3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

	Model model;
	View view;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		
		this.view.addConvertListener(new ConvertButtonListener());
		this.view.
	}

}

class ConvertButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

class ConvertButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
