package com.Davi.poems.tools;


import com.Davi.poems.basic.author;
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
 * Created by David on 2017/3/3.
 */
public class authorSource {

    private ArrayList<author> authorArrayList;
    //private final String PATH = "A:/ssdworkspace/tangUI/src/com/Davi/poems/source/authors.xml";
    private final String PATH = "src/main/resources/authors.xml";
    public authorSource(){

        String path = this.PATH;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(path);

            //System.out.println(doc.getXmlEncoding());

            NodeList authorList = doc.getElementsByTagName("authors");

            authorArrayList = new ArrayList<author>();

            //System.out.println("--------------遍历-----------------");

            for (int i = 0; i <authorList.getLength(); i++) {
                Node n = authorList.item(i);
                NodeList children = n.getChildNodes();
                //System.out.println("一个authors有"+children.getLength()+"个子节点");

                for (int j = 0; j < children.getLength(); j++) {
                    if (children.item(j).getNodeType() == Node.ELEMENT_NODE){
                        authorArrayList.add(new author(children.item(j).getFirstChild().getNodeValue()));
                        //System.out.println("the "+(j+1)+"author subNode name is "+children.item(j).getNodeName());
                        //System.out.println("the "+(j+1)+"author subNode value is "+children.item(j).getFirstChild().getNodeValue());
                    }
                }
            }

            //System.out.println("一共有"+authorList.getLength()+"个作者");




        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  ArrayList<author> getAtc(){
        return this.authorArrayList;
    }

    public static void main(String[] args) {
        authorSource as = new authorSource();
        //authorSource as = new authorSource("A:/ssdworkspace/tangUI/src/com/Davi/poems/source/author1.xml");

//        for (author ass:as.getAtc()) {
//            System.out.println(ass.getName());
//        }

//        System.out.println(as.getAtc().get(1).getName());
//        System.out.println(new author("李世民").getName());

        Iterator<author> iterator = as.getAtc().iterator();
        while(iterator.hasNext()){
            if(iterator.next().getName().equals("李白")){
                System.out.println("yes");
                break;
            }
        }



    }

}
