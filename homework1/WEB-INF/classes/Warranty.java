import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/Warranty")

public class Warranty extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    displayLogin(request, response, pw, true);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}

	protected void displayLogin(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");

		pw.print("<h2 class='title meta'><a style='font-size: 18px;>By selecting the 'Buy Warranty' you agree to the company terms and policies</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");
		pw.print("<form method='post' action='Cart'>");
		
		pw.print("<li><form method='post' action='Cart'>" +
						"<input type='hidden' name='name' value='Warranty price'>"+
						"<input type='hidden' name='type' value='war'>"+
						"<input type='hidden' name='maker' value='war'>"+
						"<input type='hidden' name='access' value='war'>"+
						"<input type='submit' class='btnbuy' value='Buy Warranty'></form></li>");
		pw.print("</form>");
		pw.print("</div></div>");
		utility.printHtml("Footer.html");
	}
}