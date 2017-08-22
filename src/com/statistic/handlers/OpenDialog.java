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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.statistic.fileformat.IFileFormat;
import com.statistic.folders.DirecroryStructure;
import com.statistic.views.DescriptionView;
import com.statistic.views.ExplorerView;

public class OpenDialog extends AbstractHandler implements IHandler
{
	public static final String ID = "com.statistic.count.openDialog";

	@Override
	public Object execute(ExecutionEvent a_event) throws ExecutionException
	{
		Shell shell = new Shell(Display.getDefault());

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

		if(resultString != null)
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
						new File(resultString), fileFormat);

				// Файл указанного формата отсутствует
				if(direcroryStructure == null || direcroryStructure.getAmountOfFile() == 0)
				{
					MessageDialog.openWarning(shell, "Предупреждение",
							"Не было найдено ниодного файла формата " + fileFormat.toString());
					return null;
				}

				// таблица вывода
				// IFormatViewer tFormatViewer =
				// FileFormat.toTableViewer(fileFormat);

				// инициализация дерева
				discroptionView.setFormatViewer(explorerView.getFormatViewer());

				// указатель на окно с таблицей результатов
				explorerView.setDescriptionView(discroptionView);

				// обозреватель файлов, формирующий структуру
				//explorerView.fillTreeViewer(DirecroryStructure.addRootElement(direcroryStructure));
				explorerView.fillTreeViewer(direcroryStructure);
				// очистить от старых значений
				// discroptionView.getTableViewer().getTable().removeAll();
			}
			catch(PartInitException e)
			{
				e.printStackTrace();
			}
		}

		return null;

	}

}
