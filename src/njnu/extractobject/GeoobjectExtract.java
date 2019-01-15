package njnu.extractobject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeoobjectExtract {
	
	/**
	 * 从地理百科语料获取文本描述地理实体名称
	 * @param txt
	 * @return
	 */
	public String getGeoobjectfromtxt(String txt){
		String geoobjectname="";
		
		String regularbegin ="(.*?)(\\）)(.*$)";
		//String regularbegin ="(.*)（[a-zA-Z]{2,40}）";
		
		Pattern pt = Pattern.compile(regularbegin);	
		Matcher match = pt.matcher(txt);
		if (match.find()) {
			geoobjectname = match.group();
			geoobjectname=geoobjectname.split("（")[0];
			
		}else{
			
			System.out.println("地理实体名称，不符合文本命名规则！");
			System.out.println(txt);
		}
		
		return geoobjectname;
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String txtfile="安溪县（AnxiXian）福建省泉州市辖县，中国茶叶基地之一。位于省境东南部，晋江上游。面积2933平方公里，人口91.62万。县府驻凤城镇。五代南唐置清溪县，宋改为安溪县。县境位于戴云山脉东南坡。地形以湖头—石坪为界，可分为：西部（内安溪）为戴云山脉的主体部分，地势高峻；东部（外安溪）以低山、丘陵为主，并镶嵌有若干向东南开口的马蹄形盆地。西溪自西北流向东南，在盆地内的沿溪两岸，发育了一片冲积平原，为主要农耕地区。西部气候属中亚热带，东部属南亚热带。粮食作物以水稻为主。安溪茶园面积约0.67万公顷，居全省各县市首位。盛产乌龙茶，其珍品“铁观音”远销30多国家和地区。矿产资源较丰富，有煤、铁、锰、铅、锌、石灰岩和石墨等。潘田铁矿是全国重要的富矿。轻工业以制茶为主。交通运输以公路为主，新建的漳（平）—泉（州）铁路已通车到县境剑斗。（赵昭昞）";
		
		GeoobjectExtract ge=new GeoobjectExtract();
		System.out.println(ge.getGeoobjectfromtxt(txtfile));

	}

}
