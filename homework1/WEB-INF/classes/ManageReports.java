import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ManageReports")

public class ManageReports extends HttpServlet {

	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		displayLogin(request, response, pw, true);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}


	/*  Login Screen is Displayed, Registered User specifies credentials and logins into the Game Speed Application. */
	protected void displayLogin(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<h2 class='title meta' style='text-align:center; padding-top:20px;'><a style='font-size:32px; color:orange;'>Inventory Reports</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:400px; height:500px; margin:25px; margin-left: auto;margin-right: auto;'>");
		pw.print("<p style='font-size:20px; color:yellow; text-align:center;'><a href='Inventory'>Inventory Report</a></p>");
		pw.print("<p style='font-size:20px; color:white; text-align:center;'><a href='InventoryBar'>Inventory Graph Report</a></p>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center;'><a href='Productsales'>Products on Sale</a></p>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center;'><a href='ProductRebate'>Products that have Manufacturer Rebate</a></p>");
		pw.print("<h2 class='title meta' style='text-align:center; padding-top:20px;'><a style='font-size:32px; color:orange;'>Sales Reports</a></h2>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center;'><a href='Sales'>Total Sales Report</a></p>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center;'><a href='SalesGraph'>Sales Graph Report</a></p>");
		pw.print("<p style='font-size:20px; color:#333333; text-align:center;'><a href='Salesdaily'>Daily Sales Report</a></p>");		
		
		pw.print("</div></div>");
		utility.printHtml("Footer.html");
	}

}
