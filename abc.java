package shivam;


import java.io.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.pept.transport.Connection;


/**
 * Servlet implementation class Stud
 */
@WebServlet("/Stud")
public class abc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String First_name,Last_name,Email_id,Age;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public abc() {
        super();
        // TODO Auto-generated constructor stub
    }
    String DB_URL= "";
    String USERNAME= "";
    String PASSWORD= "mysql";
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter pr=response.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=(Connection) DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			First_name=request.getParameter("First_name");
			Last_name=request.getParameter("Last_name");
			Email_id=request.getParameter("Email_id");
			Age=request.getParameter("Age");

		Object op = null;
		PreparedStatement ps;
		String id;
		if(op.equals("insert")){
			Statement stmt=(Statement) ((java.sql.Connection) con).createStatement();
			int i=((java.sql.Statement) stmt).executeUpdate("insert into stud values('"+First_name+"','"+Last_name+"','"+Email_id+"','"+Age+"');");
			if(i<0)
			{
				pr.println("Failed to insert");
			}
			else
			{
				pr.println("Inserted into database successfully...");
			}
		}
		else if(op.equals("update")){
			ps=(PreparedStatement) ((java.sql.Connection) con).prepareStatement("update stud set name=?,email=?,year=?,dob=? where id=?;");  
			ps.setString(1,First_name);  
			ps.setString(2,Last_name);  
			ps.setString(3,Email_id);
			ps.setString(4,Age);

			int i=ps.executeUpdate();				
			if(i<0)
			{
				pr.println("Unsuccessful");
			}
			else
			{
				pr.println("Record updated successfully...");
			}
		}
		else if(op.equals("delete")){
			ps=(PreparedStatement) ((java.sql.Connection) con).prepareStatement("delete from info where Last_name=?;");   
			ps.setString(1,Last_name);
			int i=ps.executeUpdate();				
			if(i<0)
			{
				pr.println("Unsuccessful");
			}
			else
			{
				pr.println("Record Deleted Successfully...");
			}
		}
		else{
			ResultSet rs=null;
			String sql="select * from info where Last_name='"+Last_name+"';";
			ps=(PreparedStatement) ((java.sql.Connection) con).prepareStatement(sql);
			rs=ps.executeQuery();
			  	
			while(rs.next()){
				pr.println("First_name: "+rs.getString(1)+"<br>");
				pr.println("Last_name: "+rs.getString(2)+"<br>");
				pr.println("Email_id: "+rs.getString(3)+"<br>");
				pr.println("Age: "+rs.getString(4)+"<br>");
			}					
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
