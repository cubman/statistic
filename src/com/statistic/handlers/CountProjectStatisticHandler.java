package com.statistic.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import com.statistic.count.FileRestriction;
import com.statistic.fileformat.FileFormatManager;


public class CountProjectStatisticHandler extends AbstractHandler implements IHandler
{
	@Override
	public Object execute(ExecutionEvent a_event) throws ExecutionException
	{
		Shell shell = new Shell(Display.getDefault());
 
		IWorkbench  workbench = PlatformUI.getWorkbench();

		ISelectionService service = workbench.getActiveWorkbenchWindow().getSelectionService();
		ISelection selection = service.getSelection();
		String workspaceDirectory = null;
		FileRestriction fileRestriction = null;
		
		if (selection instanceof IStructuredSelection) {
	        
	        Object obj = ((IStructuredSelection) selection).getFirstElement();
	        
	        Assert.isNotNull(obj);
	        Assert.isTrue(obj instanceof IContainer);
	        IContainer iContainer = (IContainer)obj;
	        IProject iProject =  iContainer.getProject();
	        //iProject.getWorkspace().getNatureDescriptors()[0].
	        
	        
	        try
			{
	        	fileRestriction = new FileRestriction(0, FileFormatManager.getInstance().getFileFormatByProjectNature(iProject.getDescription().getNatureIds()));
			}
			catch(CoreException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			workspaceDirectory = ((IContainer) obj).getLocation().toFile().getAbsolutePath();

	    }
		try
		{
			workbench.showPerspective("com.statistic.count.perspective", workbench.getActiveWorkbenchWindow());
		}
		catch(WorkbenchException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		CreateExplorer.setData(workspaceDirectory, a_event, fileRestriction, shell);
		

		return null;

	}

}
