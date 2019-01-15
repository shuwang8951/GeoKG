package njnu.extracgeoknowledge;

import java.util.ArrayList;
import java.util.UUID;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

import njnu.neo4j.connect;
import njnu.neo4j.neo4jDatatype;

public class LinkEntity {
	
	private Iterable<Relationship> objectrelations=null;
	
	private Iterable<Relationship> staterelations=null;
	
	private Iterable<Relationship> targetobjectrelations=null;
	
	private Iterable<Relationship> targetlocationrelations=null;
	
	
	public void linklocation2geoobject(){
		//连接数据库
		connect neo4jconnect=new connect();
		
		/**
		 * 开启操作
		 */
		try(Transaction tx = neo4jconnect.db.beginTx()){
			
			ResourceIterator<Node> geoobjects=neo4jconnect.db.findNodes(neo4jDatatype.nodetype.geoobject);
			
			Node eachgeoobject=null;

			ArrayList<Node> states=new ArrayList<>();
			
			ArrayList<Node> locations=new ArrayList<>();
			
			int i=1;
			
			Node tempstate=null;
			
			Node templocation=null;
			
			Node objectnodes=null;
			
			Node targetobjecct=null;
			
			Node temptargetstate=null;
			
			Node targetstate=null;
			
			Node temptargetlocation=null;
			
			Node targetlocation=null;
			
			Relationship locationrelationship=null;
			
			
			//得到每一个地理实体节点
			while(geoobjects.hasNext()){
				
				eachgeoobject=geoobjects.next();
				System.out.println("正在处理第"+(i++)+"个地理对象......."+eachgeoobject.getProperty("name").toString());
				
				objectrelations=eachgeoobject.getRelationships();
				for(Relationship r:objectrelations){
					tempstate=r.getStartNode();
					
					//得到每一个地理实体状态
					if(tempstate.hasProperty("stateorder")){
						states.add(tempstate);
					}else{}
				}
				
				//得到每一个地理实体状态的位置
				for(Node eachgeoobjectstate:states){
					if(eachgeoobjectstate!=null){
						staterelations=eachgeoobjectstate.getRelationships();
						for(Relationship rl:staterelations){
							templocation=rl.getStartNode();
						
							if(templocation.hasProperty("locword")){
								locations.add(templocation);
							}else{}
						}
					}
					
					//找到存在的地理实体节点
					for(Node eachobjectlocation:locations){
						//System.out.println(eachobjectlocation.getProperty("locword"));
						objectnodes=neo4jconnect.db.findNode(neo4jDatatype.nodetype.geoobject, "name", eachobjectlocation.getProperty("locword"));
						if(objectnodes!=null){
							targetobjecct=neo4jconnect.db.findNode(neo4jDatatype.nodetype.geoobject, "name", eachobjectlocation.getProperty("locword"));
						
							//找到目标地理实体状态
							targetobjectrelations=targetobjecct.getRelationships();
							for(Relationship tor:targetobjectrelations){
								temptargetstate=tor.getStartNode();
								
								//System.out.println(temptargetstate.getProperty("stateorder"));
								
								if(temptargetstate.hasProperty("stateorder")){
									if(temptargetstate.getProperty("stateorder").toString().equals("0")){
										targetstate=temptargetstate;
										
										//找到固有位置
										targetlocationrelations=targetstate.getRelationships();
										for(Relationship tlr:targetlocationrelations){
											temptargetlocation=tlr.getStartNode();
											
											
											if(temptargetlocation.hasProperty("固有")){
												targetlocation=temptargetlocation;
												
												//判断是否已经存在relation关系
												if(eachobjectlocation.hasRelationship(neo4jDatatype.relationtype.relation)){
													
												}else{
													//建立relation
													locationrelationship=targetlocation.createRelationshipTo(eachobjectlocation,neo4jDatatype.relationtype.relation);
													locationrelationship.setProperty("id", UUID.randomUUID().toString());
													locationrelationship.setProperty("relationword", "");
													locationrelationship.setProperty("sentence", eachobjectlocation.getProperty("sentence"));
													//System.out.println("成功构建关系");
											
													//提交
													tx.success();
												}
																						
											}
																				
										}
										
									}else{}
									
								}else{}
							}
				
						}
					}
				
				}
				System.out.println("第"+(i-1)+"个地理对象处理完成.");
				tempstate=null;
				
				templocation=null;
				
				objectnodes=null;
				
				targetobjecct=null;
				
				targetstate=null;
				
				temptargetlocation=null;
				
				targetlocation=null;
				
				locationrelationship=null;
				
				states.clear();
				
				locations.clear();
				
				System.gc();
				
			}				
			
			
			//提交
			tx.success();
		}
		
		//关闭
		neo4jconnect.registerShutdownHook(neo4jconnect.db);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkEntity le=new LinkEntity();
		le.linklocation2geoobject();

	}

}
