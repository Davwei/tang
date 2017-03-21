package com.Davi.poems.basic;

/**
 * Created by David on 2017/3/17.
 */
public class wordClass {
    private String word ;
    private String[] pinYin;
    //平仄有0，1，2，3四种类型
    private int pingZe;
    private int yunbu;
    private int kind;

    public wordClass(String word,int pinZe,int yunbu,int kind){
        this.word = word;
        this.pingZe = pinZe;
        this.yunbu = yunbu;
        this.kind = kind;
    }
    //这个构造函数的数据平仄部分有问题
    public wordClass(String word,String[] pinYin,int pinZe){
        this.word = word;
        this.pingZe = pinZe;
        this.pinYin = pinYin;

    }

    public wordClass(String word,int pinZe,int yunbu){
        this.word = word;
        this.pingZe = pinZe;
        this.yunbu = yunbu;
    }
    public wordClass(){}


    public void setWord(String word) {
        this.word = word;
    }

    public String[] getPinYin() {
        return pinYin;
    }

    public void setPinYin(String[] pinYin) {
        this.pinYin = pinYin;
    }

    public String getWord() {
        return word;
    }

    public int getPingZe() {
        return pingZe;
    }

    public void setPingZe(int pingZe) {
        this.pingZe = pingZe;
    }
    public String toString(){
        String tmp = "这个字是："+word;
        StringBuilder sb = new StringBuilder();
        if (pinYin == null)
            return tmp + " "+ pingZe;
        else {
            for (int i = 0; i < pinYin.length; i++) {
                sb.append(pinYin[i] + " ");
            }
            return tmp + sb.toString() + pingZe;

        }
    }

    public int getYunbu() {
        return yunbu;
    }

    public void setYunbu(int yunbu) {
        this.yunbu = yunbu;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
