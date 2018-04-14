//import org.apache.commons.codec.language.bm.Lang;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class Perceptron {
	public double theta=0;
	public Double[] weight;
	
	public Perceptron(int weightDim) {
		weight = new Double[weightDim];
		// we can change weights and experiment
        System.out.println("Weights:");
		for(int i=0; i<weightDim; i++){
			weight[i] = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(-1, 1))
				    .setScale(25, RoundingMode.HALF_UP)
				    .doubleValue();
		    //weight[i]=ThreadLocalRandom.current().nextDouble(-2, 2);
		    System.out.print(weight[i]+", ");
        }

	}
	
	public void update_weight(double alpha, LanguageSample ls) {

            double y = output(ls); //our output
            int d; //DESIRED output
            if (ls.getClassName().equalsIgnoreCase("pl")) //here the name of a class
                d = 1;
            else
                d = 0;

            //WEIGHTS
            
            for(int i=0; i<weight.length; i++) {
    			weight[i]+=(d-y)*alpha*ls.parameters[i]*y*(1-y);
    		}
            //normalization
    		double length=0;
    		
    		//Normalization
    		for(int i=0; i<ls.parameters.length; i++) {
    			length+=Math.pow(ls.parameters[i], 2);
    		}
    		
    		length=Math.sqrt(length);
    		if(Double.isNaN(length))
    			System.out.println("ERRRRRRORRRR");
    		for(int i=0; i<weight.length; i++) {
    			  weight[i]/=BigDecimal.valueOf(weight[i]).round(new MathContext(20, RoundingMode.HALF_UP)).doubleValue();
    			  //System.out.println(weight[i]+" "+length);
    		}
    		
    		theta+=(d-y)*alpha*(-1)*y*(1-y);
            
	}
	
	public double output(LanguageSample ls) {
		

		double dot_product=1;
		
		for(int i=0; i<weight.length; i++) {
			dot_product+=ls.parameters[i]*weight[i];
		}
        double NET = dot_product-theta;
        
        if(Double.isNaN(NET)) {
        	System.out.println("ERROR");
        }
        //sigmoid
        double output =(1/(1+Math.exp(-NET))) ;
        return output;

//		if(dot_product>=theta) {
//			return 1;
//		}
//		return 0;
	}
	
	public String outputClass(LanguageSample ls) {
		double output = output(ls);
		if(output==1)
			return "Iris-virginica";
		return
				"Iris-versicolor";
	}
}


