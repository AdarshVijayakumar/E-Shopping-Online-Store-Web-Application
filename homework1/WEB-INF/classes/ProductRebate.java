import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProductRebate")

public class ProductRebate extends HttpServlet {

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
		HashMap<String,Product> pMap = MySqlDataStoreUtilities.prodrebate();
		utility.printHtml("Header.html");
		

		pw.print("<table style=\"font-size:14px; width:80%;color:orange\">");
		pw.print("<tr>");
		
		pw.print("<h1 style=\"color: #777;border-bottom: 2px solid #777;\">Product Rebate Details</h1>");
		pw.print("</td>");
		
		pw.print("</table>");
		
		pw.print("<br/>");
		pw.print("<br/>");
		/* pw.print("<h3 style=\"font-weight: bold\">PRODUCT DETAILS</h3>");  */
		pw.print("<div >");
		pw.print("<table style=\"font-size:14px; width:80%;color:orange\">");					
		pw.print("<tr>");
		pw.print("<th align=\"center\" width=\"50%\">");
		pw.print("<span style=\"font-size:24px\">Product</span>");
		pw.print("</th>");
		pw.print("<th align=\"center\" width=\"50%\">");
		pw.print("<span style=\"font-size:24px\">Rebate Amount</span>");
		pw.print("</th>");
		pw.print("</tr>");
		pw.print("</table>");
		// ----------------------------------------------------------------------------------
				
		//Map<String, String> mostLikedProductsMap = MongoDBDataStoreUtilities.getTop5MostLikedProducts();
			
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
					pw.print("<td  width=\"50%\">");
					pw.print("<span style=\"font-size:20px\">"+product.getName()+"</span>");				   
					pw.print("</td>");
					 pw.print("<td align=\"center\" width=\"50%\">");	
					pw.print("<span style=\"font-size:20px\">"+product.getRebateAmt()+"</span>");						
					pw.print("</td>");
					/*pw.print("<td align=\"center\" width=\"50%\">");	
					pw.print("<span style=\"font-size:20px;font-weight: bold\">"+product.getProdcount()+"</span>");						
					pw.print("</td>");	 */								
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
