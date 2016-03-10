package mvcV4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.print.PrintException;
import javax.swing.*;

public class PrinterInterface extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void createPrinterInterface(final printPDF printerObject) throws HeadlessException {
    	
    	int numOfPrinters = printerObject.getPrinters().size();
    	ArrayList<String> printerName = printerObject.getPrinters();
    	final JFrame frame = new JFrame("Choose a printer to print to:");
        final JPanel panel = new JPanel(new GridBagLayout());
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.setResizable(false);
		GridBagConstraints c = new GridBagConstraints();
		ArrayList<JButton> printerButtons= new ArrayList<JButton>();
		if(numOfPrinters == 0){
			JLabel noPrintersFound = new JLabel("No Printers Found");
			c.gridx = 0;
			c.gridy = -1;
			c.insets = new Insets(0,0,100,0);
			panel.add(noPrintersFound,c);
		}
		for(int x = 0; x < numOfPrinters; x++){
			printerButtons.add(new JButton(printerName.get(x)));
			c.gridx = 0;
			c.gridy = x;
			printerButtons.get(x).setPreferredSize(new Dimension(250, 30));
			final JButton buttonNamer = printerButtons.get(x);
			buttonNamer.setName(printerObject.getPrinters().get(x));
			printerButtons.get(x).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Printing to: " + buttonNamer.getName());
					printerObject.setPrinter(buttonNamer.getName());
					try {
						printerObject.printFile();
					} catch (PrintException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					frame.dispose();
				}

			});
			
			panel.add(printerButtons.get(x), c);
		}
		JButton cancel = new JButton("Cancel");
		c.gridx = 0;
		c.gridy = numOfPrinters;
		c.insets = new Insets(0,0,0,0);
		cancel.setPreferredSize(new Dimension(250, 30));
		
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}

		});
		
		panel.add(cancel, c);
        final JScrollPane scroll = new JScrollPane(panel);
        frame.setLayout(new BorderLayout());
        frame.add(scroll, BorderLayout.CENTER);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}