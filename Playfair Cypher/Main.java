/*
 * Zach Kuhns
 * Playfair Cypher
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static boolean validateInput(String line) {
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) < 'A' || line.charAt(i) > 'Z' && line.charAt(i) != ' ') {
				return false;
			}
		}
		
		return true;
	}
	
	public static String getMessage(BufferedReader input) throws IOException {
		String message;
		while (true) {
			System.out.print("Message: ");
			message = input.readLine();
			message = message.toUpperCase();
			if (validateInput(message)) {
				break;
			}
		}
		
		return message;
	}
	
	public static String getKey(BufferedReader input) throws IOException {
		String key;
		while (true) {
			System.out.print("Key: ");
			key = input.readLine();
			key = key.toUpperCase();
			if (validateInput(key)) {
				break;
			}
		}
		
		return key;
	}
	
	public static void main(String[] args) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		String message;
		String key;
		
		PlayfairCypher playfair = new PlayfairCypher();
		
		while (true) {
			try {
				message = getMessage(input);
				key = getKey(input);
				String encrypt = playfair.encrypt(message, key);
				System.out.println(encrypt);
				System.out.println(playfair.decrypt(encrypt, key));
			} catch (IOException e) {
				break;
			}
		}
		
		try {
			input.close();
		} catch (IOException e) {
			return;
		}
	}
}
