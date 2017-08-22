package com.statistic.handlers;

import java.io.File;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.statistic.fileformat.IFileFormat;
import com.statistic.folders.DirecroryStructure;
import com.statistic.views.DescriptionView;
import com.statistic.views.ExplorerView;

public class createExplorer
{
	public void setData(String workspaceDirectory, ExecutionEvent a_event, List<IFileFormat> formatToSearch, Shell a_shell)
	{
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
				//IFileFormat fileFormat = explorerView.getFormatViewer();

				// рекурсивно сформированное дерево, с указанным форматом
				DirecroryStructure direcroryStructure = new DirecroryStructure(
						new File(workspaceDirectory), formatToSearch);

				// Файл указанного формата отсутствует
				if(direcroryStructure == null || direcroryStructure.getAmountOfFile() == 0)
				{
					explorerView.clearExplorer();
					
					MessageDialog.openWarning(a_shell, "Предупреждение",
							"Не было найдено ниодного файла из существующих форматов формата");
					return;
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
	}
}