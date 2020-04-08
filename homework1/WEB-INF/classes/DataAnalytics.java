import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.*;
import javax.servlet.http.HttpSession;
@WebServlet("/DataAnalytics")

public class DataAnalytics extends HttpServlet {
	static DBCollection myReviews;
	/* Trending Page Displays all the Consoles and their Information in Game Speed*/

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
				
		
		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to View Reviews");
			response.sendRedirect("Login");
			return;
		}
		
						
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Data Analytics on Review</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<table id='bestseller'>");
		pw.print("<form method='post' action='FindReviews'>");
	
     		pw.print("<table id='bestseller'>");
			pw.print("<tr>");
			pw.print("<td> <input type='checkbox' name='queryCheckBox' value='productName'> Select </td>");
                                pw.print("<td> Product Name: </td>");
                                pw.print("<td>");
                                       pw.print("<select name='productName'>");
				       pw.print("<option value='ALL_PRODUCTS'>All Products</option>");
                                       pw.print("<option value='Sony 4K Ultra HD'>Sony 4K Ultra HD</option>");
                                       pw.print("<option value='Sony Smart LED TV'>Sony Smart LED TV</option>");
                                       pw.print("<option value='Sony Bravia'>Sony Bravia</option>");
                                       pw.print(" <option value='LG 4K OLED TV '>LG 4K OLED TV </option>");
                                       pw.print("<option value='LG 4K UHD TV'>LG 4K UHD TV</option>");
									   
							           pw.print("<option value='Samsung 4K UHD 7 Series'>Samsung 4K UHD 7 Series</option>");
										pw.print("<option value='Samsung 4K 8 Series'>Samsung 4K 8 Series</option>");
										pw.print("<option value='Bose 8 Sorround Sound Speaker'>Bose 8 Sorround Sound Speaker</option>");
										pw.print("<option value='Bose S1 Pro System'>Bose S1 Pro System</option>");
										pw.print("<option value='Sony Muteki Audio System'>Sony Muteki Audio System</option>");
										pw.print("<option value='Sony Portable GPX'>Sony Portable GPX</option>");
										pw.print("<option value='Logitech Z313 with Subwoofer'>Logitech Z313 with Subwoofer</option>");
										pw.print("<option value='Logitech Z506 2.1 Sorround Speaker System'>Logitech Z506 2.1 Sorround Speaker System</option>");
										pw.print("<option value='i Phone 6s Plus'>i Phone 6s Plus</option>");
										pw.print("<option value='i Phone XS Plus'>i Phone XS Plus</option>");
										pw.print("<option value='Samsung Galaxy S9'>Samsung Galaxy S9</option>");
										pw.print("<option value='Samsung Galaxy Note 10'>Samsung Galaxy Note 10</option>");
										pw.print("<option value='Nokia 9.1 Series'>Nokia 9.1 Series</option>");
										pw.print("<option value='Nokia 10 Series'>Nokia 10 Series</option>");
										pw.print("<option value='MacBook Pro'>MacBook Pro</option>");
										pw.print("<option value='MacBook Air'>MacBook Air</option>");
										pw.print("<option value='HP Pavilion 15.6 HD'>HP Pavilion 15.6 HD</option>");
										pw.print("<option value='HP Envy 15.6'>HP Envy 15.6</option>");
										pw.print("<option value='DELL inspiron 15.6'>DELL inspiron 15.6</option>");
										pw.print("<option value='Dell Latitude Ultrabook'>Dell Latitude Ultrabook</option>");
										pw.print("<option value='Google Home'>Google Home</option>");
										pw.print("<option value='Google Home mini'>Google Home mini</option>");
										pw.print("<option value='Amazon Echo Dot'>Amazon Echo Dot</option>");
										pw.print("<option value='Amazon echo 2nd Gen'>Amazon echo 2nd Gen</option>");
										pw.print("<option value='Amazon Alexa'>Amazon Alexa</option>");
										pw.print("<option value='Fitbit Fitness Tracker'>Fitbit Fitness Tracker</option>");
										pw.print("<option value='Fitbit - Inspire Fit'>Fitbit - Inspire Fit</option>");
										pw.print("<option value='Polar - A370 Fitness Tracker'>Polar - A370 Fitness Tracker</option>");
										pw.print("<option value='Polar M430 GPS Running Watch'>Polar M430 GPS Running Watch</option>");
										pw.print("<option value='Moko Smart Fitness Tracker'>Moko Smart Fitness Tracker</option>");
										pw.print("<option value='Moko Fitness sweatproof wrist watch'>Moko Fitness sweatproof wrist watch</option>");
										pw.print("<option value='Samsung Gear S Smart Watch'>Samsung Gear S Smart Watch</option>");
										pw.print("<option value='Samsung Galaxy G3 Smartwatch'>Samsung Galaxy G3 Smartwatch</option>");
										pw.print("<option value='LG W270 2.0 Series'>LG W270 2.0 Series</option>");
										pw.print("<option value='LG SmartTek 2.0 W Series'>LG SmartTek 2.0 W Series</option>");
										pw.print("<option value='Sony SmartWatch2 Waterproof'>Sony SmartWatch2 Waterproof</option>");
										pw.print("<option value='Sony X8 Smart Watch '>Sony X8 Smart Watch </option>");
										pw.print("<option value='Sony WH100XM3 Stero Headphone'>Sony WH100XM3 Stero Headphone</option>");
										pw.print("<option value='Sony RF3 Noise Cancelling headphone'>Sony RF3 Noise Cancelling headphone</option>");
										pw.print("<option value='Bose Sound Sport Headphones'>Bose Sound Sport Headphones</option>");
										pw.print("<option value='Bose 2.1 ZX Series QuietComfort Wireless Headphone'>Bose 2.1 ZX Series QuietComfort Wireless Headphone</option>");
										pw.print("<option value='Sennheiser PXC 550 Wireless Headphone'>Sennheiser PXC 550 Wireless Headphone</option>");
										pw.print("<option value='Sennheiser RS 120 Noise Cancelling Headphone'>Sennheiser RS 120 Noise Cancelling Headphone</option>");
										pw.print("<option value='Basic'>Basic</option>");
										pw.print("<option value='Premium'>Premium</option>");
										pw.print("<option value='Ultimate'>Ultimate</option>");
										

                                pw.print("</td>");
			pw.print("<tr>");
			     pw.print("<td> <input type='checkbox' name='queryCheckBox' value='productPrice'> Select </td>");
                                pw.print("<td> Product Price: </td>");
                              pw.print(" <td>");
                                  pw.print("  <input type='number' name='productPrice' value = '0' size=10  /> </td>");
						pw.print("<td>");
					pw.print("<input type='radio' name='comparePrice' value='EQUALS_TO' checked> Equals <br>");
					pw.print("<input type='radio' name='comparePrice' value='GREATER_THAN'> Greater Than <br>");
					pw.print("<input type='radio' name='comparePrice' value='LESS_THAN'> Less Than");
					pw.print("</td></tr>");				
                            
  			
			pw.print("<tr><td> <input type='checkbox' name='queryCheckBox' value='reviewRating'> Select </td>");
                               pw.print(" <td> Review Rating: </td>");
                               pw.print(" <td>");
                                   pw.print(" <select name='reviewRating'>");
                                       pw.print(" <option value='1' selected>1</option>");
                                       pw.print(" <option value='2'>2</option>");
                                       pw.print(" <option value='3'>3</option>");
                                     pw.print("   <option value='4'>4</option>");
                                      pw.print("  <option value='5'>5</option>");
                                pw.print("</td>");
				pw.print("<td>");
				pw.print("<input type='radio' name='compareRating' value='EQUALS_TO' checked> Equals <br>");
				pw.print("<input type='radio' name='compareRating' value='GREATER_THAN'> Greater Than"); 
			pw.print("</td></tr>");
			
			pw.print("<tr>");
								pw.print("<td> <input type='checkbox' name='queryCheckBox' value='retailerCity'> Select </td>");
                                pw.print("<td> Retailer City: </td>");
                                pw.print("<td>");
                                pw.print("<input type='text' name='retailerCity' /> </td>");
								
                            pw.print("</tr>");
							
							 pw.print("<tr>");
								pw.print("<td> <input type='checkbox' name='queryCheckBox' value='retailerZipcode'> Select </td>");
                               pw.print(" <td> Retailer Zip code: </td>");
                               pw.print(" <td>");
                                    pw.print("<input type='text' name='retailerZipcode' /> </td>");
					        pw.print("</tr>");
				pw.print("<tr><td>");
					pw.print("<input type='checkbox' name='extraSettings' value='GROUP_BY'> Group By");
								pw.print("</td>");
								pw.print("<td>");
								pw.print("<select name='groupByDropdown'>");
                                pw.print("<option value='GROUP_BY_CITY' selected>City</option>");
                                pw.print("<option value='GROUP_BY_PRODUCT'>Product Name</option>");                                        
                                pw.print("</td><td>");
								pw.print("<input type='radio' name='dataGroupBy' value='Count' checked> Count <br>");
								pw.print("<input type='radio' name='dataGroupBy' value='Detail'> Detail <br>");
								pw.print("</td></tr>");
								
									
			
						pw.print("<tr>");
                                pw.print("<td colspan = '4'> <input type='submit' value='Find Data' class='btnbuy' /> </td>");
                            pw.print("</tr>");
							
							
		pw.print("</table>");	
		pw.print("</div></div></div>");			
		utility.printHtml("Footer.html");
	
	
	
			
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
