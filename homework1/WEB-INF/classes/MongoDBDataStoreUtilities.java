import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.AggregationOutput;
import java.util.*;
                	
public class MongoDBDataStoreUtilities
{
static DBCollection myReviews;
public static DBCollection getConnection()
{
MongoClient mongo;
mongo = new MongoClient("localhost", 27017);

DB db = mongo.getDB("CustomerReviews");
 myReviews= db.getCollection("myReviews");	
return myReviews; 
}


public static String insertReview(String productname,String username,String producttype,String productmaker,String reviewrating,String reviewdate,String reviewtext,String retailerpin,String price,String retailercity,String retailerstate,String retailername,String sale,String rebate,String id,String age,String gender,String occupation)
{
	try
		{		
			getConnection();
			BasicDBObject doc = new BasicDBObject("title", "myReviews").
				append("userName", username).
				append("productName", productname).
				append("productType", producttype).
				append("productMaker", productmaker).
				append("reviewRating",Integer.parseInt(reviewrating)).
				append("reviewDate", reviewdate).
				append("reviewText", reviewtext).
				append("retailerpin", retailerpin).
				append("retailercity", retailercity).
				append("retailerstate", retailerstate).
				append("retailername", retailername).
				append("Product on Sale", sale).
				append("Manufacturer Rebate", rebate).
				append("User ID", id).
				append("User Age", age).
				append("User Gender", gender).
				append("User Occupation", occupation).
				append("price",Double.parseDouble(price)); 
			myReviews.insert(doc);
			return "Successfull";
		}
		catch(Exception e)
		{
		return "UnSuccessfull";
		}	
		
}

public static HashMap<String, ArrayList<Review>> selectReview()
{	
	HashMap<String, ArrayList<Review>> reviews=null;
	
	try
		{

	getConnection();
	DBCursor cursor = myReviews.find();
	reviews=new HashMap<String, ArrayList<Review>>();
	while (cursor.hasNext())
	{
			BasicDBObject obj = (BasicDBObject) cursor.next();				
	
		   if(!reviews.containsKey(obj.getString("productName")))
			{	
				ArrayList<Review> arr = new ArrayList<Review>();
				reviews.put(obj.getString("productName"), arr);
			}
			ArrayList<Review> listReview = reviews.get(obj.getString("productName"));		
			Review review =new Review(obj.getString("productName"),obj.getString("userName"),obj.getString("productType"),obj.getString("productMaker"),
				obj.getString("reviewRating"),obj.getString("reviewDate"),obj.getString("reviewText"),obj.getString("retailerpin"),obj.getString("price"),obj.getString("retailercity"),obj.getString("retailerstate"),obj.getString("retailername"),obj.getString("Product on Sale"),obj.getString("Manufacturer Rebate"),obj.getString("User ID"),obj.getString("User Age"),obj.getString("User Gender"),obj.getString("User Occupation"));
			//add to review hashmap
			listReview.add(review);
		
			}
 		return reviews;
		}
		catch(Exception e)
		{
		 reviews=null;
		 return reviews;	
		}	

     
	}
	

  public static  ArrayList <Bestrating> topProducts(){
	  ArrayList <Bestrating> Bestrate = new ArrayList <Bestrating> ();
	  try{
		  
	  getConnection();
	  DBObject groupProducts = new BasicDBObject("_id","$productName"); 
	  groupProducts.put("average",new BasicDBObject("$avg","$reviewRating"));
	  DBObject group = new BasicDBObject("$group",groupProducts);
	  DBObject limit=new BasicDBObject();
      limit=new BasicDBObject("$limit",5);
	  
	  DBObject sortFields = new BasicDBObject("average",-1);
	  DBObject sort = new BasicDBObject("$sort",sortFields);
	  AggregationOutput output = myReviews.aggregate(group,sort,limit);
      for (DBObject res : output.results()) {
        
		String zipcode =(res.get("_id")).toString();
        String count = (res.get("average")).toString();	
        Bestrating best = new Bestrating(zipcode,count);
		Bestrate.add(best);
	
	  }
	
	}catch (Exception e){ System.out.println(e.getMessage());}
   return Bestrate;
  }
  
  	  public static ArrayList <Mostsoldzip> mostsoldZip(){
	  ArrayList <Mostsoldzip> mostsoldzip = new ArrayList <Mostsoldzip> ();
	  try{
		  
	  getConnection();
      DBObject groupProducts = new BasicDBObject("_id","$retailerpin"); 
	  groupProducts.put("count",new BasicDBObject("$sum",1));
	  DBObject group = new BasicDBObject("$group",groupProducts);
	  DBObject limit=new BasicDBObject();
      limit=new BasicDBObject("$limit",5);
	  
	  DBObject sortFields = new BasicDBObject("count",-1);
	  DBObject sort = new BasicDBObject("$sort",sortFields);
	  AggregationOutput output = myReviews.aggregate(group,sort,limit);
      for (DBObject res : output.results()) {
        
		String zipcode =(res.get("_id")).toString();
        String count = (res.get("count")).toString();	
        Mostsoldzip mostsldzip = new Mostsoldzip(zipcode,count);
		mostsoldzip.add(mostsldzip);
	
	  }
	  
	 
	  
	}catch (Exception e){ System.out.println(e.getMessage());}
      return mostsoldzip;
  }
  
   public static ArrayList <Mostsold> mostsoldProducts(){
	  ArrayList <Mostsold> mostsold = new ArrayList <Mostsold> ();
	  try{
		  
	  
      getConnection();
      DBObject groupProducts = new BasicDBObject("_id","$productName"); 
	  groupProducts.put("count",new BasicDBObject("$sum",1));
	  DBObject group = new BasicDBObject("$group",groupProducts);
	  DBObject limit=new BasicDBObject();
      limit=new BasicDBObject("$limit",5);
	  
	  DBObject sortFields = new BasicDBObject("count",-1);
	  DBObject sort = new BasicDBObject("$sort",sortFields);
	  AggregationOutput output = myReviews.aggregate(group,sort,limit);
	  
      for (DBObject res : output.results()) {
	  
      
       
		String prodcutname =(res.get("_id")).toString();
        String count = (res.get("count")).toString();	
        Mostsold mostsld = new Mostsold(prodcutname,count);
		mostsold.add(mostsld);
	
	  }
	  
	 
	  
	}catch (Exception e){ System.out.println(e.getMessage());}
      return mostsold;
  }	

  //Get all the reviews grouped by product and zip code;
public static ArrayList<Review> selectReviewForChart() {

		
        ArrayList<Review> reviewList = new ArrayList<Review>();
        try {

            getConnection();
            Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
            dbObjIdMap.put("retailerpin", "$retailerpin");
            dbObjIdMap.put("productName", "$productName");
            DBObject groupFields = new BasicDBObject("_id", new BasicDBObject(dbObjIdMap));
            groupFields.put("count", new BasicDBObject("$sum", 1));
            DBObject group = new BasicDBObject("$group", groupFields);

            DBObject projectFields = new BasicDBObject("_id", 0);
            projectFields.put("retailerpin", "$_id");
            projectFields.put("productName", "$productName");
            projectFields.put("reviewCount", "$count");
            DBObject project = new BasicDBObject("$project", projectFields);

            DBObject sort = new BasicDBObject();
            sort.put("reviewCount", -1);

            DBObject orderby = new BasicDBObject();            
            orderby = new BasicDBObject("$sort",sort);
            

            AggregationOutput aggregate = myReviews.aggregate(group, project, orderby);

            for (DBObject result : aggregate.results()) {

                BasicDBObject obj = (BasicDBObject) result;
                Object o = com.mongodb.util.JSON.parse(obj.getString("retailerpin"));
                BasicDBObject dbObj = (BasicDBObject) o;
                Review review = new Review(dbObj.getString("productName"), dbObj.getString("retailerpin"),
                        obj.getString("reviewCount"), null);
                reviewList.add(review);
                
            }
            return reviewList;

        }

        catch (

        Exception e) {
            reviewList = null;
            
            return reviewList;
        }

    }
  

	
}	