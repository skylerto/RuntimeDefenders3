package mvcV4;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tabparts.AutofixLog;
import tabparts.LargeNumberException;
import version13.CannotReadFileException;
import version13.ConversionException;
import version13.EmptyFileException;
import version13.NoFileExistsException;
import version13.NoMusicException;
import version13.TextToPDF;

import com.alee.utils.FileUtils;
import com.itextpdf.text.*;

public class Controller
{

	private static Model model;
	private View view;
	private static boolean active = true;

	public Controller(View view)
	{
		this.view = view;

		this.view.addSelectButtonListener(new SelectButtonListener());
		this.view.addSaveButtonListener(new SaveButtonListener());
		this.view.addCorrectionButtonListener(new CorrectionButtonListener());
		this.view.spacingListener(new SpacingListener());
		this.view.titleListener(new TitleListener());
		this.view.subtitleListener(new SubtitleListener());
		this.view.titleFocusListener(new TitleFocusListener());
		this.view.subtitleFocusListener(new SubtitleFocusListener());
		this.view.staffSizeListener(new StaffSizeListener());
		this.view.measureSpaceListener(new MeasureSpaceListener());
		this.view.titleFontSizeListener(new TitleFontSizeListener());
		this.view.subtitleFontSizeListener(new SubtitleFontSizeListener());
		this.view.leftMarginListener(new LeftMarginListener());
		this.view.rightMarginListener(new RightMarginListener());
		this.view.pageSizeListener(new PageSizeListener());
		this.view.inputPathListener(new InputPathListener());
		this.view.inputPathFocusListener(new InputPathFocusListener());
	}

	/**
	 * Displays the passed error.
	 * 
	 * @param error
	 */
	public static void displayError(String error)
	{
		View.showError(error);
	}

	/**
	 * Deletes all temporary files when the program closes
	 */
	protected static void deleteFilesOnExit()
	{
		File temp_image = new File(model.getPreviewImage());
		temp_image.deleteOnExit();
		File temp_pdf = new File(TextToPDF.DEFAULT_OUTPUTPATH);
		temp_pdf.deleteOnExit();
		File temp_log = new File(AutofixLog.LOG_PATH);
		temp_log.deleteOnExit();
	}

	/*
	 * protected static void setWriter(TextToPDF test2) { test = test2; }
	 * 
	 * protected static TextToPDF getWriter() { return test; }
	 */

	protected static void setModel(Model model2)
	{
		model = model2;
	}

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	public static Model getModel()
	{
		return model;
	}
}

/**
 * Updates the preview when the title is edited (when focus is lost).
 */
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
		try
		{
			/* If the value didn't change from the current value then do nothing */
			if (!model.getTitle().equals(View.title.getText()))
			{
				model.setTitle(View.title.getText());
				model.converter.updateTitle((View.title.getText()));
				IMGCreator.createPreview(model);

				// CHECK IF CONVERSION WAS DONE PROPERLY.
				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image);
			}

		} catch (ConversionException e1)
		{
			Controller.displayError(e1.getMessage());
		}
	}
}

/**
 * Updates the preview when the title is edited (when "enter" is pressed).
 */
class TitleListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = Controller.getModel();
		try
		{
			/* If the value didn't change from the current value then do nothing */
			if (!model.getTitle().equals(View.title.getText()))
			{
				model.setTitle(View.title.getText());
				model.converter.updateTitle((View.title.getText()));
				IMGCreator.createPreview(model);

				// CHECK IF CONVERSION WAS DONE PROPERLY.

				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image);
			}

		} catch (ConversionException e1)
		{
			Controller.displayError(e1.getMessage());
		}
	}
}

/**
 * Updates the preview when the subtitle is edited (when focus is lost).
 */
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
		try
		{
			/* If the value didn't change from the current value then do nothing */
			if (!model.getSubTitle().equals(View.subtitle.getText()))
			{
				model.setSubTitle(View.subtitle.getText());
				model.converter.updateSubtitle((View.subtitle.getText()));
				IMGCreator.createPreview(model);

				// CHECK IF CONVERSION WAS DONE PROPERLY.

				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image);
			}

		} catch (ConversionException e1)
		{
			Controller.displayError(e1.getMessage());
		}
	}
}

/**
 * Updates the preview when the subtitle is edited (when "enter" is pressed).
 */
class SubtitleListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = Controller.getModel();
		try
		{
			/* If the value didn't change from the current value then do nothing */
			if (!model.getSubTitle().equals(View.subtitle.getText()))
			{
				model.setSubTitle(View.subtitle.getText());
				model.converter.updateSubtitle((View.subtitle.getText()));
				IMGCreator.createPreview(model);

				// CHECK IF CONVERSION WAS DONE PROPERLY.

				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image);
			}

		} catch (ConversionException e1)
		{
			Controller.displayError(e1.getMessage());
		}
	}
}

/**
 * Shows and hides the "Enter to apply prompt."
 */
class InputPathFocusListener implements FocusListener
{
	Model model = Controller.getModel();

	@Override
	public void focusGained(FocusEvent e)
	{
		View.inputPrompt.setVisible(true);
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		Model model = Controller.getModel();

		if (!model.getFilenameWithExtension().equals(View.input.getText()))
		{
			View.input.setText(model.getFilenameWithExtension());
		}
		View.inputPrompt.setVisible(false);
	}
}

/**
 * Updates the view when a different path is entered.
 */
class InputPathListener implements ActionListener
{
	Model model = new Model();
	String image;
	Rectangle pagesize;

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Controller.setModel(model);

		if (!model.getFilenameWithExtension().equals(View.input.getText()))
		{
			model.setFilenameWithExtention(View.input.getText());
			model.setFilename(Utils.removeFileExtension(View.input.getText()));

			View.setComponentsEnabled(false);
			View.resetView();
			View.repaintPreview("");

			convert();
			View.showLoading();
			View.previewPane.setCursor(Cursor
					.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}
	}

	private void convert()
	{
		SwingWorker<Void, Integer> ConvertTask = new SwingWorker<Void, Integer>()
		{

			@Override
			protected Void doInBackground() throws NoFileExistsException,
					CannotReadFileException, EmptyFileException,
					NoMusicException, LargeNumberException, ConversionException
			{
				publish(1);
				model.initializeConverter();
				model.runConverter();
				IMGCreator.createPreview(model);
				Controller.deleteFilesOnExit();
				image = IMGCreator.getLastConverted();
				pagesize = model.getPageSize();
				return null;
			}

			@Override
			protected void process(List<Integer> chunks)
			{
				if (chunks.get(0) == 1)
				{
					View.progressBar.setIndeterminate(true);
				}
			}

			@Override
			protected void done()
			{
				try
				{
					get();
					View.progressBar.setIndeterminate(false);
					View.previewPane.setCursor(Cursor
							.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					View.repaintPreview(image, pagesize);
					// IF CONVERTED PROPERLY, ENABLE ALL BUTTONS and populate
					// the fields.

					// GET CONVERTED FIELD VALUES.
					// ENABLE FIELDS
					View.setComponentsEnabled(true);

					// SET FIELD VALUES
					View.title.setText(model.getTitle());
					View.subtitle.setText(model.getSubTitle());
					View.staffSpacing.setValue((int) model.getSpacing());
					View.staffSize.setValue(model.getStaffSize());
					View.measureSpace.setValue((int) model.getMeasureSpace());
					View.titleFontSize.setValue((int) model.getTitleFontSize());
					View.subtitleFontSize.setValue((int) model
							.getSubTitleFontSize());
					View.leftMarginSpace.setValue((int) model.getLeftMargin());
					View.rightMarginSpace.setValue((int) model.getLeftMargin());
					View.pageList.setSelectedIndex(0);
					View.propertiesPane.getVerticalScrollBar().setValue(0);

					View.updateCorrection(model.getFilename());

				} catch (InterruptedException | ExecutionException e1)
				{
					// ELSE display the error message and don't enable buttons.
					View.progressBar.setIndeterminate(false);
					View.previewPane.setCursor(Cursor
							.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					View.cl.show(View.statusPanel, "correctPanel");
					Controller.displayError(e1.getMessage());
				}
			}
		};
		ConvertTask.execute();
	}
}

/**
 * Updates the view when a different path is chosen.
 */
class SelectButtonListener implements ActionListener
{
	private File inputfile = new File(".");
	Model model = new Model();
	String image;
	Rectangle pagesize;

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Controller.setModel(model);

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(inputfile);
		FileTypeFilter text_filter = new FileTypeFilter("Text File *.txt",
				new String[]
				{ ".txt" });
		chooser.addChoosableFileFilter(text_filter);
		chooser.setFileFilter(text_filter);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION)
		{
			inputfile = chooser.getCurrentDirectory();
			String filenameWithExtension = chooser.getSelectedFile().toString();

			model.setFilenameWithExtention(filenameWithExtension);
			model.setFilename(Utils.removeFileExtension(filenameWithExtension));

			View.input.setText(filenameWithExtension);
			View.setComponentsEnabled(false);
			View.resetView();
			View.repaintPreview("");

			convert();
			View.showLoading();
			View.previewPane.setCursor(Cursor
					.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}
	}

	private void convert()
	{
		SwingWorker<Void, Integer> ConvertTask = new SwingWorker<Void, Integer>()
		{

			@Override
			protected Void doInBackground() throws NoFileExistsException,
					CannotReadFileException, EmptyFileException,
					NoMusicException, LargeNumberException, ConversionException
			{
				publish(1);
				model.initializeConverter();
				model.runConverter();
				IMGCreator.createPreview(model);
				Controller.deleteFilesOnExit();
				image = IMGCreator.getLastConverted();
				pagesize = model.getPageSize();
				return null;
			}

			@Override
			protected void process(List<Integer> chunks)
			{
				if (chunks.get(0) == 1)
				{
					View.progressBar.setIndeterminate(true);
				}
			}

			@Override
			protected void done()
			{
				try
				{
					get();
					View.progressBar.setIndeterminate(false);
					View.previewPane.setCursor(Cursor
							.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					View.repaintPreview(image, pagesize);
					// IF CONVERTED PROPERLY, ENABLE ALL BUTTONS and populate
					// the fields.

					// GET CONVERTED FIELD VALUES.
					// ENABLE FIELDS
					View.setComponentsEnabled(true);

					// SET FIELD VALUES
					View.title.setText(model.getTitle());
					View.subtitle.setText(model.getSubTitle());
					View.staffSpacing.setValue((int) model.getSpacing());
					View.staffSize.setValue(model.getStaffSize());
					View.measureSpace.setValue((int) model.getMeasureSpace());
					View.titleFontSize.setValue((int) model.getTitleFontSize());
					View.subtitleFontSize.setValue((int) model
							.getSubTitleFontSize());
					View.leftMarginSpace.setValue((int) model.getLeftMargin());
					View.rightMarginSpace.setValue((int) model.getLeftMargin());
					View.pageList.setSelectedIndex(0);
					View.propertiesPane.getVerticalScrollBar().setValue(0);

					View.updateCorrection(model.getFilename());

				} catch (InterruptedException | ExecutionException e1)
				{
					// ELSE display the error message and don't enable buttons.
					View.progressBar.setIndeterminate(false);
					View.previewPane.setCursor(Cursor
							.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					View.cl.show(View.statusPanel, "correctPanel");
					Controller.displayError(e1.getMessage());
				}
			}
		};
		ConvertTask.execute();
	}
}

/**
 * Saves the file in the desired location with the desired name.
 */
class SaveButtonListener implements ActionListener
{
	private File outputfile = new File(".");

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = Controller.getModel();
		JFileChooser chooser = new JFileChooser();
		FileTypeFilter pdf_filter = new FileTypeFilter(
				"Portable Document Format *.pdf", new String[]
				{ ".pdf" });
		chooser.addChoosableFileFilter(pdf_filter);
		chooser.setFileFilter(pdf_filter);
		chooser.setCurrentDirectory(outputfile);
		chooser.setDialogTitle("Select PDF Destination");
		// chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);

		String input = model.getFilenameWithExtension();
		if (chooser.showSaveDialog(chooser) == JFileChooser.APPROVE_OPTION)
		{
			outputfile = chooser.getCurrentDirectory();
			String outputFilename = chooser.getSelectedFile().toString();
			if (!outputFilename.matches("(.*)(\\.pdf){1}"))
				outputFilename += ".pdf";
			FileUtils.copyFile("./outputfiles/musicPDF.pdf", outputFilename);
			/*
			 * TextToPDF test; try { test = new TextToPDF(outputFilename,
			 * input); test.WriteToPDF();
			 * 
			 * } catch (NoFileExistsException | CannotReadFileException |
			 * EmptyFileException | NoMusicException | LargeNumberException |
			 * ConversionException e1) {
			 * Controller.displayError(e1.getMessage()); }
			 */
		}
	}
}

/**
 * Opens the correction log JDialog.
 */
class CorrectionButtonListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		View.correctionLogDialog.setLocationRelativeTo(View.previewPane
				.getVerticalScrollBar());
		View.correctionLogScroller.getVerticalScrollBar().setValue(0);
		View.correctionLogScroller.getHorizontalScrollBar().setValue(0);
		View.correctionLogDialog.setVisible(true);
	}
}

/**
 * Updates the preview when the staff spacing slider is moved.
 */
class SpacingListener implements ChangeListener
{
	public void stateChanged(ChangeEvent e)
	{
		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				if (model.getSpacing() < View.staffSpacing.getMinimum())
					model.setSpacing(View.staffSpacing.getMinimum());
				else if (model.getSpacing() > View.staffSpacing.getMaximum())
					model.setSpacing(View.staffSpacing.getMaximum());
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getSpacing() != View.staffSpacing.getValue())
				{
					model.setSpacing(View.staffSpacing.getValue());
					model.converter
							.updateSpacing((View.staffSpacing.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.
					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}
		}
	}
}

/**
 * Updates the preview when the staff size slider is moved.
 */
class StaffSizeListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getStaffSize() != View.staffSize.getValue())
				{
					model.setStaffSize(View.staffSize.getValue());
					model.converter.updateElementSize((View.staffSize
							.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.

					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}
		}
	}
}

/**
 * Updates the preview when the measure space slider is moved.
 */
class MeasureSpaceListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getMeasureSpace() != View.measureSpace.getValue())
				{
					model.setMeasureSpace(View.measureSpace.getValue());
					model.converter.updateMeasureSpace((View.measureSpace
							.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.

					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}
		}
	}
}

/**
 * Updates the preview when the title font size slider is moved.
 */
class TitleFontSizeListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getTitleFontSize() != View.titleFontSize.getValue())
				{
					model.setTitleFontSize(View.titleFontSize.getValue());
					model.converter.updateTitleSize((View.titleFontSize
							.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.

					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}
		}
	}
}

/**
 * Updates the preview when the subtitle font size slider is moved.
 */
class SubtitleFontSizeListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getSubTitleFontSize() != View.subtitleFontSize
						.getValue())
				{
					model.setSubTitleFontSize(View.subtitleFontSize.getValue());
					model.converter.updateSubtitleSize((View.subtitleFontSize
							.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.

					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}
		}
	}
}

/**
 * Updates the preview when the left margin space slider is moved.
 */
class LeftMarginListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getLeftMargin() != View.leftMarginSpace.getValue())
				{
					model.setLeftMargin(View.leftMarginSpace.getValue());
					model.converter.updateLeftMargin((View.leftMarginSpace
							.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.

					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}
		}
	}
}

/**
 * Updates the preview when the right margin space slider is moved.
 */
class RightMarginListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getRightMargin() != View.rightMarginSpace.getValue())
				{
					model.setRightMargin(View.rightMarginSpace.getValue());
					model.converter.updateRightMargin((View.rightMarginSpace
							.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.

					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}
		}
	}
}

/**
 * Updates the preview when the page size is changed.
 */
class PageSizeListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = Controller.getModel();
		try
		{
			/* Get the corresponding rectangle from the GUI page size */
			Rectangle pagesize = model
					.convertPageSizeToRectangle((String) View.pageList
							.getSelectedItem());
			String ps = model.convertPageSizeToString(model.getPageSize());

			/* If the value didn't change from the current value then do nothing */
			if (!ps.equals((String) View.pageList.getSelectedItem()))
			{
				model.setPageSize(pagesize);
				model.converter.updatePageSize(pagesize);
				IMGCreator.createPreview(model);

				// CHECK IF CONVERSION WAS DONE PROPERLY.

				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image, pagesize);
			}

		} catch (ConversionException e1)
		{
			Controller.displayError(e1.getMessage());
		}
	}
}
