package BankingApplications;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

  
 public class Transactions 
 
{	

	public Connection getConnectionMethod() throws SQLException
	{
		Driver driver = new com.mysql.cj.jdbc.Driver();
		DriverManager.registerDriver(driver);			
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingapplication", "root" ,"root");
		return con;
	}
	
	public boolean withdrawl(double TOTALAMT) throws SQLException
	{
		boolean res=false;
		Connection con = getConnectionMethod();
		Scanner sc = new Scanner(System.in);
		
		
		
		System.out.println("Enter The Account NO For Payment = ");
		int accno = sc.nextInt();
		
		System.out.println("Enter the password = ");
		String password = sc.next();
		
		PreparedStatement pst = con.prepareStatement("select password , accbalance from bankingapplication where accno = ?");
		pst.setInt(1, accno);
		
		ResultSet rs = pst.executeQuery();	
		 
		while (rs.next())
		{
			String PASSWORD = rs.getString(1);
			double accbalance = rs.getInt(2);	
			
		
			if(accbalance>=TOTALAMT)
			{				
				if(password .equals(PASSWORD))
				{
					accbalance = accbalance-TOTALAMT;
					
					PreparedStatement pst2 = con.prepareStatement("update bankingapplication set accbalance = ? where accno= ? ");
					pst2.setDouble(1, accbalance);
					pst2.setInt(2, accno);					
					pst2.executeUpdate();
					System.out.println("Your Transaction of Rs "+TOTALAMT+" is done");
					res=true;
				 
				}
				
				else
				{
					System.out.println("Wrong password you have Entered , Enter the Correct Password = ");
					String pass2 = sc.next();
					if(pass2 .equals(PASSWORD))
					{
						accbalance = accbalance-TOTALAMT;
						
						PreparedStatement pst2 = con.prepareStatement("update bankingapplication set accbalance = ? where accno= ? ");
						pst2.setDouble(1, accbalance);
						pst2.setInt(2, accno);					
						pst2.executeUpdate();
						System.out.println("Your Transaction of Rs "+TOTALAMT+" is done");
						res=true;
					}
				}
			}
			else
			{
				System.out.println("The amount you entered is insufficient in this account");
//				break;
				res=false;			   
			}
		}
		return res;
	}
}

 
