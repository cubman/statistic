package com.statistic.count;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.statistic.views.DescriptionView;
import com.statistic.views.ExplorerView;

public class Perspective implements IPerspectiveFactory
{

	@Override
	public void createInitialLayout(IPageLayout layout)
	{
		layout.setEditorAreaVisible(false);
		layout.addView(ExplorerView.ID, IPageLayout.LEFT, 0.35f, layout.getEditorArea());
		layout.addView(DescriptionView.ID, IPageLayout.RIGHT, 0.5f, layout.getEditorArea());
	}
}
