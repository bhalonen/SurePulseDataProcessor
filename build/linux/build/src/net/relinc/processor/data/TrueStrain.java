package net.relinc.processor.data;

public class TrueStrain extends DataSubset{

	public TrueStrain(double[] timed, double[] datad) {
		super(timed, datad);
	}

	@Override
	public double[] getUsefulTrimmedData() {
		return getTrimmedData();
	}

}
