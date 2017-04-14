package com.Davi.poems.basic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by David on 2017/4/12.
 */
public class event implements Serializable{
    private static final long serialVersionUID = 3584042093326873203L;
    public ArrayList<tangClass> getResult() {
        return result;
    }

    public void setResult(ArrayList<tangClass> result) {
        this.result = result;
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public operation getOp() {
        return op;
    }

    public void setOp(operation op) {
        this.op = op;
    }

    private ArrayList<tangClass> result;

    private String inputString = "";
    public operation op;

    public event(operation op,String input){
        this.setOp(op);
        this.setInputString(inputString);
    }
    public String toString(){
        String out = "";
        for (int i = 0; i < result.size(); i++) {
            out = out + "\n" + result.get(i).toString();
        }
        return out;
    }

}
