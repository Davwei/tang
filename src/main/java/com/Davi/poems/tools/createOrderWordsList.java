package com.Davi.poems.tools;

import com.Davi.poems.basic.tangClass;
import com.Davi.poems.basic.wordClass;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by David on 2017/4/9.
 * 为了筛选常用字
 * 每一次排序用时44s+ 提高运行效率可以改进算法
 */
public class createOrderWordsList {

    Logger logger = Logger.getLogger(createOrderWordsList.class);
    DocumentBuilderFactory dbf;
    DocumentBuilder db;
    Document doc ;
    TransformerFactory tff;
    Transformer tf;
    DOMSource domSource;
    ByteArrayOutputStream bos;
    File file;

    FileOutputStream out;

    StreamResult xmlResult;

    tangSource ts = new tangSource();
    Iterator<tangClass> iterator = ts.getTangClassArrayList().iterator();
    StringBuilder sb;
    ArrayList<String> hasFound = new ArrayList<>();
    ArrayList<wordClass> result = new ArrayList<>();
    public int size ;
    final ArrayList<Element> words = new ArrayList<Element>();
    public createOrderWordsList(){


        dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        doc = db.newDocument();
        doc.setXmlVersion("1.0");



        while (iterator.hasNext()) {
            tangClass tmp = iterator.next();
            String text = tmp.getContext();

            for (int i = 0; i < text.length(); i++) {
                if (isChinese(text.charAt(i))) {
                    sb = new StringBuilder();
                    String word = sb.append(text.charAt(i)).toString();
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
            wordClass tmpWord = iterator1.next();
            words.add(this.getWord(tmpWord.getWord(),tmpWord.getCount()));
            size = size + tmpWord.getCount();
        }


    }
    public void buildXML(){

        //String xmlStr;
        try {

            logger.info("words size is "+words.size());
            Element root = doc.createElement("root");
            doc.appendChild(root);

            for (int i = 0; i < words.size(); i++) {
                root.appendChild(words.get(i));
            }
            tff = TransformerFactory.newInstance();
            tf = tff.newTransformer();
            domSource = new DOMSource(doc);
            bos = new ByteArrayOutputStream();
            tf.transform(domSource, new StreamResult(bos));
            //xmlStr = bos.toString();

            //save file
            this.saveFile();






        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void saveFile() throws IOException, TransformerException {
        file = new File("/wordCountList.xml");
        if (!file.exists()){
            file.createNewFile();
        }
        out = new FileOutputStream(file);
        xmlResult = new StreamResult(out);
        tf.transform(domSource,xmlResult);
    }
    public void setWords (String str,int count){
        for (String s: str.split("")) {
            words.add(getWord(s,count));
        }
    }

    public Element getWord(String word,int count){
        Element w = doc.createElement("字符");
        w.setTextContent(word);
        return this.setWordPingZe(w,count);
    }

    public Element setWordPingZe(Element word,int count){
        Element countsize = doc.createElement("出现次数");
        countsize.setTextContent(String.valueOf(count));
        word.appendChild(countsize);
        return word;
    }

    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    public static void main(String[] args) {
        createOrderWordsList cowl = new createOrderWordsList();
        cowl.buildXML();

        //System.out.println(cowl.result.get(0).getCount());

    }


}
