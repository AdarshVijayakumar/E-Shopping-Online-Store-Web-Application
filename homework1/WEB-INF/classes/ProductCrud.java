import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProductCrud")

public class ProductCrud extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			Utilities utility = new Utilities(request, pw);
			String action = request.getParameter("button");
			
			String msg = "good";
			String producttype= "",productId="",productName="",productImage="",productManufacturer="",productCondition="",prod = "";
			double productPrice=0.0,productDiscount = 0.0;
			HashMap<String, TV> alltvs = new HashMap<String, TV>();
			HashMap<String, SoundSystem> allsoundsystems = new HashMap<String, SoundSystem>();
			HashMap<String, Phone> allphones = new HashMap<String, Phone>();
			HashMap<String, Laptop> alllaptops = new HashMap<String, Laptop>();
			HashMap<String, VoiceAssistant> allvoiceassistants = new HashMap<String, VoiceAssistant>();
			HashMap<String, FitnessWatch> allfitnesswatches = new HashMap<String, FitnessWatch>();
			HashMap<String, SmartWatch> allsmartwatches = new HashMap<String, SmartWatch>();
			HashMap<String, Headphone> allheadphones = new HashMap<String, Headphone>();
			HashMap<String, WirelessPlan> allwirelessplans = new HashMap<String, WirelessPlan>();

			if (action.equals("add") || action.equals("update"))
			{	
				System.out.println("HAIII");
				 producttype = request.getParameter("producttype");
				 productId   = request.getParameter("productId");
				 productName = request.getParameter("productName"); 
				 productPrice = Double.parseDouble(request.getParameter("productPrice"));
				 productImage = request.getParameter("productImage");
				 productManufacturer = request.getParameter("productManufacturer");
				 productCondition = request.getParameter("productCondition");
				 productDiscount = Double.parseDouble(request.getParameter("productDiscount"));
				 
			}
			else{
				productId   = request.getParameter("productId");
			}	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");

			if(action.equals("add"))
			{
			  		if(producttype.equals("TV")){
				  		alltvs = MySqlDataStoreUtilities.getTVs();
				  			if(alltvs.containsKey(productId)){
					  			msg = "Product already available";
					  
				  			}
					  
			  		}
			  		else if(producttype.equals("soundsystem")){
				  		allsoundsystems = MySqlDataStoreUtilities.getSoundSystems();
				  			if(allsoundsystems.containsKey(productId)){
					  			msg = "Product already available";
				  			}
			  		}
			  		else if (producttype.equals("phone")){
				  		allphones = MySqlDataStoreUtilities.getPhones();
				  			if(allphones.containsKey(productId)){
					  			msg = "Product already available";
				  			}
			 		}
			 		else if(producttype.equals("laptop")){
				  		alllaptops = MySqlDataStoreUtilities.getLaptops();
				  			if(alllaptops.containsKey(productId)){
					  			msg = "Product already available";
				  			}
			  		}
			  		else if(producttype.equals("voiceassistant")){
				  		allvoiceassistants = MySqlDataStoreUtilities.getVoiceAssistants();
				  			if(allvoiceassistants.containsKey(productId)){
					  			msg = "Product already available";
				  			}
			  		}
			  		else if(producttype.equals("fitnesswatch")){
				  		allfitnesswatches = MySqlDataStoreUtilities.getFitnessWatches();
				  			if(allfitnesswatches.containsKey(productId)){
					  			msg = "Product already available";
				  			}
			  		}
			  		else if(producttype.equals("smartwatch")){
				  		allsmartwatches = MySqlDataStoreUtilities.getSmartWatches();
				  			if(allsmartwatches.containsKey(productId)){
					  			msg = "Product already available";
				  			}
			  		}
			  		else if(producttype.equals("headphone")){
				  		allheadphones = MySqlDataStoreUtilities.getHeadphones();
				  			if(allheadphones.containsKey(productId)){
					  			msg = "Product already available";
				  			}
			  		}
			  		else if(producttype.equals("wirelessplan")){
				  		allwirelessplans = MySqlDataStoreUtilities.getWirelessPlans();
				  			if(allwirelessplans.containsKey(productId)){
					  			msg = "Product already available";
				  			}
			  		}
			 		// else if (producttype.equals("accessories")){  
						// if(!request.getParameter("product").isEmpty())
						// {
						// 	prod = request.getParameter("product");
						// 	allconsoles = MySqlDataStoreUtilities.getConsoles();
						// 	if(allconsoles.containsKey(prod))
						// 	{
						// 		allaccessories = MySqlDataStoreUtilities.getAccessories();
						// 		if(allaccessories.containsKey(productId)){
						// 			msg = "Product already available";
						// 		}
						// 	}else{
						// 		msg = "The product related to accessories is not available";
						// 	}
						
						
						// }else{
						// 	msg = "Please add the prodcut name";
						// }
				  
			  	// 	}	
			  		if (msg.equals("good"))
			  		{  
				  		try
				  		{
					  		msg = MySqlDataStoreUtilities.addproducts(producttype,productId,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,prod);
				  		}
				  		catch(Exception e)
				  		{ 
							msg = "Product cannot be inserted";
				  		}
				  		msg = "Product has been successfully added";
			  		}					
			}
			else if(action.equals("update"))
			{
				
			  if(producttype.equals("TV")){
				  alltvs = MySqlDataStoreUtilities.getTVs();
				  if(!alltvs.containsKey(productId)){
					  msg = "Product not available";
				  }
					  
			  }
			  else if(producttype.equals("soundsystem"))
			  {
				  allsoundsystems = MySqlDataStoreUtilities.getSoundSystems();
				  if(!allsoundsystems.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }
			  else if (producttype.equals("phone"))
			  {
				  allphones = MySqlDataStoreUtilities.getPhones();
				  if(!allphones.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }
			  else if(producttype.equals("laptop"))
			  {
				  alllaptops = MySqlDataStoreUtilities.getLaptops();
				  if(!alllaptops.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }
			  else if(producttype.equals("voiceassistant"))
			  {
				  allvoiceassistants = MySqlDataStoreUtilities.getVoiceAssistants();
				  if(!allvoiceassistants.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }
			  else if(producttype.equals("fitnesswatch"))
			  {
				  allfitnesswatches = MySqlDataStoreUtilities.getFitnessWatches();
				  if(!allfitnesswatches.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }
			  else if(producttype.equals("smartwatch"))
			  {
				  allsmartwatches = MySqlDataStoreUtilities.getSmartWatches();
				  if(!allsmartwatches.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }
			  else if(producttype.equals("wirelessplan"))
			  {
				  allwirelessplans = MySqlDataStoreUtilities.getWirelessPlans();
				  if(!allwirelessplans.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }
			  else if(producttype.equals("headphone"))
			  {
				  allheadphones = MySqlDataStoreUtilities.getHeadphones();
				  if(!allheadphones.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }
			 //  else if (producttype.equals("accessories"))
			 //  {
				//   allaccessories = MySqlDataStoreUtilities.getAccessories();
				//   if(!allaccessories.containsKey(productId)){
				// 	  msg = "Product not available";
				// }
			 //  }	
			  if (msg.equals("good"))
			  {		
				
				  try
				  {
					msg = MySqlDataStoreUtilities.updateproducts(producttype,productId,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount);
				  }
				  catch(Exception e)
				  { 
					msg = "Product cannot be updated";
				  }
				  msg = "Product has been successfully updated";
			  } 
			}
				else if(action.equals("delete"))
			{
				  msg = "bad";
				  alltvs = MySqlDataStoreUtilities.getTVs();
				  if(alltvs.containsKey(productId)){
					  msg = "good";
					  
				  }
					  
			  	System.out.println("1");
				  allsoundsystems = MySqlDataStoreUtilities.getSoundSystems();
				  System.out.println("2");
				  System.out.println(allsoundsystems);
				  System.out.println(productId);
				  //System.out.println(msg);
				  if(allsoundsystems.containsKey(productId)){
					  msg = "good";
					  System.out.println("3");
				  }
			  
				  allphones = MySqlDataStoreUtilities.getPhones();
				  if(allphones.containsKey(productId)){
					  msg = "good";
				  }

				  alllaptops = MySqlDataStoreUtilities.getLaptops();
				  if(alllaptops.containsKey(productId)){
					  msg = "good";
				  }

				  allvoiceassistants = MySqlDataStoreUtilities.getVoiceAssistants();
				  if(allvoiceassistants.containsKey(productId)){
					  msg = "good";
				  }

				  allfitnesswatches = MySqlDataStoreUtilities.getFitnessWatches();
				  if(allfitnesswatches.containsKey(productId)){
					  msg = "good";
				  }

				  allsmartwatches = MySqlDataStoreUtilities.getSmartWatches();
				  if(allsmartwatches.containsKey(productId)){
					  msg = "good";
				  }

				  allheadphones = MySqlDataStoreUtilities.getHeadphones();
				  if(allheadphones.containsKey(productId)){
					  msg = "good";
				  }

				  allwirelessplans = MySqlDataStoreUtilities.getWirelessPlans();
				  if(allwirelessplans.containsKey(productId)){
					  msg = "good";
				  }
			  
				//   allaccessories = MySqlDataStoreUtilities.getAccessories();
				//   if(allaccessories.containsKey(productId)){
				// 	  msg = "good";
				// }
		       	
				  if (msg.equals("good"))
				  {		
					
					  try
					  {  
						
						 msg = MySqlDataStoreUtilities.deleteproducts(productId);
					  }
					  catch(Exception e)
					  { 
						msg = "Product cannot be deleted";
					  }
					   msg = "Product has been successfully deleted";
				  }
				  else{
					  msg = "Product not available";
				  }
			}	
				
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");
			pw.print("<h4 style='color:blue'>"+msg+"</h4>");
			pw.print("</div></div></div>");		
			utility.printHtml("Footer.html");
			
	}
}