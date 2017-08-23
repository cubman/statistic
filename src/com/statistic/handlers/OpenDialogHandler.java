package com.statistic.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.statistic.count.FileRestriction;
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
		FileRestriction fileRestriction = formatChooseDialog.openDialog();
		
		if (fileRestriction == null)
			return null;
		
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
		
			explorerView.setRestriction(fileRestriction);
		// выбранный формат
		//List<IFileFormat> fileFormat = explorerView.getFormatViewer();
		CreateExplorer.setData(resultString, a_event, fileRestriction, shell);
		}
		catch(PartInitException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
