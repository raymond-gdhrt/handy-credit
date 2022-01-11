/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.views;

import com.google.gson.Gson;
import org.json.simple.JSONArray;

/**
 *
 * @author RayGdhrt
 */
public class ChartData {

    private String[] labels;
    private int[] intData1;
    private int[] intData2;
    private float[] floatData1;

    private float[] floatData2;
    private float[] floatData3;
    private float[] floatData4;
    private float[] floatData5;

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public int[] getIntData1() {
        return intData1;
    }

    public void setIntData1(int[] intData1) {
        this.intData1 = intData1;
    }

    public float[] getFloatData1() {
        return floatData1;
    }

    public void setFloatData1(float[] floatData1) {
        this.floatData1 = floatData1;
    }

    public int[] getIntData2() {
        return intData2;
    }

    public void setIntData2(int[] intData2) {
        this.intData2 = intData2;
    }

    public float[] getFloatData2() {
        return floatData2;
    }

    public void setFloatData2(float[] floatData2) {
        this.floatData2 = floatData2;
    }

    public String composeLableString() {

        // Convert numbers array into JSON string.
        return new Gson().toJson(this.labels);

    }

    public float[] getFloatData3() {
        return floatData3;
    }

    public void setFloatData3(float[] floatData3) {
        this.floatData3 = floatData3;
    }

    public float[] getFloatData4() {
        return floatData4;
    }

    public void setFloatData4(float[] floatData4) {
        this.floatData4 = floatData4;
    }

    public float[] getFloatData5() {
        return floatData5;
    }

    public void setFloatData5(float[] floatData5) {
        this.floatData5 = floatData5;
    }

    public String composeIntData1String() {

        return new Gson().toJson(this.intData1);

    }

    public String composeIntData2String() {

        return new Gson().toJson(this.intData2);

    }

    public String composeFloatData1String() {

        return new Gson().toJson(this.floatData1);

    }

    public String composeFloatData2String() {

        return new Gson().toJson(this.floatData2);

    }

    public String composeFloatData3String() {

        return new Gson().toJson(this.floatData3);

    }

    public String composeFloatData4String() {

        return new Gson().toJson(this.floatData4);

    }

    public String composeFloatData5String() {

        return new Gson().toJson(this.floatData5);

    }

}
