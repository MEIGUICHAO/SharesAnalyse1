package com.mgc.sharesanalyse.entity;

public class SinaDealDatailBean {

    /**
     * symbol : sh601216
     * name : 君正集团
     * ticktime : 14:57:00
     * price : 7.320
     * volume : 42000
     * prev_price : 7.330
     * kind : E
     */

    private String symbol;
    private String name;
    private String ticktime;
    private String price;
    private String volume;
    private String prev_price;
    private String kind;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicktime() {
        return ticktime;
    }

    public void setTicktime(String ticktime) {
        this.ticktime = ticktime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPrev_price() {
        return prev_price;
    }

    public void setPrev_price(String prev_price) {
        this.prev_price = prev_price;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
