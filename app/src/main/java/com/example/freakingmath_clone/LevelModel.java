package com.example.freakingmath_clone;

public class LevelModel {
    //difficult Level
    public int difficultLevel = 1; // easy

    //operators
    public static final int ADD = 0;
    public static final int MIN = 1;
    public static final int MUL = 2;
    public static final int DIV = 3;

    //opetator text
    public static final String ADD_TEXT = "+";
    public static final String MIN_TEXT = "-";
    public static final String MUL_TEXT = "x";
    public static final String DIV_TEXT = ":";
    public static final String EQU_TEXT = "=";
    public static final String[] arrOperatorText = {ADD_TEXT,
            MIN_TEXT,
            MUL_TEXT,
            DIV_TEXT};

    // components of formular
    public int a;
    public int b;
    public int result;
    public int operator = 0;
    public boolean correctORwrong;
    public String strOperator = "";
    public String strResult = "";

    //set max value of operator depend on level: easy, medium, hard;
    public static final  int MAX_OPERATOR_LEVEL_EASY        = 5;
    public static final  int MAX_OPERATOR_LEVEL_MEDIUM      = 10;
    public static final  int MAX_OPERATOR_LEVEL_HARD        = 50;

    public static final int[] arrMaxOperationValue = {MAX_OPERATOR_LEVEL_EASY,
            MAX_OPERATOR_LEVEL_MEDIUM,
            MAX_OPERATOR_LEVEL_HARD};

}
