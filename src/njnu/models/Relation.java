package njnu.models;

import java.util.UUID;

//关系类型枚举
interface RelationTypes{
	public static final String time = "time";
	public static final String location = "location";
	public static final String attribute = "attribute";
	public static final String geoname = "geoname";
	public static final String unset = "unset";
}

public class Relation {
	// 关系类型，分为本质属性和非本质属性
	public enum relationtype{
		time,
		location,
		attribute,
		geoname,
		unset
	}
	
	public String id;
	
	public String relationword;
	
	public String sentence;
	
	public relationtype relationtype;
	
	public State startstate;
	
	public State endstate;
	
	public GeoObject startbelonggeoobject=null;
	
	public GeoObject endbelonggeoobject=null;

	
	public Time starttime = null;
	public Time endtime= null;
	public Location startlocation= null;
	public Location endlocation= null;
	public Attribute startattribute= null;
	public Attribute endattribute= null;
	
	
	
	public Relation(){
		this.id = UUID.randomUUID().toString();
		
		this.relationword = "";
		this.sentence = "";
		
		this.relationtype = relationtype.unset;
		
		this.startstate = new State();
		this.endstate = new State();
		
	}
	
	public String getId() {
		return id;
	}
	
	public String getRelationword() {
		return relationword;
	}
	
	public void setRelationword(String relationword) {
		this.relationword = relationword;
	}
	
	public String getSentence() {
		return sentence;
	}
	
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
	public relationtype getRelationtype() {
		return relationtype;
	}
	
	public void setRelationtype(relationtype relationtype) {
		
        switch(relationtype){
        case time:
        	this.starttime = new Time();
        	this.endtime =new Time();
        	break;
        case location:
        	this.startlocation = new Location();
        	this.endlocation = new Location();
        	break;
        case attribute:
        	this.startattribute = new Attribute();
        	this.endattribute = new Attribute();
            break;
        default:
           break;
        }
		
		this.relationtype = relationtype;
	}
	
	public State getStartstate() {
		return startstate;
	}
	public void setStartstate(State startstate) {
		this.startstate = startstate;
	}
	
	public State getEndstate() {
		return endstate;
	}
	
	public void setEndstate(State endstate) {
		this.endstate = endstate;
	}

	public GeoObject getStartbelonggeoobject() {
		return startbelonggeoobject;
	}

	public void setStartbelonggeoobject(GeoObject startbelonggeoobject) {
		this.startbelonggeoobject = startbelonggeoobject;
	}

	public GeoObject getEndbelonggeoobject() {
		return endbelonggeoobject;
	}

	public void setEndbelonggeoobject(GeoObject endbelonggeoobject) {
		this.endbelonggeoobject = endbelonggeoobject;
	}

	public Time getStarttime() {
		return starttime;
	}

	public void setStarttime(Time starttime) {
		this.starttime = starttime;
	}

	public Time getEndtime() {
		return endtime;
	}

	public void setEndtime(Time endtime) {
		this.endtime = endtime;
	}

	public Location getStartlocation() {
		return startlocation;
	}

	public void setStartlocation(Location startlocation) {
		this.startlocation = startlocation;
	}

	public Location getEndlocation() {
		return endlocation;
	}

	public void setEndlocation(Location endlocation) {
		this.endlocation = endlocation;
	}

	public Attribute getStartattribute() {
		return startattribute;
	}

	public void setStartattribute(Attribute startattribute) {
		this.startattribute = startattribute;
	}

	public Attribute getEndattribute() {
		return endattribute;
	}

	public void setEndattribute(Attribute endattribute) {
		this.endattribute = endattribute;
	}

	public String tostring(){
		String starttempelementinfo = "";
		String endtempelementinfo = "";
		switch (this.relationtype) {
		case time:
			starttempelementinfo= "起始时间:"+this.starttime.getTimeword();
			endtempelementinfo = "结束时间:"+this.endtime.getTimeword();
			break;
		case location:
			starttempelementinfo= "起始状态:"+this.startlocation.getLocword();
			endtempelementinfo = "结束状态:"+this.endlocation.getLocword();
			break;

		case attribute:
			starttempelementinfo= "起始属性:"+this.startattribute.getAttributeWord();
			endtempelementinfo = "结束属性:"+this.endattribute.getAttributeWord();
			break;

		default:
			starttempelementinfo= "起始属性:无";
			endtempelementinfo = "结束属性:无";
			break;
		}
		return "关系ID:"+this.id+
				"改变词汇:"+this.getRelationword()+
				"所属句:"+this.getSentence()+
				"关系类型:"+this.relationtype.toString()+
				"起始状态:"+ this.startstate.tostring()+
				"起始关系"+starttempelementinfo+
				"结束状态:"+ this.endstate.tostring()+
				"结束关系"+endtempelementinfo;
	} 
}
