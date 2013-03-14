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
		
		out.println("<html>\n" +
					"  <head>\n" +
					"    <title>Vote For Your School And Sign Up For A Free CodeLocker Account!</title>\n" +
					"  </head>\n" +
					"  <body>\n" +
					"    <h1>Vote for your school!</h1>\n" +
					"    <form name=\"input\" action=\"Vote\" method=\"post\">\n" +
					"      Email: <input type=\"text\" NAME=\"email\"><BR />\n" +
					"      <input type=\"checkbox\" name=\"useragreement\" value=\"agree\">I agree to the <A HREF=\"\">terms and conditions!</A><BR />\n" +
					"      <input type=\"submit\" VALUE=\"Vote!\">\n" +
					"    </FORM>\n" +
					"  </body>\n" +
					"</html>\n");
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
		
		out.println("<html>\n" +
					"  <head>\n" +
					"    <title>Sign Up For A Free CodeLocker Account!</title>\n" +
					"  </head>\n" +
					"  <body>\n" +
					"    <h1>CodeLocker Signup!</h1>\n" +
					"    <p>An email has been sent to: " + email + "</p>\n" +
					"  </body>\n" +
					"</html>\n");
	}

}
