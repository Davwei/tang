package com.Davi.poems.tools;


import com.Davi.poems.basic.tangClass;
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
 * Created by David on 2017/3/7.
 */
public class tangSource {
    private ArrayList<tangClass> tangClassArrayList;
    //private final String PATH = "A:/ssdworkspace/tangUI/src/com/Davi/poems/source/fullshici.xml";
    //private final String PATH = "src/main/resources/fullshici.xml";
    public tangSource(){
        String path = this.getClass().getResource("/fullshici.xml").toString();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(path);

            NodeList tangList = doc.getElementsByTagName("唐诗");

            tangClassArrayList = new ArrayList<tangClass>();

            for (int i = 0; i < tangList.getLength() ; i++) {
                Node n = tangList.item(i);
                NodeList children = n.getChildNodes();
                //tangClass tangTmp = new tangClass();
                String title = null, author = null, context = null;
                for (int j = 0; j < children.getLength() ; j++) {

                    if (children.item(j).getNodeType() == Node.ELEMENT_NODE){

                        if (children.item(j).getNodeName() != null&&children.item(j).getFirstChild() != null) {
                            /**
                             * tangClass(String title,String auther,String context)
                             */
                            if (children.item(j).getNodeName().equals("题目")) title = children.item(j).getFirstChild().getNodeValue();
                            else if (children.item(j).getNodeName().equals("作者")) author = children.item(j).getFirstChild().getNodeValue();
                            else if (children.item(j).getNodeName().equals("正文")) context = children.item(j).getFirstChild().getNodeValue();
                            //System.out.println("the " + (j + 1) + " tangClass subNode name is " + children.item(j).getNodeName());
                            //System.out.println("the " + (j + 1) + " tangClass subNode value is " + children.item(j).getFirstChild().getNodeValue().trim());
                        }

                    }
                }
                if (title != null && author != null && context != null){

                    tangClassArrayList.add(new tangClass(title,author,context));

                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回全诗词
     * @return
     */
    public ArrayList<tangClass> getTangClassArrayList(){
        return this.tangClassArrayList;
    }

    /**
     * 返回只有目标作者数组的诗词
     * @param authors
     * @return
     */
    public ArrayList<tangClass> getTangClassArrayListWithAuthor(String[] authors){
        ArrayList<tangClass> result = new ArrayList<tangClass>();
        Iterator<tangClass> iterator = this.getTangClassArrayList().iterator();
        while (iterator.hasNext()){
            tangClass tmp = iterator.next();
            for (String author:authors) {
                if (author.equals(tmp.getAuther())){
                    result.add(tmp);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        //String path = "A:/ssdworkspace/tangUI/src/com/Davi/poems/source/fullshici.xml";
        tangSource ts = new tangSource();

        System.out.println("全唐诗词匹配---------------");
        Iterator<tangClass> iterator = ts.getTangClassArrayList().iterator();
        while(iterator.hasNext()){

            //System.out.println(iterator.next().toString());
            /*if (iterator.next().getAuther().equals("李白")){
                System.out.println("yes");
                break;
            }*/
            if (iterator.next().getTitle().equals("静夜思")){
                System.out.println("yes");
                break;
            }


        }
        System.out.println("目标作者唐诗匹配-----------");
        String[] authors = {"李白","王维","杜甫"};
        System.out.println(ts.getTangClassArrayListWithAuthor(authors).size());



    }
}
