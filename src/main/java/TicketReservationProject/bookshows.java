package TicketReservationProject;


import java.sql.Connection;

import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import BankingApplications.Transactions;


public class bookshows 
{
	public Connection getConnection() throws SQLException
	{
		Driver driver = new com.mysql.cj.jdbc.Driver();
		DriverManager.registerDriver(driver);			
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmyshow", "root" ,"root");
		return con;
	}
	
//	public Connection getConnection2() throws SQLException
//	{
//		Driver driver = new com.mysql.cj.jdbc.Driver();
//		DriverManager.registerDriver(driver);			
//		Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingapplication", "root" ,"root");
//		return con2;
//	}
	
	void date()
	{
		   DateTimeFormatter d = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
		   LocalDateTime D = LocalDateTime.now();  
		   System.out.println(d.format(D)); 
		   
		   DateTimeFormatter t = DateTimeFormatter.ofPattern("HH:mm");  
		   LocalDateTime T = LocalDateTime.now();  
		   System.out.println(t.format(T)); 
	}
	
	
	  
	void display() throws SQLException 
	{
		Connection con = getConnection();
		
		PreparedStatement pst = con.prepareStatement("select * from bookmyshow");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
		{			
//			   DateTimeFormatter d = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
//			   LocalDateTime D = LocalDateTime.now();  
//			   
//			   DateTimeFormatter t = DateTimeFormatter.ofPattern("HH:mm");  
//			   LocalDateTime T = LocalDateTime.now();		
			 			   
//			   if( rs.getString(2) <= D && rs.getString(4) < T )
////			   {
				   System.out.println("SHOW ID ="+rs.getInt(1));
				   System.out.println("DATE = "+rs.getString(2)+" / "+" MOVIE NAME =  "+rs.getString(3)+ " / "+" SHOW TIME = "+rs.getString(4)+" / " + " AVAILABLE BOOKINGS  =  "+rs.getInt(5));
				   System.out.println("---------------------------------------------------------------------------------------"); 
//			   }
		}
	} 
	
	//COLLECTION OF DATA FROM THE USER AND BOOKING PROCESSING
	void booking() throws SQLException, ParseException
	{
		Scanner sc = new Scanner(System.in);
		Connection con = getConnection();
//		Connection con2 = getConnection2();
		
		
		
		System.out.println("ENTER THE SHOW ID =");
		int ID = sc.nextInt();
		
		
		System.out.println("ENTER THE NUMBER OF TICKETS TO BE BOOKED =");
		int BOOKED = sc.nextInt();
		
		//3  SELECTING AVAILABLE SEATS CAPACITY 
		PreparedStatement pst3 = con.prepareStatement("select capacity from bookmyshow where id =? ");
		pst3.setInt(1, ID);
		ResultSet rs1 = pst3.executeQuery();
		while(rs1.next())
		{
			// 4 ENSURING EXISTING AVAILABLE SEATS >= USERS REQUESTING NUMBER OF SEATS AND PROCESSING FURTHER
			if(rs1.getInt(1)>=BOOKED)
			{
								
				// PAYMENT TRANSACTIONS
				System.out.println();
				
				double price = 120.00;
				double total = price*BOOKED;
				
				System.out.println("COST OF PER TICKET = "+price);
				System.out.println("YOUR TOTAL COST OF "+BOOKED+" TICKETS IS = "+total);
				double gst = 0.18*total;
				System.out.println("GST PRICE 18% OF "+total+" = "+gst);
				double TOTALAMT = gst+total;
				System.out.println("TOTAL AMOUNT TO BE PAID = "+TOTALAMT);
								
				System.out.println();				
				
				// TRANSACTIONS CLASS OBJECT 
				
				Transactions T = new Transactions ();

				boolean RS = T.withdrawl(TOTALAMT);
				
				if(RS == true) 
				{
					System.out.println();
					System.out.println("YOUR BOOKINGS ARE CONFIRMED , ENJOY OUR SHOW");				
					
					//5 ADDING THE NEW NUMBER OF BOOKING TO OLD OLD NUMBER OF BOOKING
					PreparedStatement pst4 = con.prepareStatement("select booked from bookmyshow where id =? ");
					pst4.setInt(1, ID);
					ResultSet rs3 = pst4.executeQuery();
					
					
					while(rs3.next())
					{ 
						// 1 UPDATING NUMBER OF BOOKING TICKETS IN DATABASE
					
						int oldCapacity = rs3.getInt(1)+BOOKED;							
						PreparedStatement pst1 = con.prepareStatement("update bookmyshow set booked = ? where id =?");
						pst1.setInt(1, oldCapacity);
						pst1.setInt(2, ID);
						pst1.executeUpdate();
						System.out.println();
					}
	
			           // 2 UPDATING REMAINING AVAILABLE SEATS IN DATABASE
					
					int newCapacity =  rs1.getInt(1)- BOOKED;
					PreparedStatement pst2 = con.prepareStatement("update bookmyshow set capacity = ? where id = ?");
					pst2.setInt(1,newCapacity);
					pst2.setInt(2, ID);
					pst2.executeUpdate();
				}
			}
			
			else
			{
				int availableSeat = rs1.getInt(1);
				if(availableSeat == 0)
				{ 
					System.out.println("SORRY FOR THE INCONVIENCE , BOOKINGS ARE FULLED");
				}
				
				else
				{
					System.out.println("SORRY FOR THE INCONVIENCE ONLY "+availableSeat+ " BOOKINGS ARE AVAILABLE");
				}
			}			
		}

}
}
