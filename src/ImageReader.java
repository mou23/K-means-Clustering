import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class ImageReader {	
	List<Double>x=new ArrayList<Double>();
	String path;
	int numberOfClusters;
	Color arr[];
	
	public ImageReader(String path, int number) {
		this.numberOfClusters = number;
		this.path = path;
		this.arr = new Color[number];
		Scanner in=new Scanner(System.in);
		for(int i=0;i<number;i++){
			System.out.println("Enter RGB of cluster "+ (i+1));
			arr[i]=new Color(in.nextInt(),in.nextInt(),in.nextInt());
		}
		in.close();
	}

	private void readImage(){
		try
		{
			BufferedImage image = ImageIO.read(new File(this.path));
			int width = image.getWidth();
			int height = image.getHeight();
			for (int j = 0; j < height; j++) {
				for (int k = 0; k < width; k++) {
					int rgb1 = image.getRGB(k, j);
					Color col1 = new Color(rgb1, true);
					int r1 = col1.getRed();
					int g1 = col1.getGreen();
					int b1=col1.getBlue();
					double mean=(double)(r1+g1+b1)/3;
					x.add(mean);
					//System.out.println(mean);
				}
			}				
		} 
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}	
	
	public void clusterImage()
	{
		this.readImage();
		Cluster ob=new Cluster(x,numberOfClusters); 
//		System.out.println("CLUSTER created");
		try
		{
			BufferedImage image = ImageIO.read(new File(path));
			int width = image.getWidth();
			int height = image.getHeight();
			/*boolean f1=false;
			boolean f2=false;
			boolean f3=false;*/
			for (int j = 0; j < height; j++) {
				for (int k = 0; k < width; k++) {
					int rgb1 = image.getRGB(k, j);
					Color col = new Color(rgb1, true);
					int r = col.getRed();
					int g = col.getGreen();
					int b=col.getBlue();
					
					double mean=(double)(r+g+b)/3;
					int cls=ob.getClass(mean);
						
					image.setRGB(k, j, arr[cls].getRGB());
										
				}
			}
			ImageIO.write(image, "jpg",new File("out.jpg"));
		} 
		
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
