/**
 * Represents a number as a doubly-linked list where each node contains a digit.
 * This allows for representation and manipulation of large numbers beyond standard data type limits.
 */
public class LinkedNumber {
    private int base;
    private DLNode<Digit> front;
    private DLNode<Digit> rear;

    /**
     * Constructs a LinkedNumber from a string representation.
     *
     * @param num    The string representation of the number.
     * @param baseNum The base of the number system for this number.
     * @throws LinkedNumberException if the input string is null or empty.
     */
    public LinkedNumber(String num, int baseNum) {
    	// Initial validation of input
        if (num == null || num.isEmpty()) {
            throw new LinkedNumberException("no digits given");
        }
        this.base = baseNum;
     // Populate the linked list with digits from the string
        for (int i = 0; i < num.length(); i++) {
            char ch = num.charAt(i);
            Digit digit = new Digit(ch); // Assume the Digit constructor that accepts a char
            DLNode<Digit> newNode = new DLNode<>(digit);
            if (front == null) {
                front = rear = newNode;
            } else {
                rear.setNext(newNode);
                newNode.setPrev(rear);
                rear = newNode;
            }
        }
    }

    /**
     * Constructs a LinkedNumber from an integer.
     *
     * @param num The integer to convert into a LinkedNumber.
     */
    public LinkedNumber(int num) {
        // Convert num to String
        String numStr = String.valueOf(num);
        this.base = 10; // Default base for integer input
        if (numStr.isEmpty()) {
            throw new LinkedNumberException("no digits given");
        }
        for (int i = 0; i < numStr.length(); i++) {
            char ch = numStr.charAt(i);
            Digit digit = new Digit(ch);
            DLNode<Digit> newNode = new DLNode<>(digit);
            if (front == null) {
                front = rear = newNode;
            } else {
                rear.setNext(newNode);
                newNode.setPrev(rear);
                rear = newNode;
            }
        }
    }
    
    /**
     * Checks if the number represented by the linked list is valid for its base.
     *
     * @return true if the number is valid for its base, false otherwise.
     */
    public boolean isValidNumber() {
        DLNode<Digit> current = front;
        while (current != null) {
            // Use the getValue method from the Digit class to get the numeric value of the digit
            int value = current.getElement().getValue();
            if (value < 0 || value >= base) {
                return false; // Digit is not valid for the base
            }
            current = current.getNext(); // Move to the next node
        }
        return true; // All digits are valid
    }
    
 // Getter methods for base, front, and rear
    public int getBase() {
    	return base;
    }
    
    public DLNode<Digit> getFront(){
    	return front;
    }
    
    public DLNode<Digit> getRear(){
    	return rear;
    }
    
    /**
     * Returns the number of digits in the LinkedNumber.
     *
     * @return The count of digits.
     */
    public int getNumDigits() {
        int count = 0; // Initialize count to 0
        DLNode<Digit> current = front; // Start with the front node
        while (current != null) { // Iterate through the list
            count++; // Increment count for each node
            current = current.getNext(); // Move to the next node
        }
        return count; // Return the total count of nodes
    }
    
    /**
     * Returns a string representation of the LinkedNumber.
     *
     * @return The number as a string.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Create a StringBuilder to accumulate the digits
        DLNode<Digit> current = front; // Start with the front node
        while (current != null) { // Iterate through the list
            sb.append(current.getElement().toString()); // Append the digit's string representation
            current = current.getNext(); // Move to the next node
        }
        return sb.toString(); // Convert the StringBuilder to a String and return it
    }
    
    /**
     * Compares this LinkedNumber with another object for equality.
     *
     * @param obj The object to compare with.
     * @return true if the given object represents the same number, false otherwise.
     */
    public boolean equals(Object obj) {
        // Check if the object is compared with itself
        if (obj == this) {
            return true;
        }

        // Check if the object is an instance of LinkedNumber
        if (!(obj instanceof LinkedNumber)) {
            return false;
        }
        
        // Typecast obj to LinkedNumber so that we can compare data members
        LinkedNumber other = (LinkedNumber) obj;

        // Check if the bases are different
        if (this.base != other.base) {
            return false;
        }

        // Check nodes one by one
        DLNode<Digit> currentThis = this.front;
        DLNode<Digit> currentOther = other.front;
        while (currentThis != null && currentOther != null) {
            if (!currentThis.getElement().equals(currentOther.getElement())) {
                return false; // Digits are different
            }
            // Move to the next node in each list
            currentThis = currentThis.getNext();
            currentOther = currentOther.getNext();
        }

        // If either of the lists has more elements left, they are not equal
        return currentThis == null && currentOther == null;
    }
    
    /**
     * Converts this LinkedNumber to a new base and returns the result as a new LinkedNumber.
     *
     * @param newBase The base to convert to.
     * @return A new LinkedNumber in the specified base.
     * @throws LinkedNumberException if the number is invalid for its current base.
     */
    public LinkedNumber convert(int newBase) {
        if (!isValidNumber()) {
            throw new LinkedNumberException("cannot convert invalid number");
        }

        // Convert to decimal first
        int decimalValue = 0;
        DLNode<Digit> current = front;
        int digitCount = getNumDigits(); // Assuming this method correctly counts the digits
        int position = digitCount - 1; // Start with the most significant digit
        while (current != null) {
            int digitValue = current.getElement().getValue();
            decimalValue += digitValue * Math.pow(this.base, position);
            position--;
            current = current.getNext();
        }

        // Convert from decimal to newBase
        String newBaseNumberStr = convertDecimalToBase(decimalValue, newBase);

        // Return new LinkedNumber
        return new LinkedNumber(newBaseNumberStr, newBase);
    }


    // Helper method to convert a decimal number to a string representation in a given base
    private String convertDecimalToBase(int number, int base) {
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            int remainder = number % base;
            sb.append(remainder < 10 ? (char) ('0' + remainder) : (char) ('A' + (remainder - 10)));
            number /= base;
        }
        return sb.reverse().toString(); // Reverse the string to get the correct order
    }
    
    /**
     * Adds a digit to the LinkedNumber at a specified position.
     *
     * @param digit The digit to add.
     * @param position The position at which to add the digit.
     * @throws LinkedNumberException if the position is invalid.
     */
    public void addDigit(Digit digit, int position) {
    	int size = getNumDigits();
    	
    	if ( position < 0 || position > size){
    		throw new LinkedNumberException("Invalid position");
    	}
    	DLNode<Digit> newNode = new DLNode<>(digit);
    	
    	if (position == 0) {
    		if (rear!= null) {
    			newNode.setPrev(rear);
    			rear.setNext(newNode);
    		} else { 
    			front = newNode;
    		}
    		rear = newNode;
    	} else if (position == size) {
    		newNode.setNext(front);
    		if (front != null) {
    			front.setPrev(newNode);
    		}
    		front = newNode;
    	} else {
    		DLNode<Digit> current = front;
    		int steps = size - position;
    		while (steps > 0 && current.getNext() != null) {
    			current = current.getNext();
    			steps--;
    		}
    		if (current.getPrev() != null) {
    			current.getPrev().setNext(newNode);
    			newNode.setPrev(current.getPrev());
    		}
    		newNode.setNext(current);
    		current.setPrev(newNode);
    	}
    }
    
    /**
     * Removes a digit from the LinkedNumber at a specified position and returns its value.
     *
     * @param position The position of the digit to remove.
     * @return The value of the removed digit.
     * @throws LinkedNumberException if the position is invalid.
     */
    public int removeDigit(int position) {
        int numDigits = getNumDigits();
        if (position < 0 || position >= numDigits) {
            throw new LinkedNumberException("invalid position");
        }

        // Adjust the position for reverse traversal (if needed)
        int adjustedPosition = numDigits - position - 1;
        DLNode<Digit> current = front;
        for (int i = 0; i < adjustedPosition; i++) {
            current = current.getNext();
        }

        // Calculate the value represented by this digit
        int value = current.getElement().getValue() * (int)Math.pow(base, position);
        
        // Remove the node
        if (current.getPrev() != null) {
            current.getPrev().setNext(current.getNext());
        } else { // Removing the front node
            front = current.getNext();
        }
        if (current.getNext() != null) {
            current.getNext().setPrev(current.getPrev());
        } else { // Removing the rear node
            rear = current.getPrev();
        }

        return value;
    }   
}