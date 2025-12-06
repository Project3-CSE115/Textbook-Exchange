package BookExchangeApp;

public class Student extends User{
	
	public Student(String username, String ID, String password, String email, String contact) {
		super(username, ID, email, password, contact);
	}
	
	@Override
	public void displayUserProfile() {
		System.out.println("*".repeat(20));
		System.out.println("/t/tUSER PROFILE");
		System.out.println("Name/t: "+ username +
							"/nStudent ID: "+ ID +
							"/nEmail: "+ email +
							"/nContact: "+ contact);
			
	}
}
