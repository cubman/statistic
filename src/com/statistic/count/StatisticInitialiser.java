package com.statistic.count;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class StatisticInitialiser extends AbstractPreferenceInitializer
{


	public StatisticInitialiser()
	{
	}

	@Override
	public void initializeDefaultPreferences()
	{
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.setDefault("PATH", "C:/");
	}

}
