import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Salesdaily")

public class Salesdaily extends HttpServlet {

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
		HashMap<String,Product> pMap = MySqlDataStoreUtilities.getsalesdaily();
		utility.printHtml("Header.html");
		

		pw.print("<table style=\"font-size:14px; width:80%;color:#333334\">");
		pw.print("<tr>");
		
		pw.print("<h1 style=\"color: #777;border-bottom: 2px solid #777;\">Daily Sale Details</h1>");
		pw.print("</td>");
		
		pw.print("</table>");
		
		pw.print("<br/>");
		pw.print("<br/>");
		pw.print("<div >");
		pw.print("<table style=\"font-size:14px; width:100%;color:orange\">");					
		pw.print("<tr>");
		pw.print("<th align=\"center\" width=\"15%\">");
		pw.print("<span style=\"font-size:24px;font-weight: bold\">Order Date</span>");
		pw.print("</th>");
		// pw.print("<th align=\"center\" width=\"15%\">");
		// pw.print("<span style=\"font-size:24px;font-weight: bold\">Product</span>");
		// pw.print("</th>");
		// pw.print("<th align=\"center\" width=\"5%\">");
		// pw.print("<span style=\"font-size:24px;font-weight: bold\">Quantity</span>");
		// pw.print("</th>");
		pw.print("<th align=\"center\" width=\"15%\">");
		pw.print("<span style=\"font-size:24px;font-weight: bold\">Total Sales</span>");
		pw.print("</th>");
		pw.print("</tr>");
		pw.print("</table>");
							
			
				
			
					
						
					if(pMap != null){
						if(pMap.isEmpty()){
							pw.print("<br/>");
							pw.print("<br/>");
							pw.print("<h3 style=\"font-weight: bold\">Not enough information available to display this!</h3>");
						}
						else{
							for (HashMap.Entry<String,Product> entry : pMap.entrySet()) {
								Product product = entry.getValue();
								pw.print("<table style=\"font-size:14px; width:80%;color:white\">");			    
								pw.print("<tr>");
								pw.print("<td  width=\"20%\">");
								pw.print("<span style=\"font-size:20px\">"+product.getdate()+"</span>");				   
								pw.print("</td>");
								//  pw.print("<td align=\"center\" width=\"40%\">");	 
								// pw.print("<span style=\"font-size:20px\">"+product.getName()+"</span>");						
								// pw.print("</td>");
								// pw.print("<td align=\"center\" width=\"40%\">");	
								// pw.print("<span style=\"font-size:20px\">"+product.getcnt()+"</span>");						
								//pw.print("</td>");	
                                pw.print("<td align=\"center\" width=\"15%\">");	
                                Double total = Math.round(product.getTotal() * 100.0) / 100.0; 
								pw.print("<span style=\"font-size:20px\">"+total+"</span>");						
								pw.print("</td>");								
								pw.print("</tr>");               
								pw.print("</table>");
							}
						}
					}
					else{
						pw.print("<br/>");
						pw.print("<br/>");
						pw.print("<h1>SQL Server is not running!</h1>");
					}									
					pw.print("</div >");	


		utility.printHtml("Footer.html");
	}

}
