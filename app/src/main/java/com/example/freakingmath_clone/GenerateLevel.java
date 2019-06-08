package com.example.freakingmath_clone;

import android.util.Log;

import java.util.Random;

public class GenerateLevel {
    // score of each level
    public static final int EASY = 10;
    public static final int MEDIUM = 20;
    public static final int HARD = 150;

    public static LevelModel generateLevel(int count) {
        LevelModel levelModel = new LevelModel();
        Random rand = new Random();
        // get current difficult level
        if (count <= EASY) {
            levelModel.difficultLevel = 1;
        } else {
            if (count <= MEDIUM) {
                levelModel.difficultLevel = 2;
            } else {
                if (count <= HARD) {
                    levelModel.difficultLevel = 3;
                } else {
                    levelModel.difficultLevel = 4;
                }
            }
        }

        //random operator
        levelModel.operator = rand.nextInt(levelModel.difficultLevel);

        //random a,b
        levelModel.a = rand.nextInt(levelModel.arrMaxOperationValue[levelModel.difficultLevel]) + 1;
        levelModel.b = rand.nextInt(levelModel.arrMaxOperationValue[levelModel.difficultLevel]) + 1;

        //random correct or wrong
        levelModel.correctORwrong = rand.nextBoolean();

        //random result
        if (levelModel.correctORwrong == false) {
            switch (levelModel.operator) {
                case LevelModel.ADD:
                    do {
                        levelModel.result = rand.nextInt(levelModel.arrMaxOperationValue[levelModel.difficultLevel]);
                    } while (levelModel.result == levelModel.a + levelModel.b);
                    break;
                case LevelModel.MIN:
                    do {
                        levelModel.result = rand.nextInt(levelModel.arrMaxOperationValue[levelModel.difficultLevel]);
                    } while (levelModel.result == levelModel.a - levelModel.b);
                    break;
                case LevelModel.MUL:
                    do {
                        levelModel.result = rand.nextInt(levelModel.arrMaxOperationValue[levelModel.difficultLevel]);
                    } while (levelModel.result == levelModel.a * levelModel.b);
                    break;
                case LevelModel.DIV:
                    do {
                        levelModel.result = rand.nextInt(levelModel.arrMaxOperationValue[levelModel.difficultLevel]);
                    } while (levelModel.result == levelModel.a / levelModel.b);
                    break;
                default:
                    break;
            }
        } else {
            switch (levelModel.operator) {
                case LevelModel.ADD:
                    levelModel.result = levelModel.a + levelModel.b;
                    break;
                case LevelModel.MIN:
                    levelModel.result = levelModel.a - levelModel.b;
                    break;
                case LevelModel.MUL:
                    levelModel.result = levelModel.a * levelModel.b;
                    break;
                case LevelModel.DIV:
                    levelModel.result = levelModel.a / levelModel.b;
                    break;
                default:
                    break;
            }
        }

        //create string operator
        levelModel.strOperator = String.valueOf(levelModel.a) + " "
                + levelModel.arrOperatorText[(levelModel.operator)] + " "
                + String.valueOf(levelModel.b);

        //create string result
        levelModel.strResult = LevelModel.EQU_TEXT + " " + String.valueOf(levelModel.result);
        return levelModel;
    }
}
