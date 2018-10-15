package com.hippotang;

import javafx.scene.image.*;

public class MineRectangle extends javafx.scene.shape.Rectangle{
    // borders
    public static Image horizontalBorder = new Image("file:res/bordertb.gif");
    public static Image verticalBorder = new Image("file:res/borderlr.gif");

    // before opening a cell
    public static Image blank = new Image("file:res/blank.gif");
    public static Image bombQuestion = new Image("file:res/bombquestion.gif");
    public static Image bombFlagged = new Image("file:res/bombflagged.gif");

    // after cell is detonated
    public static Image bombRevealed = new Image("file:res/bombrevealed.gif");
    public static Image bombDeath = new Image("file:res/bombdeath.gif");
    public static Image bombMisflagged = new Image("file:res/bombmisflagged.gif");

    // when cell is open
    public static Image open0 = new Image("file:res/open0.gif");
    public static Image open1 = new Image("file:res/open1.gif");
    public static Image open2 = new Image("file:res/open2.gif");
    public static Image open3 = new Image("file:res/open3.gif");
    public static Image open4 = new Image("file:res/open4.gif");
    public static Image open5 = new Image("file:res/open5.gif");
    public static Image open6 = new Image("file:res/open6.gif");
    public static Image open7 = new Image("file:res/open7.gif");
    public static Image open8 = new Image("file:res/open8.gif");

    // faces
    public static Image facesmile = new Image("file:res/facesmile.gif");
    public static Image faceooh = new Image("file:res/faceooh.gif");
    public static Image facedead = new Image("file:res/facedead.gif");

    // for debug
    public static Image[] imgArr = {blank, bombQuestion, bombFlagged, bombRevealed, bombDeath, bombMisflagged, open0,
                                    open1, open2, open3, open4, open5, open6, open7, open8};

    // to easily reference numbered images
    public static Image[] cellNumImgArr = {open0, open1, open2, open3, open4, open5, open6, open7, open8};

    public static ImageView[] imgViewArr = new ImageView[imgArr.length];

    public MineRectangle() {
        super();
    }

    public MineRectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}
