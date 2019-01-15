package njnu.extractrela;

import java.util.ArrayList;

public class RelationExtract {
	
	//spatial relation list
	public ArrayList<String> srlist;
	
	//temporal relation list
	public ArrayList<String> trlist;
	
	//attribute relation list
	public ArrayList<String> arlist;
	
	//constructor
	public RelationExtract(){
		srlist=new ArrayList<String>();
		
		trlist=new ArrayList<String>();
		
		arlist=new ArrayList<String>();
	}
	
	//get relation
	public void getRelations(String paragraph){
		
		Spatialrelation sr=new Spatialrelation();
		Temporalrelation tr=new Temporalrelation();
		Attributerelation ar=new Attributerelation();
		
		this.srlist=sr.extractSR(paragraph);
		
		this.trlist=tr.extractTR(paragraph);
		
		this.arlist=ar.extractAR(paragraph);
		
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String temp="位于内蒙古自治区境内大兴安岭中段西麓";
		
		RelationExtract re=new RelationExtract();
		re.getRelations(temp);
		System.out.println("location relation："+re.srlist.size());
		for(String sr:re.srlist){
			System.out.println(sr);
		}
		System.out.println("location relation："+re.trlist.size());
		for(String tr:re.trlist){
			System.out.println(tr);
		}
		System.out.println("location relation："+re.arlist.size());
		for(String ar:re.arlist){
			System.out.println(ar);
		}
		

	}

}
