package com.Davi.poems.tools;

import com.Davi.poems.basic.tangClass;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by David on 2017/3/22.
 */
public class toTextFile {
    FileOutputStream fileOutputStream;
    BufferedOutputStream bufferedOutputStream;
    FileWriter fileWriter;
    ArrayList<tangClass> outPutFile;
    String path = "";
    public toTextFile(ArrayList<tangClass> outPutFile){
        this.outPutFile = outPutFile;
    }
    public void createFile(String path){

    }
    public void fillFile(File e) throws IOException {
        //完成arrayList的保存工作
        fileOutputStream = new FileOutputStream(e);
        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        Iterator<tangClass> iterator = outPutFile.iterator();
        String fileString = "";
        while (iterator.hasNext()){
            tangClass tmp = iterator.next();
            fileString = fileString+"作者是: "+tmp.getAuther()+"\n 题目是: "+tmp.getTitle()+"\n 内容是: "+tmp.getContext()+ "\n";
        }
        bufferedOutputStream.write(fileString.getBytes("utf-8"));
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();

    }

    public static void main(String[] args) {

    }
}
