package com.statistic.views;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

import com.statistic.count.Activator;
import com.statistic.fileformat.AbstractStatistic;
import com.statistic.fileformat.FileFormatManager;
import com.statistic.fileformat.IFileFormat;
import com.statistic.folders.DirecroryStructure;

public class ExplorerView extends ViewPart
{

	public static String		ID					= "com.statistic.count.Explorer";

	private TreeViewer			m_treeViewer;
	private Combo				m_comboDropDown;
	private DescriptionView		m_descriptionView;
	private FileFormatManager	m_fileFormatManager	= FileFormatManager.getInstance();
	private IFileFormat		m_iFileFormat;
	private Spinner				m_spinner;

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

		m_comboDropDown = new Combo(a_parent, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);

		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);

		m_comboDropDown.setLayoutData(gridData);

		gridData = new GridData(SWT.CENTER, SWT.CENTER, true, false);

		m_spinner = new Spinner(a_parent, SWT.BORDER);
		m_spinner.setIncrement(5);
		m_spinner.setMinimum(10);
		m_spinner.setMaximum(200);

		m_spinner.setToolTipText("Минимальное значение строковых код");
		
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
							Integer.parseInt(m_spinner.getText()));

					m_descriptionView.changeName(direcroryStructure.getDirectoryName(),
							direcroryStructure.getFullDirectoryPath());

					System.out.println(direcroryStructure.getDirectoryName());
				}
				// клик произведен по папке
				else
				{
					AbstractStatistic abstractStatistic = (AbstractStatistic) selectedNode;

					m_descriptionView.printFileStatistic(abstractStatistic);
					m_descriptionView.changeName(abstractStatistic.getShortFileName(),
							abstractStatistic.getLongFileName());

					System.out.println(abstractStatistic.getLongFileName());
				}

			});

		// список форматов для поиска
		for(IFileFormat fileFormat : m_fileFormatManager.getFileFormats())
			m_comboDropDown.add(fileFormat.toString());

		// выбрали другой формат
		m_comboDropDown.addListener(SWT.Modify,
				lis -> m_iFileFormat =  m_fileFormatManager.getFileFormats().get(m_comboDropDown.getSelectionIndex()));

		// выбрали первый по списку
		m_comboDropDown.select(0);

	}

	/*// получить текущий класс обработки
	public IFileFormat getSelectedFileFormat()
	{
		return m_iFileFormat;
	}*/

	// получить ссылку на окно с таблицей
	public void setDescriptionView(DescriptionView a_descriptionView)
	{
		m_descriptionView = a_descriptionView;
	}

	// получить 
	public IFileFormat getFormatViewer()
	{
		return m_iFileFormat;
	}

	public void fillTreeViewer(DirecroryStructure a_direcroryStructure)
	{
		m_treeViewer.setLabelProvider(
				new DirectoryLabelProvider(createImageOfDirectory(), createImageOfFile()));
		m_treeViewer.setInput(a_direcroryStructure);
	}

	// изображение директории
	private ImageDescriptor createImageOfDirectory()
	{
		return Activator.imageDescriptorFromPlugin("org.eclipse.e4.ui.workbench.swt",
				"/icons/full/obj16/fldr_obj.gif");
	}

	// изображение файла
	private ImageDescriptor createImageOfFile()
	{
		return m_iFileFormat.getFormatViewer().getFileImage();
	}

	@Override
	public void setFocus()
	{

	}

}
