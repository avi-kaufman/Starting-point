/* Tests the "quality" of Java's Math.random function.
*/
public  class  TestRandom {
    //?????????????static int a = 0;
    public static void main(String[]  args) {
	    
        //how much times to call math random
	    int n = Integer.parseInt(args[0]);
	     
	     double ratio = 0;
	     
	    //varibals for randon > 0.5 && < 0.5
	    int biggerThanHalf = 0;
	    int smallerThanHalf = 0;
        
        //call math random n times
	    for(int i = 0; i < n; i++){
	        double rundom = Math.random();
	        
	        if (rundom > 0.5){
	            biggerThanHalf = biggerThanHalf + 1;
	        }else{
	            smallerThanHalf = smallerThanHalf +1;
	        }     
	            
	    
	        if (biggerThanHalf < smallerThanHalf){
	            ratio = (double)biggerThanHalf/smallerThanHalf;
	        }else{
	            ratio = (double)smallerThanHalf/biggerThanHalf;
	        } 
	    }
	            System.out.println("> 0.5: " + biggerThanHalf + " times");     
	            System.out.println("< 0.5: " + smallerThanHalf + " times");   
	            System.out.println("ratio: " + ratio);
	    
	}
		
		
}

