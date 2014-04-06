package com.opticalcobra.storybear.main;

import java.util.Comparator;

public class CompareLevelAppearance implements Comparator<ILevelAppearance> {

	@Override
	public int compare(ILevelAppearance arg0, ILevelAppearance arg1) {
		int result;
		
		result = arg0.getBlock() - arg1.getBlock();
		
		if (result > 0){
			return 1;
		} else if (result < 0)
		{
			return -1;
		} else {
			return 0;
		}
	}

}
