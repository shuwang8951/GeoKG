package njnu.neo4j;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.IndexManager;

/**
 * 清空数据库
 * @author SHUWANG
 * @since 2018.7.26
 */
public class clearDB {
    private GraphDatabaseService graph;

    
    public clearDB(GraphDatabaseService graph) {
        this.graph =  graph;
        clean();
        System.out.println("------database has been cleaned successfully!-------");
    }

    public Map<String, Object> clean() {
        Map<String, Object> result = new HashMap<String, Object>();
        Transaction tx = graph.beginTx();
        try {
            removeNodes(result);
            clearIndex(result);
            tx.success();
        } finally {
            tx.close();
        }
        return result;
    }

    private void removeNodes(Map<String, Object> result) {
        int nodes = 0, relationships = 0;
        for (Node node : graph.getAllNodes()) {
            for (Relationship rel : node.getRelationships(Direction.OUTGOING)) {
                rel.delete();
                relationships++;
            }
            node.delete();
            nodes++;
        }
        result.put("nodes", nodes);
        result.put("relationships", relationships);

    }

    private void clearIndex(Map<String, Object> result) {
        IndexManager indexManager = graph.index();
        result.put("node-indexes", Arrays.asList(indexManager.nodeIndexNames()));
        result.put("relationship-indexes", Arrays.asList(indexManager.relationshipIndexNames()));
        for (String ix : indexManager.nodeIndexNames()) {
            indexManager.forNodes(ix).delete();
        }
        for (String ix : indexManager.relationshipIndexNames()) {
            indexManager.forRelationships(ix).delete();
        }
    }
}