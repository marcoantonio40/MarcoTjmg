package com.project.marco.config;

import lombok.Getter;

@Getter
public enum Meses {



    JANUARY(1, "January"),
    FEBRUARY(2, "February"),
    MARCH(3, "March"),
    APRIL(4, "April"),
    MAY(5, "May"),
    JUNE(6, "June"),
    JULY(7, "July"),
    AUGUST(8, "August"),
    SEPTEMBER(9, "September"),
    OCTOBER(10, "October"),
    NOVEMBER(11, "November"),
    DECEMBER(12, "December");

    private int mesInt;
    private String mesString;


    Meses(int mesInt, String mesString){
        this.mesInt = mesInt;
        this.mesString = mesString;
    }

    public String getMesString(int mesNumber){
        Meses[] values = this.values();
        for(Meses mes: values){
            if(mes.getMesInt() == mesNumber){
                return mes.getMesString();
            }
        }
        return null;
    }

}
