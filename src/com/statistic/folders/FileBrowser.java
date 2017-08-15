package com.statistic.folders;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.statistic.file.count.AbstractStatistic;

// обозреватель файлов
public class FileBrowser {
	
	// дерево файлов
    private TreeViewer viewer;

    public FileBrowser(TreeViewer a_treeViewer)
	{
		viewer = a_treeViewer;
	}
    
    // установить источник данных для отображения
    public  void setProvider(ITreeContentProvider a_contentProvider)
	{
		viewer.setContentProvider(a_contentProvider);
	}
    
    // создание начального состояний структуры отображения дерева
    @PostConstruct
    public void createControls(DirecroryStructure a_direcroryStructure, AbstractStatistic a_abstractStatistic) {
        viewer.setLabelProvider(new ViewLabelProvider(createImageOfDirectory(), createImageOfFile(a_abstractStatistic)));
        viewer.setFilters(new MyFilter());
        viewer.setInput(a_direcroryStructure);
    }

    // изображение директории
    private ImageDescriptor createImageOfDirectory() {
        Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
        URL url = FileLocator.find(bundle, new Path("icons/folder.png"), null);
        return ImageDescriptor.createFromURL(url);
    }

    // изображение файла
    private ImageDescriptor createImageOfFile(AbstractStatistic a_abstractStatistic) {
    	return a_abstractStatistic.getImage();
    	
       // return ImageDescriptor.createFromImage(a_abstractStatistic.getImage());
    }
    
    
    
    // клсс иконка - текст файла
    class ViewLabelProvider extends LabelProvider /*implements IStyledLabelProvider */{

    	// изображение директории
        private ImageDescriptor directoryImage; 
        // изображение файла
        private ImageDescriptor fileImage;
        //объект ресурсов проета
        private ResourceManager resourceManager;

        public ViewLabelProvider(ImageDescriptor directoryImage, ImageDescriptor a_fileDescriptor) {
            this.directoryImage = directoryImage;
            this.fileImage = a_fileDescriptor;
        }

        // получение название файла
        @Override
        public String getText(Object a_element)
        {
        	if (a_element instanceof DirecroryStructure) {
        		DirecroryStructure direcroryStructure = ((DirecroryStructure)a_element);
        		return String.format("%s ( %d )", direcroryStructure.m_directoryName, direcroryStructure.m_amountOfFiles);
        	}
        	else return ((AbstractStatistic)a_element).getShortFileName();

        }
      
        // установка изображения ждя файла
        @Override
        public Image getImage(Object element) {
                return element instanceof DirecroryStructure ? 
                		getResourceManager().createImage(directoryImage) :
                			getResourceManager().createImage(fileImage);          
        }

        // очищение ресурсов
        @Override
        public void dispose() {
            // garbage collection system resources
            if (resourceManager != null) {
                resourceManager.dispose();
                
            resourceManager = null;
            }
        }

        // выделение ресурсов
        protected ResourceManager getResourceManager() {
            if (resourceManager == null) {
                resourceManager = new LocalResourceManager(JFaceResources.getResources());
            }
            return resourceManager;
        }

    }
    
    
    class MyFilter extends ViewerFilter{
    	  @Override
    	  public boolean select(Viewer viewer, Object parentElement, Object element){
    		  if (element instanceof AbstractStatistic)
    				return true;
    			
    		  DirecroryStructure direcroryStructure = (DirecroryStructure)element;
    		  return direcroryStructure.m_amountOfFiles != 0;
    		/*  
    	    else {
    	      StructuredViewer sviewer = (StructuredViewer) viewer;
    	      ITreeContentProvider provider = (ITreeContentProvider) sviewer.getContentProvider();
    	      for (Object child: provider.getChildren(element)){
    	        if (select(viewer, element, child))
    	          return true;
    	      }
    	      return false;
    	    }*/
    	  }
    	}
}