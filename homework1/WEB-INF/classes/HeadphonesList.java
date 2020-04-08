import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HeadphonesList")

public class HeadphonesList extends HttpServlet {

	/* Headphone Page Displays all the headphones and their Information in Game Speed */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String, Headphone> allheadphones = new HashMap<String, Headphone>();
		try{
		     allheadphones = MySqlDataStoreUtilities.getHeadphones();
		}
		catch(Exception e)
		{
			
		}
		
        

		/* Checks the Tablets type whether it is microsft or sony or nintendo */
		String name = null;
		String CategoryName = request.getParameter("maker");

		HashMap<String, Headphone> hm = new HashMap<String, Headphone>();
		if(CategoryName==null){
			hm.putAll(allheadphones);
			name = "";
		}
		else
		{
		   if(CategoryName.equals("Bose"))
		   {
			 for(Map.Entry<String,Headphone> entry : allheadphones.entrySet())
			 {
				if(entry.getValue().getRetailer().equals("Bose"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
			 }
				name = "Bose";
		   }
		   else if(CategoryName.equals("Sony"))
		    {
			for(Map.Entry<String,Headphone> entry : allheadphones.entrySet())
				{
				 if(entry.getValue().getRetailer().equals("Sony"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				 name = "Sony";
			}
			else if(CategoryName.equals("Sennheiser"))
			{
				for(Map.Entry<String,Headphone> entry : allheadphones.entrySet())
				{
				 if(entry.getValue().getRetailer().equals("Sennheiser"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Sennheiser";
			}
		}

		
		/* Header, Left Navigation Bar are Printed.

		All the Headphone and Headphone information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" headphones</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Headphone> entry : hm.entrySet())
		{
			Headphone Headphone = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+Headphone.getName()+"</h3>");
			pw.print("<strong>$"+Headphone.getPrice()+"</strong><ul>");
			pw.print("<li id='item'><img src='images/headphone/"+Headphone.getImage()+"' alt='' /></li>");
			
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='headphone'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='headphones'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='price' value='"+Headphone.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='headphones'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if(i%3==0 || i == size) pw.print("</tr>");
			i++;
		}	
		pw.print("</table></div></div></div>");
   
		utility.printHtml("Footer.html");
		
	}
}
