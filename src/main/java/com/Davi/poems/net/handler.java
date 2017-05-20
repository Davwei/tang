package com.Davi.poems.net;

import com.Davi.poems.basic.event;
import com.Davi.poems.basic.tangClass;
import com.Davi.poems.tools.analyze;
import com.Davi.poems.tools.myException;
import org.apache.http.HttpException;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static com.Davi.poems.basic.operation.*;

/**
 * Created by David on 2017/4/12.
 */
public class handler {
    Logger log = Logger.getLogger(handler.class);
    public String OPERATION = "OPERATION";
    public String INPUT = "INPUT";
    public event getEvent(HttpServletRequest request) throws myException, UnsupportedEncodingException {
        //处理request请求，调用方法得到返回值，放入event
        String operations = request.getHeader(OPERATION);
        com.Davi.poems.basic.operation op;
        analyze az = new analyze();
        String inputString = URLDecoder.decode(request.getHeader("INPUT"),request.getCharacterEncoding());
        log.info(inputString);
        ArrayList<tangClass> result = new ArrayList<>();
        switch (operations){
            case "isSearch":
                op = isSearch;
                result = az.match(inputString);
                break;
            case "possibleSearch":
                op = possibleSearch;
                result = az.PMatch(inputString);
                break;
            case "pairMatch":
                op = pairMatch;
                result = az.pairMatch(inputString);
                break;
            case "pairGetNormal":
                op = pairGetNormal;
                result = new ArrayList<>();
                tangClass tmp = new tangClass();
                tmp.setPairs(az.nomalSegment(inputString));
                result.add(tmp);
                break;
            default:
               throw new myException("未知操作或不支持的操作");

        }
        event e = new event(op,inputString);
        log.info("result size: "+result.size());
        e.setResult(result);
        return e;
    }
}
