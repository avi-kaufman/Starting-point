// Generates three integer random numbers in a given range,
// and prints them in increasing order.
public class Ascend {
	public static void main(String[] args) {
		
		// lim has taken from cmd
		int lim = Integer.parseInt(args[0]);
		// 3 integer from random fun'
		int a = (int)((Math.random()*lim));
		int b = (int)((Math.random()*lim));
		int c = (int)((Math.random()*lim));
		//program prints the 3 nun 
		System.out.println(a +" "+ b + " " + c);
		
		// comput the min max and middle 
		int minOfTo = Math.min(a,b);
		int minOfThree = Math.min(minOfTo,c);
		int maxOfTo = Math.max(a,b);
		int maxOfThree = Math.max(maxOfTo,c);
		int middle = (a + b + c)-(maxOfThree + minOfThree);
		//program prints the ascend 3 nun
		System.out.println(minOfThree + " " + middle + " " + maxOfThree);
	}
}
