package njnu.extracgeoknowledge;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

import path.PathConfig;
import njnu.models.Attribute;
import njnu.models.Change;
import njnu.models.Location;
import njnu.models.Relation;
import njnu.models.Relation.relationtype;
import njnu.models.State;
import njnu.neo4j.clearDB;
import njnu.neo4j.connect;
import njnu.neo4j.neo4jDatatype;
import njnu.utility.FileProcessing;

public class ProcessCorpus {
	
	private String corpuspath;
	
	public String eachcorpusfile;
	
	public ObtainGeoknowledge og;
	
	public ProcessCorpus(){
		corpuspath=PathConfig.TESTCORPUSFILESPATHS;
		//corpuspath=PathConfig.CORPUSFILESPATHS;
		//corpuspath=PathConfig.CORPUSESUMFILESPATHS;
		eachcorpusfile="";
		og=new ObtainGeoknowledge();
	}
	
	public void getKnowledge(){
		//获取语料列表名称
		String[] filenames=FileProcessing.getfile(corpuspath);
		
		//连接数据库
		connect neo4jconnect=new connect();
		//清空现有数据库
		clearDB C=new clearDB(neo4jconnect.db);
		C.clean();
		
		//新建节点，关系
		Node tempgeoobjectnode = null;
		Node tempstatenode = null;
		Node temptimenode = null;
		Node templocationnode = null;
		Node tempattributenode = null;
		Relationship tempchangerelationship=null;
		Relationship temprelationrelationship=null;
		
		//得到每项语料内容
		for(int i=0;i<filenames.length;i++){
			System.out.println("#########################################");
			System.out.println("正在处理第"+(i+1)+"篇语料......");
			System.out.println(filenames[i]);
			eachcorpusfile=FileProcessing.readFile(PathConfig.CORPUSFILESPATHS+"/"+filenames[i]);
			
			og=new ObtainGeoknowledge();
			og.getKnowledge(eachcorpusfile);
			
			og.printCurrentGeoobject();
			
			//写入数据库
			try(Transaction tx = neo4jconnect.db.beginTx()){
				
				//新建地理实体节点
				if(neo4jconnect.db.findNode(neo4jDatatype.nodetype.geoobject, "id", og.textgeoobject.id)==null){
					
					if(neo4jconnect.db.findNode(neo4jDatatype.nodetype.geoobject, "name", og.textgeoobject.name)!=null){
						
						tempgeoobjectnode =neo4jconnect.db.findNode(neo4jDatatype.nodetype.geoobject, "name", og.textgeoobject.name);
						
						
					}else{
						
						//不存在地理实体节点
						tempgeoobjectnode = neo4jconnect.db.createNode(neo4jDatatype.nodetype.geoobject);
						tempgeoobjectnode.setProperty("id", og.textgeoobject.id);
						tempgeoobjectnode.setProperty("name", og.textgeoobject.name);
						
					}
					
					
				}else{
					//存在地理实体节点
					tempgeoobjectnode = neo4jconnect.db.findNode(neo4jDatatype.nodetype.geoobject, "id", og.textgeoobject.id);
				}
				
				//新建状态节点
				for(State state:og.textgeoobject.states){
					if(neo4jconnect.db.findNode(neo4jDatatype.nodetype.state, "id", state.id)==null){
						//不存在状态节点，新建状态节点
						tempstatenode=neo4jconnect.db.createNode(neo4jDatatype.nodetype.state);
						tempstatenode.setProperty("id", state.id);
						tempstatenode.setProperty("stateorder", state.stateorder);
						//构建状态与从属地理实体间关系
						tempstatenode.createRelationshipTo(tempgeoobjectnode,neo4jDatatype.relationtype.belong);
						
						//新建时间节点
						temptimenode=neo4jconnect.db.createNode(neo4jDatatype.nodetype.time);
						temptimenode.setProperty("id", state.time.id);
						temptimenode.setProperty("timeword", state.time.timeword);
						temptimenode.setProperty("sentence", state.time.sentence);
						//构建时间与从属状态间关系
						temptimenode.createRelationshipTo(tempstatenode,neo4jDatatype.relationtype.belong);
						
						//新建每个位置节点
						for(Location loc:state.location){
							//新建单个位置节点
							templocationnode=neo4jconnect.db.createNode(neo4jDatatype.nodetype.location);
							templocationnode.setProperty("id", loc.id);
							templocationnode.setProperty("locword", loc.locword);
							templocationnode.setProperty("sentence", loc.sentence);
							//构建位置与从属状态间关系
							templocationnode.createRelationshipTo(tempstatenode,neo4jDatatype.relationtype.belong);
						}
						
						//新建每个属性节点
						for(Attribute attr:state.attributes){
							//新建单个位置节点
							tempattributenode=neo4jconnect.db.createNode(neo4jDatatype.nodetype.attribute);
							tempattributenode.setProperty("id", attr.id);
							tempattributenode.setProperty("attributeWord", attr.attributeWord);
							tempattributenode.setProperty("sentence", attr.sentence);
							//构建位置与从属状态间关系
							tempattributenode.createRelationshipTo(tempstatenode,neo4jDatatype.relationtype.belong);
						}
						
					}else{
						//存在状态节点
						tempstatenode=neo4jconnect.db.findNode(neo4jDatatype.nodetype.state, "id", state.id);
						
						//新建每个属性节点
						for(Attribute attr:state.attributes){
							//新建单个位置节点
							tempattributenode=neo4jconnect.db.createNode(neo4jDatatype.nodetype.attribute);
							tempattributenode.setProperty("id", attr.id);
							tempattributenode.setProperty("attributeWord", attr.attributeWord);
							tempattributenode.setProperty("sentence", attr.sentence);
							//构建位置与从属状态间关系
							tempattributenode.createRelationshipTo(tempstatenode,neo4jDatatype.relationtype.belong);
						}
						
					}
				}
				
				//新建改变
				for(Change chg:og.textgeoobject.changes){
					Node tempstartstate	=neo4jconnect.db.findNode(neo4jDatatype.nodetype.state, "id", chg.startstate.id);
					Node tempendstate	=neo4jconnect.db.findNode(neo4jDatatype.nodetype.state, "id", chg.endstate.id);
					if(tempstartstate!=null && tempendstate!=null){
						tempchangerelationship=tempstartstate.createRelationshipTo(tempendstate,neo4jDatatype.relationtype.change);
						tempchangerelationship.setProperty("id", chg.id);
						tempchangerelationship.setProperty("changeword", chg.changeword);
						tempchangerelationship.setProperty("sentence", chg.sentence);
					}else{
						//System.out.println(og.textgeoobject.name+":"+"改变构建不完全，状态存在缺失。");
					}	
				}
				
				//新建关系
				for(Relation rel:og.textgeoobject.relations){
					
					//地名关系
					if(rel.relationtype==relationtype.geoname){
						//已有关联地理实体
						if(neo4jconnect.db.findNode(neo4jDatatype.nodetype.geoobject, "id", rel.startbelonggeoobject.id)!=null){
							//Node tempstartgeoobject=neo4jconnect.db.findNodes(neo4jDatatype.nodetype.geoobject, "name", rel.startbelonggeoobject.name).next();
							Node tempstartstate=neo4jconnect.db.findNode(neo4jDatatype.nodetype.state, "id", rel.startstate.id);
							Node tempstartlocation=neo4jconnect.db.findNode(neo4jDatatype.nodetype.location, "id", rel.startlocation.id);
							
							if(tempstartlocation==null){
								tempstartlocation=neo4jconnect.db.createNode(neo4jDatatype.nodetype.location);
								tempstartlocation.setProperty("id", rel.endlocation.id);
								tempstartlocation.setProperty("locword", rel.endlocation.locword);
								tempstartlocation.setProperty("sentence", rel.endlocation.sentence);
							}

							if(tempstartstate!=null && tempstartlocation!=null){
								//找出end node
								Node tempendlocation=neo4jconnect.db.findNode(neo4jDatatype.nodetype.location, "id", rel.endlocation.id);
								temprelationrelationship=tempstartlocation.createRelationshipTo(tempendlocation,neo4jDatatype.relationtype.relation);
								temprelationrelationship.setProperty("id", rel.id);
								temprelationrelationship.setProperty("relationword", rel.relationword);
								temprelationrelationship.setProperty("sentence", rel.sentence);
							}else{
								//System.out.println(rel.startbelonggeoobject.name);
								//System.out.println("关联地理实体构建不完全。");
							}
							
							
						}else{
						//不存在关联地理实体
							if(neo4jconnect.db.findNode(neo4jDatatype.nodetype.geoobject, "name", rel.startbelonggeoobject.name)!=null){
								Node tempstartgeoobject=neo4jconnect.db.findNode(neo4jDatatype.nodetype.geoobject, "name", rel.startbelonggeoobject.name);
								
								Node tempstartstate = null;
								
								Node tempstartlocation = null;
								
								Iterable<Relationship> objectrelations=tempstartgeoobject.getRelationships();
								for(Relationship r:objectrelations){
									Node tempstate=r.getStartNode();
									if(tempstate.getProperty("stateorder").equals("0")){
										tempstartstate=tempstate;
									}else{
										
									}
								}
								
								if(tempstartstate!=null){
									Iterable<Relationship> staterelations=tempstartstate.getRelationships();
									for(Relationship r:staterelations){
										Node templocation=r.getStartNode();
										if(templocation.getProperty("time").equals("固有")){
											tempstartlocation=templocation;
										}else{
											
										}
									}
								}
								
								
								if(tempstartstate!=null && tempstartlocation!=null){
									//找出end node
									ResourceIterator<Node> nodes=neo4jconnect.db.findNodes(neo4jDatatype.nodetype.location, "id", rel.endlocation.id);
									Node tempendlocation=nodes.next();
									//建立关系
									temprelationrelationship=tempstartlocation.createRelationshipTo(tempendlocation,neo4jDatatype.relationtype.relation);
									temprelationrelationship.setProperty("id", rel.id);
									temprelationrelationship.setProperty("relationword", rel.relationword);
									temprelationrelationship.setProperty("sentence", rel.sentence);
								}
											
								
							}else{
								Node tempstartgeoobject=neo4jconnect.db.createNode(neo4jDatatype.nodetype.geoobject);
								tempstartgeoobject.setProperty("id", rel.startbelonggeoobject.id);
								tempstartgeoobject.setProperty("name", rel.startbelonggeoobject.name);
								//System.out.println(rel.startbelonggeoobject.name+".........................关联地理实体节点构造完成");
								
								Node tempstartstate=neo4jconnect.db.createNode(neo4jDatatype.nodetype.state);
								tempstartstate.setProperty("id", rel.startstate.id);
								tempstartstate.setProperty("stateorder", rel.startstate.stateorder);
								tempstartstate.createRelationshipTo(tempstartgeoobject,neo4jDatatype.relationtype.belong);
								//时间
								Node tempstarttime=neo4jconnect.db.createNode(neo4jDatatype.nodetype.time);
								tempstarttime.setProperty("id", rel.startstate.time.id);
								tempstarttime.setProperty("timeword", rel.startstate.time.timeword);
								tempstarttime.createRelationshipTo(tempstartstate,neo4jDatatype.relationtype.belong);
								//位置
								Node tempstartlocation=neo4jconnect.db.createNode(neo4jDatatype.nodetype.location);
								tempstartlocation.setProperty("id", rel.startlocation.id);
								tempstartlocation.setProperty("固有", rel.startlocation.locword);
								tempstartlocation.createRelationshipTo(tempstartstate,neo4jDatatype.relationtype.belong);
								
								//找出end node
								ResourceIterator<Node> nodes=neo4jconnect.db.findNodes(neo4jDatatype.nodetype.location, "id", rel.endlocation.id);
								Node tempendlocation=nodes.next();
								//建立关系
								temprelationrelationship=tempstartlocation.createRelationshipTo(tempendlocation,neo4jDatatype.relationtype.relation);
								temprelationrelationship.setProperty("id", rel.id);
								temprelationrelationship.setProperty("relationword", rel.relationword);
								temprelationrelationship.setProperty("sentence", rel.sentence);
								
							}
							
							
						}
						
					}else{
						//通用关系
						Node tempstartstate	=neo4jconnect.db.findNode(neo4jDatatype.nodetype.state, "id", rel.startstate.id);
						Node tempendstate	=neo4jconnect.db.findNode(neo4jDatatype.nodetype.state, "id", rel.endstate.id);
						if(tempstartstate!=null && tempendstate!=null){
							temprelationrelationship=tempstartstate.createRelationshipTo(tempendstate,neo4jDatatype.relationtype.change);
							temprelationrelationship.setProperty("id", rel.id);
							temprelationrelationship.setProperty("relationword", rel.relationword);
							temprelationrelationship.setProperty("sentence", rel.sentence);
						}else{
							//System.out.println(og.textgeoobject.name+":"+"关系构建不完全，状态存在缺失。");
						}	
						
					}
					
					
				}
				
				
				
				
				/**
				 * 提交
				 */
				tx.success();
				System.out.println("第"+(i+1)+"篇语料输入数据库。");
			}
			
			
			System.out.println("第"+(i+1)+"篇语料处理完成。");
			System.out.println("#########################################");
		}
		/**
		 * 关闭数据库
		 */
		neo4jconnect.registerShutdownHook(neo4jconnect.db);
		
	}
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProcessCorpus pc=new ProcessCorpus();
		pc.getKnowledge();

	}

}
