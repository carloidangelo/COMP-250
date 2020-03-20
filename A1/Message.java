package assignment1;

public class Message {
	
	public String message;
	public int lengthOfMessage;

	public Message (String m){
		message = m;
		lengthOfMessage = m.length();
		this.makeValid();
	}
	
	public Message (String m, boolean b){
		message = m;
		lengthOfMessage = m.length();
	}
	
	/**
	 * makeValid modifies message to remove any character that is not a letter and turn Upper Case into Lower Case
	 */
	public void makeValid(){
		//INSERT YOUR CODE HERE
		int k = 0;
		String msgCopy = new String(message);
		for (int i = 0; i < msgCopy.length(); i++) {
	    	if (!Character.isLetter(msgCopy.charAt(i))){
	    		message = message.substring(0, k) + message.substring(k + 1);
	    		k--;
	    	}
	    	k++;
	    }
	    message = message.toLowerCase();
	    lengthOfMessage = message.length();
	}

	/**
	 * prints the string message
	 */
	public void print(){
		System.out.println(message);
	}
	
	/**
	 * tests if two Messages are equal
	 */
	public boolean equals(Message m){
		if (message.equals(m.message) && lengthOfMessage == m.lengthOfMessage){
			return true;
		}
		return false;
	}
	
	/**
	 * caesarCipher implements the Caesar cipher : it shifts all letter by the number 'key' given as a parameter.
	 * @param key
	 */
	public void caesarCipher(int key){
		// INSERT YOUR CODE HERE
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < message.length(); i++){
			char letter = message.charAt(i);
			key = key % 26;
			int index = alphabet.indexOf(letter) + key;
			if (index > 25){
				index = index - 26;				
			}
			if (index < 0){
				index = index + 26;
			}
			message = message.substring(0, i) + alphabet.charAt(index) + message.substring(i + 1);
		}
	}
	
	public void caesarDecipher(int key){
		this.caesarCipher(- key);
	}
	
	/**
	 * caesarAnalysis breaks the Caesar cipher
	 * you will implement the following algorithm :
	 * - compute how often each letter appear in the message
	 * - compute a shift (key) such that the letter that happens the most was originally an 'e'
	 * - decipher the message using the key you have just computed
	 */
	public void caesarAnalysis(){
		// INSERT YOUR CODE HERE
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		int count[] = new int[26];
		for (int i = 0; i < alphabet.length(); i++){
			int counter = 0;
			for (int j = 0; j < message.length(); j++){
				char msgLetter = message.charAt(j);
				char alphaLetter = alphabet.charAt(i);
				if (alphaLetter == msgLetter){
					counter++;
				}	
			}
			count[i] = counter;
		}
		int index = 0, max = 0;
		for (int k = 0; k < alphabet.length(); k++){
			if (count[k] > max){
				max = count[k];
				index = k;
			}		
		}
		int key = index - alphabet.indexOf('e');
		this.caesarDecipher(key);
	}
	
	/**
	 * vigenereCipher implements the Vigenere Cipher : it shifts all letter from message by the corresponding shift in the 'key'
	 * @param key
	 */
	public void vigenereCipher (int[] key){
		// INSERT YOUR CODE HERE
		int keyValues[] = new int[message.length()];
		int counter = 0;
		while (true){
			for (int i = 0; i < key.length; i++){
				if (counter == message.length()){
					break;
				}
				keyValues[counter] = key[i];
				counter++;
			}
			if (counter < message.length()){
				continue;
			}
			break;
		}
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		for (int j = 0; j < keyValues.length; j++){
			char letter = message.charAt(j);
			int key2 = keyValues[j] % 26;
			int index = alphabet.indexOf(letter) + key2;
			if (index > 25){
				index = index - 26;				
			}
			if (index < 0){
				index = index + 26;
			}
			message = message.substring(0, j) + alphabet.charAt(index) + message.substring(j + 1);
		}
	}

	/**
	 * vigenereDecipher deciphers the message given the 'key' according to the Vigenere Cipher
	 * @param key
	 */
	public void vigenereDecipher (int[] key){
		// INSERT YOUR CODE HERE
		int negativeKey[] = new int[key.length];
		for (int i = 0; i < key.length; i++){
			negativeKey[i] = key[i] * -1;
		}
		this.vigenereCipher(negativeKey);
	}
	
	/**
	 * transpositionCipher performs the transition cipher on the message by reorganizing the letters and eventually adding characters
	 * @param key
	 */
	public void transpositionCipher (int key){
		// INSERT YOUR CODE HERE
		int msgLength = message.length();
		int numberOfRows = (int)Math.ceil((double)msgLength/key);
		int numberOfColumns = key;
		char[][] array = new char[numberOfRows][numberOfColumns];
		int counter = 0;
		for (int i = 0; i < numberOfRows ; i++){
			for (int j = 0; j < numberOfColumns ; j++){
				if (counter >= msgLength){				
					array[i][j] = '*';
					counter++;
					continue;
				}
				array[i][j] = message.charAt(counter);
				counter++;
			}
		}
		counter = 0;
		char[] newMessage = new char[numberOfRows * numberOfColumns];
		for (int x = 0; x < numberOfColumns ; x++){
			for (int y = 0; y < numberOfRows ; y++){
				newMessage[counter] = array[y][x];
				counter++;
			}
		}
		String message2 = String.valueOf(newMessage);
		message = message2;
		lengthOfMessage = message.length();
	}
	
	/**
	 * transpositionDecipher deciphers the message given the 'key'  according to the transition cipher.
	 * @param key
	 */
	public void transpositionDecipher (int key){
		// INSERT YOUR CODE HERE
		int msgLength = message.length();
		int numberOfRows = msgLength/key;
		int numberOfColumns = key;
		int counter = 0;
		char[][] array = new char[numberOfRows][numberOfColumns];
		for (int i = 0; i < numberOfColumns ; i++){
			for (int j = 0; j < numberOfRows ; j++){
				array[j][i] = message.charAt(counter);
				counter++;
			}
		}
		counter = 0;
		char[] originalMessage = new char[numberOfRows * numberOfColumns];
		for (int x = 0; x < numberOfRows ; x++){
			for (int y = 0; y < numberOfColumns ; y++){
				originalMessage[counter] = array[x][y];	
				counter++;
			}
		}
		String message2 = String.valueOf(originalMessage);
		Message M1 = new Message(message2);
		message = M1.message;
		lengthOfMessage = message.length();
	}
	
}