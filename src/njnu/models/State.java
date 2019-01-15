package njnu.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * 状态类
 * @author wslab
 * @version 2018.8.17
 */

public class State {

	//状态ID
	public String id;
	//状态序号
	public int stateorder;

	//该状态地理对象对应的时间
	public Time time;
	//该状态地理对象对应的位置
	public List<Location> location;
	//该状态地理对象对应的属性
	public List<Attribute> attributes;

	//所属地理对象
	public GeoObject belonggeoobject=null;

	
	//构造函数
	public State(){
		this.id = UUID.randomUUID().toString();
		//staticstate
		this.stateorder = 0;

		//time
		time=new Time();
		//location
		location=new ArrayList<Location>();
		//attributes		
		attributes = new ArrayList<Attribute>();


	}
	
	public String getId() {
		return id;
	}
	
	public int getStateorder() {
		return stateorder;
	}
	
	public void setStateorder(int stateorder) {
		this.stateorder = stateorder;
	}

	public Time getTime() {
		return time;
	}
	
	public void setTime(Time time) {
		this.time = time;
	}
	
	public List<Location> getLocation() {
		return location;
	}

	public void setLocation(List<Location> location) {
		this.location = location;
	}
	/**
	 * 
	 * @param location
	 */
	public void addLocation(Location location) {
		this.location.add(location);
	}
	

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	/**
	 * 
	 * @param attributes
	 */
	public void addAttributes(Attribute attributes) {
		this.attributes.add(attributes);
	}
	
	public GeoObject getBelonggeoobject() {
		return belonggeoobject;
	}

	public void setBelonggeoobject(GeoObject belonggeoobject) {
		this.belonggeoobject = belonggeoobject;
	}
	
	/**
	 * 2018.08.10
	 * @return 返回状态的基本信息以及对应的位置、时间以及属性信息
	 */
		public String tostring() {

			return "状态ID:"+this.id+
				   ";״状态序号: "+ this.stateorder+
				   ";时间词: "+this.time.getTimeword()+
				   ";位置词数目: "+this.location.size()+
				   ";属性数量: "+this.attributes.size();
		}
	
}
