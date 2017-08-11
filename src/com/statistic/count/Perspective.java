package com.statistic.count;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import Views.DiscroptionView;
import Views.ExplorerView;

public class Perspective implements IPerspectiveFactory {

	@Override	
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.addView(ExplorerView.ID, IPageLayout.LEFT, 0.4f, layout.getEditorArea());
		layout.addView(DiscroptionView.ID, IPageLayout.RIGHT, 0.5f, layout.getEditorArea());
	}
}
