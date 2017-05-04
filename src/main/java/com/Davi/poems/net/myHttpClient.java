package com.Davi.poems.net;


import com.Davi.poems.basic.event;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URLEncoder;

/**
 * Created by David on 2017/4/12.
 * 测试服务器而写的发送程序,可以根据这个程序与server通信
 *
 */
public class myHttpClient {
    public String clientPort = "http://192.168.168.102:8888";
    public myHttpClient() throws UnsupportedEncodingException {
        Logger logger = Logger.getLogger(myHttpClient.class);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //HttpEntity params = null;

        HttpPost request = new HttpPost(clientPort);

        //request.addHeader("OPERATION","possibleSearch");

        //request.addHeader("INPUT", URLEncoder.encode("樱花", "UTF-8"));

        request.addHeader("OPERATION","isSearch");

        request.addHeader("INPUT", URLEncoder.encode("床前***", "UTF-8"));


        logger.info(request.getHeaders("INPUT")[0]);
        //request.setHeader("Content-Type","text/xml; charset=utf-8");
        try {
            CloseableHttpResponse response = httpclient.execute(request);
            //解析response
            InputStream bis = response.getEntity().getContent();
            ObjectInputStream ois = new ObjectInputStream(bis);
            event e = (event) ois.readObject();

            logger.info(e.toString());
            int status = response.getStatusLine().getStatusCode();
            logger.info("返回 "+status);
            System.out.println(status);
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        try {
            myHttpClient client = new myHttpClient();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
