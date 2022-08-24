package Training_Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Scanner;

public class bankmenu {
	private static final int NULL=0;
	
	static Connection con =DBConnection.getConnection();
	static String sql="";
	//First option-Create Account
	public static boolean createAcc(String uname, String city, float balance, String acc_type,String pass) {
		try {
			if(uname=="" || pass=="" || city=="" || balance==NULL || acc_type=="") {
				System.out.println("All fields required");
				return false;
			}
			Statement st=con.createStatement();
			sql="INSERT INTO customer values(bankseq.nextval,'"+uname+"','"+city+"','"+pass+"','"+balance+"','"+acc_type+"')";
			
			if(st.executeUpdate(sql)==1) {
				System.out.println("**************************************");
				
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery("select acc_no from customer where name='"+uname+"' and pass='"+pass+"'");
				while(rs.next()) {
					System.out.println("Your account number is: "+rs.getInt("acc_no"));
				}
				return true;
			}
		}
		catch(SQLIntegrityConstraintViolationException e) {
			System.out.println("Username not Available!");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean login(String uname,String pass) {
		try {
			if(uname=="" || pass=="") {
				System.out.println("All fields required!");
				return false;
			}
			PreparedStatement st=con.prepareStatement("select * from customer where name='"+uname+"' and pass='"+pass+"'");
			ResultSet rs=st.executeQuery();
			Scanner sc=new Scanner(System.in);
			int ch=0;
			if(rs.next()) {
				System.out.println("Welcome, "+uname);
				int acc_no=rs.getInt("acc_no");
				float amt=0;
				while(true) {
					try {
						System.out.println("1. Check Balance");
						System.out.println("2. Withraw Money");
						System.out.println("3. Deposit Money");
						System.out.println("4. Logout");
						System.out.println("Please select an option");
						ch=sc.nextInt();
						switch(ch) {
						case 1:
							bankmenu.getBalance(acc_no);
							break;
						case 2:
							System.out.println("Enter the amount to withraw: ");
							amt=sc.nextFloat();
							bankmenu.withdraw(acc_no,amt);
							break;
						case 3:
							System.out.println("Enter the amount to deposit: ");
							amt=sc.nextFloat();
							bankmenu.deposit(acc_no,amt);
							break;
						case 4:
							break;
						default:
							System.out.println("Invalid option");
						}
						
						return true;
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
			else {
				return false;
			}
			
		}
		catch(SQLIntegrityConstraintViolationException e) {
			System.out.println("Username not available");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void getBalance(int acc_no) {
		try {
			sql="select * from customer where acc_no="+acc_no;
			PreparedStatement st=con.prepareStatement(sql);
			ResultSet rs=st.executeQuery(sql);
			System.out.println("**************************************");
	        System.out.printf("%12s %10s %10s\n","Account No", "Name","Balance");
	        while(rs.next()) {
	        	System.out.printf("%12d %10s %10d.00\n",rs.getInt("acc_no"),rs.getString("name"),rs.getInt("balance"));
	        }
	        System.out.println("**************************************\n");  
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean withdraw(int acc_no, float amount) throws SQLException {
		if(acc_no==NULL || amount ==NULL) {
			System.out.println("All fields required");
			return false;
		}
		try {
			sql="select * from customer where acc_no="+acc_no;
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt("balance")<amount) {
					System.out.println("Insufficient Balance!");
					return false;
				}
			}
			Statement st=con.createStatement();
			sql = "update customer set balance=balance-"+ amount + " where acc_no=" + acc_no;
	            if (st.executeUpdate(sql) == 1) {
	                System.out.println("Amount Debited!");
	                Statement stmt=con.createStatement();
					ResultSet rs1=stmt.executeQuery("select balance from customer where acc_no='"+acc_no+"'");
					while(rs1.next()) {
						System.out.println("Your updated balance is: "+rs1.getInt("balance"));
					}
	            }
	            return true;
			}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean deposit(int acc_no, float amount) throws SQLException {
		if(acc_no==NULL || amount ==NULL) {
			System.out.println("All fields required");
			return false;
		}
		try {
			Statement st=con.createStatement();
			sql = "update customer set balance=balance+"+ amount + " where acc_no=" + acc_no;
	            if (st.executeUpdate(sql) == 1) {
	                System.out.println("Amount Credited!");
	                Statement stmt=con.createStatement();
					ResultSet rs1=stmt.executeQuery("select balance from customer where acc_no='"+acc_no+"'");
					while(rs1.next()) {
						System.out.println("Your updated balance is: "+rs1.getInt("balance"));
					}
	            }
	            return true;
			}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
