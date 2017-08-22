package com.statistic.handlers;

import java.io.File;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.statistic.fileformat.IFileFormat;
import com.statistic.folders.DirecroryStructure;
import com.statistic.views.DescriptionView;
import com.statistic.views.ExplorerView;
import com.statistic.views.FormatChooseDialog;

public class OpenDialogHandler extends AbstractHandler implements IHandler
{
	public static final String ID = "com.statistic.count.openDialog";

	@Override
	public Object execute(ExecutionEvent a_event) throws ExecutionException
	{
		Shell shell = new Shell(Display.getDefault());
		FormatChooseDialog formatChooseDialog = new FormatChooseDialog(shell);
		formatChooseDialog.openDialog();
		try
		{
			PlatformUI.getWorkbench().showPerspective("com.statistic.count.perspective",
					PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		}
		catch(WorkbenchException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// диалоговое окно выбора директории
		DirectoryDialog dialog = new DirectoryDialog(shell);
		// путь по умолчанию
		dialog.setFilterPath("D:\\EclipseWorkDirectory");

		// выбранный пользователем каталог(путь к нему)
		String resultString = dialog.open();

		// дерево папок
		ExplorerView explorerView;
		try
		{
			explorerView = (ExplorerView) HandlerUtil
					.getActiveWorkbenchWindow(a_event).getActivePage()
					.showView(ExplorerView.ID);
		

		// выбранный формат
		List<IFileFormat> fileFormat = explorerView.getFormatViewer();
		new createExplorer().setData(resultString, a_event, fileFormat, shell);
		}
		catch(PartInitException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
