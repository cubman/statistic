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
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.statistic.file.count.AbstractStatistic;

public class FileBrowser {
    private TreeViewer viewer;

    public FileBrowser(TreeViewer a_treeViewer)
	{
		viewer = a_treeViewer;
	}
    
    public  void setPrivider(ITreeContentProvider a_contentProvider)
	{
		viewer.setContentProvider(a_contentProvider);
	}
    
    @PostConstruct
    public void createControls(DirecroryStructure a_direcroryStructure, AbstractStatistic a_abstractStatistic) {
        viewer.setLabelProvider(new ViewLabelProvider(createImageDescriptor(), createImageFile(a_abstractStatistic)));
        viewer.setInput(a_direcroryStructure);
    }

    private ImageDescriptor createImageDescriptor() {
        Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
        URL url = FileLocator.find(bundle, new Path("icons/folder.png"), null);
        return ImageDescriptor.createFromURL(url);
    }


    private ImageDescriptor createImageFile(AbstractStatistic a_abstractStatistic) {
       /* Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
        URL url = FileLocator.find(bundle, new Path("icons/folder.png"), null);*/
    	return a_abstractStatistic.getImage();
    	
       // return ImageDescriptor.createFromImage(a_abstractStatistic.getImage());
    }
    
    class ViewLabelProvider extends LabelProvider /*implements IStyledLabelProvider */{

        private ImageDescriptor directoryImage;
        private ImageDescriptor fileImage;
        private ResourceManager resourceManager;

        public ViewLabelProvider(ImageDescriptor directoryImage, ImageDescriptor a_fileDescriptor) {
            this.directoryImage = directoryImage;
            this.fileImage = a_fileDescriptor;
        }

        @Override
        public String getText(Object a_element)
        {
        	if (a_element instanceof DirecroryStructure)
        		return ((DirecroryStructure)a_element).m_directoryName;
        	else return ((AbstractStatistic)a_element).getShortFileName();

        }
      
        @Override
        public Image getImage(Object element) {
            if (element instanceof DirecroryStructure) {
                    return getResourceManager().createImage(directoryImage);        
            }  else {
            	return getResourceManager().createImage(fileImage);      
            }

         
        }

       /* @Override
        public void dispose() {
            // garbage collection system resources
            if (resourceManager != null) {
                resourceManager.dispose();
                resourceManager = null;
            }
        }*/

        protected ResourceManager getResourceManager() {
            if (resourceManager == null) {
                resourceManager = new LocalResourceManager(JFaceResources.getResources());
            }
            return resourceManager;
        }

       /* private String getFileName(File file) {
            String name = file.getName();
            return name.isEmpty() ? file.getPath() : name;
        }*/
    }


   /* class FileModifiedLabelProvider extends LabelProvider implements IStyledLabelProvider {

        private DateFormat dateLabelFormat;

        public FileModifiedLabelProvider(DateFormat dateFormat) {
            dateLabelFormat = dateFormat;
        }

        @Override
        public StyledString getStyledText(Object element) {
            if (element instanceof File) {
                File file = (File) element;
                long lastModified = file.lastModified();
                return new StyledString(dateLabelFormat.format(new Date(lastModified)));
            }
            return null;
        }
    }*/

  /*  class FileSizeLabelProvider extends LabelProvider implements IStyledLabelProvider {

        @Override
        public StyledString getStyledText(Object element) {
            if (element instanceof File) {
                File file = (File) element;
                if (file.isDirectory()) {
                    // a directory is just a container and has no size
                    return new StyledString("0");
                }
                return new StyledString(String.valueOf(file.length()));
            }
            return null;
        }
    }*/

    public void setFocus() {
        viewer.getControl().setFocus();
    }
}