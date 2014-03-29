package mvcV4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tabparts.LargeNumberException;
import version13.CannotReadFileException;
import version13.ConversionException;
import version13.EmptyFileException;
import version13.NoFileExistsException;
import version13.NoMusicException;
import version13.TextToPDF;

import com.itextpdf.text.DocumentException;

public class Controller
{
	
	private static Model model;
	private View view;

	public Controller(View view)
	{
		this.view = view;

		this.view.addConvertButtonListener(new ConvertButtonListener());
		this.view.addSelectButtonListener(new SelectButtonListener());
		this.view.addSaveButtonListener(new SaveButtonListener());
		this.view.addCorrectionButtonListener(new CorrectionButtonListener());
		this.view.spacingListener(new SpacingListener());
		this.view.titleListener(new TitleListener());
		this.view.subtitleListener(new SubtitleListener());
		this.view.titleFocusListener(new TitleFocusListener());
		this.view.subtitleFocusListener(new SubtitleFocusListener());
		this.view.elementSizeListener(new ElementSizeListener());
	}

	/*protected static void setWriter(TextToPDF test2)
	{
		test = test2;
	}

	protected static TextToPDF getWriter()
	{
		return test;
	}*/

	protected static void setModel(Model model2)
	{
		model = model2;
	}

	public static Model getModel()
	{
		return model;
	}
}

class TitleFocusListener implements FocusListener
{
	Model model = Controller.getModel();

	@Override
	public void focusGained(FocusEvent e)
	{
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		Model model = Controller.getModel();
		try {
			/* If the value didn't change from the current value then do nothing */
			if (!model.getTitle().equals(View.title.getText())) {
				model.setTitle(View.title.getText());
				model.converter.updateTitle((View.title.getText()));
				IMGCreator.createPreview(model);
				
				// CHECK IF CONVERSION WAS DONE PROPERLY.
				
				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image);
			}
			
			View.updateCorrection(model.getFilename());

		} catch (ConversionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class TitleListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = Controller.getModel();
		try {
			/* If the value didn't change from the current value then do nothing */
			if (!model.getTitle().equals(View.title.getText())) {
				model.setTitle(View.title.getText());
				model.converter.updateTitle((View.title.getText()));
				IMGCreator.createPreview(model);
				
				// CHECK IF CONVERSION WAS DONE PROPERLY.
				
				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image);
			}
			
			View.updateCorrection(model.getFilename());

		} catch (ConversionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class SubtitleFocusListener implements FocusListener
{
	Model model = Controller.getModel();

	@Override
	public void focusGained(FocusEvent e)
	{
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		Model model = Controller.getModel();
		try {
			/* If the value didn't change from the current value then do nothing */
			if (!model.getSubTitle().equals(View.subtitle.getText())) {
				model.setSubTitle(View.subtitle.getText());
				model.converter.updateSubtitle((View.subtitle.getText()));
				IMGCreator.createPreview(model);
				
				// CHECK IF CONVERSION WAS DONE PROPERLY.
				
				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image);
			}
			
			View.updateCorrection(model.getFilename());

		} catch (ConversionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class SubtitleListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = Controller.getModel();
		try {
			/* If the value didn't change from the current value then do nothing */
			if (!model.getSubTitle().equals(View.subtitle.getText())) {
				model.setSubTitle(View.subtitle.getText());
				model.converter.updateSubtitle((View.subtitle.getText()));
				IMGCreator.createPreview(model);
				
				// CHECK IF CONVERSION WAS DONE PROPERLY.
				
				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image);
			}
			
			View.updateCorrection(model.getFilename());

		} catch (ConversionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class SelectButtonListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = new Model();
		Controller.setModel(model);

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select Text File");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{

			String filenameWithExtension = chooser.getSelectedFile().toString();
			
			model.setFilenameWithExtention(filenameWithExtension);
			model.setFilename(Utils.removeFileExtension(filenameWithExtension));

			View.input.setText(filenameWithExtension);
			View.input.setEnabled(true);
			View.convertButton.setEnabled(true);
		}

		// Save the path and filename.
		// Update destination path.

		View.repaintPreview("");
	}
}

/**
 * Saves the file in the desired location with the desired name.
 * 
 * @author Skyler
 * 
 */
class SaveButtonListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = Controller.getModel();
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select PDF Destination");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);

		String input = model.getFilenameWithExtension();
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			String outputFilename = chooser.getSelectedFile().toString();
			TextToPDF test;
			try
			{
				test = new TextToPDF(outputFilename, input);
				test.WriteToPDF();

			} catch (NoFileExistsException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CannotReadFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (EmptyFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoMusicException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LargeNumberException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ConversionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}

/**
 * Update the stuffs from the GUI to the conversion code and convert, then
 * redisplay the image.
 * 
 * @author Skyler
 * 
 */
class ApplyButtonListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub

	}

}

class CorrectionButtonListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = Controller.getModel();
		String input = model.getFilenameWithExtension();
		Utils.openAndReadFile(input);
	}
}

class ConvertButtonListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = Controller.getModel();
		
		try {
			model.initializeConverter();
			model.runConverter();
			
			IMGCreator.createPreview(model);

			// CHECK IF CONVERSION WAS DONE PROPERLY.

			String image2 = IMGCreator.getLastConverted();
			View.repaintPreview(image2);
			// IF IT WAS, ENABLE ALL BUTTONS and populate the fields.

			// GET CONVERTED FIELD VALUES.
			// ENABLE FIELDS
			View.title.setEnabled(true);
			View.subtitle.setEnabled(true);
			View.staffSpacing.setEnabled(true);
			View.elementSize.setEnabled(true);
			
			// SET FIELD VALUES
			View.title.setText(model.getTitle());
			View.subtitle.setText(model.getSubTitle());
			View.staffSpacing.setValue((int) model.getSpacing());
			View.elementSize.setValue(model.getElementSize());
			View.saveButton.setEnabled(true);

			// ELSE display the error message and don't enable buttons.

		} catch (NoFileExistsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CannotReadFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (EmptyFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoMusicException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LargeNumberException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ConversionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}

class SpacingListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{
			// TODO Auto-generated method stub
			Model model = Controller.getModel();
			try {
				/* If the slider value didn't change from the current value then do nothing */
				if (model.getSpacing() != View.staffSpacing.getValue()) {
					model.setSpacing(View.staffSpacing.getValue());
					model.converter.updateSpacing((View.staffSpacing.getValue()));
					IMGCreator.createPreview(model);
					
					// CHECK IF CONVERSION WAS DONE PROPERLY.
					
					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}
				
				View.updateCorrection(model.getFilename());

			} catch (ConversionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}
}

class ElementSizeListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{
			// TODO Auto-generated method stub
			Model model = Controller.getModel();
			try {
				/* If the slider value didn't change from the current value then do nothing */
				if (model.getElementSize() != View.elementSize.getValue()) {
					model.setElementSize(View.elementSize.getValue());
					model.converter.updateElementSize((View.elementSize.getValue()));
					IMGCreator.createPreview(model);
					
					// CHECK IF CONVERSION WAS DONE PROPERLY.
					
					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}
				
				View.updateCorrection(model.getFilename());

			} catch (ConversionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}
}

/**
 * Displays more of the settings for the properties of the pdf.
 * 
 * @author Skyler
 * 
 */
class SettingsButtonListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub

	}

}