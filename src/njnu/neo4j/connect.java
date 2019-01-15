package njnu.neo4j;

import java.io.File;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import path.PathConfig;


public class connect {
	
	private GraphDatabaseFactory dbFactory;
	
	public GraphDatabaseService db;
	
	/**
	 * 构造函数------连接neo4j database
	 */
	public connect(){
		dbFactory=new GraphDatabaseFactory();
		
		db= dbFactory.newEmbeddedDatabase(PathConfig.NEO4JDATABASEPATH);
		
		System.out.println(PathConfig.NEO4JDATABASEPATH.getName()+"Database Load!");
		
	}
	
	
	
	
	/**
	 * 数据库关闭
	 * @param db
	 */
	public void registerShutdownHook(final GraphDatabaseService db) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                db.shutdown();
            }
        });
    }
	
	
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		connect neo4jconnect=new connect();
		
		
		/**
		 * 清空现有数据库
		 */
		clearDB C=new clearDB(neo4jconnect.db);
		C.clean();
		
		/**
		 * 开启操作
		 */
		try(Transaction tx = neo4jconnect.db.beginTx()){
			Node ge1 = null;
			/**
			 * 增加节点
			 */
			if(neo4jconnect.db.findNode(neo4jDatatype.nodetype.time, "order", "1")==null){
				ge1 = neo4jconnect.db.createNode(neo4jDatatype.nodetype.state);
				ge1.setProperty("order", "1");
				ge1.setProperty("sourceobject", "moutain");
				System.out.println("add note successfully!");
			}
			
			
			Node ge2 = neo4jconnect.db.createNode(neo4jDatatype.nodetype.time);
			ge2.setProperty("type", "point");
			ge2.setProperty("sourcestate", "ge1");
			
			Node ge3 = neo4jconnect.db.createNode(neo4jDatatype.nodetype.location);
			ge3.setProperty("type", "2");
			ge3.setProperty("sourcestate", "ge1");
			
			Node ge4 = neo4jconnect.db.createNode(neo4jDatatype.nodetype.attribute);
			ge4.setProperty("type", "essentialelement");
			ge4.setProperty("sourcestate", "ge1");
			
			Node ge5 = neo4jconnect.db.createNode(neo4jDatatype.nodetype.state);
			ge5.setProperty("order", "2");
			ge5.setProperty("sourceobject", "moutain");
			
			Node ge6 = neo4jconnect.db.createNode(neo4jDatatype.nodetype.time);
			ge6.setProperty("type", "point");
			ge6.setProperty("sourcestate", "ge5");
			
			Node ge7 = neo4jconnect.db.createNode(neo4jDatatype.nodetype.time);
			ge7.setProperty("type", "5555555555555");
			ge7.setProperty("sourcestate", "555555555555");
			
			/**
			 * 删除节点
			 */
			Node deletenode=neo4jconnect.db.findNode(neo4jDatatype.nodetype.time, "type", "5555555555555");
			deletenode.delete();
			
			/**
			 * 增加关系
			 */
			if(neo4jconnect.db.findNode(neo4jDatatype.nodetype.time, "order", "1")==null){
				Relationship r1 = ge1.createRelationshipTo(ge5,neo4jDatatype.relationtype.change);
			r1.setProperty("type","non-essential change");
			
			Relationship r2 = ge2.createRelationshipTo(ge6,neo4jDatatype.relationtype.relation);
			r1.setProperty("type","time relation");
			}
			
			
			
			/**
			 * 删除关系
			 */
			Relationship  deleterelation=ge2.getSingleRelationship(neo4jDatatype.relationtype.relation, Direction.INCOMING);
			//deleterelation.delete();
			
			
			
			/**
			 * 提交
			 */
			tx.success();
		}
		
		System.out.println("Done successfully");
		
		/**
		 * 关闭
		 */
		neo4jconnect.registerShutdownHook(neo4jconnect.db);
	}

}
