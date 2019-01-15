package njnu.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

interface Types{
	public static final String point = "point";
	public static final String range = "range";
	public static final String description = "description";
	public static final String unset = "unset";
}

interface timeReference{
	public static final String commonality = "commonality";
	public static final String relativity = "relativity";
	public static final String fuzziness = "fuzziness";
	public static final String continuity = "continuity";
	public static final String periodicity = "periodicity";
}

/**
 * 
 * @author Prime
 *
 */
public class Time {
	//时间类型，分为点时间，段时间，描述时间
	public enum timetype{
		point,
		range,
		description,
		unset
	}
	//时间参考类型
    public enum treferencetype{
    	commonality,
    	relativity,
    	fuzziness,
    	continuity,
    	periodicity
	}
    
	//表示时间的ID
	public String id;

	//时间词
	public String timeword;

	//所属句
	public String sentence;	
  
    //时间类型
	public timetype timetype;  
	
	//参考信息
    public Map treference;
    
    //所属的地理实体状态
	public State belongstate=null;
	
	//所属的地理实体
	public GeoObject belongobject=null;	

    
    public Time(){
    	this.id = UUID.randomUUID().toString();

    	this.timeword = "";
    	
    	this.sentence = "";
    	
    	this.timetype = timetype.unset;
    	
    	treference=new HashMap();
    	
    	this.treference.put(treferencetype.commonality, "");
    	this.treference.put(treferencetype.continuity, "");
    	this.treference.put(treferencetype.fuzziness, "");
    	this.treference.put(treferencetype.periodicity, "");
    	this.treference.put(treferencetype.relativity, "");
    }

	public String getId() {
		return id;
	}

	public String getTimeword() {
		return timeword;
	}

	public void setTimeword(String timeword) {
		this.timeword = timeword;
	}


	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
	public timetype getTimetype() {
		return timetype;
	}

	public void setTimetype(timetype timetype) {
		this.timetype = timetype;
	}
	
	public Map getTreference() {
		return treference;
	}

	public void setTreference(treferencetype key,String value) {
		this.treference.put(key, value);
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
 * 2018.08.09
 * @return 返回该时间状态下的时间信息以及地理实体相关信息
 */
	public String tostring() {
		

		//时间参考
		String treference = "time reference information: ";
        //循环输出time reference  
        Iterator it = this.treference.keySet().iterator();
        while(it.hasNext()) {
            String key = it.next().toString();  
            treference=treference+key+ ":";  
            treference=treference+this.treference.get(key)+"  ";
        }
		
		return "时间id: "+this.id+
			   ";时间词:" + this.timeword + 
			   ";所属句: " + this.sentence + 
			   ";时间类型:"+this.timetype.toString()+
			   ";时间参考:"+treference;
	}
	
}
