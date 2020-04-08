import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session; 
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}



	/*  Printhtml Function gets the html file name as function Argument, 
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

	public void printHtml(String file) {
		String result = HtmlToString(file);
		//to print the right navigation in header of username cart and logout etc
		if (file == "Header.html") {
				result=result+"<div id='menu' style='float: right;'><ul>";
			if (session.getAttribute("username")!=null){
				String username = session.getAttribute("username").toString();
				String usertype = session.getAttribute("usertype").toString();
				switch(usertype){
					case "customer":
						username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
						result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
							+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
							+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
							+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
							break;	
					case "manager":
						username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
						result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
							+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
							+ "<li><a href='Registration'><span class='glyphicon'>AddUser</span></a></li>"
							+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
							+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
							break;
					case "retailer":
						username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
						result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
							+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
							+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
							+ "<li><a href='ManageProducts'><span class='glyphicon'>Manage</span></a></li>"
							+ "<li><a href='ManageReports'><span class='glyphicon'>Reports</span></a></li>"
							+ "<li><a href='DataAnalytics'><span class='glyphicon'>DataAnalytics</span></a></li>"
							+ "<li><a href='DataVisualization'><span class='glyphicon'>DataVisualization</span></a></li>"
							+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
							break;
				}
				
			}
			else
				result = result +"<li><a href='ViewOrder'><span class='glyphicon'>View Order</span></a></li>"+ "<li><a href='Login'><span class='glyphicon'>Login</span></a></li>";
				result = result +"<li><a href='Cart'><span class='glyphicon'>Cart("+CartCount()+")</span></a></li></ul></div></div><div id='page'>";
				pw.print(result);
		} else
				pw.print(result);
	}
	

	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} 
		catch (Exception e) {
		}
		return result;
	} 

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}
	
	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/
	
	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}
	
	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}
	
	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> hm=new HashMap<String, User>();
		//String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{		
				/*FileInputStream fileInputStream=new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\homework1\\UserDetails.txt"));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
				hm= (HashMap)objectInputStream.readObject(); */
				hm=MySqlDataStoreUtilities.selectUser();
			}
			catch(Exception e)
			{
			}	
		User user = hm.get(username());
		return user;
	}
	
	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<OrderItem>(); 
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}

	/*  getOrdersPaymentSize Function gets  the size of OrderPayment */
	public int getOrderPaymentSize(){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		//String TOMCAT_HOME = System.getProperty("catalina.home");
		//int size=0;
			try
			{
				/* FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\homework1\\PaymentDetails.txt"));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
				orderPayments = (HashMap)objectInputStream.readObject(); */
				orderPayments =MySqlDataStoreUtilities.selectOrder();
			}
			catch(Exception e)
			{
			
			}
			int size=0;
			for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
					 //size=size + 1;
					size=entry.getKey();
					 
			}
			return size;		
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int CartCount(){
		if(isLoggedin())
		return getCustomerOrders().size();
		return 0;
	}
	
	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

	public void storeProduct(String name,String type,String maker, String acc){
		if(!OrdersHashMap.orders.containsKey(username())){	
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		HashMap<String, TV> alltvs = new HashMap<String, TV>();
		HashMap<String, SoundSystem> allsoundsystems = new HashMap<String, SoundSystem>();
		HashMap<String, Phone> allphones = new HashMap<String, Phone>();
		HashMap<String, Laptop> alllaptops = new HashMap<String, Laptop>();
		HashMap<String, VoiceAssistant> allvoiceassistants = new HashMap<String, VoiceAssistant>();
		HashMap<String, FitnessWatch> allfitnesswatches = new HashMap<String, FitnessWatch>();
		HashMap<String, SmartWatch> allsmartwatches = new HashMap<String, SmartWatch>();
		HashMap<String, Headphone> allheadphones = new HashMap<String, Headphone>();
		HashMap<String, WirelessPlan> allwirelessplans = new HashMap<String, WirelessPlan>();

		if(type.equals("TV")){
			TV tv = null;
			try{
			alltvs = MySqlDataStoreUtilities.getTVs();
			}
			catch(Exception e){
				
			}
			tv = alltvs.get(name);
			OrderItem orderitem = new OrderItem(tv.getName(), tv.getPrice(), tv.getImage(), tv.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("soundsystem")){
			SoundSystem soundsystem = null;
			try{
			allsoundsystems = MySqlDataStoreUtilities.getSoundSystems();
			}
			catch(Exception e){
				
			}
			soundsystem = allsoundsystems.get(name);
			OrderItem orderitem = new OrderItem(soundsystem.getName(), soundsystem.getPrice(), soundsystem.getImage(), soundsystem.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("phone")){
			Phone phone = null;
			try{
			allphones = MySqlDataStoreUtilities.getPhones();
			}
			catch(Exception e){
				
			}
			phone = allphones.get(name);
			OrderItem orderitem = new OrderItem(phone.getName(), phone.getPrice(), phone.getImage(), phone.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("laptop")){
			Laptop laptop = null;
			try{
			alllaptops = MySqlDataStoreUtilities.getLaptops();
			}
			catch(Exception e){
				
			}
			laptop = alllaptops.get(name);
			OrderItem orderitem = new OrderItem(laptop.getName(), laptop.getPrice(), laptop.getImage(), laptop.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("voiceassistant")){
			VoiceAssistant voiceassistant = null;
			try{
			allvoiceassistants = MySqlDataStoreUtilities.getVoiceAssistants();
			}
			catch(Exception e){
				
			}
			voiceassistant = allvoiceassistants.get(name);
			OrderItem orderitem = new OrderItem(voiceassistant.getName(), voiceassistant.getPrice(), voiceassistant.getImage(), voiceassistant.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("fitnesswatch")){
			FitnessWatch fitnesswatch = null;
			try{
			allfitnesswatches = MySqlDataStoreUtilities.getFitnessWatches();
			}
			catch(Exception e){
				
			}
			fitnesswatch = allfitnesswatches.get(name);
			OrderItem orderitem = new OrderItem(fitnesswatch.getName(), fitnesswatch.getPrice(), fitnesswatch.getImage(), fitnesswatch.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("smartwatches")){
			SmartWatch smartwatch = null;
			try{
			allsmartwatches = MySqlDataStoreUtilities.getSmartWatches();
			}
			catch(Exception e){
				
			}
			smartwatch = allsmartwatches.get(name);
			OrderItem orderitem = new OrderItem(smartwatch.getName(), smartwatch.getPrice(), smartwatch.getImage(), smartwatch.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("headphone")){
			Headphone headphone = null;
			try{
			allheadphones = MySqlDataStoreUtilities.getHeadphones();
			}
			catch(Exception e){
				
			}
			headphone = allheadphones.get(name);
			OrderItem orderitem = new OrderItem(headphone.getName(), headphone.getPrice(), headphone.getImage(), headphone.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("wirelessplan")){
			WirelessPlan wirelessplan = null;
			try{
			allwirelessplans = MySqlDataStoreUtilities.getWirelessPlans();
			}
			catch(Exception e){
				
			}
			wirelessplan = allwirelessplans.get(name);
			OrderItem orderitem = new OrderItem(wirelessplan.getName(), wirelessplan.getPrice(), wirelessplan.getImage(), wirelessplan.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("accessories")){	
			Accessory accessory = SaxParserDataStore.accessories.get(name); 
			OrderItem orderitem = new OrderItem(accessory.getName(), accessory.getPrice(), accessory.getImage(), accessory.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("war")){
			OrderItem orderitem = new OrderItem("Warranty price", 20, "war.jpg", "warranty");
			orderItems.add(orderitem);
		}
		
	}
	// store the payment details for orders
	public void storePayment(int orderId,
		String orderName,double orderPrice,String userAddress,String creditCardNo,String orderdate){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
		// String TOMCAT_HOME = System.getProperty("catalina.home");
			// get the payment details file 
			try
			{
				/* FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\homework1\\PaymentDetails.txt"));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
				orderPayments = (HashMap)objectInputStream.readObject(); */
				orderPayments=MySqlDataStoreUtilities.selectOrder();
			}
			catch(Exception e)
			{
			
			}
			if(orderPayments==null)
			{
				orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
			}
			// if there exist order id already add it into same list for order id or create a new record with order id
			
			if(!orderPayments.containsKey(orderId)){	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(orderId, arr);
			}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);		
		OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo,orderdate);
		listOrderPayment.add(orderpayment);	
			
			// add order details into file

			try
			{	
				/* FileOutputStream fileOutputStream = new FileOutputStream(new File(TOMCAT_HOME+"\\webapps\\homework1\\PaymentDetails.txt"));
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            	objectOutputStream.writeObject(orderPayments);
				objectOutputStream.flush();
				objectOutputStream.close();       
				fileOutputStream.close(); */
				MySqlDataStoreUtilities.insertOrder(orderId,username(),orderName,orderPrice,userAddress,creditCardNo,orderdate);
				MySqlDataStoreUtilities.updateProductInventory(orderName);
			}
			catch(Exception e)
			{
				System.out.println("inside exception file not written properly");
			}	
	}

	public String storeReview(String productname,String producttype,String productmaker,String reviewrating,String reviewdate,String  reviewtext,String reatilerpin,String price,String city,String state,String name,String sale,String rebate,String id,String age,String gender,String occupation){
	String message=MongoDBDataStoreUtilities.insertReview(productname,username(),producttype,productmaker,reviewrating,reviewdate,reviewtext,reatilerpin,price,city,state,name,sale,rebate,id,age,gender,occupation);
		if(!message.equals("Successfull"))
		{ return "UnSuccessfull";
		}
		else
		{
		HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
		try
		{
			reviews=MongoDBDataStoreUtilities.selectReview();
		}
		catch(Exception e)
		{
			
		}
		if(reviews==null)
		{
			reviews = new HashMap<String, ArrayList<Review>>();
		}
			// if there exist product review already add it into same list for productname or create a new record with product name
			
		if(!reviews.containsKey(productname)){	
			ArrayList<Review> arr = new ArrayList<Review>();
			reviews.put(productname, arr);
		}
		ArrayList<Review> listReview = reviews.get(productname);		
		Review review = new Review(productname,username(),producttype,productmaker,reviewrating,reviewdate,reviewtext,reatilerpin,price,city,state,name,sale,rebate,id,age,gender,occupation);
		listReview.add(review);	
			
			// add Reviews into database
		
		return "Successfull";	
		}
	}
	
	/* getConsoles Functions returns the Hashmap with all consoles in the store.*/

	public HashMap<String, TV> getTV(){
			HashMap<String, TV> hm = new HashMap<String, TV>();
			hm.putAll(SaxParserDataStore.tvs);
			return hm;
	}
	
	/* getGames Functions returns the  Hashmap with all Games in the store.*/

	public HashMap<String, SoundSystem> getSoundSystems(){
			HashMap<String, SoundSystem> hm = new HashMap<String, SoundSystem>();
			hm.putAll(SaxParserDataStore.soundsystems);
			return hm;
	}
	
	/* getTablets Functions returns the Hashmap with all Tablet in the store.*/

	public HashMap<String, Phone> getPhones(){
			HashMap<String, Phone> hm = new HashMap<String, Phone>();
			hm.putAll(SaxParserDataStore.phones);
			return hm;
	}

	public HashMap<String, Laptop> getLaptops(){
			HashMap<String, Laptop> hm = new HashMap<String, Laptop>();
			hm.putAll(SaxParserDataStore.laptops);
			return hm;
	}

	public HashMap<String, VoiceAssistant> getVoiceAssistants(){
			HashMap<String, VoiceAssistant> hm = new HashMap<String, VoiceAssistant>();
			hm.putAll(SaxParserDataStore.voiceassistants);
			return hm;
	}

	public HashMap<String, FitnessWatch> getFitnessWatches(){
			HashMap<String, FitnessWatch> hm = new HashMap<String, FitnessWatch>();
			hm.putAll(SaxParserDataStore.fitnesswatches);
			return hm;
	}

	public HashMap<String, SmartWatch> getSmartWatches(){
			HashMap<String, SmartWatch> hm = new HashMap<String, SmartWatch>();
			hm.putAll(SaxParserDataStore.smartwatches);
			return hm;
	}

	public HashMap<String, Headphone> getHeadphones(){
			HashMap<String, Headphone> hm = new HashMap<String, Headphone>();
			hm.putAll(SaxParserDataStore.headphones);
			return hm;
	}

	public HashMap<String, WirelessPlan> getWirelessPlans(){
			HashMap<String, WirelessPlan> hm = new HashMap<String, WirelessPlan>();
			hm.putAll(SaxParserDataStore.wirelessplans);
			return hm;
	}
	
	/* getProducts Functions returns the Arraylist of consoles in the store.*/

	public ArrayList<String> getProductsTV(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, TV> entry : getTV().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of games in the store.*/

	public ArrayList<String> getProductsSoundSystems(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, SoundSystem> entry : getSoundSystems().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	/* getProducts Functions returns the Arraylist of Tablets in the store.*/

	public ArrayList<String> getProductsPhones(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Phone> entry : getPhones().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public ArrayList<String> getProductsLaptops(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Laptop> entry : getLaptops().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	public ArrayList<String> getProductsVoiceAssistants(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, VoiceAssistant> entry : getVoiceAssistants().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public ArrayList<String> getProductsFitnessWatches(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, FitnessWatch> entry : getFitnessWatches().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public ArrayList<String> getProductsSmartWatches(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, SmartWatch> entry : getSmartWatches().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public ArrayList<String> getProductsHeadphones(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Headphone> entry : getHeadphones().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public ArrayList<String> getProductsWirelessPlans(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, WirelessPlan> entry : getWirelessPlans().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	

}
