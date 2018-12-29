import java.util.List;
import java.util.Random;

public class Cluster {
	List<Double>x;
	int numberOfClass;
	double cluster[][];
	int counter[];
	double newMean[];
	double prevMean[];
	int iteration;
	
	public Cluster(List<Double> x, int numberOfClass) 
	{
//		System.out.println("CLUSTER");
		this.x = x;
		this.numberOfClass = numberOfClass;
		this.cluster = new double[numberOfClass][x.size()];
		this.counter = new int[numberOfClass];
		this.newMean = new double[numberOfClass];
		this.prevMean = new double[numberOfClass];
		iteration=0;
		for(int i=0;i<numberOfClass;i++){
			prevMean[i]=-1;
		}
//		System.out.println(x.size());
		initializeArray();
		Random rand = new Random();
		//Collections.sort(testList);
		for(int i=0;i<x.size();i++){
			int value=rand.nextInt(numberOfClass);
			//System.out.println(value);
			cluster[value][counter[value]]=x.get(i);
			counter[value]++;
		}
	
//		System.out.println("ARRAY");
//		for(int i=0;i<numberOfClass;i++){
//			//for(int j=0;j<counter[i];j++){
//				System.out.println(counter[i]);
//			//}
//		}
		reOrder();
	}
	
	private void initializeArray()
	{
		for(int i=0;i<numberOfClass;i++){
			counter[i]=0;
		}
	}
	
	private void reOrder()
	{
		iteration++;
		if(iteration>=200){
			return;
		}
		
		for(int i=0;i<numberOfClass;i++){
			newMean[i]=calculateMean(cluster[i],counter[i]);
		}
		
		/*if(equalMean()==true){
			System.out.println("EQUAL "+iteration);
			return;
		}*/
		
		initializeArray();
		
		for(int i=0;i<x.size();i++){
			double min=100000;
			int cls=-1;
			for(int j=0;j<numberOfClass;j++){
				double value=calculateDistance(x.get(i),newMean[j]);
				if(value<min){
					min=value;
					cls=j;
				}
			}
			cluster[cls][counter[cls]]=x.get(i);
			counter[cls]++;
		}
		prevMean=newMean;
	
		reOrder();
	}
	
	private boolean equalMean()
	{
		for(int i=0;i<numberOfClass;i++){
			if(newMean[i]!=prevMean[i]){
				//System.out.println(newMean[i]+" "+prevMean[i]);
				return false;
			}
			else{
				System.out.println("EQU "+newMean[i]+" "+prevMean[i]);
			}
		}
		return true;
	}
	
	private double calculateDistance(double d1,double d2)
	{
		return (Math.abs((double)d1-d2));
	}
	
	private double calculateMean(double []d,int count)
	{
		double total=0;
		for(int i=0;i<count;i++){
			total+=d[i];
		}
//		System.out.println("MEAN "+(double)total/count);
		return ((double)total/count);
	}
	
	public int getClass(double value)
	{
		for(int i=0;i<numberOfClass;i++){
			for(int j=0;j<counter[i];j++){
				if(cluster[i][j]==value){
					return i;
				}
			}
		}
		return -1;
	}
}
