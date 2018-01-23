package com.example.lequan.lichvannien.dao;

import android.content.Context;
import android.graphics.Typeface;

public class DAOTypeface {
    Typeface Avo;
    Typeface Bold;
    Typeface Dax;
    Typeface DaxBold;
    Typeface Light;
    Typeface Regular;
    Typeface SFUN;
    Typeface Tahoma;
    Typeface Thin;

    public DAOTypeface(Context context) {
        this.Regular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        this.Light = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        this.Thin = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf");
        this.Bold = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
        this.Tahoma = Typeface.createFromAsset(context.getAssets(), "fonts/tahomabd.ttf");
        this.SFUN = Typeface.createFromAsset(context.getAssets(), "fonts/SFUN.ttf");
        this.Avo = Typeface.createFromAsset(context.getAssets(), "fonts/UTM Avo.ttf");
        this.Dax = Typeface.createFromAsset(context.getAssets(), "fonts/UTM Dax.ttf");
        this.DaxBold = Typeface.createFromAsset(context.getAssets(), "fonts/UTM DaxBold.ttf");
    }

    public Typeface getBold() {
        return this.Bold;
    }

    public Typeface getLight() {
        return this.Light;
    }

    public Typeface getRegular() {
        return this.Regular;
    }

    public Typeface getThin() {
        return this.Thin;
    }

    public Typeface getTahoma() {
        return this.Tahoma;
    }

    public Typeface getSFUN() {
        return this.SFUN;
    }

    public Typeface getAvo() {
        return this.Avo;
    }

    public Typeface getDax() {
        return this.Dax;
    }

    public Typeface getDaxBold() {
        return this.DaxBold;
    }
}
