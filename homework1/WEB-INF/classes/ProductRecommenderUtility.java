import java.io.*;
import java.sql.*;
import java.io.IOException;
import java.util.*;

public class ProductRecommenderUtility{
	
	static Connection conn = null;
    static String message;
	
	public static String getConnection()
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestdealdatabase","root","root");							
			message="Successfull";
			return message;
		}
		catch(SQLException e)
		{
			 message="unsuccessful";
		     return message;
		}
		catch(Exception e)
		{
			 message="unsuccessful";
		     return message;
		}
	}

	public HashMap<String,String> readOutputFile(){

		String TOMCAT_HOME = System.getProperty("catalina.home");
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
		HashMap<String,String> prodRecmMap = new HashMap<String,String>();
		try {

            br = new BufferedReader(new FileReader(new File(TOMCAT_HOME+"\\webapps\\homework1\\output.csv")));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] prod_recm = line.split(cvsSplitBy,2);
				prodRecmMap.put(prod_recm[0],prod_recm[1]);
            }
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
		
		return prodRecmMap;
	}
	
	public static Product getProduct(String product){
		
		Product prod=new Product();
		try 
		{
			String msg = getConnection();
			String selectProd="select * from  Productdetails where Id=?";
			PreparedStatement pst = conn.prepareStatement(selectProd);
			pst.setString(1,product);
			ResultSet rs = pst.executeQuery();
		
			while(rs.next())
			{	
					//Product prod = new Product();					
					prod.setId(rs.getString("Id"));
					prod.setType(rs.getString("ProductType"));
					prod.setName(rs.getString("productName"));
					prod.setDescription(rs.getString("ProductType"));
					prod.setDisplay_under(rs.getString("ProductType"));
					prod.setImageUrl(rs.getString("productImage"));
					prod.setPrice(rs.getDouble("productPrice"));
					prod.setDiscounAmt(rs.getDouble("productDiscount"));
					// product.setRebateAmt(rs.getDouble("rebateAmount"));
					// product.setProdcount(rs.getDouble("count"));
					//HashMap<String, Accessory> aMap = getAccessory(product.getId());
					//product.setAccessories(aMap);
			}
			rs.close();
			pst.close();
			conn.close();
		}
		catch(Exception e)
		{
		}
		return prod;	
	}
}