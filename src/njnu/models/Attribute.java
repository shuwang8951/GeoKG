package njnu.models;

import java.util.Iterator;
import java.util.UUID;

//属性类型
interface AttTypes{
	public static final String nature = "nature";
	public static final String unnature = "unnature";
	public static final String unset = "unset";
}

public class Attribute {
	// 属性类型枚举
	public enum atttype{
		nature,
		unnature,
		unset
	}
	
	//属性ID
	public String id;

	//属性词
	public String attributeWord;
	
	//所属句
	public String sentence;
	
	//属性类型
	public atttype attType;
	
    //所属的地理实体状态
	public State belongstate=null;
	
	//所属的地理实体
	public GeoObject belongobject=null;	
	
	//无参构造函数
	public Attribute(){
		this.id = UUID.randomUUID().toString();
    	this.attributeWord = "";
    	this.sentence = "";

    	this.attType = attType.unset;
	}
	//有参构造函数
//	public Attribute(){
//		
//	}
	
	
	public String getId() {
		return id;
	}
	
	public String getAttributeWord(){
		return attributeWord;
	}
	
	public void setAttributeWord(String attributeWord) {
		this.attributeWord = attributeWord;
	}
	
	public String getSentence() {
		return sentence;
	}
	
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
	public atttype getAttType() {
		return attType;
	}
	
	public void setAttType(atttype attType) {
		this.attType = attType;
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
	 * @return  返回地理实体属性以及基本信息
	 */
		public String tostring() {
			
			
			return "属性id:" + this.id +
				   ";属性词: "+this.attributeWord+
				   "; 所属句: " + this.sentence + 
				   ";属性类型:"+this.attType.toString();
		}
	
}
