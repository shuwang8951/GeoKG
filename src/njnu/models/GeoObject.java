package njnu.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 地理实体对象
 * @author wslab
 * @version 2018.8.17
 */
public class GeoObject {
	//地理实体ID
	public String id;
	//地理实体名称
	public String name;
	//地理实体状态list
	public List<State> states ;
	//地理实体变化list
	public List<Change> changes ;
	//地理实体关系list
	public List<Relation> relations ;
	
	public GeoObject(){
    	this.id = UUID.randomUUID().toString();
    	this.name = null;
    	//初始化地理实体时，至少有一个状态
    	states = new ArrayList<State>();
    	this.states.add(new State());
    	this.states.get(0).time.setTimeword("长期");;
    	//change
    	changes = new ArrayList<Change>();
    	//relation
    	relations = new ArrayList<Relation>();

	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}
	
	/**
	 * 添加state
	 * @param state
	 */
	public void addStates(State state) {
		this.states.add(state);
	}

	public List<Change> getChanges() {
		return changes;
	}

	public void setChanges(List<Change> changes) {
		this.changes = changes;
	}
	/**
	 * 添加change
	 * @param change
	 */
	public void addChanges(Change change) {
		this.changes.add(change) ;
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}
	
	/**
	 * 添加relation
	 * @param relations
	 */
	public void addRelations(Relation relation) {
		this.relations.add(relation);
	}
	
	public String tostring(){
		return "地理实体名称: "+this.name+
			   ";地理实体ID: "+this.id+
			   ";地理实体状态数目: "+this.states.size()+
			   ";地理实体变化数目: "+this.changes.size()+
			   ";地理实体关系数目: "+this.relations.size();
			   
	}
	
}
