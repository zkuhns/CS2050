/*
 * Zach Kuhns
 * Playfair Cypher
 */

import java.util.ArrayList;

public class PlayfairCypher {
	/*
	 * This method returns an ArrayList with the alphabet
	 * assembled using the key value.  This alphabet replaces
	 * all j's with i's.
	 */
	private ArrayList<Character> buildAlphabet(String key) {
		ArrayList<Character> alphabet = new ArrayList<Character>();
		
		for (int i = 0; i < key.length(); i++) {
			if (!alphabet.contains(key.charAt(i))) {
				if (key.charAt(i) != 'J') {
					alphabet.add(key.charAt(i));
				}
			}
		}
		
		for (char i = 'A'; i < 'J'; i++) {
			if (!alphabet.contains(i)) {
				alphabet.add(i);
			}
		}
		
		for (char i = 'K'; i <= 'Z'; i++) {
			if (!alphabet.contains(i)) {
				alphabet.add(i);
			}
		}
		
		return alphabet;
	}
	
	/*
	 * This method returns a two dimensional character
	 * array the represents the cypher.  
	 */
	private char[][] buildCypher(ArrayList<Character> alphabet) {
		char[][] cypher = new char[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				cypher[i][j] = alphabet.get(i*5 + j);
			}
		}
		
		return cypher;
	}
	
	/*
	 * This method returns the column index of the character in the
	 * cypher.
	 */
	private int getCypherColumn(char character, ArrayList<Character> alphabet) {
		int alphabetIndex = alphabet.indexOf(character);
		return alphabetIndex % 5;
	}
	
	/*
	 * This method return the row index of the character in the
	 * cypher.
	 */
	private int getCypherRow(char character, ArrayList<Character> alphabet) {
		int alphabetIndex = alphabet.indexOf(character);
		return alphabetIndex / 5;
	}
	
	/*
	 * This method returns a two character string by swapping
	 * the column indexes of the two characters.
	 */
	private String encryptRectangle(int x1, int y1, int x2, int y2, char[][] cypher) {
		String token = "";
		token += cypher[y1][x2];
		token += cypher[y2][x1];
		return token;
	}
	
	/*
	 * This method returns a two character string by
	 * moving the indexes of the two characters down one.
	 */
	private String encryptRow(int x1, int y1, int x2, int y2, char[][] cypher) {
		String token = "";
		
		if (y1 == 4) {
			y1 = 0;
		} else {
			y1++;
		}
		
		if (y2 == 4) {
			y2 = 0;
		} else {
			y2++;
		}
		
		token += cypher[y1][x1];
		token += cypher[y2][x2];
		return token;
	}
	
	/*
	 * This method returns a two character string by
	 * moving the indexes of the two characters right one.
	 */
	private String encryptColumn(int x1, int y1, int x2, int y2, char[][] cypher) {
		String token = "";
		
		if (x1 == 4) {
			x1 = 0;
		} else {
			x1++;
		}
		
		if (x2 == 4) {
			x2 = 0;
		} else {
			x2++;
		}
		
		token += cypher[y1][x1];
		token += cypher[y2][x2];
		return token;
	}
	
	/*
	 * This method takes a pair of characters and encrypts them
	 * using the various playfair transformations.  
	 */
	private String encryptPair(char one, char two, ArrayList<Character> alphabet, char[][] cypher) {
		String pair = "";
		int x1, y1, x2, y2;
		
		x1 = getCypherColumn(one, alphabet);
		y1 = getCypherRow(one, alphabet);
		x2 = getCypherColumn(two, alphabet);
		y2 = getCypherRow(two, alphabet);
		
		if (x1 == x2) {
			pair += encryptRow(x1, y1, x2, y2, cypher);
		} else if (y1 == y2) {
			pair += encryptColumn(x1, y1, x2, y2, cypher);
		} else {
			pair += encryptRectangle(x1, y1, x2, y2, cypher);
		}
		
		return pair;
	}
	
	/*
	 * This method handles encrypting the pairs of characters
	 * using the playfair cypher algorithm.  
	 */
	private String encrypt(String message, ArrayList<Character> alphabet, char[][] cypher) {
		String encrypted = "";
		
		message = message.replace('J', 'I');
		
		for (int i = 0; i < message.length(); i++) {
			if (i == message.length()-1) {
				encrypted += encryptPair(message.charAt(i), 'X', alphabet, cypher);
			} else if (message.charAt(i) == message.charAt(i+1)) {
				encrypted += encryptPair(message.charAt(i), 'X', alphabet, cypher);
			} else {
				encrypted += encryptPair(message.charAt(i), message.charAt(i+1), alphabet, cypher);
				i++;
			}
		}
		
		return encrypted;
	}
	
	/*
	 * This method handles building the alphabet,
	 * the two dimensional character cypher, and
	 * encrypting the message using the key.
	 */
	public String encrypt(String message, String key) {
		ArrayList<Character> alphabet = buildAlphabet(key);
		System.out.println(alphabet.toString());
		
		char[][] cypher = buildCypher(alphabet);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(cypher[i][j]);
			}
			System.out.println();
		}
		
		return encrypt(message, alphabet, cypher);
	}
	
	/*
	 * This method returns a two character string by swapping
	 * the column indexes of the two characters.
	 */
	private String decryptRectangle(int x1, int y1, int x2, int y2, char[][] cypher) {
		String token = "";
		token += cypher[y1][x2];
		token += cypher[y2][x1];
		return token;
	}
	
	/*
	 * This method returns a two character string by
	 * moving the indexes of the two characters up one.
	 */
	private String decryptRow(int x1, int y1, int x2, int y2, char[][] cypher) {
		String token = "";
		
		if (y1 == 0) {
			y1 = 4;
		} else {
			y1--;
		}
		
		if (y2 == 0) {
			y2 = 4;
		} else {
			y2--;
		}
		
		token += cypher[y1][x1];
		token += cypher[y2][x2];
		return token;
	}
	
	/*
	 * This method returns a two character string by
	 * moving the indexes of the two characters left one.
	 */
	private String decryptColumn(int x1, int y1, int x2, int y2, char[][] cypher) {
		String token = "";
		
		if (x1 == 0) {
			x1 = 4;
		} else {
			x1--;
		}
		
		if (x2 == 0) {
			x2 = 4;
		} else {
			x2--;
		}
		
		token += cypher[y1][x1];
		token += cypher[y2][x2];
		return token;
	}
	
	/*
	 * This method takes a pair of characters and decrypts them
	 * using the various playfair transformations.  
	 */
	private String decryptPair(char one, char two, ArrayList<Character> alphabet, char[][] cypher) {
		String pair = "";
		int x1, y1, x2, y2;
		
		x1 = getCypherColumn(one, alphabet);
		y1 = getCypherRow(one, alphabet);
		x2 = getCypherColumn(two, alphabet);
		y2 = getCypherRow(two, alphabet);
		
		if (x1 == x2) {
			pair += decryptRow(x1, y1, x2, y2, cypher);
		} else if (y1 == y2) {
			pair += decryptColumn(x1, y1, x2, y2, cypher);
		} else {
			pair += decryptRectangle(x1, y1, x2, y2, cypher);
		}
		
		return pair;
	}
	
	/*
	 * This method handles decrypting the pairs of characters
	 * using the playfair cypher algorithm.  
	 */
	private String decrypt(String message, ArrayList<Character> alphabet, char[][] cypher) {
		String decrpyted = "";
		
		for (int i = 0; i < message.length(); i++) {
			if (i == message.length()-1) {
				decrpyted += decryptPair(message.charAt(i), 'X', alphabet, cypher);
			} else if (message.charAt(i) == message.charAt(i+1)) {
				decrpyted += decryptPair(message.charAt(i), 'X', alphabet, cypher);
			} else {
				decrpyted += decryptPair(message.charAt(i), message.charAt(i+1), alphabet, cypher);
				i++;
			}
		}
		
		return decrpyted;
	}
	
	/*
	 * This method handles building the alphabet,
	 * the two dimensional character cypher, and
	 * decrypting the message using the key.
	 */
	public String decrypt(String message, String key) {
		ArrayList<Character> alphabet = buildAlphabet(key);
		System.out.println(alphabet.toString());
		
		char[][] cypher = buildCypher(alphabet);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(cypher[i][j]);
			}
			System.out.println();
		}
		
		return decrypt(message, alphabet, cypher);
	}
}
