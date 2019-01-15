package njnu.models;

import java.util.UUID;

//改变类型枚举
interface ChangeTypes{
	public static final String nature = "nature";
	public static final String unnature = "unnature";
	public static final String unset = "unset";
}

public class Change {
	// 改变类型，分为本质属性和非本质属性
	public enum changetype{
		nature,
		unnature,
		unset
	}
	
	public String id;
	
	public String changeword;
	
	public String sentence;
	
	public State startstate;
	
	public State endstate;
	
	public changetype changetype;
	
	public GeoObject startbelonggeoobject=null;
	
	public GeoObject endbelonggeoobject=null;
	
	public Change(){
		this.id = UUID.randomUUID().toString();
		
		this.changeword ="";
		
		this.sentence="";
		

		this.startstate = new State();
		
		this.endstate = new State();
		
		this.changetype = changetype.unset;

	}
	
	public String getId() {
		return id;
	}
	
	public String getChangeword() {
		return changeword;
	}

	public void setChangeword(String changeword) {
		this.changeword = changeword;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
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

	public changetype getChangetype() {
		return changetype;
	}

	public void setChangetype(changetype changetype) {
		this.changetype = changetype;
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

	public String tostring(){
		return "改变ID:"+this.id+
				"；改变词汇:"+this.getChangeword()+
				"；所属句:"+this.getSentence()+
				"；改变类型:"+this.changetype.toString()+
				"；开始状态:"+this.startstate.toString()+
				"；结束状态:"+this.endstate.toString();
				
				
	} 
}
