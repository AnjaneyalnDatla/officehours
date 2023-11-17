package org.example;


public class Day {
    
    String open;
    String close;
    String name;

    public Day() {
    }
    
    public Day(String open, String close, String name) {
        this.open = open;
        this.close = close;
        this.name = name;
    }
    public String getOpen() {
        return open;
    }
    public void setOpen(String open) {
        this.open = open;
    }
    public String getClose() {
        return close;
    }
    public void setClose(String close) {
        this.close = close;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    

    
}
