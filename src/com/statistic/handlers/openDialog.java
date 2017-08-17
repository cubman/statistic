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

import com.statistic.file.viewer.IFormatViewer;
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

		// диалоговое окно выбора директории
		DirectoryDialog dialog = new DirectoryDialog(shell);
		// путь по умолчанию
		dialog.setFilterPath("D:\\EclipseWorkDirectory\\gitproject\\statistic\\src\\com");

		// выбранный пользователем каталог(путь к нему)
		String reString = dialog.open();

		if(reString == null)
			MessageDialog.openWarning(shell, "Предупреждение", "Вы не выбрали директорию");
		else
		{
			try
			{
				// окно с таблицей результатов
				DiscroptionView discroptionView = (DiscroptionView) HandlerUtil
						.getActiveWorkbenchWindow(a_event).getActivePage()
						.showView(DiscroptionView.ID);

				// дерево папок
				ExplorerView explorerView = (ExplorerView) HandlerUtil
						.getActiveWorkbenchWindow(a_event).getActivePage()
						.showView(ExplorerView.ID);

				// выбранный формат
				FileFormat fileFormat = FileFormat.toString(explorerView.comboDropDown
						.getItem(explorerView.comboDropDown.getSelectionIndex()));

				// рекурсивно сформированное дерево, с указанным форматом
				DirecroryStructure direcroryStructure = DirecroryStructure
						.createDirectory(new File(reString), fileFormat);

				// Файл указанного формата отсутствует
				if(direcroryStructure == null || direcroryStructure.m_amountOfFiles == 0) {
					MessageDialog.openWarning(shell, "Предупреждение",
							"Не было найдено ниодного файла формата "
									+ FileFormat.toFormat(fileFormat));
					return null;
				}

				IFormatViewer tFormatViewer = FileFormat.toTableViewer(fileFormat,
						discroptionView.m_tableViewer);
				
				// инициализация дерева
				discroptionView.m_iTableViewer = tFormatViewer;

				// обозреватель файлов, формирующий структуру
				FileBrowser fileBrowser = new FileBrowser(explorerView.aTreeViewer);
				fileBrowser.createControls(direcroryStructure, tFormatViewer);

				// указатель на окно с таблицей результатов
				explorerView.m_discroptionView = discroptionView;
				
				// очистить от старых значений
				discroptionView.m_tableViewer.getTable().removeAll();
			}
			catch(PartInitException e)
			{
				e.printStackTrace();
			}
		}

		return null;

	}

}
