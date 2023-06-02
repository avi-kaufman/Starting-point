/**
 * A simulation of covid vaccination which contains two lines, one for each dose of the vaccine.
 * The first line is a heap, in which subjects are ordered according to their age.
 * The second line is a queue in which subjects are ordered according to the when they've received the first vaccine. 
 */
public class COVIDSimulation {

	/*
	 * The heap and queue which compose the data structure.
	 */
	Heap heap;
	Queue q;
	
	/**
	 * Creates an empty simulation with a given capacity
	 */
	public COVIDSimulation(int capacity){
			heap = new Heap(capacity);
			q = new Queue(capacity);
	}
	
	
	/**
	 * Adds a new Subject to the data structure.
	 * The Subject is entered to the heap, according to its age, to wait for the first dose of the vaccine
	 * 
	 * @param s - the subject
	 */
	public void addSubject(Subject s){
			heap.insert(s);
	}
	
	/**
	 * Gives the first dose of the vaccine
	 * Removes the the subject who is first in line to receive the first dose and puts it at the end of the line to receive
	 * the second dose.
	 * 
	 * @return the customer with the highest priority.
	 */
	public void giveFirstDose(){
		Subject receiveFirstDose = heap.extractMax();
		q.enqueue(receiveFirstDose);
	}
	
	/**
	 * Gives the first dose of the vaccine
	 * The subject must be removed from the front of the queue for the second vaccine.
	 * 
	 * @return the subject at the front of the queue for the second vaccine.
	 */
	public Subject giveSecondDose(){
			return q.dequeue();
	}
	
	/**
	 * Removes a Subject with a given name from the queue to get the second dose.
	 * Does nothing if there is such Subject in the queue.
	 * 
	 * @param name - the name of the subject to be removed.
	 */
	public void removeSubject(String name) {
			q.remove(name);
	}
	
    public static void main (String[] args){
    	
    	/*
    	 * A basic test to check your class.
    	 * Expected outcome:
		 * Joseph Douglas, age: 61
		 * Alice Douglas, age: 60
		 * Jubal Harshaw, age: 45
	     * Michael Valentine, age: 16
		 * null
		 * 
	     */
    	COVIDSimulation q = new COVIDSimulation(10);
    	Subject a = new Subject(16, "Michael Valentine");
    	Subject b = new Subject(45, "Jubal Harshaw");
    	Subject c = new Subject(60, "Alice Douglas");
    	Subject d = new Subject(61, "Joseph Douglas");
    	Subject e = new Subject(26, "Jill Boardman");
    	
    	
    	q.addSubject(a);  	
    	q.addSubject(b);
    	q.addSubject(c);
    	q.addSubject(d);
    	
    	q.giveFirstDose();
    	q.giveFirstDose();
    	System.out.println(q.giveSecondDose());
    	System.out.println(q.giveSecondDose());
    	q.addSubject(e);
    	
    	q.giveFirstDose();
    	q.giveFirstDose();
    	System.out.println(q.giveSecondDose());
    	
    	q.giveFirstDose();
    	q.removeSubject("Jill Boardman");
    	System.out.println(q.giveSecondDose());
    	System.out.println(q.giveSecondDose());
    }
}
