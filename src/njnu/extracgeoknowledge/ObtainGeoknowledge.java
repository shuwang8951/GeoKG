package njnu.extracgeoknowledge;

import java.util.ArrayList;
import java.util.List;

import njnu.extractattr.AttrExtract;
import njnu.extractchange.ChangeExtract;
import njnu.extractloc.LocExtract;
import njnu.extractobject.GeoobjectExtract;
import njnu.extractrela.RelationExtract;
import njnu.extracttime.TimeExtract;
import njnu.models.Attribute;
import njnu.models.Change;
import njnu.models.GeoObject;
import njnu.models.Location;
import njnu.models.Relation;
import njnu.models.Relation.relationtype;
import njnu.models.State;
import njnu.models.Time;
import njnu.utility.FileProcessing;
import njnu.utility.JudgeAttrType;
import njnu.utility.JudgeChgType;
import njnu.utility.JudgeLocType;
import njnu.utility.JudgeTimeType;
import njnu.utility.TextProcessing;

public class ObtainGeoknowledge {
	
	public GeoObject textgeoobject;
	
	public String corpustext;
	
	public ObtainGeoknowledge(){
		textgeoobject=new GeoObject();
		corpustext="";
	}
	
	/**
	 * 从单篇语料获取地理知识
	 * @param textcorpus
	 * 
	 */
	public void getKnowledge(String textcorpus){

		
		//获取文本描述的地理对象名称
		GeoobjectExtract ge=new GeoobjectExtract();
		textgeoobject.setName(ge.getGeoobjectfromtxt(textcorpus));
		this.corpustext=TextProcessing.deleteHead(textcorpus);
		//System.out.println(this.corpustext);
		
		//分句
		ArrayList<String> sentences=TextProcessing.paraSplit(corpustext);
		
		//每一句
		for(String sentence:sentences){
			State state = null;
			Time time = null;
			Location location = null;
			ArrayList<Location> locations=new ArrayList<Location>();
			Attribute tempattribute = null;
			ArrayList<Attribute> attributes=new ArrayList<Attribute>();
			Relation temprelation = null;
			ArrayList<Relation> relations=new ArrayList<Relation>();
			Change tempchange = null;
			ArrayList<Change> changes=new ArrayList<Change>();
			
			boolean hasnewstate=false;
			
			try {
				//时间抽取
				TimeExtract timeextract = new TimeExtract();
				List<String> timel=timeextract.fore(sentence);
				if(!timel.isEmpty()){
					time=new Time();
					time.setTimeword(timel.get(0));
					time.setSentence(sentence);
					time.setTimetype(JudgeTimeType.Judge(time.timeword, textgeoobject.name));
					time.setBelongobject(textgeoobject);
				}
				
				
				//位置抽取
				LocExtract locationextract=new LocExtract();
				List<String> locl=locationextract.loc(sentence);
				for(String loc:locl){
					location=new Location();
					location.setLocword(loc);
					location.setSentence(sentence);
					location.setLoctype(JudgeLocType.Judge(location.locword, textgeoobject.name));
					location.setBelongobject(textgeoobject);
					locations.add(location);
				}
				
				
				//属性抽取
				AttrExtract attributeextract=new AttrExtract();
				List<String> attributel= attributeextract.getAttribute(sentence);
				for(String attr:attributel){
					tempattribute=new Attribute();
					tempattribute.setAttributeWord(attr);
					tempattribute.setSentence(sentence);
					tempattribute.setAttType(JudgeAttrType.Judge(tempattribute.attributeWord, textgeoobject.name));
					tempattribute.setBelongobject(textgeoobject);
					attributes.add(tempattribute);
				}
				
				//改变抽取
				ChangeExtract changeextract=new ChangeExtract();
				List<String> changel=changeextract.getChange(sentence);
				for(String chg:changel){
					tempchange=new Change();
					tempchange.setChangeword(chg);
					tempchange.setSentence(sentence);
					tempchange.setChangetype(JudgeChgType.Judge(tempchange.changeword, textgeoobject.name));
					tempchange.setStartbelonggeoobject(textgeoobject);
					tempchange.setEndbelonggeoobject(textgeoobject);
					changes.add(tempchange);
				}
				
				//关系抽取
				RelationExtract relationextract=new RelationExtract();
				relationextract.getRelations(sentence);
				for(String spatial:relationextract.srlist){
					temprelation=new Relation();
					temprelation.setRelationword(spatial);
					temprelation.setSentence(sentence);
					temprelation.setRelationtype(relationtype.location);
					relations.add(temprelation);
					
				}
				for(String temporal:relationextract.trlist){
					temprelation=new Relation();
					temprelation.setRelationword(temporal);
					temprelation.setSentence(sentence);
					temprelation.setRelationtype(relationtype.time);
					relations.add(temprelation);
					
				}
				for(String attribute:relationextract.arlist){
					temprelation=new Relation();
					temprelation.setRelationword(attribute);
					temprelation.setSentence(sentence);
					temprelation.setRelationtype(relationtype.attribute);
					relations.add(temprelation);
					
				}
				
				
				/**
				 * 状态的添加
				 */
				if(timel.isEmpty()){
					hasnewstate=false;
					//不存在时间，认为句内描述的是固有状态
					state=textgeoobject.states.get(0);
					state.setBelonggeoobject(textgeoobject);
					
					//句内位置，属性赋予状态
					for(Location l:locations){
						state.addLocation(l);
					}
					for(Attribute a:attributes){
						state.addAttributes(a);
					}
					
				}else{
					hasnewstate=true;
					//存在时间，认为有新状态
					state=new State();
					state.setStateorder(textgeoobject.getStates().size());
					state.setBelonggeoobject(textgeoobject);
					
					//句内时间，位置，属性赋予状态
					state.setTime(time);
					for(Location l:locations){
						state.addLocation(l);
					}
					for(Attribute a:attributes){
						state.addAttributes(a);
					}
					
					//将新状态加入地理实体
					textgeoobject.addStates(state);
					
				}
				
				//状态排序````````````````````todo````````````
				
				//添加改变
				if(changel.isEmpty()){
				}else{
					if(hasnewstate==true){
						//存在新状态
						//添加改变
						for(Change chg:changes){
							chg.setEndstate(state);
							chg.setStartstate(textgeoobject.getStates().get(state.stateorder-1));
							
							
							chg.setStartbelonggeoobject(textgeoobject);
							chg.setEndbelonggeoobject(textgeoobject);
						}
						textgeoobject.setChanges(changes);
						
					}else{
						//添加新状态
						hasnewstate=true;
						state=new State();
						state.setStateorder(textgeoobject.getStates().size());
						state.setBelonggeoobject(textgeoobject);
						
						//句内时间，位置，属性赋予状态
						for(Location l:locations){
							state.addLocation(l);
						}
						for(Attribute a:attributes){
							state.addAttributes(a);
						}
						textgeoobject.addStates(state);
						
						//添加改变
						for(Change chg:changes){
							chg.setEndstate(state);
							chg.setStartstate(textgeoobject.getStates().get(state.stateorder-1));
							chg.setStartbelonggeoobject(textgeoobject);
							chg.setEndbelonggeoobject(textgeoobject);
						}
						textgeoobject.setChanges(changes);	
					}
				}

				
				//添加关系
				if(!locations.isEmpty()){
					//存在其他地名，与其他地理实体存在关系
					GeoObject otherobject=new GeoObject();
					otherobject.setName(locations.get(0).locword);
					if(hasnewstate=true){
						//句内存在新状态
						for(Relation rel:relations){
							rel.setStartstate(state);
							rel.setStartbelonggeoobject(textgeoobject);
							rel.setEndstate(otherobject.getStates().get(0));
							rel.setEndbelonggeoobject(otherobject);	
							
							textgeoobject.addRelations(rel);
						}
						
						
					}else{
						//句内不存在新状态
						for(Relation rel:relations){
							rel.setStartstate(textgeoobject.getStates().get(0));
							rel.setStartbelonggeoobject(textgeoobject);
							rel.setEndstate(otherobject.getStates().get(0));
							rel.setEndbelonggeoobject(otherobject);		

							textgeoobject.addRelations(rel);
						}
						
					}
					
				}else{
					//不存在其他地名，自身存在关系
					if(hasnewstate=true){
						//句内存在新状态
						for(Relation rel:relations){
							rel.setStartstate(state);
							rel.setStartbelonggeoobject(textgeoobject);
							rel.setEndstate(textgeoobject.getStates().get(0));
							rel.setEndbelonggeoobject(textgeoobject);
							
							textgeoobject.addRelations(rel);												
						}
						
					}else{
						//句内不存在新状态
						for(Relation rel:relations){
							rel.setStartstate(textgeoobject.getStates().get(0));
							rel.setStartbelonggeoobject(textgeoobject);
							rel.setEndstate(textgeoobject.getStates().get(0));
							rel.setEndbelonggeoobject(textgeoobject);

							textgeoobject.addRelations(rel);
						}
					}								
					
				}
				
				//将状态中地名表达转为关系
				for(Location l:locations){
					Relation tempr=new Relation();
					tempr.setRelationword("");
					tempr.setSentence(l.sentence);
					//建立关联地理对象
					GeoObject relateobject=new GeoObject();
					relateobject.setName(l.locword);
					//System.out.println(l.locword);
					tempr.setStartbelonggeoobject(relateobject);
					tempr.setStartstate(relateobject.states.get(0));
					//建立关联位置
					Location relatelocation=new Location();
					relatelocation.setLocword("");
					relatelocation.setSentence(l.sentence);
					relateobject.states.get(0).addLocation(relatelocation);
					
					tempr.setStartlocation(relatelocation);
					
					
					//关联本位置
					tempr.setEndbelonggeoobject(textgeoobject);
					tempr.setEndstate(state);
					tempr.setEndlocation(l);
					tempr.setRelationtype(relationtype.geoname);
					
					//System.out.println(l.locword);
					tempr.startbelonggeoobject.setName(l.locword);;
					
					textgeoobject.addRelations(tempr);;
					//System.out.println(textgeoobject.relations.get(textgeoobject.relations.size()-1).startlocation.id);
				}
				
				
				
						
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}	
		
	}
	
	public void printCurrentGeoobject(){
		//地理实体基本信息
				System.out.println("---------------");
				System.out.println("地理实体基本信息:");
				System.out.println(this.textgeoobject.tostring());
				//状态
				System.out.println("---------------");
				System.out.println("状态基本信息:");
				for(State st:this.textgeoobject.states){
					System.out.println(st.tostring());
					System.out.println("---------------");
					System.out.println("状态-"+st.stateorder+"-中时间基本信息:");
					System.out.println(st.time.tostring());
					System.out.println("---------------");
					System.out.println("状态-"+st.stateorder+"-中位置基本信息:");
					for(Location l:st.location){
						System.out.println(l.tostring());
					}
					System.out.println("---------------");
					System.out.println("状态-"+st.stateorder+"-中属性基本信息:");
					for(Attribute a:st.attributes){
						System.out.println(a.tostring());
					}
					System.out.println("---------------");
				}
				System.out.println("---------------");
				System.out.println("改变基本信息:");
				for(Change ch:this.textgeoobject.changes){
					System.out.println(ch.tostring());
				}
				System.out.println("---------------");
				System.out.println("关系基本信息:");
				for(Relation re:this.textgeoobject.relations){
					System.out.println(re.tostring());
				}
		
	}
	
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path="./files/corpus/安溪县.txt";
		ObtainGeoknowledge ogk=new ObtainGeoknowledge();
		ogk.corpustext=FileProcessing.readFile(path);
		ogk.corpustext=TextProcessing.deleteSpace(ogk.corpustext);
		System.out.println(ogk.corpustext);
		
		ogk.getKnowledge(ogk.corpustext);
		//输出地理对象信息
		ogk.printCurrentGeoobject();
		
		List<Relation> t=ogk.textgeoobject.relations;
		for(Relation r:t){
			System.out.println(r.startbelonggeoobject.name);
		}
		

	}

}
