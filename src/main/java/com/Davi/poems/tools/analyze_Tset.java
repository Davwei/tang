package com.Davi.poems.tools;

import com.Davi.poems.basic.event;
import com.Davi.poems.basic.operation;
import com.Davi.poems.basic.wordClass;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
/**
 * Created by wei on 2017/2/27.
 */
public class analyze_Tset {
    public static void main(String[] args) throws myException {
        Logger logger = Logger.getLogger(analyze_Tset.class);
        //两种分隔符
        /*analyze ana = new analyze("李白|杜甫&123456");
        for(int i = 0;i < ana.getAuthor().length;i++) {
            System.out.println(ana.getAuthor()[i]);
        }
        //只有一个作者
        analyze ana1 = new analyze("杜甫&123456");
        for(int i = 0;i < ana1.getAuthor().length;i++) {
            //System.out.println(ana);
            System.out.println(ana1.getAuthor()[i]);
        }
        //有两个以上分隔符
        analyze ana2 = new analyze("&李白杜甫&123456");
        for(int i = 0;i < ana2.getAuthor().length;i++) {
            System.out.println(ana2.getAuthor()[i]);
        }*/

        String str = "";
        analyze ana3 = new analyze();
        authorSource as = new authorSource();
        tangSource ts = new tangSource();


        //int size = ana3.getTagetString(str).size();

        /*
        ArrayList<tangClass> result = ana3.match(str,as,ts);
        System.out.println("精确匹配结果数量： "+result.size());
        Iterator<tangClass> iterator = result.iterator();
        while ( iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }*/



        /*ArrayList<tangClass> result1 = ana3.PMatch(str,as,ts);
        System.out.println("模糊匹配结果数量： "+result1.size());
        Iterator<tangClass> iterator1 = result1.iterator();
        while ( iterator1.hasNext()){
            System.out.println(iterator1.next().toString());
        }*/



        /*System.out.println("----------------------");
        for (int i = 0; i < ana3.getLength(str) ; i++) {
            System.out.println(ana3.getTagetString(str).get(i+1));
            //System.out.println(ana3.getPinYin(ana3.getTagetString(str).get(i+1)));
        }*/


        /*
        String context = "日照香炉生紫烟";
        if (ana3.possiblyMatch("日照",context)){
            System.out.println("matcheed!!!");
        }else{
            System.out.println("Not matched!");
        }


        String yun = "sing1";
        System.out.println(yun.length());
        System.out.println(yun.substring(0,4));
        //System.out.println(context.split("").length);

        String guang = "guang";
        int length = guang.length();
        String[] result = ana3.pinYinIs("光");
        for (String s:result) {
            //System.out.println(s.subSequence(0,length));
            if (s.subSequence(0,length).equals(guang)) {
                System.out.println("matched!!!!!");
            }
        }
        if (ana3.isMatch("床前***（guang）","床前明月光")){
            System.out.println("matched!");
        }
        if (ana3.possiblyMatch("床前***（guang）","床前明月光")){
            System.out.println("pmatched!");
        }
        */

        //System.out.println(ana3.PMatch("樱花").size());






        //ana3.judge("*(guang)","光");

        /*
        String strsplit = "&1";
        String[] resultset = strsplit.split("&");
        for (String s:resultset) {
            System.out.println(s);
        }
        */

        String strPinYin = "*（guang）";
        //System.out.println(ana3.getPinYin(strPinYin));


        String pairInput = "春眠不觉晓123";
        //System.out.println(ana3.getTagetFromRubbish(pairInput).size());
        //System.out.println(ana3.getKindsFromRubbish(pairInput).size());


        //System.out.println(ana3.isPair(2,(1<<31)-1));
        //System.out.println((Integer.toBinaryString((1<<31)-1)));

        /*
        String context = "寻寻觅觅，冷冷清清，凄凄惨惨戚戚。乍暖还寒时候，最难将息。三杯两盏淡酒，怎敌他、晚来风急。雁过也，正伤心，却是旧时相识。满地黄花堆积。";
        if (ana3.isMatch("寻寻觅觅",context)){
            System.out.println("matcheed!!!");
        }else{
            System.out.println("Not matched!");
        }*/



        System.out.println("----------------------");

        System.out.println(ana3.getPair(ana3.nomalSegment("大地在打工")));

        /*ArrayList<String> testArray = new ArrayList<>();
        testArray.add("nh");
        StringBuilder sb = new StringBuilder();
        if (testArray.contains(sb.append('n').append('h').toString())){
            System.out.println("yes");
        }
        System.out.println(testArray.indexOf("nh"));
        ArrayList<wordClass> testWordArray = new ArrayList<>();
        testWordArray.add(new wordClass("你",0));
        testWordArray.get(0).setCount(3);
        System.out.println(testWordArray.get(0).getCount());*/

        //System.out.println(ana3.getPair("无法无天"));
        //logger.debug("succeed");

        /*HashMap<Integer,String> testMap = new HashMap<>(3);
        testMap.put(Integer.valueOf("0"),"123");
        System.out.println(testMap.get(Integer.valueOf("0")));*/



    }
}
