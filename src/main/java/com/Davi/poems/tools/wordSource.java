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
 * Created by David on 2017/3/17.
 */
public class wordSource {

    Logger logger = Logger.getLogger(wordSource.class);
    //private String PATH = "A:/ssdworkspace/tangUI/src/com/Davi/poems/source/pingyinzidian.xml";
    //private String PATH = "src/main/resources/pingyinzidian.xml";
    private ArrayList<wordClass> wordClassArrayList;
    public wordSource(){

        long start = System.currentTimeMillis();

        String path = this.getClass().getResource("/pingyinzidian.xml").toString();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(path);
            NodeList wordList = doc.getElementsByTagName("pingzezidian");
            wordClassArrayList = new ArrayList<wordClass>();

            for (int i = 0; i < wordList.getLength(); i++) {
                Node node = wordList.item(i);
                NodeList children = node.getChildNodes();
                wordClass wc = new wordClass();
                for (int j = 0; j < children.getLength(); j++) {
                    if (children.item(j).getNodeType() == Node.ELEMENT_NODE){
                        String word ,pingYins ;
                        int pingZe = 0;
                        if (children.item(j).getNodeName() != null && children.item(j).getFirstChild() != null) {
                            if (children.item(j).getNodeName().equals("字符")) {
                                word = children.item(j).getFirstChild().getNodeValue();
                                wc.setWord(word);
                                //System.out.println(word);
                            }
                            if (children.item(j).getNodeName().equals("读音")) {
                                pingYins = children.item(j).getFirstChild().getNodeValue();
                                if (pingYins != null) {
                                    //经过试验 当前变量可以分割
                                    String[] toPingYin = pingYins.split(" ");
                                    //规范数据，去掉花括号
                                    for (int k = 0; k < toPingYin.length; k++) {
                                        if (toPingYin[k].contains("{") && toPingYin[k].contains("}")) {
                                            String tmp = toPingYin[k];
                                            StringBuilder sb = new StringBuilder();
                                            for (int l = 0; l < tmp.length(); l++) {
                                                if (tmp.charAt(l) == '{' || tmp.charAt(l) == '}') {
                                                } else
                                                    sb.append(tmp.charAt(l));
                                            }
                                            toPingYin[k] = sb.toString();
                                        }
                                    }
                                    //System.out.println(toPingYin[0]);
                                    wc.setPinYin(toPingYin);
                                }


                            }
                            if (1 == 0)//平仄数据待修复
                                if (children.item(j).getNodeName().equals("平仄")) {
                                    if (children.item(j).getFirstChild().getNodeValue() != null) {
                                        pingZe = Integer.valueOf(children.item(j).getFirstChild().getNodeValue());
                                        wc.setPingZe(pingZe);
                                        //System.out.println(pingZe);
                                    }
                                }

                        }
                    }
                }
                //必须在这个位置
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
    public ArrayList<wordClass> getWordClassArrayList(){
        return this.wordClassArrayList;
    }

    public static void main(String[] args) {
        wordSource ws = new wordSource();
        Iterator<wordClass> iterator = ws.getWordClassArrayList().iterator();
        if (1 == 1)
        while (iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }

}
