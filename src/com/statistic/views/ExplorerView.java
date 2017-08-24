package com.statistic.views;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

import com.statistic.count.Activator;
import com.statistic.count.FileRestriction;
import com.statistic.fileformat.AbstractStatistic;
import com.statistic.folders.DirecroryStructure;

public class ExplorerView extends ViewPart
{

	public static String		ID					= "com.statistic.count.Explorer";

	private TreeViewer			m_treeViewer;

	private DescriptionView		m_descriptionView;
	//private FileFormatManager	m_fileFormatManager	= FileFormatManager.getInstance();
	//private List<IFileFormat>	m_iFileFormat = new ArrayList<>();
	private FileRestriction m_fileRestriction;

	public ExplorerView()
	{

	}

	
	@Override
	public void createPartControl(Composite a_parent)
	{
		setPartName("Обозреватель папок");

		GridLayout layout = new GridLayout(2, false);
		a_parent.setLayout(layout);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		m_treeViewer = new TreeViewer(a_parent,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);

		m_treeViewer.setContentProvider(new DirectoryContentProvider());
		m_treeViewer.setFilters(new DirectoryFilterEmptyFolders());
	
		
		Tree aTree = m_treeViewer.getTree();
		aTree.setLayoutData(gridData);	
		
		// двойное нажатие на папку
		m_treeViewer.addDoubleClickListener(listener ->
			{
				IStructuredSelection thisSelection = (IStructuredSelection) listener.getSelection();
				Object selectedNode = thisSelection.getFirstElement();

				if(m_descriptionView == null)
				{
					System.out.println("Описание пусто");

					return;
				}

				// клик произведен по директории
				if(selectedNode instanceof DirecroryStructure)
				{
					DirecroryStructure direcroryStructure = (DirecroryStructure) selectedNode;

					m_descriptionView.printDirectoryStatistic(
							DirecroryStructure.getStatisticForSelectedFolder(direcroryStructure),
							m_fileRestriction.getLineAmount());

					m_descriptionView.changeTitle(direcroryStructure.getDirectoryName(),
							direcroryStructure.getFullDirectoryPath(), createImageOfDirectory());

					System.out.println(direcroryStructure.getDirectoryName());
				}
				// клик произведен по папке
				else
				{
					AbstractStatistic abstractStatistic = (AbstractStatistic) selectedNode;

					m_descriptionView.printFileStatistic(abstractStatistic);
					m_descriptionView.changeTitle(abstractStatistic.getShortFileName(),
							abstractStatistic.getLongFileName(), abstractStatistic.getImage());

					System.out.println(abstractStatistic.getLongFileName());
				}

			});
	}

	// очистить дерево директорий
	public void clearExplorer()
	{
		m_treeViewer.setInput(null);
	}

	public void setRestriction(FileRestriction a_fileRestriction)
	{
		m_fileRestriction = a_fileRestriction;
	}
	
	// получить ссылку на окно с таблицей
	public void setDescriptionView(DescriptionView a_descriptionView)
	{
		m_descriptionView = a_descriptionView;
	}

	public void fillTreeViewer(DirecroryStructure a_direcroryStructure)
	{
		m_treeViewer.setLabelProvider(new DirectoryLabelProvider(createImageOfDirectory()));
		m_treeViewer.setInput(new Object[] { a_direcroryStructure });
		m_treeViewer.expandToLevel(2);
	}

	// изображение директории
	private ImageDescriptor createImageOfDirectory()
	{
		return Activator.imageDescriptorFromPlugin("org.eclipse.e4.ui.workbench.swt",
				"/icons/full/obj16/fldr_obj.gif");
	}
	/*
	 * // изображение файла private ImageDescriptor createImageOfFile() { return
	 * m_iFileFormat.getFormatViewer().getFileImage(); }
	 */

	@Override
	public void setFocus()
	{

	}

}
