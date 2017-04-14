package com.Davi.poems.tools;

import com.Davi.poems.basic.tangClass;
import com.Davi.poems.basic.wordClass;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by David on 2017/4/9.
 * 为了筛选常用字
 */
public class createOrderWordsList {
    tangSource ts = new tangSource();
    Iterator<tangClass> iterator = ts.getTangClassArrayList().iterator();
    StringBuilder sb;
    ArrayList<String> hasFound = new ArrayList<>();
    ArrayList<wordClass> result = new ArrayList<>();
    public int size ;
    public createOrderWordsList(){
        while (iterator.hasNext()) {
            tangClass tmp = iterator.next();
            String text = tmp.getContext();

            for (int i = 0; i < text.length(); i++) {
                if (isChinese(text.charAt(i))) {
                    sb = new StringBuilder();
                    String word = sb.append(text.charAt(i)).toString();
                    //TODO word ++
                    if (hasFound.contains(word)) {
                        int location = hasFound.indexOf(word);
                        result.get(location).setCount(result.get(location).getCount() + 1);
                    } else {
                        hasFound.add(word);
                        wordClass newWord = new wordClass(word, 1);
                        result.add(newWord);
                    }
                }
            }

        }


        int length = result.size();
        int max,oldmax;
        for (int i = 0; i < length; i++) {
            max = i;
            for (int j = 1; j < length; j++) {
                wordClass tmpMax = result.get(i);
                wordClass tmpOld;
                if (result.get(j).getCount() > tmpMax.getCount()){
                    oldmax = max;
                    max = j;
                    tmpOld = result.get(oldmax);
                    tmpMax = result.get(max);

                    //swap
                    result.remove(oldmax);
                    result.add(oldmax,tmpMax);
                    result.remove(max);
                    result.add(max,tmpOld);

                }
            }


        }
        //System.out.println(hasFound.size());
        Iterator<wordClass> iterator1 = result.iterator();
        while (iterator1.hasNext()){
            size = size + iterator1.next().getCount();
        }

    }

    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    public static void main(String[] args) {
        createOrderWordsList cowl = new createOrderWordsList();




        //System.out.println(cowl.result.get(0).getCount());

    }


}
