package njnu.neo4j;



import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import path.PathConfig;

public class TEST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		GraphDatabaseService db = dbFactory.newEmbeddedDatabase(PathConfig.NEO4JDATABASEPATH);
		try (Transaction tx = db.beginTx()) {
			// Perform DB operations	
			tx.success();
		}
		

	}

}
