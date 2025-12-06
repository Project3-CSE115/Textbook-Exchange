package BookExchangeApp;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		UserAccount account = new UserAccount();
		
		while(true) {
			if(account.getThisUser() == null) {
				System.out.println("*".repeat(30)+"\t\t\tNSU BOOK EXCHANGE PLATFORM\n"+"*".repeat(30));
				System.out.println("1. Register");
				System.out.println("2. Login");
				System.out.println("3. Exit");
				System.out.println("Choose option (1-3): ");
				
				int choice = 0;
				try {
					choice = Integer.parseInt(input.nextLine());
				} catch (Exception e) {
					System.out.println("Invalid input!");
					continue;
				}
				
				try {
					switch(choice) {
					case 1: 
						account.register(input);
						break;
					case 2: 
						account.login(input);
						break;
					case 3:
						System.out.println("Thank you for using NSU Book Exchange!");
						input.close();
						break;
					default:
						System.out.println("Invalid Selection!");
					}
				} catch(NewException e) {
					System.out.println(e.getMessage());
				}
			}
			else {
				System.out.println("*".repeat(20)+"WELCOME, "+account.getThisUser().getName()+"*".repeat(20));
				System.out.println("1. View Profile");
				System.out.println("2. Edit Profile");
				System.out.println("3. Logout");
				System.out.println(" Choose option(1-3): ");
				
				int choice2 = Integer.parseInt(input.nextLine());
				
				switch(choice2) {
				case 1:
					account.viewUserProfile();
					break;
				case 2:
					account.editUserProfile();
					break;
				case 3:
					account.logout();
					break;
				default:
					System.out.println("Invalid Selection!");
				}
			}
		}
	}

}
