package net.relinc.libraries.data;

import net.relinc.libraries.data.DataSubset.baseDataType;

public class IncidentBarStrainPulse extends IncidentPulse {

	public IncidentBarStrainPulse(double[] t, double[] d) {
		super(t, d);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double[] getUsefulTrimmedData() {
		return super.getTrimmedData();
	}
	
	@Override
	public baseDataType getBaseDataType() {
		return baseDataType.DISPLACEMENT;
	}

}
