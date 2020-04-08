import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AddProducts")

public class AddProducts extends HttpServlet {

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
		pw.print("<h2 class='title meta' style='text-align:center; padding-top:20px;'><a style='font-size:32px; color:#ffffff; text-align:center;'>Add Product</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");

		pw.print("<form name ='AddProduct' method='post'>");

		pw.print("<p style='font-size:16px; font-weight:bold; color:#ffffff; text-align:center; padding-top:50px;'>Enter the details of the product to be added</p>");
		pw.print("<table>");	

		pw.print("<tr><td style='text-align:right; font-size:22px; color:#ffffff;'>");
     	pw.print("ProductID*:</td>");
		pw.print("<td><input type='text' style='height:10px;' name='ProductID'>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:22px; color:#ffffff;'>");
     	pw.print("Product Name*:</td>");
		pw.print("<td><input type='text' style='height:10px;' name='ProductName'>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:22px; color:#ffffff;'>");
     	pw.print("Product Description*:</td>");
		pw.print("<td><input type='text' style='height:10px;' name='Description'>");
		pw.print("</td></tr>");
		
		pw.print("<tr><td style='text-align:right; font-size:22px; color:#ffffff;'>");
     	pw.print("Price*:</td>");
		pw.print("<td><input type='text' style='height:10px;' name='Price'>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:22px; color:#ffffff;'>");
     	pw.print("Category*:</td>");
		pw.print("<td><select name='Category'><option value=''>---- Please Select----</option>");
		pw.print("<option value='tv'>TV</option>");
		pw.print("<option value='soundsystem'>Sound System</option>");
		pw.print("<option value='phone'>Phones</option>");
		pw.print("<option value='laptop'>Laptop</option>");
		pw.print("<option value='voiceassistant'>Voice Assistants</option>");
		pw.print("<option value='fitnesswatch'>Fitness Watch</option>");
		pw.print("<option value='smartwatch'>Smart Watch</option>");
		pw.print("<option value='headphone'>Headphone</option>");
		pw.print("<option value='wirelessplan'>Wireless Plan</option></select>");
		pw.print("</td></tr>");

		pw.print("<tr><td style='text-align:right; font-size:22px;color:#ffffff;'>");
     	pw.print("Discount*:</td>");
		pw.print("<td><input type='text' style='height:10px;' name='Discount'>");
		pw.print("</td></tr>");

		pw.print("<tr><td colspan='2' style='text-align:center;'>");
		pw.print("<input type='submit' name='Add Product' class='btnbuy'>");
		pw.print("</td></tr></table></form>");
		pw.print("</div></div>");

		utility.printHtml("Footer.html");
	}

}
