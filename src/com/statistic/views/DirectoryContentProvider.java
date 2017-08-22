package com.statistic.views;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.statistic.fileformat.AbstractStatistic;
import com.statistic.folders.DirecroryStructure;

public class DirectoryContentProvider implements ITreeContentProvider
{
	// получить все элементы для текущего элемента дерева
		@Override
		public Object[] getElements(Object a_inputElement)
		{
			DirecroryStructure direcroryStructure = (DirecroryStructure) a_inputElement;
			/*ArrayList<Object> ret = new ArrayList<>(direcroryStructure.getListDirectoryStructure());

			ret.addAll(direcroryStructure.getListAbstractStatistic());

			return ret.toArray();*/
			

			//if (direcroryStructure.getParent() == null)//
				return new Object[] {direcroryStructure};// getChildren(a_inputElement);
			
		}

		
		// получить для корневого дочерние элементы
		@Override
		public Object[] getChildren(Object a_parentElement)
		{
			DirecroryStructure direcroryStructure = (DirecroryStructure) a_parentElement;

			ArrayList<Object> ret = new ArrayList<>(direcroryStructure.getListDirectoryStructure());
			ret.addAll(direcroryStructure.getListAbstractStatistic());

			return ret.toArray();
		}

		// получить родительскую ссылку
		@Override
		public Object getParent(Object a_element)
		{
			if(a_element instanceof AbstractStatistic)
				return null;

			DirecroryStructure direcroryStructure = (DirecroryStructure) a_element;
			return direcroryStructure.getParent();
		}

		// проверить наличие дочерних узлов
		@Override
		public boolean hasChildren(Object a_element)
		{
			if(a_element instanceof AbstractStatistic)
			return false;

			DirecroryStructure direcroryStructure = (DirecroryStructure) a_element;
			ArrayList<Object> ret = new ArrayList<>(direcroryStructure.getListDirectoryStructure());
			ret.addAll(direcroryStructure.getListAbstractStatistic());
			return !ret.isEmpty();
		}
}
