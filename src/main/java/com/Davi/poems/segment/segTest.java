package com.Davi.poems.segment;

import com.Davi.poems.tools.analyze;
import com.Davi.poems.tools.myException;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by David on 2017/4/16.
 */
public class segTest {
    public static boolean isSameSubject(Nature a, Nature b){
        if (a.equals(b)){
            return true;
        }else {
            return false;
        }
    }


    public static void main(String[] args) {
        System.out.println(HanLP.segment("春雨纷纷何所似"));
        analyze ana = new analyze();


        List<Term> resultTmp = HanLP.segment("春雨纷纷何所似"); //标准分词
        List<String> resultString = new LinkedList<>();

        long start = System.currentTimeMillis();
        for (Term t : resultTmp) {
            System.out.print(t.word + " & ");
            String tmp = "";

            try {
                while (true) {
                    tmp = ana.getPair(t.word);
                    System.out.println(HanLP.segment(tmp).size());
                    System.out.println(HanLP.segment(tmp).get(0));
                    if (HanLP.segment(tmp).size() == 1) {
                        if (isSameSubject(HanLP.segment(tmp).get(0).nature,t.nature)){
                            break;
                        }
                    }
                }
            } catch (myException e) {
                e.printStackTrace();
            }
            resultString.add(tmp);

        }
        long end = System.currentTimeMillis();
        for (String s: resultString) {
            System.out.print(s);
        }
        System.out.println("\n"+"Time "+(end - start)+ "ms");




    }
}
