// Computes an approximation of PI.
public class CalcPi {

    public static void main(String args[]) { 
	
		//the numbrs of time to run the while loop
		int num = Integer.parseInt(args[0]);
		//adition to pi/4
		double sum = 1;
		
		    for(int i = 1; i < num; i++){
				// add on time to sum for 1 iteration
				double denominator = (1 / (2.0 * i + 1));
				
				if (i % 2 != 0){
					sum = sum - denominator;
                }else{
					sum = sum + denominator;}
				
			}
			
		System.out.println("pi according to Java: " + Math.PI);
		System.out.println("pi, approximated:     " + 4.0 * sum);
	}
}