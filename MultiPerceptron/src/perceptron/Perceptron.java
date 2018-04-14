package perceptron;
import java.util.concurrent.ThreadLocalRandom;

public class Perceptron {
	public double theta=0;
	public double[] weight;
	
	public Perceptron(int weightDim) {
		weight = new double[weightDim];
		// we can change weights and experiment
		weight[0]=ThreadLocalRandom.current().nextDouble(-2, 2); //different random generators
		weight[1]=ThreadLocalRandom.current().nextDouble(-2, 2);
		weight[2]=ThreadLocalRandom.current().nextDouble(-2, 2);
		weight[3]=ThreadLocalRandom.current().nextDouble(-2, 2);
		
		// for this test set
		// 100% accuracy, when alpha = 0.5, it = 100
//		weight[0]=0.6530823701800665;
//		weight[1]=0.17859799891777817;
//		weight[2]=1.0891135496161843;
//		weight[3]=1.1980219984817255;
		System.out.println("Weights: "+weight[0]+","+weight[1]+","+weight[2]+","+weight[3]);
	}
	
	public void update_weight(double alpha, Iris x, int length) {
		
		int y = output(x); //our output
		int d; //DESIRED output
		if(x.getclassName().equals("Iris-virginica")) //here the name of a class
			d=1;
		else
			d=0;
		
		//WEIGHTS
		weight[0]+=(d-y)*alpha*x.getA();
		weight[1]+=(d-y)*alpha*x.getB(); 
		weight[2]+=(d-y)*alpha*x.getC(); 
		weight[3]+=(d-y)*alpha*x.getD();

		//THETA
		theta+=(d-y)*alpha*(-1); 
	}
	
	public int output(Iris x) {
		double dot_product=0;
			dot_product+=x.getA()*weight[0];
			dot_product+=x.getB()*weight[1];
			dot_product+=x.getC()*weight[2];
			dot_product+=x.getD()*weight[3];
			
		if(dot_product>=theta) {
			return 1; 
		}
		return 0; 
	}
	
	public String outputClass(Iris x) {	
		int output = output(x);
		if(output==1)
			return "Iris-virginica";
		return
				"Iris-versicolor";
	}
}


