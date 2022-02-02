/*
 * List for business categories currently supported by CRAMS
 */
package com.handycredit.systems.constants;

/**
 *
 * @author RayGdhrt
 */
public enum BusinessCategory {
    carpetry("Carpentery", 8), mettalworks("Metal works", 3), pottery("Pottery", 3), artist("Artist", 3), hardware("Hard ware", 3), grcerystore("Grocery store", 3);

    BusinessCategory(String uiName, int score) {
        this.uiName = uiName;
        this.score = score;
    }
    private int score;
    private String uiName;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUiName() {
        return uiName;
    }

    public void setUiName(String uiName) {
        this.uiName = uiName;
    }

}
