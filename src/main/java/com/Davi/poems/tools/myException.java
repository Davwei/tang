package com.Davi.poems.tools;

/**
 * Created by wei on 2017/2/27.
 */
public class myException extends Exception{
    private String message;

    public myException(String s){
        this.message = s;
        System.out.println(s);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
