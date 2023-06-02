/**
 * A heap, implemented as an array.
 * The elements in the heap are instances of the class Subject,
 * and the heap is ordered according to the Subject ages and names.
 * 
 *
 */
public class Heap {
	
	/*
	 * The array in which the elements are kept according to the heap order.
	 * The following must always hold true:
	 * 			if i < size then heap[i].heapIndex == i
	 */
	Subject[] subjects; //The array in which the elements are kept according to the heap order.
	int size; // the number of elements in the heap, necessarily size <= heap.length
	
	/**
	 * Creates an empty heap with the given capacity (this is the initial size of the array, which may change later on)
	 */
	public Heap(int capacity){
		subjects = new Subject[capacity + 1];
		size = 0;
	}
	
	/**
	 * Constructs a heap from a given arbitrary array of Subjects.
	 * This should be done according to the "buildheap" function studied in class.
	 * You may NOT use the insert function of heap.
	 * 
	 * NOTE: for this function you may use a loop which runs over the array once.
	 * 
	 */
	public Heap(Subject[] arr) {
		subjects = new Subject[arr.length +1];
		size = subjects.length - 1;
		// copy the array elemnts from the given array to the subjects array
		for (int i = 0; i < arr.length; i++) {
			subjects[i + 1] = arr[i];
		}
		for (int j = size / 2; j > 0; j--) {
			percDown(j, subjects[j], size, subjects);
		}
	}
	private void percDown(int index, Subject sub, int sizeOfTheHeap , Subject[] arr){
		//if index is a leaf
		if (2 * index > sizeOfTheHeap){
			arr[index] = sub;
		}
		//if index hes a single child
		if (2 * index == sizeOfTheHeap){
			if (arr[2 * index].compareTo(sub) > 0){
				arr[index] = arr[2 * index];
				arr[2 * index] = sub;
			}
			else {
				arr[index] = sub;
			}
		}
		//if index hes two child
		if (2 * index < sizeOfTheHeap){
			int j = 0;
			if (arr[2 * index].compareTo(arr[2 * index + 1]) > 0){
				j = 2 * index;
			}
			else {
				j = 2 * index + 1;
			}
			if ((arr[j].compareTo(sub)) > 0){
				arr [index] = arr[j];
				percDown(j,sub,size,subjects);
			}
			else {
				arr [index] = sub;
			}
		}
	}
	
    /**
     * Returns the size of the heap.
     *
     * @return the size of the heap
     */
    public int size(){
			return size;
    }
    
    /**
     * Inserts a given element into the heap.
     * 
     * If at any point the array becomes full as a result of inserting too many subjects, 
     * then the size of the array should be doubled to handle extra subjects.
     * NOTE: you may use a loop to iterate through the entire array
     * for the purpose of resizing it and for this purpose only.
     * 
     * @param e - the element to be inserted.
     */
    public void insert(Subject e){
        	if (size == subjects.length - 1){
        		Subject[] temp = new Subject[2 * size + 1];
				for (int i = 1; i <= size ; i++) {
					temp[i] = subjects[i];
				}
				subjects = temp;
			}
		subjects[size + 1] = e;
		size++;
		percUp(size , e, subjects);
    }

    private void percUp(int index , Subject sub, Subject[] arr) {
    	int parent = index / 2;
    	if (index == 1){
    		arr[index] = sub;
		}
    	else {
    		if(arr[parent].compareTo(sub) > 0){
    			arr[index] = sub;
			}else {
    			arr[index] = arr[parent];
    			percUp(parent, sub, subjects);
			}
		}
	}
    

	
	/**
	 * Returns and does not remove the subject next in line to receive the first dose of the vaccine.
	 * 
	 * @return the subject next in line to receive the first dose of the vaccine.
	 */
    public Subject findMax(){
			return subjects[1];
    }
    
	/**
	 * Returns and removes the the subject next in line to receive the first dose of the vaccine.
	 * Should return null if the heap is empty.
	 * 
	 * @return the subject next in line to receive the first dose of the vaccine.
	 */
    public Subject extractMax() {
		if (this.size == 0) {
			return null;
		}
		Subject max = subjects[1];
		subjects[1] = subjects[size];
		size--;
		percDown(1,subjects[1],size,subjects);
		return max;
    }
    
    
  
    
    public static void main (String[] args){
    	/*
    	 * A basic test for the heap.
    	 * You should be able to run this before implementing the queue.
    	 * 
    	 * Expected outcome: 
    	 * 	Umberto, age: 41
		 *	Leto, age: 63
		 *	Leto, age: 63
		 *	Umberto, age: 41
		 *	Jon, age: 27
		 *	Corwin, age: 16
    	 * 
    	 */
    	Heap heap = new Heap(2);
    	Subject a = new Subject(41, "Umberto");
    	Subject b = new Subject(27, "Jon");
    	Subject c = new Subject(63, "Leto");
    	Subject d = new Subject(16, "Corwin");
    	
    	heap.insert(a);
    	System.out.println(heap.findMax());
    	
    	heap.insert(b);
    	heap.insert(c);
    	heap.insert(d);
    	System.out.println(heap.findMax());
     	System.out.println(heap.extractMax());
    	System.out.println(heap.extractMax());
        System.out.println(heap.extractMax());
    	System.out.println(heap.extractMax());
    }
}
