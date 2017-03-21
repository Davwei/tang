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

/**
 * Created by David on 2017/3/21.
 */
public class kindSource {
    private final String PATH = "src/main/resources/zifenlei.xml";
    private ArrayList<wordClass> wordClassArrayList;
    public kindSource(){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(PATH);

            wordClassArrayList = new ArrayList<wordClass>();

            NodeList root = doc.getElementsByTagName("zifenlei");
            //获得全部的字符
            for (int i = 0; i < root.getLength(); i++) {
                wordClass wc = new wordClass();
                Node node = root.item(i);
                NodeList children = node.getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    if (children.item(j).getNodeType() == Node.ELEMENT_NODE){
                        if (children.item(j).getNodeName().equals("zi")){
                            wc.setWord(children.item(j).getFirstChild().getNodeValue());
                        }else if (children.item(j).getNodeName().equals("fenlei")){
                            wc.setKind(Integer.valueOf(children.item(j).getFirstChild().getNodeValue()));
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
        return wordClassArrayList;
    }
}
