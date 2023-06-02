// Splits a restaurant bill evenly among three diners.
public class Bill3 {
	public static void main(String[] args) {
	    String name1 = args[0];
		String name2 = args[1];
		String name3 = args[2];
		int bill = Integer.parseInt(args[3]);
		
		double dividedBy3 = (bill/3.0);
		dividedBy3 = Math.ceil(dividedBy3);
		
		System.out.println("dear " + name1 + ", " + name2 + ", and " + name3 + ": pay " + dividedBy3 + " shekels each.");
		


	    	    
	}
}
