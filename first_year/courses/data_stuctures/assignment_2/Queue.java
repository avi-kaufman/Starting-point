/**
 * A queue of Subjects, implemented as a cyclic array.
 * 
 * IMPORTANT: you may not use any loops/recursions in this class.
 */
public class Queue {

	Subject[] cyclicSubjects; // a cyclic array to implement the queue
	int size;
	int front;
	
	/**
	 * Constructs an empty queue with the given capacity (this is the initial size of the array, which may change later on).
	 */
	public Queue(int capacity){
			cyclicSubjects = new Subject[capacity];
			size = 0;
			front = 0;
	}
	
	/**
	 * Removes and returns the first subject in the queue
	 * If the queue if empty should return null.
	 * 
	 * @return the first subject in the queue
	 */
	public Subject dequeue(){
		if(cyclicSubjects[front] == null){
			return null;
		}
			Subject first = cyclicSubjects[front];
			front = (front + 1) % cyclicSubjects.length;
			size--;
		return first;
	}
	
	/**
	 * Returns and does not remove the subject next in line to receive the second dose of the vaccine.
	 * 
	 * @return the subject next in line to receive the second dose of the vaccine.
	 */
	public Subject peek(){
			return cyclicSubjects[front];
	}
	
	/**
	 * Adds a new subject to the back of the queue
	 * 
	 * If at any point the queue becomes full as a result of inserting too many subjects, 
     * then the size of the array should be doubled to handle extra subjects.
     * NOTE: you may use a loop to iterate through the entire array
     * for the purpose of resizing it and for this purpose only.
     * 
	 * @param e - the subject
	 */
	public void enqueue(Subject e){
		// the array is full
			if ( size == cyclicSubjects.length) {
				Subject[] temp = new Subject[2 * size];
				for (int i = 0; i <= size; i++){
					temp[i] = cyclicSubjects[(front + i) % cyclicSubjects.length];
				}
				cyclicSubjects = temp;
				cyclicSubjects[size] = e;
				front = 0;
				size++;
			}else { //the array is not full
				cyclicSubjects[(front + size) % cyclicSubjects.length] = e;
				size++;
			}
	}
	
    
    /**
     * Removes a Subject with a given name from the queue.
     * If such a Subject does not exist returns false.
     * 
     * The order rest of the Subjects should have the same order after removal.
     * 
     * NOTE: you may use a loop to iterate through the entire array in this function.
     * 
     * @param name -  the name of the Subject who should be removed.
     * @return - true if and only if the subject was removed.
     */
	public boolean remove(String name){
			// the subject in the queue
		int length = cyclicSubjects.length;
		boolean isNameFound = false;
		for (int i = front; i <= size + front - 1; i++) {
			if ((cyclicSubjects[i % length].name).equals(name) || isNameFound ) {
				isNameFound = true;
				cyclicSubjects[i % length] = cyclicSubjects[(i +1) % length];

			}
		}
		if(isNameFound){
			    size--;
			return true;
		}

			// the subject arnot in the queue
			return isNameFound;
	}

	public static void main (String[] args){
    	/*
    	 * A basic test for the queue.
    	 * You should be able to run this before implementing the simulation.
    	 * 
    	 * Expected outcome: 
		 * Benedict, age: 24
		 * Benedict, age: 24
		 * Benedict, age: 24
		 * Corwin, age: 23
		 * Eric, age: 22
		 * Caine, age: 21
		 * Bleys, age: 20
		 * true
	     * Brand, age: 19
	     * Gerard, age: 17
		 * Random, age: 16
		 * null
    	 * 
    	 */
    	Queue q = new Queue(5);
    	Subject a = new Subject(24, "Benedict");
    	Subject b = new Subject(23, "Corwin");
    	Subject c = new Subject(22, "Eric");
    	Subject d = new Subject(21, "Caine");
    	Subject e = new Subject(20, "Bleys");
    	Subject f = new Subject(19, "Brand");
    	Subject g = new Subject(18, "Julian");
    	Subject h = new Subject(17, "Gerard");
    	Subject i = new Subject(16, "Random");
    	
    	
   
    	q.enqueue(a);
    	System.out.println(q.peek());
    	
    	q.enqueue(b);
    	q.enqueue(c);
    	q.enqueue(d);
    	System.out.println(q.peek());
    	q.enqueue(e);
    	q.enqueue(f);
     	System.out.println(q.dequeue());
    	System.out.println(q.dequeue());
        System.out.println(q.dequeue());
    	System.out.println(q.dequeue());
    	
    	q.enqueue(g);
    	q.enqueue(h);
    	q.enqueue(i);
    	
     	System.out.println(q.dequeue());
     	System.out.println(q.remove("Julian"));
    	System.out.println(q.dequeue());
        System.out.println(q.dequeue());
    	System.out.println(q.dequeue());
    	System.out.println(q.dequeue());
    }
}

