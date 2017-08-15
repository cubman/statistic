package com.statistic.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.statistic.folders.DirecroryStructure;
import com.statistic.folders.FileBrowser;
import com.statistic.folders.FileFormat;
import com.statistic.views.DiscroptionView;
import com.statistic.views.ExplorerView;

public class openDialog extends AbstractHandler implements IHandler
{	
	@Override
	public Object execute(ExecutionEvent a_event) throws ExecutionException
	{

		Shell shell = new Shell(Display.getDefault());
 	   // shell.open();
 	    DirectoryDialog dialog = new DirectoryDialog(shell);
 	    dialog.setFilterPath("c:\\"); // Windows specific
 	   // System.out.println("RESULT=" + dialog.open());
 	    
 	    String reString = dialog.open();
 	    
 	    if (reString == null)
 	    	MessageDialog.openWarning(shell, "Предупреждение", "Вы не выбрали директорию");
 	    else {
 	    	DirecroryStructure direcroryStructure = DirecroryStructure.createDirectory(new File(reString /*"D:\\EclipseWorkDirectory\\git project\\statistic\\src\\com"*/), FileFormat.Java);
 			// direcroryStructure.m_parent = null;
 			
 			try
 			{
 				DiscroptionView discroptionView = (DiscroptionView)HandlerUtil.getActiveWorkbenchWindow(a_event).getActivePage().showView(DiscroptionView.ID);
 				
 				ExplorerView explorerView = (ExplorerView)HandlerUtil.getActiveWorkbenchWindow(a_event).getActivePage().showView(ExplorerView.ID);
 				FileBrowser fileBrowser = new FileBrowser(explorerView.m_table);
 				fileBrowser.setProvider(direcroryStructure);
 				fileBrowser.createControls(direcroryStructure, 
 						FileFormat.toAbstractStatistic(
 								FileFormat.toString(
 										explorerView.comboDropDown.getItem(
 												explorerView.comboDropDown.getSelectionIndex())), null));
 				
 				explorerView.m_discroptionView = discroptionView;
 			}
 			catch(PartInitException e)
 			{
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
		}
		// TODO Auto-generated method stub
		
		return null;
		
	}

}
