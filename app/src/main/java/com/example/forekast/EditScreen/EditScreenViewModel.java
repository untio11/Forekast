package com.example.forekast.EditScreen;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

public class EditScreenViewModel extends ViewModel {
    Bitmap bitmap;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
