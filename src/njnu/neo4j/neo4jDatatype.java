package njnu.neo4j;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;

public class neo4jDatatype {
	
	/**
	 * 设置标签名称
	 * @author wslab
	 *
	 */
	public enum nodetype implements Label {
		geoobject,state,time,location,attribute;
	}
	
	
	/**
	 * 设置关系名称
	 * @param args
	 */
	public enum relationtype implements RelationshipType{
		belong,change,relation;
	}

}
