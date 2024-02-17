package TicketReservationProject;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import BankingApplications.Transactions;


public class UserInterface 
{
	public static void main(String[] args) throws SQLException, ParseException 
	{	
		Scanner sc = new Scanner(System.in);
		bookshows B = new bookshows();

	
		System.out.println("WELCOME TO LUXE CINEMAS");
		System.out.println();
		B.display();
		System.out.println();
		System.out.println("ENTER 1 FOR BOOKING , ENTER 2 FOR EXIT ");	
		System.out.println();
		
		int userOpt = sc.nextInt();
		
		switch (userOpt)
		{
		case 1 :
		{ 
//			B.date();
			B.booking();
		}
		break;
		case 2 :
		{
			System.out.println("THANK YOU FOR VISITING");
		}
		break;
		}
		
	}
}
 