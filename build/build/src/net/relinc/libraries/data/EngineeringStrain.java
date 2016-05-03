package net.relinc.libraries.data;

import net.relinc.libraries.data.DataSubset.baseDataType;

public class EngineeringStrain extends DataSubset {

	public EngineeringStrain(double[] t, double[] d){
		super(t, d);
	}

	@Override
	public double[] getUsefulTrimmedData() {
		return getTrimmedData();
	}
	
	@Override
	public baseDataType getBaseDataType() {
		return baseDataType.DISPLACEMENT;
	}
	
}
