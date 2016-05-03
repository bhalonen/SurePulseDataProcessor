package net.relinc.libraries.sample;

import net.relinc.libraries.data.DataFile;
import net.relinc.libraries.data.DataLocation;
import net.relinc.libraries.data.DataSubset;
import net.relinc.libraries.data.IncidentPulse;
import net.relinc.libraries.data.ReflectedPulse;
import net.relinc.libraries.data.TransmissionPulse;

public abstract class HopkinsonBarSample extends Sample {
	
	public abstract double getInitialCrossSectionalArea();
	public abstract double getHopkinsonBarTransmissionPulseSign();
	public abstract double getHopkinsonBarReflectedPulseSign();
	public abstract double[] getTrueStressFromEngStressAndEngStrain(double[] engStress, double[] engStrain);
	
//	private double length;
//	
//	public double getLength(){
//		return length;
//	}
//	public void setLength(double l){
//		length = l;
//	}
	
	public double[] getEngineeringStressFromForce(double[] force){
		double[] stressValues = new double[force.length];
		for(int i = 0; i < stressValues.length; i++){
			stressValues[i] = force[i] / getInitialCrossSectionalArea(); //method is above
		}
		return stressValues;
	}
	
	public double[] getForceFromTransmissionBarStrain(double[] barStrain) {
		double[] force = new double[barStrain.length];
		for(int i = 0; i < barStrain.length; i++){
			force[i] = getHopkinsonBarTransmissionPulseSign() * barStrain[i] * barSetup.TransmissionBar.youngsModulus * barSetup.TransmissionBar.getArea();
		}
		return force;
	}
	
	public double[] getEngineeringStrainFromIncidentBarReflectedPulseStrain(double[] time, double[] reflectedStrain) {
		double[] strainRate = new double[reflectedStrain.length];
		double strainRateMultiplier = 2 * barSetup.IncidentBar.getWaveSpeed() / (length);
		for(int i = 0; i < strainRate.length; i++){
			strainRate[i] = strainRateMultiplier * getHopkinsonBarReflectedPulseSign() * reflectedStrain[i]; //method sets sign of pulse.
		}
		double[] strain = new double[strainRate.length];
		for(int i = 0; i < strain.length; i++){

			if(i==0){
				strain[0]=0;
			}
			else{
				strain[i] = strain[i - 1] + strainRate[i] * (time[i] - time[i - 1]);
			}


		}
		return strain;
	}
	public double[] getFrontFaceForce() {
		ReflectedPulse reflectedPulse = null;//(ReflectedPulse) getCurrentDisplacementDatasubset();
		IncidentPulse incidentPulse = null;
		try{
			reflectedPulse = (ReflectedPulse)getCurrentDisplacementDatasubset();
			DataLocation reflectedLocation =  getLocationOfDataSubset(reflectedPulse);
			//find incident in same datafile.
			DataFile file = DataFiles.get(reflectedLocation.dataFileIndex);
			for(DataSubset subset : file.dataSubsets){
				if(subset instanceof IncidentPulse){
					incidentPulse = (IncidentPulse)subset;
					break;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		double sign = (this instanceof CompressionSample || this instanceof ShearCompressionSample) ? -1 : 1;
		
		return reflectedPulse.getFrontFaceForce(barSetup.IncidentBar, incidentPulse.getUsefulTrimmedData(), sign);
	}
}
