package com.Davi.poems.segment;

import com.Davi.poems.tools.analyze;
import com.hankcs.hanlp.seg.common.Term;

import java.util.List;

/**
 * Created by David on 2017/4/16.
 */
public class resultSet {

    private List<Term> resultList;
    private int size = 0;
    private String resultString;
    private String[] words,subjects;

    public resultSet(List<Term> resultList){
        //resultString [春雨/n, 纷纷/d, 何所/nr, 似/vg]
        this.resultList = resultList;
    }

    public String[] getWords() {
        String[] tmp = resultString.split(",");
        words = new String[tmp.length];
        StringBuilder sb = new StringBuilder();
        int count = 0 ;
        for (String s : tmp) {
            for (int i = 0; i < s.length() ; i++) {
                if (isChinese(s.charAt(i))){
                    sb.append(s.charAt(i));
                }
            }
            words[count++] = sb.toString();
        }
        return words;
    }
    public String[] getSubjects(){
        String[] tmp = resultString.split(",");
        subjects = new String[tmp.length];
        StringBuilder sb = new StringBuilder();
        int count = 0 ;
        for (String s : tmp) {
            for (int i = 0; i < s.length() ; i++) {
                if (isEnglish(String.valueOf(s.charAt(i)))){
                    sb.append(s.charAt(i));
                }
            }
            subjects[count++] = sb.toString();
        }

        return subjects;
    }
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FBB;// 根据字节码判断
    }
    public static boolean isEnglish(String c) {  return c.matches("^[a-zA-Z]*");}

    public static void main(String[] args) {
        String c = "A";
        String c1 = "z";
        if (isEnglish(c)){
            System.out.println("yes");
        }
        if (isEnglish(c1)){
            System.out.println("yes2");
        }
    }

}
