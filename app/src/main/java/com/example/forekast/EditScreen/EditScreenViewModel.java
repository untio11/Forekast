package com.example.forekast.EditScreen;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

class EditScreenViewModel extends ViewModel {
    // Bitmap of the picture in editscreen
    private Bitmap bitmap;

    // Get current bitmap of picture (for changing orientation)
    public Bitmap getBitmap() {
        return bitmap;
    }

    // Set bitmap of picture whenever the imageView is changed.
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
