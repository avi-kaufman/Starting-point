// Demonstrates the Collatz conjecture. */
public class Collatz {
	public static void main(String args[]) {
		// the seed from user
		int n = Integer.parseInt(args[0]);
		// Determine whether to print or not
		String mode = args[1];

		// for loop to varies the seed from 1 to N
		for (int l = 1; l <= n; l++) {
			if (mode.equals("v")) {
				// l = seed
				System.out.print(l);
			}

			// number of steps it took to reech  1
			int m = 1;
			//i = l = seed
			int i = l;

			do{
				// the rules
				if(i % 2 == 0) {
					i = i / 2;
				}else{
					i = i * 3 + 1;
				}

				// Determine whether to print or not
				if (mode.equals("v")){
					System.out.print(" " + i);
				}
				m++;

			} while (i != 1);
			// Determine whether to print or not
			if (mode.equals("v")) {
				// // number of steps it took to reech  1
				System.out.println(" " + "(" + m + ")");
			}

		}
		// the summary line
		System.out.println("The first " + n + " hailstone sequences reached 1" );

	}
}