package com.Davi.poems.tools;


import com.Davi.poems.basic.wordClass;
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
 * Created by David on 2017/3/20.
 */
public class pzSource {
    //private String PATH="A:/ssdworkspace/tangUI/src/com/Davi/poems/source/pingze.xml";
    private String PATH="src/main/resources/pingze.xml";
    private ArrayList<wordClass> wordClassArrayList;
    public pzSource(){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();


        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(PATH);
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

                    if (children.item(j).getNodeType() == Node.ELEMENT_NODE){
                        if (children.item(j).getNodeName().equals("上平去入")){
                            wc.setPingZe(Integer.valueOf(children.item(j).getFirstChild().getNodeValue()));
                            //System.out.println(children.item(j).getFirstChild().getNodeValue());
                        }
                        if (children.item(j).getNodeName().equals("韵部")){
                            wc.setYunbu(Integer.valueOf(children.item(j).getFirstChild().getNodeValue()));
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

    }
    public ArrayList<wordClass> getWordClassArrayList(){
        return this.wordClassArrayList;
    }

    public static void main(String[] args) {
        pzSource pz = new pzSource();
        Iterator<wordClass> iterator = pz.getWordClassArrayList().iterator();
        while (iterator.hasNext()){
            wordClass word = iterator.next();
            if (word.getWord().equals("光")){
                System.out.println("matched!!");
                System.out.println(word.getWord());
                System.out.println(word.getPingZe());
                System.out.println(word.getYunbu());
                break;
            }
        }
    }
}
