package com.statistic.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import com.statistic.count.Activator;
import com.statistic.count.FileRestriction;
import com.statistic.fileformat.FileFormatManager;

public class CountProjectStatisticHandler extends AbstractHandler implements IHandler
{
	@Override
	public Object execute(ExecutionEvent a_event) throws ExecutionException
	{
		Shell shell = new Shell(Display.getDefault());

		// активная перспектива
		IWorkbench workbench = PlatformUI.getWorkbench();
		ISelectionService service = workbench.getActiveWorkbenchWindow().getSelectionService();
		ISelection selection = service.getSelection();

		// выбор осуществлен в перспективе. не связанной с той, в которой была
		// выбрана диретория
		if(selection == null || ((IStructuredSelection) selection).getFirstElement() == null)
		{
			MessageDialog.openInformation(shell, "Объект не активен",
					"Объект выделения был утрачен");
			return null;
		}

		String workspaceDirectory = null;
		FileRestriction fileRestriction = null;

		// была выбрана папка/ проект
		if(selection instanceof IStructuredSelection)
		{

			Object obj = ((IStructuredSelection) selection).getFirstElement();

			Assert.isNotNull(obj);
			Assert.isTrue(obj instanceof IContainer);
			IContainer iContainer = (IContainer) obj;
			IProject iProject = iContainer.getProject();
			try
			{
				// получаем список форматов исходя из проекта
				fileRestriction = new FileRestriction(Activator.getDefault().getPreferenceStore()
		                .getInt("SPINNER"), FileFormatManager.getInstance()
						.getFileFormatByProjectNature(iProject.getDescription().getNatureIds()));
			}
			catch(CoreException e)
			{
				e.printStackTrace();
			}
			// получаем абсолютный путь до папки проекта
			workspaceDirectory = ((IContainer) obj).getLocation().toFile().getAbsolutePath();

		}
		try
		{
			workbench.showPerspective("com.statistic.count.perspective",
					workbench.getActiveWorkbenchWindow());
		}
		catch(WorkbenchException e1)
		{
			e1.printStackTrace();
		}

		CreateExplorer.setData(workspaceDirectory, a_event, fileRestriction, shell);

		return null;

	}

}
