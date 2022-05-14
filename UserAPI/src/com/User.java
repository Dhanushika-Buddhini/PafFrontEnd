package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class User {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, UserFirstName, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PAFGROUPPROJECT","root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertUser(String UserFirstName, String UserLastName, String UserAddress, String UserAccountNo, String UserContactNo,String UserEmail, String UserNIC)  
	{   
		String output = ""; 	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement 
			String query = " insert into user(`UID`,`UserFirstName`,`UserLastName`,`UserAddress`,`UserAccountNo`,`UserContactNo`,`UserEmail`,`UserNIC`)" + " values (?, ?, ?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, UserFirstName);
			 preparedStmt.setString(3, UserLastName);
			 preparedStmt.setString(4, UserAddress);
			 preparedStmt.setString(5, UserAccountNo);
			 preparedStmt.setString(6, UserContactNo);
			 preparedStmt.setString(7, UserEmail);
			 preparedStmt.setString(8, UserNIC);
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newUser = readUser(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the User.\"}";  
			System.err.println(e.getMessage());   
		} 		
	  return output;  
	} 	
	
	public String readUser()  
	{   
		String output = ""; 
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border=\'1\'><tr><th>User First Name</th><th>User Last Name</th><th>User Address</th><th>User Account No</th><th>User Contact No</th><th>User Email</th><th>User NIC</th><th>Update</th><th>Remove</th></tr>";
	  
			String query = "select * from user";    
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				 String UID = Integer.toString(rs.getInt("UID"));
				 String UserFirstName = rs.getString("UserFirstName");
				 String UserLastName = rs.getString("UserLastName");
				 String UserAddress = rs.getString("UserAddress");
				 String UserAccountNo = rs.getString("UserAccountNo");
				 String UserContactNo = rs.getString("UserContactNo");
				 String UserEmail = rs.getString("UserEmail");
				 String UserNIC = rs.getString("UserNIC");
				 
				 
				// Add into the html table 
				output += "<tr><td><input id=\'hidUserIDUpdate\' name=\'hidUserIDUpdate\' type=\'hidden\' value=\'" + UID + "'>" 
							+ UserFirstName + "</td>"; 
				output += "<td>" + UserLastName + "</td>";
				output += "<td>" + UserAddress + "</td>";
				output += "<td>" + UserAccountNo + "</td>";
				output += "<td>" + UserContactNo + "</td>";
				output += "<td>" + UserEmail + "</td>";
				output += "<td>" + UserNIC + "</td>";
				
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-userid='" + UID + "'>" + "</td></tr>"; 
			
			}
			con.close(); 
	   
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the User.";    
			System.err.println(e.getMessage());   
		} 	 
		return output;  
	}
	
	public String updateUser(String UID, String UserFirstName, String UserLastName, String UserAddress, String UserAccountNo, String UserContactNo, String UserEmail, String UserNIC)  
	{   
		String output = "";  
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE user SET UserFirstName=?,UserLastName=?,UserAddress=?,UserAccountNo=?,UserContactNo=?,UserEmail=?,UserNIC=?"  + "WHERE UID=?";  	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, UserFirstName);
			 preparedStmt.setString(2, UserLastName);
			 preparedStmt.setString(3, UserAddress);
			 preparedStmt.setString(4, UserAccountNo);
			 preparedStmt.setString(5, UserContactNo);
			 preparedStmt.setString(6, UserEmail);
			 preparedStmt.setString(7, UserNIC);
			 
			 preparedStmt.setInt(8, Integer.parseInt(UID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close();  
			String newUser = readUser();    
			output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the User.\"}";   
			System.err.println(e.getMessage());   
		} 	 
	  return output;  
	} 
	
	public String deleteUser(String UID)   
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{
				return "Error while connecting to the database for deleting."; 			
			} 
	 
			// create a prepared statement    
			String query = "delete from user where UID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(UID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newUser = readUser();    
			output = "{\"status\":\"success\", \"data\": \"" +  newUser + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the User.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
}
