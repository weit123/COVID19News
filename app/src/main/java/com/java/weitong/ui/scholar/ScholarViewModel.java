package com.java.weitong.ui.scholar;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ScholarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ScholarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is scholar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}