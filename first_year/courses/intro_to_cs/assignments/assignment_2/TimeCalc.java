
// Represents the hh:mm time format using an AM/PM format. 
public class TimeCalc  {

	public static void main(String[] args) {

		//take end convert the input 
		int hours = Integer.parseInt("" + args[0].charAt(0) + args[0].charAt(1));
		int minutes = Integer.parseInt("" + args[0].charAt(3) + args[0].charAt(4));

		int extraMinutes = Integer.parseInt(args[1]);
		//for edge case
		String zero = "0";
		String toZero = "00";

		//at the end program should print AM or PM
		String AMorPM = " AM";

		//if input invaild 
		if (hours < 0 || minutes < 0 || extraMinutes < 0 || hours > 23 || minutes > 59){
			System.out.println("Invalid input");

		}else{
			//comput the extraMinutes and convert them to hour
			hours = hours + ((minutes + extraMinutes) / 60);
			hours = hours % 24;
			minutes = ((minutes + extraMinutes) % 60);

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
}
