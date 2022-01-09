package com.dumbapp.destinyclassscanner;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private Bitmap capture;

    public void setCapture(Bitmap capture) {
        this.capture = capture;
    }

    public Bitmap getCapture() {
        return capture;
    }
}
