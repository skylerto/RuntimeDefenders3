package MVC;

public class runGUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GUIView view = new GUIView();
		GUIModel model = new GUIModel();
		GUIController con = new GUIController(model, view);
		view.main(args);
		

	}

}
