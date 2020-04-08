import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Product { 
    String name;
    String id;
    String description;
    String display_under;
    String imageUrl;    
    Double price;
    Double discounAmt; 
    Double rebateAmt;
	Double prodcount;
	Double cnt;
	Double Total;
	String date;
	String retailer;
	// String condition;
	String type;
    Map<String,Accessory> accessories;
    
    public Product(){
  //       this.id=id;
		// this.name=name;
		// this.price=price;
		// this.imageUrl=imageUrl;
		// this.retailer = retailer;
		// this.condition=condition;
		// this.type=type;
		// this.discounAmt = discounAmt;

        accessories=new HashMap<String,Accessory>();
    }

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public Double getcnt() {
		return cnt;
	}

	public void setcnt(Double cnt) {
		this.cnt = cnt;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public Double getTotal() {
		return Total;
	}

	public void setTotal(Double Total) {
		this.Total = Total;
	}

	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}
	
	public String getRetailer() {
		return retailer;
	}


	public String getdate() {
		return date;
	}

	public void setdate(String date) {
		this.date = date;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public String getId() {
		return id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDisplay_under(String display_under) {
		this.display_under = display_under;
	}

	public String getDisplay_under() {
		return display_under;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPrice() {
		return price;
	}

	public void setAccessories(Map<String,Accessory> accessories) {
		this.accessories = accessories;
	}

	public Map<String,Accessory> getAccessories() {
		return accessories;
	}

	public void setDiscounAmt(Double discounAmt) {
		this.discounAmt = discounAmt;
	}

	public Double getDiscounAmt() {
		return discounAmt;
	}

	public void setRebateAmt(Double rebateAmt) {
		this.rebateAmt = rebateAmt;
	}

	public Double getRebateAmt() {
		return rebateAmt;
	}
public void setProdcount(Double prodcount) {
		this.prodcount = prodcount;
	}

	public Double getProdcount() {
		return prodcount;
	}
	
  

}