package com.example.forekast.EditScreen;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

class EditScreenViewModel extends ViewModel {
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
