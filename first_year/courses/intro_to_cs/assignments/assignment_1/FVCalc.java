// Computes the future value of a saving investment.
public class FVCalc {
	public static void main(String[] args){
		// the input from the investor
		int currentValue = ((Integer.parseInt(args[0])));
		double rate = Double.parseDouble(args[1]);
		int n = ((Integer.parseInt(args[2])));
		
		// rate divided by 100 to know the Interest rate
		rate = rate/100;
		// formula to compute the future valeu
		int futureValeu = (int)(currentValue*(Math.pow((1 + rate),n)));
		
		System.out.println("Afrer " + n + " years," + " a $" + currentValue + " saved at " + rate*100 + "% " + "will yield $" + futureValeu);
		
		
		
	}
}