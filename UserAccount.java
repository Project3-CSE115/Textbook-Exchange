package BookExchangeApp;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserAccount {
	private static final String FILE_NAME = "users_list.txt";
	private ArrayList<User> users = new ArrayList<>();
	private User thisUser = null;
	
	public UserAccount() {
		
	}
	
	public void register(Scanner input) throws NewException {
		System.out.println("*".repeat(20)+"\nFull Name: ");
		String fullName = input.nextLine();
		System.out.println("Student ID: ");
		String ID = input.nextLine();
		
		if(findUserID(ID) != null) {
			throw new NewException("Entered Student ID is already registered!");
		}
		
		System.out.println("Email: ");
		String email = input.nextLine();
		System.out.println("Contact: ");
		String contact = input.nextLine();
		System.out.println("Password: ");
		String password = input.nextLine();
		System.out.println("Confirm Password: ");
		String password2 = input.nextLine();
		
		if(!password.equals(password2)) {
			throw new NewException("The Passwords do not Match! Try again!");
		}
		
		System.out.println("Register as: ");
		System.out.println("(a) Buyer\t(b)Seller");
		
		int choice = Integer.parseInt(input.nextLine());
		
		User newUser = (choice)
	}
	
	 
	
}
