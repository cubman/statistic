package com.statistic.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.statistic.count.Activator;
import com.statistic.count.FileRestriction;
import com.statistic.count.SpinnerFieldEditor;
import com.statistic.fileformat.FileFormatManager;
import com.statistic.fileformat.IFileFormat;

public class FormatChooseDialog extends Dialog
{

	private FileRestriction		m_fileRestriction;
	private Table				m_table;
	private Spinner				m_spinner;
	private FileFormatManager	fileFormatManager	= FileFormatManager.getInstance();

	public FormatChooseDialog(Shell a_parent)
	{
		super(a_parent);
		// TODO Auto-generated constructor stub
	}

	public FileRestriction openDialog()
	{
		Shell shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

		// Shell dialog = new Shell(parent, SWT.DIALOG_TRIM |
		// SWT.APPLICATION_MODAL);
		shell.setSize(500, 190);

		shell.setText("Выберите параметры поиска");
		createContents(shell);
		// shell.pack();
		shell.open();

		Display display = getParent().getDisplay();
		Point point = display.getActiveShell().getLocation();
		Point mainWindow = getParent().getSize();
		Point paramWindow = shell.getSize();

		shell.setLocation(point.x + (mainWindow.x - point.x) / 2 - paramWindow.x / 2,
				point.y + (mainWindow.y - point.y) / 2 - paramWindow.y / 2);

		shell.addListener(SWT.Move, tener -> System.out.println(shell.getLocation()));
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}

		return m_fileRestriction;
	}

	private void createContents(final Shell shell)
	{
		shell.setLayout(new GridLayout(3, false));

		Label mainMessage = new Label(shell, SWT.NONE);
		mainMessage.setText("Выберите параметры поиска:");
		GridData data = new GridData();
		data.horizontalSpan = 3;
		mainMessage.setLayoutData(data);

		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		m_table = new Table(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		data.heightHint = 15;
		data.horizontalSpan = 2;
		m_table.setLayoutData(data);

		data = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		m_spinner = new Spinner(shell, SWT.BORDER | SWT.RIGHT);
		m_spinner.setIncrement(SpinnerFieldEditor.INCREMENT_VALUE);
		m_spinner.setMinimum(SpinnerFieldEditor.MIN_VALUE);
		m_spinner.setMaximum(SpinnerFieldEditor.MAX_VALUE);
		m_spinner.setToolTipText("Минимальное значение кодовых строк");
		m_spinner.setSelection(Activator.getDefault().getPreferenceStore().getInt("SPINNER"));
		m_spinner.setLayoutData(data);

		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		Label chosenFile = new Label(shell, SWT.FILL);
		chosenFile.setVisible(false);

		Font initialFont = chosenFile.getFont();
		FontData[] fontData = initialFont.getFontData();
		for(int i = 0; i < fontData.length; i++)
		{
			fontData[i].setHeight(8);
			fontData[i].setStyle(SWT.ITALIC);
		}

		Font newFont = new Font(shell.getDisplay(), fontData);
		chosenFile.setFont(newFont);
		chosenFile.setLayoutData(data);

		m_table.addListener(SWT.Selection, lis ->
			{
				int cnt = 0;

				for(int i = 0; i < m_table.getItemCount(); ++i)
					if(m_table.getItem(i).getChecked())
						++cnt;

				chosenFile.setVisible(cnt > 0);
				chosenFile.setText(String.format("* Вы выбрали: %d", cnt));
			});

		List<IFileFormat> list = fileFormatManager.getFileFormats();

		for(int i = 0; i < list.size(); i++)
		{
			TableItem item = new TableItem(m_table, SWT.NONE);
			item.setText(list.get(i).toString());
		}

		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(data);
		ok.addListener(SWT.Selection, event ->
			{
				m_fileRestriction = new FileRestriction(Integer.parseInt(m_spinner.getText()),
						getSelectedFileFormats());
				if(m_fileRestriction.getSelectedFormats().isEmpty())
					MessageDialog.openError(shell, "Выберите формат",
							"Не было выбрано ниодного формата");
				else
					shell.close();
			});

		Button cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Cancel");
		data = new GridData(GridData.FILL_HORIZONTAL);
		cancel.setLayoutData(data);
		cancel.addListener(SWT.Selection, event ->
			{
				m_fileRestriction = null;
				shell.close();
			});

		// Set the OK button as the default, so
		// user can type input and press Enter
		// to dismiss
		shell.setDefaultButton(ok);
	}

	private List<IFileFormat> getSelectedFileFormats()
	{
		List<IFileFormat> res = new ArrayList<>();

		for(int i = 0; i < m_table.getItemCount(); ++i)
			if(m_table.getItem(i).getChecked())
				res.add(fileFormatManager.getFileFormats().get(i));

		return res;
	}
}
