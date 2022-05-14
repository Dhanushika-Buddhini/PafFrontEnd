package com;

import com.User;
import java.io.IOException; 
import java.util.HashMap; 
import java.util.Map; 
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UserService")
public class UserService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	User UserObj = new User();

    public UserService() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String output = UserObj.insertUser(
				request.getParameter("UserFirstName"),      
				request.getParameter("UserLastName"),
				request.getParameter("UserAddress"),
				request.getParameter("UserAccountNo"),
				request.getParameter("UserContactNo"),
				request.getParameter("UserEmail"),
				request.getParameter("UserNIC")); 
				response.getWriter().write(output);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method

		Map paras = getParasMap(request); 
		 
		 String output = UserObj.updateUser(
				    paras.get("hidUserIDSave").toString(),     
		    		paras.get("UserFirstName").toString(),     
		    		paras.get("UserLastName").toString(),
		    		paras.get("UserAddress").toString(),
		    		paras.get("UserAccountNo").toString(),
		    		paras.get("UserContactNo").toString(),
		    		paras.get("UserEmail").toString(),
		    		paras.get("UserNIC").toString()); 		 
		 			response.getWriter().write(output);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 Map paras = getParasMap(request); 		 
		 String output = UserObj.deleteUser(paras.get("UID").toString());  		 
		 response.getWriter().write(output);
	}
	
	// Convert request parameters to a Map
		private static Map getParasMap(HttpServletRequest request)
		{
		 Map<String, String> map = new HashMap<String, String>();
		try
		 { 
		 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
		 String queryString = scanner.hasNext() ?
		 scanner.useDelimiter("\\A").next() : "";
		 scanner.close();
		 String[] params = queryString.split("&");
		 for (String param : params)
		 { 
		
		String[] p = param.split("=");
		 map.put(p[0], p[1]);
		 }
		 }
		catch (Exception e)
		 {
		 }
		return map;
		}

}
