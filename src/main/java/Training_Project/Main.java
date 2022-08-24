package Training_Project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Connection con =DBConnection.getConnection();
		Scanner sc=new Scanner(System.in);
		String name="";
		String city="";
		String acc_type="";
		float balance;
		String pass="";
		int choice;
		while(true) {
			System.out.println("**********WELCOME TO DOXBANK**********");
			System.out.println("1. Create a new Account");
			System.out.println("2. Login");
			try {
				System.out.println("Please select an option");
				choice=sc.nextInt();
				sc.nextLine();
				switch(choice) {
				case 1:
					try {
						System.out.println("Enter your name: ");
						name=sc.nextLine();
						System.out.println("Enter your city: ");
						city=sc.nextLine();
						System.out.println("Enter the account type(Salary/Saving/Current): ");
						acc_type=sc.nextLine();
						System.out.println("Enter opening balance: ");
						balance=sc.nextFloat();
						sc.nextLine();
						System.out.println("Enter your password: ");
						pass=sc.nextLine();
						if(bankmenu.createAcc(name, city, balance, acc_type, pass)) {
							System.out.println("Account created successfully!");
						}
						else {
							System.out.println("Account couldn't be created");
						}
					}
					catch(Exception e) {
						e.printStackTrace();
						System.out.println("Enter valid data");
					}
					break;
				case 2:
					try {
						System.out.println("Enter username: ");
						name=sc.nextLine();
						System.out.println("Enter password: ");
						pass=sc.nextLine();
						if(bankmenu.login(name, pass)) {
							System.out.println("Exited Successfully");
						}
						else {
							System.out.println("Couldn't login. Please try again with correct credentials.");
						}
					}
					catch(Exception e) {
						System.out.println("Couldn't login. Please try again with correct credentials.");
					}
					break;
				default:
					System.out.println("Choice Invalid");
				}
				
			}
			catch(Exception e) {
				System.out.println("Enter valid data");
			}
		}
		
	
	}

}
