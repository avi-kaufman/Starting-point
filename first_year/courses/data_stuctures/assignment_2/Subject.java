public class Subject {
	int age;
	String name;
	
	/**
	 * A standard constructor for the customer class
	 * 
	 * @param age
	 * @param name
	 */
	
	public Subject(int age, String name){
		this.name = name;
		this.age = age;
	}

	/**
	 * Compares this subject to another subject.
	 * This subject is considered smaller than the other subject if and only if
	 * the age of this subject is smaller than the other customer or, if the ages are equal 
	 * then the name of this subject is larger in the lexicorapgic ordering than the name of the other subject.
	 * 
	 * If this subject is smaller returns a negative number. If this subject is bigger return a positive number.
	 * If the subjects are equal return 0.
	 * 
	 * 
	 * @param other
	 * @return a negative/positive or zero number of this customer is smaller/greater or equal to other
	 */
	public int compareTo(Subject other) {
		if (this.age < other.age) return -1;
		if (this.age > other.age) return 1;
		if (this.age == other.age) return lexicorapgicCompering(this.name, other.name);
		return 0;
	}

	public int lexicorapgicCompering(String name1, String name2){
		for (int i = 0; i < name1.length() && i < name2.length(); i++) {
			if (name1.charAt(i) > name2.charAt(i)) return -1;
			if (name1.charAt(i) < name2.charAt(i)) return 1;
		}
		if (name1.length() > name2.length()) return -1;
		if (name1.length() < name2.length()) return 1;
		return 0;
	}

	
	/**
	 * Returns a string representation of this Subject.
	 * The string should be in the format: <name>, age: <age>.
	 * For example, 'Yoni, age: 32'
	 * 
	 * @return A string representation of this Subject.
	 */
	public String toString(){
		 return (this.name + ", age: " + this.age);
	}
}


