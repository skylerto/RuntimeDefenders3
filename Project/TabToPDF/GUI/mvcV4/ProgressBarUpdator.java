package mvcV4;

import javax.swing.JProgressBar;

public class ProgressBarUpdator implements Runnable {
	
	private JProgressBar jpbar = null;
	private Integer value = null;
	
	/**
	 * 
	 * @param jpbar
	 */
	public ProgressBarUpdator(JProgressBar jpbar) {
		this.jpbar = jpbar;
		jpbar.setMaximum(100);
		
	}
	/**
	 * 
	 * @param value
	 */
	
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * 
	 */
	@Override
	public void run() {
		do {
			if(value != null)
				jpbar.setValue((int) Math.round(Math.floor(value.intValue()*100/100)));
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				
		}while (value == null || value.intValue() < jpbar.getMaximum());
		
	}
	

}
