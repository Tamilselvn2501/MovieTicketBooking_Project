package TicketReservationProject;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class updation
{
	public Connection getConnection() throws SQLException
	{
		Driver D = new com.mysql.cj.jdbc.Driver();
		DriverManager.registerDriver(D);			
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmyshow", "root" ,"root");
		return con;
	}
	
	void insertAndUpdate() throws SQLException
	{
		Connection con = getConnection();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("SELECT THE OPERATION TO BE PERFORMED ");
		System.out.println();
		System.out.println("SELECT 1 FOR INSERTING NEW SHOWS , SELECT 2 FOR UPDATING SHOWS ");
		
		int userOpt = sc.nextInt();
		
		switch (userOpt)
		
		{
		case 1 :
		{
			System.out.println("ENTER THE NUMBER OF SHOWS TO BE ENTERED ");
			int numOfRows = sc.nextInt();
			
			while (numOfRows > 0)
			{
				PreparedStatement pst1 = con.prepareStatement("insert into bookmyshow (id,date,movie,time,capacity) values (?,?,?,?,?)");
				
				System.out.println("ENTER THE ID OF THE MOVIE");
				int id = sc.nextInt();
				System.out.println("ENTER THE DATE OF THE MOVIE date format (dd-mm-yyyy)");
				String date = sc.next();
				System.out.println("ENTER THE NAME OF THE MOVIE");
				String movie = sc.next();
				System.out.println("ENTER THE SHOW TIME OF THE MOVIE");
				String time = sc.next();
				System.out.println("ENTER THE CAPACITY OF THE SEATS");
				int capacity = sc.nextInt();
//				System.out.println("ENTER THE NUMBER OF BOOKED SEATS");
//				int booked = sc.nextInt();
			
				pst1.setInt(1, id);
				pst1.setString(2, date);
				pst1.setString(3, movie);
				pst1.setString(4, time); 
				pst1.setInt(5, capacity);
//				pst.setInt(6, booked);
				
				pst1.executeUpdate();
				
				PreparedStatement pst2 = con.prepareStatement("select * from bookmyshow where id = ?");
				pst2.setInt(1, id);
				ResultSet rs = pst2.executeQuery();
				
				while(rs.next())
				{
					System.out.println("NEW SHOW ADDED ");
					System.out.println();
					System.out.println("MOVIE NAME =  "+rs.getString(3)+" / AVAILABLE BOOKINGS  =  "+rs.getInt(5));
					System.out.println();
					System.out.println("DATE = "+rs.getString(2)+" / SHOW TIME = "+rs.getString(4));
					System.out.println("---------------------------------------------------------------------------------------");
				}
				numOfRows--;
			}			
		}
		
		break;
		
		case 2 :
		{
			System.out.println("UPDATING.........");
		}
		
		}

	}
}

public class DailyShows 
{
	public static void main(String[] args) throws SQLException 
	{
		updation U = new updation();
		U.insertAndUpdate();		
	}
}
