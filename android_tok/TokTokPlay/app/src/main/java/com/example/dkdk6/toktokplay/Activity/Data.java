package com.example.dkdk6.toktokplay.Activity;

/**
 * Created by dkdk6 on 2017-06-01.
 */

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 4p on 2017-06-01. 박진희
 */

public class Data implements Serializable {
    ArrayList<Integer> userBeat = new ArrayList<>();
    public ArrayList getList(){
        return userBeat;
    }
    public void setList(ArrayList<Integer> list){
        this.userBeat=list;
    }
}