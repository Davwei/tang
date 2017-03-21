package com.Davi.poems.basic;

/**
 * Created by David on 2017/3/6.
 */
public class tangClass {
    private String auther;
    private String dynasty;
    private String context;
    private String title;
    private String pairs;
    private int pairsWeight;

    public tangClass() {
    }
    public tangClass(String title,String auther,String context){
        this.auther = auther;
        this.context = context;
        this.title = title;
    }


    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString(){
       return "这首诗的题目是"+this.getTitle()+"\n这首诗的作者是"+this.getAuther()+"\n这首诗的内容是： "+this.getContext()+"\n" + "对偶句是："+this.getPairs() +"\n 权重是"+this.getPairsWeight()+"\n" ;
    }

    public String getPairs() {
        return pairs;
    }

    public void setPairs(String pairs) {
        this.pairs = pairs;
    }

    public int getPairsWeight() {
        return pairsWeight;
    }

    public void setPairsWeight(int pairsWeight) {
        this.pairsWeight = pairsWeight;
    }
}
