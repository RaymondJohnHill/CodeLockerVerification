package com.codelocker.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codelocker.controller.PasswordController;
import com.codelocker.controller.VerificationController;

/**
 * Servlet implementation class Verify
 */
@WebServlet("/Verify")
public class Verify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String email;
	private String verification_code;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Verify() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		email = request.getParameter("email");
		verification_code = request.getParameter("verification");
		
		PrintWriter out = response.getWriter();
		
		out.println("<HTML>\n" +
					"  <HEAD>\n" +
					"    <TITLE>Verify Your Free CodeLocker Account!</TITLE>\n" +
					"  </HEAD>\n" +
					"  <BODY>\n" +
					"    <H1>Complete Your CodeLocker Signup!</H1>\n" +
					"    <FORM NAME=\"input\" ACTION=\"Verify\" METHOD=\"post\">\n" +
					"      Enter a password: <INPUT TYPE=\"password\" NAME=\"password\"><BR />\n" +
					"      Re-enter the password: <INPUT TYPE=\"password\" NAME=\"password_check\"><BR />\n" +
					"      <INPUT TYPE=\"submit\" VALUE=\"Submit!\">\n" +
					"    </FORM>\n" +
					"  </BODY>\n" +
					"</HTML>\n");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String password = request.getParameter("password");
		String password_check = request.getParameter("password_check");
		
		PasswordController pc = new PasswordController(email);
		if(password.equals(password_check)) {
			pc.savePasswordAndSalt(verification_code, password);
		}
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		out.println("<HTML>\n" +
					"  <HEAD>\n" +
					"    <TITLE>You Have The Completed Signup For Your Free CodeLocker Account!</TITLE>\n" +
					"  </HEAD>\n" +
					"  <BODY>\n" +
					"    <H1>CodeLocker Signup Complete!</H1>\n" +
					"    <P>You have successfully signed up for CodeLocker!</P>\n" +
					"  </BODY>\n" +
					"</HTML>\n");
	}

}
