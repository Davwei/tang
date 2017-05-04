package com.Davi.poems.tools;

import com.Davi.poems.basic.wordClass;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by David on 2017/4/16.
 */
public class wordCountSource {

    Logger logger = Logger.getLogger(wordCountSource.class);

    private ArrayList<wordClass> wordClassArrayList;

    public wordCountSource(){
        long start = System.currentTimeMillis();
        DocumentBuilder db;
        String path = this.getClass().getResource("/wordCountList.xml").toString();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(path);
            NodeList wordList = doc.getElementsByTagName("字符");
            wordClassArrayList = new ArrayList<wordClass>();
            //System.out.println(wordList.getLength());
            for (int i = 0; i < wordList.getLength(); i++) {
                Node node = wordList.item(i);
                NodeList children = node.getChildNodes();
                wordClass wc = new wordClass();
                String word;
                word = node.getFirstChild().getNodeValue();
                wc.setWord(word);
                for (int j = 0; j < children.getLength(); j++) {

                    if (children.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        if (children.item(j).getNodeName().equals("出现次数")) {
                            wc.setCount(Integer.valueOf(children.item(j).getFirstChild().getNodeValue()));
                            //System.out.println(children.item(j).getFirstChild().getNodeValue());
                        }

                    }
                }

                wordClassArrayList.add(wc);

            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        logger.info("用时 "+ (end - start)+"ms");

    }


    public ArrayList<wordClass> getWordClassArrayList() {
        return wordClassArrayList;
    }
    public int getCountSum(){
        int countSum = 0;
        Iterator<wordClass> iterator = getWordClassArrayList().iterator();
        while (iterator.hasNext()){
            countSum = countSum + iterator.next().getCount();
        }
        return countSum;
    }
}
