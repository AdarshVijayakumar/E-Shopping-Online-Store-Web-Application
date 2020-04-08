import java.sql.*;
import java.util.*;
                	
public class MySqlDataStoreUtilities
{
static Connection conn = null;

public static void getConnection()
{

	try
	{
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");							
	}
	catch(Exception e)
	{
	
	}
}


public static void deleteOrder(int orderId,String orderName)
{
	try
	{
		
		getConnection();
		String deleteOrderQuery ="Delete from customerorders where OrderId=? and orderName=?";
		PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
		pst.setInt(1,orderId);
		pst.setString(2,orderName);
		pst.executeUpdate();
	}
	catch(Exception e)
	{
			
	}
}

public static void insertOrder(int orderId,String userName,String orderName,double orderPrice,String userAddress,String creditCardNo,String orderdate)
{
	try
	{
	
		getConnection();
		String insertIntoCustomerOrderQuery = "INSERT INTO customerOrders(OrderId,UserName,OrderName,OrderPrice,userAddress,creditCardNo,orderdate) "
		+ "VALUES (?,?,?,?,?,?,?);";	
			
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
		//set the parameter for each column and execute the prepared statement
		pst.setInt(1,orderId);
		pst.setString(2,userName);
		pst.setString(3,orderName);
		pst.setDouble(4,orderPrice);
		pst.setString(5,userAddress);
		pst.setString(6,creditCardNo);
		pst.setString(7,orderdate);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}		
}

public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder()
{	

	HashMap<Integer, ArrayList<OrderPayment>> orderPayments=new HashMap<Integer, ArrayList<OrderPayment>>();
		
	try
	{					

		getConnection();
        //select the table 
		String selectOrderQuery ="select * from customerorders";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();	
		ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
		while(rs.next())
		{
			if(!orderPayments.containsKey(rs.getInt("OrderId")))
			{	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(rs.getInt("orderId"), arr);
			}
			ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));		
			System.out.println("data is"+rs.getInt("OrderId")+orderPayments.get(rs.getInt("OrderId")));

			//add to orderpayment hashmap
			OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("userName"),rs.getString("orderName"),rs.getDouble("orderPrice"),rs.getString("userAddress"),rs.getString("creditCardNo"),rs.getString("orderdate"));
			listOrderPayment.add(order);
					
		}
				
					
	}
	catch(Exception e)
	{
		
	}
	return orderPayments;
}


public static void insertUser(String username,String password,String repassword,String usertype)
{
	try
	{	

		getConnection();
		String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,repassword,usertype) "
		+ "VALUES (?,?,?,?);";	
				
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
		pst.setString(1,username);
		pst.setString(2,password);
		pst.setString(3,repassword);
		pst.setString(4,usertype);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}	
}

public static HashMap<String,User> selectUser()
{	
	HashMap<String,User> hm=new HashMap<String,User>();
	try 
	{
		getConnection();
		Statement stmt=conn.createStatement();
		String selectCustomerQuery="select * from  Registration";
		ResultSet rs = stmt.executeQuery(selectCustomerQuery);
		while(rs.next())
		{	User user = new User(rs.getString("username"),rs.getString("password"),rs.getString("usertype"));
				hm.put(rs.getString("username"), user);
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static void Insertproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount,String prod)
{
	try
	{	

		getConnection();
		String addProductQurey = "INSERT INTO  Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount)" +
		"VALUES (?,?,?,?,?,?,?,?);";
		   
			String name = producttype;
	        			
			PreparedStatement pst = conn.prepareStatement(addProductQurey);
			pst.setString(1,name);
			pst.setString(2,productId);
			pst.setString(3,productName);
			pst.setDouble(4,productPrice);
			pst.setString(5,productImage);
			pst.setString(6,productManufacturer);
			pst.setString(7,productCondition);
			pst.setDouble(8,productDiscount);
			
			pst.executeUpdate();
	}
	catch(Exception e)
	{
	
	}	
}

public static HashMap<String, Product> getProductsinventory(){
		try{
			//if(getConnection()){
				
				getConnection();		
				HashMap<String, Product> products=new HashMap<String, Product>();
				String selectProductQuery ="select * from Productdetails;";
				PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
				ResultSet rs = pst1.executeQuery();
				
				while(rs.next()){
					Product product = new Product();					
					product.setId(rs.getString("Id"));
					product.setName(rs.getString("productName"));
					product.setDescription(rs.getString("ProductType"));
					product.setDisplay_under(rs.getString("ProductType"));
					product.setImageUrl(rs.getString("productImage"));
					product.setPrice(rs.getDouble("productPrice"));
					product.setDiscounAmt(rs.getDouble("productDiscount"));
					product.setRebateAmt(rs.getDouble("rebateAmount"));
					product.setProdcount(rs.getDouble("count"));
					//HashMap<String, Accessory> aMap = getAccessory(product.getId());
					//product.setAccessories(aMap);
					
					products.put(product.getId(),product);
				}
				
				pst1.close();
				conn.close();
				return products;
				
			//}
			// else{
			// 	return null;
			// }			
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}

	public static HashMap<String, Product> getProductssales(){
		try{
			//if(getConnection()){
				
				getConnection();		
				HashMap<String, Product> products=new HashMap<String, Product>();
				String selectProductQuery ="select * from Productdetails where count > 0.0;";
				PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
				ResultSet rs = pst1.executeQuery();
				
				while(rs.next()){
					Product product = new Product();					
					product.setId(rs.getString("Id"));
					product.setName(rs.getString("productName"));
					product.setDescription(rs.getString("ProductType"));
					product.setDisplay_under(rs.getString("ProductType"));
					product.setImageUrl(rs.getString("productImage"));
					product.setPrice(rs.getDouble("productPrice"));
					product.setDiscounAmt(rs.getDouble("productDiscount"));
					product.setRebateAmt(rs.getDouble("rebateAmount"));
					product.setProdcount(rs.getDouble("count"));
					//HashMap<String, Accessory> aMap = getAccessory(product.getId());
					//product.setAccessories(aMap);
					
					products.put(product.getId(),product);
				}
				
				pst1.close();
				conn.close();
				return products;
				
			//}
			// else{
			// 	return null;
			// }			
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}


public static HashMap<String, Product> productssales(){
		try{
			//if(getConnection() == 1){
				getConnection();
				HashMap<String, Product> products=new HashMap<String, Product>();
				String selectProductQuery ="select * from Productdetails where count>0";
				PreparedStatement pst = conn.prepareStatement(selectProductQuery);
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					Product product = new Product();					
					product.setId(rs.getString("Id"));
					product.setName(rs.getString("productName"));
					product.setDescription(rs.getString("ProductType"));
					product.setDisplay_under(rs.getString("ProductType"));
					product.setImageUrl(rs.getString("productImage"));
					product.setPrice(rs.getDouble("productPrice"));
					product.setDiscounAmt(rs.getDouble("productDiscount"));
					product.setRebateAmt(rs.getDouble("rebateAmount"));
					product.setProdcount(rs.getDouble("count"));
					//HashMap<String, Accessory> aMap = getAccessory(product.getId());
					//product.setAccessories(aMap);
					
					products.put(product.getId(),product);
				}
				
				pst.close();
				conn.close();
				return products;
				
			//}
			// else{
			// 	return null;
			// }			
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}

	public static HashMap<String, Product> prodrebate(){
		try{
			//if(getConnection() == 1){
				getConnection();
				HashMap<String, Product> products=new HashMap<String, Product>();
				String selectProductQuery ="select * from Productdetails where rebateAmount>0";
				PreparedStatement pst = conn.prepareStatement(selectProductQuery);
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					Product product = new Product();					
					product.setId(rs.getString("Id"));
					product.setName(rs.getString("productName"));
					product.setDescription(rs.getString("ProductType"));
					product.setDisplay_under(rs.getString("ProductType"));
					product.setImageUrl(rs.getString("productImage"));
					product.setPrice(rs.getDouble("productPrice"));
					product.setDiscounAmt(rs.getDouble("productDiscount"));
					product.setRebateAmt(rs.getDouble("rebateAmount"));
					product.setProdcount(rs.getDouble("count"));
					//HashMap<String, Accessory> aMap = getAccessory(product.getId());
					//product.setAccessories(aMap);
					
					products.put(product.getId(),product);
				}
				
				pst.close();
				conn.close();
				return products;
				
			// }
			// else{
			// 	return null;
			// }			
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}

	public static void updateProductInventory(String orderName){
		try{
				getConnection();
				
				String updateProducts = "UPDATE Productdetails SET count = count-1 where productName = ?";
				                  
				
				PreparedStatement pst = conn.prepareStatement(updateProducts);
				pst.setString(1,orderName);
				pst.executeUpdate();				
					
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}

	public static HashMap<String, Product> getsales(){
		try{
				
				getConnection();
				HashMap<String, Product> products=new HashMap<String, Product>();
				String selectProductQuery ="select orderName, orderPrice, count(orderName) as cnt, sum(orderPrice) as total from customerorders group by orderName";
				PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
				ResultSet rs = pst1.executeQuery();
				
				while(rs.next()){
					Product product = new Product();					
					//product.setId(rs.getString("Id"));
					product.setName(rs.getString("orderName"));
					//product.setDescription(rs.getString("ProductType"));
					//product.setDisplay_under(rs.getString("ProductType"));
					//product.setImageUrl(rs.getString("productImage"));
					product.setPrice(rs.getDouble("orderPrice"));
					product.setcnt(rs.getDouble("cnt"));
					product.setTotal(rs.getDouble("total"));
					//product.setDiscounAmt(rs.getDouble("productDiscount"));
					//product.setRebateAmt(rs.getDouble("rebateAmount"));
					//product.setProdcount(rs.getDouble("count"));
					//HashMap<String, Accessory> aMap = getAccessory(product.getId());
					//product.setAccessories(aMap);
					
					products.put(product.getName(),product); 
				}
				pst1.close();
				conn.close();
				return products;
				
			// }
			// else{
			// 	return null;
			// }			
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}

	public static HashMap<String, Product> getsalesdaily(){
		try{
				
				getConnection();		
				HashMap<String, Product> products=new HashMap<String, Product>();
				String selectProductQuery ="select orderdate, sum(orderPrice) as total from customerorders group by orderdate";
				PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
				ResultSet rs = pst1.executeQuery();
				
				while(rs.next()){
					Product product = new Product();					
					// product.setId(rs.getString("Id"));
					// product.setName(rs.getString("productName"));
					// product.setDescription(rs.getString("ProductType"));
					// product.setDisplay_under(rs.getString("ProductType"));
					// product.setImageUrl(rs.getString("productImage"));
					// product.setPrice(rs.getDouble("productPrice"));
					// product.setDiscounAmt(rs.getDouble("productDiscount"));
					// product.setRebateAmt(rs.getDouble("rebateAmount"));
					// product.setProdcount(rs.getDouble("count"));

					product.setdate(rs.getString("orderdate"));
					//product.setName(rs.getString("orderName"));
					//product.setcnt(rs.getDouble("cnt"));
					product.setTotal(rs.getDouble("total"));

					//HashMap<String, Accessory> aMap = getAccessory(product.getId());
					//product.setAccessories(aMap);
					
					products.put(product.getdate(),product);
				}
				
				pst1.close();
				conn.close();
				return products;
						
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}


	public static HashMap<String,TV> getTVs()
	{	
	HashMap<String,TV> hm=new HashMap<String,TV>();
	try 
	{
		getConnection();
		
		String selectTV="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectTV);
		pst.setString(1,"TV");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	TV tv = new TV(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), tv);
				tv.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,SoundSystem> getSoundSystems()
{	
	HashMap<String,SoundSystem> hm=new HashMap<String,SoundSystem>();
	try 
	{
		getConnection();
		
		String selectSoundSystem="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectSoundSystem);
		pst.setString(1,"soundsystem");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	SoundSystem soundsystem = new SoundSystem(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), soundsystem);
				soundsystem.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}
public static HashMap<String,Phone> getPhones()
{	
	HashMap<String,Phone> hm=new HashMap<String,Phone>();
	try 
	{
		getConnection();
		
		String selectPhone="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectPhone);
		pst.setString(1,"phone");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Phone phone = new Phone(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), phone);
				phone.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}
public static HashMap<String,Laptop> getLaptops()
{	
	HashMap<String,Laptop> hm=new HashMap<String,Laptop>();
	try 
	{
		getConnection();
		
		String selectLaptop="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectLaptop);
		pst.setString(1,"laptop");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Laptop laptop = new Laptop(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), laptop);
				laptop.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}
public static HashMap<String,VoiceAssistant> getVoiceAssistants()
{	
	HashMap<String,VoiceAssistant> hm=new HashMap<String,VoiceAssistant>();
	try 
	{
		getConnection();
		
		String selectVoiceAssistant="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectVoiceAssistant);
		pst.setString(1,"voiceassistant");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	VoiceAssistant voiceassistant = new VoiceAssistant(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), voiceassistant);
				voiceassistant.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}
public static HashMap<String,FitnessWatch> getFitnessWatches()
{	
	HashMap<String,FitnessWatch> hm=new HashMap<String,FitnessWatch>();
	try 
	{
		getConnection();
		
		String selectFitnessWatch="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectFitnessWatch);
		pst.setString(1,"fitnesswatch");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	FitnessWatch fitnesswatch = new FitnessWatch(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), fitnesswatch);
				fitnesswatch.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}
public static HashMap<String,SmartWatch> getSmartWatches()
{	
	HashMap<String,SmartWatch> hm=new HashMap<String,SmartWatch>();
	try 
	{
		getConnection();
		
		String selectSmartWatch="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectSmartWatch);
		pst.setString(1,"smartwatches");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	SmartWatch smartwatch = new SmartWatch(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), smartwatch);
				smartwatch.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}
public static HashMap<String,Headphone> getHeadphones()
{	
	HashMap<String,Headphone> hm=new HashMap<String,Headphone>();
	try 
	{
		getConnection();
		
		String selectHeadphone="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectHeadphone);
		pst.setString(1,"headphone");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Headphone headphone = new Headphone(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), headphone);
				headphone.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}
public static HashMap<String,WirelessPlan> getWirelessPlans()
{	
	HashMap<String,WirelessPlan> hm=new HashMap<String,WirelessPlan>();
	try 
	{
		getConnection();
		
		String selectWirelessPlan="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectWirelessPlan);
		pst.setString(1,"wirelessplan");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	WirelessPlan wirelessplan = new WirelessPlan(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), wirelessplan);
				wirelessplan.setId(rs.getString("Id"));
		//		System.out.println(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}


public static String addproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount,String prod)
{
	String msg = "Product is added successfully";
	try{
		
		getConnection();
		String addProductQurey = "INSERT INTO  Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount)" +
		"VALUES (?,?,?,?,?,?,?,?);";
		   
			String name = producttype;
	        			
			PreparedStatement pst = conn.prepareStatement(addProductQurey);
			pst.setString(1,name);
			pst.setString(2,productId);
			pst.setString(3,productName);
			pst.setDouble(4,productPrice);
			pst.setString(5,productImage);
			pst.setString(6,productManufacturer);
			pst.setString(7,productCondition);
			pst.setDouble(8,productDiscount);
			
			pst.executeUpdate();
			// try{
			// 	if (!prod.isEmpty())
			// 	{
			// 		String addaprodacc =  "INSERT INTO  Product_accessories(productName,accessoriesName)" +
			// 		"VALUES (?,?);";
			// 		PreparedStatement pst1 = conn.prepareStatement(addaprodacc);
			// 		pst1.setString(1,prod);
			// 		pst1.setString(2,productId);
			// 		pst1.executeUpdate();
					
			// 	}
			// }catch(Exception e)
			// {
			// 	msg = "Erro while adding the product";
			// 	e.printStackTrace();
		
			// }
			
			
		
	}
	catch(Exception e)
	{
		msg = "Erro while adding the product";
		e.printStackTrace();
		
	}
	return msg;
}


public static String updateproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount)
{ 
    String msg = "Product is updated successfully";
	try{
		
		getConnection();
		String updateProductQurey = "UPDATE Productdetails SET productName=?,productPrice=?,productImage=?,productManufacturer=?,productCondition=?,productDiscount=? where Id =?;" ;
		
		   
				        			
			PreparedStatement pst = conn.prepareStatement(updateProductQurey);
			
			pst.setString(1,productName);
			pst.setDouble(2,productPrice);
			pst.setString(3,productImage);
			pst.setString(4,productManufacturer);
			pst.setString(5,productCondition);
			pst.setDouble(6,productDiscount);
			pst.setString(7,productId);
			pst.executeUpdate();
			
			
		
	}
	catch(Exception e)
	{
		msg = "Product cannot be updated";
		e.printStackTrace();
		
	}
 return msg;	
}

public static String deleteproducts(String productId)
{   String msg = "Product is deleted successfully";
	try
	{
		
		getConnection();
		String deleteproductsQuery ="delete from Productdetails where Id=?";
		PreparedStatement pst = conn.prepareStatement(deleteproductsQuery);
		pst.setString(1,productId);
		
		pst.executeUpdate();
	}
	catch(Exception e)
	{
			msg = "Proudct cannot be deleted";
	}
	return msg;
}

public static void Insertproducts()
{
	try{
		
		
		getConnection();
		
		
		// String truncatetableacc = "delete from Product_accessories;";
		// PreparedStatement pstt = conn.prepareStatement(truncatetableacc);
		// pstt.executeUpdate();
		
		String truncatetableprod = "delete from  Productdetails;";
		PreparedStatement psttprod = conn.prepareStatement(truncatetableprod);
		psttprod.executeUpdate();
		
				
		
		String insertProductQurey = "INSERT INTO  Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount)" +
		"VALUES (?,?,?,?,?,?,?,?);";
		// for(Map.Entry<String,Accessory> entry : SaxParserDataStore.accessories.entrySet())
		// {   
		// 	String name = "accessories";
	 //        Accessory acc = entry.getValue();
			
		// 	PreparedStatement pst = conn.prepareStatement(insertProductQurey);
		// 	pst.setString(1,name);
		// 	pst.setString(2,acc.getId());
		// 	pst.setString(3,acc.getName());
		// 	pst.setDouble(4,acc.getPrice());
		// 	pst.setString(5,acc.getImage());
		// 	pst.setString(6,acc.getRetailer());
		// 	pst.setString(7,acc.getCondition());
		// 	pst.setDouble(8,acc.getDiscount());
			
		// 	pst.executeUpdate();
			
			
		// }
		
		
		for(Map.Entry<String,TV> entry : SaxParserDataStore.tvs.entrySet())
		{   
			String name = "TV";
	        TV tv = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,tv.getId());
			pst.setString(3,tv.getName());
			pst.setDouble(4,tv.getPrice());
			pst.setString(5,tv.getImage());
			pst.setString(6,tv.getRetailer());
			pst.setString(7,tv.getCondition());
			pst.setDouble(8,tv.getDiscount());
			
			pst.executeUpdate();
			
			
		}
		for(Map.Entry<String,SoundSystem> entry : SaxParserDataStore.soundsystems.entrySet())
		{   
			String name = "soundsystem";
	        SoundSystem soundsystem = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,soundsystem.getId());
			pst.setString(3,soundsystem.getName());
			pst.setDouble(4,soundsystem.getPrice());
			pst.setString(5,soundsystem.getImage());
			pst.setString(6,soundsystem.getRetailer());
			pst.setString(7,soundsystem.getCondition());
			pst.setDouble(8,soundsystem.getDiscount());
			
			pst.executeUpdate();
			
			
		}

		for(Map.Entry<String,Phone> entry : SaxParserDataStore.phones.entrySet())
		{   
			String name = "phone";
	        Phone phone = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,phone.getId());
			pst.setString(3,phone.getName());
			pst.setDouble(4,phone.getPrice());
			pst.setString(5,phone.getImage());
			pst.setString(6,phone.getRetailer());
			pst.setString(7,phone.getCondition());
			pst.setDouble(8,phone.getDiscount());
			
			pst.executeUpdate();
			
			
		}

		for(Map.Entry<String,Laptop> entry : SaxParserDataStore.laptops.entrySet())
		{   
			String name = "laptop";
	        Laptop laptop = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,laptop.getId());
			pst.setString(3,laptop.getName());
			pst.setDouble(4,laptop.getPrice());
			pst.setString(5,laptop.getImage());
			pst.setString(6,laptop.getRetailer());
			pst.setString(7,laptop.getCondition());
			pst.setDouble(8,laptop.getDiscount());
			
			pst.executeUpdate();
			
			
		}

		for(Map.Entry<String,VoiceAssistant> entry : SaxParserDataStore.voiceassistants.entrySet())
		{   
			String name = "voiceassistant";
	        VoiceAssistant voiceassistant = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,voiceassistant.getId());
			pst.setString(3,voiceassistant.getName());
			pst.setDouble(4,voiceassistant.getPrice());
			pst.setString(5,voiceassistant.getImage());
			pst.setString(6,voiceassistant.getRetailer());
			pst.setString(7,voiceassistant.getCondition());
			pst.setDouble(8,voiceassistant.getDiscount());
			
			pst.executeUpdate();
			
			
		}

		for(Map.Entry<String,FitnessWatch> entry : SaxParserDataStore.fitnesswatches.entrySet())
		{   
			String name = "fitnesswatch";
	        FitnessWatch fitnesswatch = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,fitnesswatch.getId());
			pst.setString(3,fitnesswatch.getName());
			pst.setDouble(4,fitnesswatch.getPrice());
			pst.setString(5,fitnesswatch.getImage());
			pst.setString(6,fitnesswatch.getRetailer());
			pst.setString(7,fitnesswatch.getCondition());
			pst.setDouble(8,fitnesswatch.getDiscount());
			
			pst.executeUpdate();
			
			
		}

		for(Map.Entry<String,SmartWatch> entry : SaxParserDataStore.smartwatches.entrySet())
		{   
			String name = "smartwatches";
	        SmartWatch smartwatches = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,smartwatches.getId());
			pst.setString(3,smartwatches.getName());
			pst.setDouble(4,smartwatches.getPrice());
			pst.setString(5,smartwatches.getImage());
			pst.setString(6,smartwatches.getRetailer());
			pst.setString(7,smartwatches.getCondition());
			pst.setDouble(8,smartwatches.getDiscount());
			
			pst.executeUpdate();
			
			
		}

		for(Map.Entry<String,Headphone> entry : SaxParserDataStore.headphones.entrySet())
		{   
			String name = "headphone";
	        Headphone headphone = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,headphone.getId());
			pst.setString(3,headphone.getName());
			pst.setDouble(4,headphone.getPrice());
			pst.setString(5,headphone.getImage());
			pst.setString(6,headphone.getRetailer());
			pst.setString(7,headphone.getCondition());
			pst.setDouble(8,headphone.getDiscount());
			
			pst.executeUpdate();
			
			
		}

		for(Map.Entry<String,WirelessPlan> entry : SaxParserDataStore.wirelessplans.entrySet())
		{   
			String name = "wirelessplan";
	        WirelessPlan wirelessplan = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,wirelessplan.getId());
			pst.setString(3,wirelessplan.getName());
			pst.setDouble(4,wirelessplan.getPrice());
			pst.setString(5,wirelessplan.getImage());
			pst.setString(6,wirelessplan.getRetailer());
			pst.setString(7,wirelessplan.getCondition());
			pst.setDouble(8,wirelessplan.getDiscount());
			
			pst.executeUpdate();
			
			
		}
		
	}catch(Exception e)
	{
  		e.printStackTrace();
	}
} 

public static HashMap<String,Product> getData()
{
		HashMap<String,Product> products=new HashMap<String,Product>();

		try
		{
			getConnection();
			Statement stmt=conn.createStatement();
			String selectCustomerQuery="select * from  productdetails";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
			while(rs.next())
			{	
				Product product = new Product();					
					product.setId(rs.getString("Id"));
					product.setType(rs.getString("ProductType"));
					product.setName(rs.getString("productName"));
					product.setDescription(rs.getString("ProductType"));
					product.setDisplay_under(rs.getString("ProductType"));
					product.setImageUrl(rs.getString("productImage"));
					product.setPrice(rs.getDouble("productPrice"));
					product.setDiscounAmt(rs.getDouble("productDiscount"));
					// product.setRebateAmt(rs.getDouble("rebateAmount"));
					// product.setProdcount(rs.getDouble("count"));
					//HashMap<String, Accessory> aMap = getAccessory(product.getId());
					//product.setAccessories(aMap);
					
					products.put(product.getId(),product);

			}
		}
		catch(Exception e)
		{
		e.printStackTrace();	
		}
		return products;			
}
	
}	