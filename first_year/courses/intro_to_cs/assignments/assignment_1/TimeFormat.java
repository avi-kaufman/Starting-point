
// Represents the hh:mm time format using an AM/PM format. 
public class TimeFormat {

	public static void main(String[] args) {
		
		String zero = "0";
		String toZero = "00";
		String AMorPM = " AM";
		
		int hours = Integer.parseInt("" + args[0].charAt(0) + args[0].charAt(1));
		int minutes = Integer.parseInt("" + args[0].charAt(3) + args[0].charAt(4));
        
		if (hours >= 12){
			AMorPM = " PM";
		}
		if (hours > 12){
			hours = hours - 12;
		}	
		
		if(hours == 0){
			if(minutes ==0){
				System.out.println(toZero + ":" + toZero + AMorPM);
			}else {
			    if(minutes < 10){
					System.out.println(toZero + ":" + zero + minutes + AMorPM);
				}else
					System.out.println(toZero + ":" + minutes + AMorPM);
			}	
		}else{
			if(minutes < 10){
				System.out.println(hours + ":" + zero + minutes + AMorPM);
				
			}else{
				System.out.println(hours + ":" + minutes + AMorPM);
				
			}
		}
		
	}
}
