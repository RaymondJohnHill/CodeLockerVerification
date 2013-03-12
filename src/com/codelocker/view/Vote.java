package com.codelocker.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codelocker.controller.VerificationController;
import com.codelocker.controller.VoteController;

/**
 * Servlet implementation class Vote
 */
@WebServlet("/Vote")
public class Vote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Vote() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		out.println("<HTML>\n" +
					"  <HEAD>\n" +
					"    <TITLE>Vote For Your School And Sign Up For A Free CodeLocker Account!</TITLE>\n" +
					"  </HEAD>\n" +
					"  <BODY>\n" +
					"    <H1>Vote for your school!</H1>\n" +
					"    <FORM NAME=\"input\" ACTION=\"Vote\" METHOD=\"post\">\n" +
					"      Email: <INPUT TYPE=\"text\" NAME=\"email\"><BR />\n" +
					"      <INPUT TYPE=\"checkbox\" NAME=\"useragreement\" VALUE=\"agree\">I agree to the <A HREF=\"\">terms and conditions!</A><BR />\n" +
					"      <INPUT TYPE=\"submit\" VALUE=\"Vote!\">\n" +
					"    </FORM>\n" +
					"  </BODY>\n" +
					"</HTML>\n");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		
		VoteController vc = new VoteController(email);
		vc.addVote();
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		out.println("<HTML>\n" +
					"  <HEAD>\n" +
					"    <TITLE>Sign Up For A Free CodeLocker Account!</TITLE>\n" +
					"  </HEAD>\n" +
					"  <BODY>\n" +
					"    <H1>CodeLocker Signup!</H1>\n" +
					"    <P>An email has been sent to: " + email + "</P>\n" +
					"  </BODY>\n" +
					"</HTML>\n");
	}

}
