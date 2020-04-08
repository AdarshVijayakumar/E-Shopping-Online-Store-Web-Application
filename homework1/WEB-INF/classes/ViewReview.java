import java.io.IOException;
import java.io.PrintWriter;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@WebServlet("/ViewReview")

public class ViewReview extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities utility= new Utilities(request, pw);
		review(request, response);
	}
	
	protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        try
                {           
                response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
                Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to view Review");
			response.sendRedirect("Login");
			return;
		}
		 String productName=request.getParameter("name");		 
		HashMap<String, ArrayList<Review>> hm= MongoDBDataStoreUtilities.selectReview();
		String userName = "";
		String reviewRating = "";
		String reviewDate;
		String reviewText = "";	
		String price = "";
		String city ="";
		String pin="";
		String category="";
		String maker="";
		String state="";
		String name="";
		String sale="";
		String rebate="";
		String uid="";
		String age="";
		String gender="";
		String occupation="";

                utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
	
                pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Showing all the Reviews</a>");
		pw.print("</h2><div class='entry'>");
			
			//if there are no reviews for product print no review else iterate over all the reviews using cursor and print the reviews in a table
		if(hm==null)
		{
		pw.println("<h2>Mongo Db server is not up and running</h2>");
		}
		else
		{
                if(!hm.containsKey(productName)){
				pw.println("<h2>There are no reviews for this product.</h2>");
			}else{
		for (Review r : hm.get(productName)) 
				{		

				pw.print("<table class='gridtable'>");
				pw.print("<tr>");
				pw.print("<td><b>Product Model Name: </td>");
				productName = r.getProductName();
				pw.print("<td>" +productName+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td><b>Product Category: </td>");
				category = r.getProductType();
				pw.print("<td>" +category+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td><b>Manufacturer Name: </td>");
				maker = r.getProductMaker();
				pw.print("<td>" +maker+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td><b> userName: </td>");
				userName = r.getUserName();
				pw.print("<td>" +userName+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td><b> price: </td>");
				price = r.getPrice();
				pw.print("<td>" +price+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td><b> Retailer Name: </td>");
				name = r.getRetailerName();
				pw.print("<td>" +name+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td><b> Retailer City: </td>");
				city = r.getRetailerCity();
				pw.print("<td>" +city+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td><b> Retailer State: </td>");
				state = r.getRetailerState();
				pw.print("<td>" +state+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td><b> Retailer Zip: </td>");
				pin = r.getRetailerPin();
				pw.print("<td>" +pin+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td><b> Product on Sale: </td>");
				sale = r.getSale();
				pw.print("<td>" +sale+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td><b> Manufacturer Rebate: </td>");
				rebate = r.getRebate();
				pw.print("<td>" +rebate+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td><b> User Id: </td>");
				uid = r.getId();
				pw.print("<td>" +uid+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td><b> User Age: </td>");
				age = r.getAge();
				pw.print("<td>" +age+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td><b> User Gender: </td>");
				gender = r.getGender();
				pw.print("<td>" +gender+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td><b> User Occupation: </td>");
				occupation = r.getOccupation();
				pw.print("<td>" +occupation+ "</td>");
				pw.print("</tr>");
				
				pw.println("<tr>");
				pw.println("<td><b> Review Rating: </td>");
				reviewRating = r.getReviewRating().toString();
				pw.print("<td>" +reviewRating+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td><b> Review Date: </td>");
				reviewDate = r.getReviewDate().toString();
				pw.print("<td>" +reviewDate+ "</td>");
				pw.print("</tr>");			
				pw.print("<tr>");
				pw.print("<td><b> Review Text: </td>");
				reviewText = r.getReviewText();
				pw.print("<td>" +reviewText+ "</td>");
				pw.print("</tr>");
				
				pw.print("<tr>");
				pw.print("<td style='border-right:none;border-left:none;border-bottom:none;border-top:none'></td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td style='border-right:none;border-left:none;border-bottom:none;border-top:none'></td>");
				pw.print("</tr>");
				
				pw.println("</table>");
				}					
							
		}
		}	       
                pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
	                     	
                    }
              	catch(Exception e)
		{
                 System.out.println(e.getMessage());
		}  			
       
	 	
		}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
            }
}
