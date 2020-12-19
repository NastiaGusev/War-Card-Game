package com.example.war.objects;

public class Card  {
    private String imgName = "";
    private int value = 0;

    public Card() { }

    public Card(String imgName, int value) {
        setImgName(imgName);
        setValue(value);
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getImgName() {
        return imgName;
    }

    public int getValue() {
        return value;
    }

    public int compareCards(Card card) {
        if (this.value > card.getValue()) {
            return 1;
        } else if (this.value < card.getValue()) {
            return -1;
        } else {
            return 0;
        }
    }
}
