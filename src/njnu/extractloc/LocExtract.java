package njnu.extractloc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import njnu.models.*;
import njnu.utility.JudgeAttrType;
import njnu.utility.JudgeLocType;
import njnu.utility.Tools;

public class LocExtract {
	
	//handlp分词
	public static Segment segment = HanLP.newSegment().enablePlaceRecognize(true);

	// 抽取地点
	public static List<String> loc(String str) {

		List s = new ArrayList();
		List<Term> termList = segment.seg(str);
//		System.out.println(termList);
		for (int k = 0; k < termList.size(); k++) {
			if (termList.get(k).nature.startsWith("ns")) {
				s.add(termList.get(k).word);
			}
		}

		return s;
	}
	
	/**
	 * whether the text contains loction word
	 * @param text
	 * @return true-contain ; false-do not contain
	 */
	public static boolean containLoc(String text){

		LocExtract lr=new LocExtract();
		List<String> l=lr.loc(text);

		return !l.isEmpty();
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String temp="中国西南地区名山，又名点苍山。位于云南省大理白族自治州中部，北始邓川，南止下关，东濒洱海，西临漾濞江河谷盆地。南北长50 余公里，东西宽约10 余公里。山峰海拔一般3000～4000 米，古冰川地貌形态典型完整，莲花峰、白云峰、鹤云峰、三阳峰、兰峰、雪人峰、应乐峰、玉局峰、马龙峰等19 座山峰，多为冰川侵蚀形成的角峰，如锥状石塔，各具特色。最高峰马龙峰海拔4122 米，高出洱海水面2000 余米。白云溪、双鸳溪、阳溪等18 条溪流，多发源于古冰斗或古冰川悬谷内，山麓的巨大洪积扇也为冰碛物。溪涧一泻千丈，落入洱海，蔚为十九峰十八溪奇观。苍山山色青翠，高峭山峰冬半年积雪不化，倒影映入洱海中，形成“银苍素洱”之胜景。苍山为经强烈抬升的断块山地，东西两侧极不对称。东侧为陡峻的断层崖壁，西侧为相对和缓的斜坡，斜坡又被河流分割成波伏山丘，缓降至黑惠江谷地。组成山地的岩石多为强烈变质的片岩、片麻岩及大理岩等，中部有花岗岩侵入体。其中，片麻岩、大理岩可作建筑材料。尤以大理石质地纯，花纹奇特，为工艺美术品原料和高级建筑材料。苍山山麓，洱海之滨，多风景名胜区，著名的有天生桥、下关温泉、三塔寺、蛇骨塔及蝴蝶泉等，为大理自然风景区的重要组成部分。";
		LocExtract le=new LocExtract();
		List locs=le.loc(temp);
		for(int i=0;i<locs.size();i++){
			System.out.println(locs.get(i).toString());
		}
	}
}
