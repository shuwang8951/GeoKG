package njnu.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;


interface locTypes{
	public static final String placename = "placename";
	public static final String address = "address";
	public static final String coordinate = "coordinate";
	public static final String description = "description";
	public static final String unset = "unset";
}

interface locReference{
	public static final String spacetype = "spacetype";
	public static final String spaecrefe = "spaecrefe";
	public static final String commonality = "commonality";
	public static final String relativity = "relativity";
	public static final String fuzziness = "fuzziness";
}

public class Location {
	// 位置类型，分为地名，地址，坐标和描述
	public enum loctype{
		placename,
		address,
		coordinate,
		description,
		unset
	}
	//位置参考类型，分为空间类型，空间参考，一致性，相关性和模糊性
    public enum lreferencetype{
    	spacetype,
    	spaecrefe,
    	commonality,
    	relativity,
    	fuzziness
	}
  
	//表示位置的ID
	public String id;
	//位置词
	public String locword;
	//所属句
	public String sentence;
    //位置类型
    public  loctype loctype;	
	//参考信息
    public Map lreference;
    
    //所属的地理实体状态
	public State belongstate=null;
	
	//所属的地理实体
	public GeoObject belongobject=null;	
    
	public Location(){
    	this.id = UUID.randomUUID().toString();

    	this.sentence = "";
    	this.locword = "";

    	this.loctype = loctype.unset;
    	
    	lreference=new HashMap();
    	this.lreference.put(lreferencetype.spacetype, "");
    	this.lreference.put(lreferencetype.spaecrefe, "");
    	this.lreference.put(lreferencetype.commonality, "");
    	this.lreference.put(lreferencetype.relativity, "");
    	this.lreference.put(lreferencetype.fuzziness, "");
	}

	public String getId() {
		return id;
	}
	
	public String getLocword() {
		return locword;
	}
	
	public void setLocword(String locword) {
		this.locword = locword;
	}
	
	public String getSentence() {
		return sentence;
	}
	
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
	public loctype getLoctype() {
		return loctype;
	}
	
	public void setLoctype(loctype loctype) {
		this.loctype = loctype;
	}
	
	public Map getLreference() {
		return lreference;
	}

	public void setLreference(Map lreference) {
		this.lreference = lreference;
	}

	public State getBelongstate() {
		return belongstate;
	}

	public void setBelongstate(State belongstate) {
		this.belongstate = belongstate;
	}

	public GeoObject getBelongobject() {
		return belongobject;
	}

	public void setBelongobject(GeoObject belongobject) {
		this.belongobject = belongobject;
	}

	/**
	 * 2018.08.10
	 * @return 返回该位置状态下的时间信息以及地理实体相关信息
	 */
		public String tostring() {
			
			//位置参考
			String lreference = "location reference information: ";
	        //循环输出location reference  
	        Iterator it = this.lreference.keySet().iterator();
	        while(it.hasNext()) {
	            String key = it.next().toString();  
	            lreference=lreference+key+ ":";  
	            lreference=lreference+this.lreference.get(key)+"  ";
//	            System.out.println(lreference);
	        }
			
			return "位置id: "+this.id+
					";位置词汇:" + this.locword + 
					
					"; 所属语句: " + this.sentence + 

					";位置类型:"+this.loctype.toString()+
					";位置参考:"+lreference;
		}
	
}
