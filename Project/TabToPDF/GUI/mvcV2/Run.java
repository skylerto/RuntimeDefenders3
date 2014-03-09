package mvcV2;

import MVC.GUIController;
import MVC.GUIModel;
import MVC.GUIView;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		View view = new View();
		Model model = new Model();
		Controller con = new Controller(model, view);
		view.main(args);
		

	}

}
