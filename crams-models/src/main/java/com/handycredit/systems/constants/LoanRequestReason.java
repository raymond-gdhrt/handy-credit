/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.constants;

/**
 *
 * @author RayGdhrt
 */
public enum LoanRequestReason {
    LocationExpansion("Expand premisses",5),
    NewBranch("Create new branch(es)",5),
    MeetTenderRequirements("Meet contract requirements",5),
    MoreStock("Purchase more stock/Inventory",5),
    NewEquipment("Purchase more equipment",4),
    LabourBoast("Get more/fresh labor",3),
    ImproveCreditScores("Improve your credit scores",1),
    NewBusiness("New business venture",2),
    Other("Other reason",0);

    private String uiName;
    private int score;

    LoanRequestReason(String name,int score) {
        this.uiName = name;
    }

    public String getUiName() {
        return uiName;
    }

    public void setUiName(String uiName) {
        this.uiName = uiName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    

}
