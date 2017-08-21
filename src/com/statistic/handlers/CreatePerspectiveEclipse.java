package com.statistic.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.e4.compatibility.SelectionService;

import com.statistic.fileformat.IFileFormat;
import com.statistic.folders.DirecroryStructure;
import com.statistic.views.DescriptionView;
import com.statistic.views.ExplorerView;


public class CreatePerspectiveEclipse extends AbstractHandler implements IHandler
{
	
	
	@Override
	public Object execute(ExecutionEvent a_event) throws ExecutionException
	{
		IWorkspace workspace = ResourcesPlugin.getWorkspace();  
		//PlatformUI.getWorkbench().showPerspective(, ResourcesPlugin.getWorkspace().get);
		ISelectionService service = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();

		ISelection selection = service.getSelection();
		File workspaceDirectory = null;
		
		if (selection instanceof IStructuredSelection)
		 {
		   Object selected = ((IStructuredSelection)selection).getFirstElement();
		   if (selected == null)
			   return null;
		   workspaceDirectory = (File)Platform.getAdapterManager().getAdapter(selected, File.class);

	
		 }
		try
		{
			PlatformUI.getWorkbench().showPerspective("com.statistic.count.perspective", PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		}
		catch(WorkbenchException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		//get location of workspace (java.io.File)  
		// = workspace.getRoot().getLocation().toFile();
	    
		//Shell shell = new Shell(Display.getDefault());

		// диалоговое окно выбора директории
		//DirectoryDialog dialog = new DirectoryDialog(shell);
		// путь по умолчанию
		//dialog.setFilterPath(workspaceDirectory);

		// выбранный пользователем каталог(путь к нему)
		//String resultString = dialog.open();

		if(workspaceDirectory != null)
		{
			try
			{
				// окно с таблицей результатов
				DescriptionView discroptionView = (DescriptionView) HandlerUtil
						.getActiveWorkbenchWindow(a_event).getActivePage()
						.showView(DescriptionView.ID);

				// дерево папок
				ExplorerView explorerView = (ExplorerView) HandlerUtil
						.getActiveWorkbenchWindow(a_event).getActivePage()
						.showView(ExplorerView.ID);

				// выбранный формат
				IFileFormat fileFormat = explorerView.getFormatViewer();

				// рекурсивно сформированное дерево, с указанным форматом
				DirecroryStructure direcroryStructure = new DirecroryStructure(
						new File(workspaceDirectory.getAbsolutePath()), fileFormat);

				// Файл указанного формата отсутствует
				if(direcroryStructure == null || direcroryStructure.getAmountOfFile() == 0)
				{
					/*MessageDialog.openWarning(shell, "Предупреждение",
							"Не было найдено ниодного файла формата "
									+ fileFormat.toString());*/
					return null;
				}

				// таблица вывода
				//IFormatViewer tFormatViewer = FileFormat.toTableViewer(fileFormat);

				// инициализация дерева
				discroptionView.setFormatViewer(explorerView.getFormatViewer());
				
				// указатель на окно с таблицей результатов
				explorerView.setDescriptionView(discroptionView);
				
				// обозреватель файлов, формирующий структуру
				explorerView.fillTreeViewer(direcroryStructure);

				// очистить от старых значений
				//discroptionView.getTableViewer().getTable().removeAll();
			}
			catch(PartInitException e)
			{
				e.printStackTrace();
			}
		}

		return null;

	}

}
