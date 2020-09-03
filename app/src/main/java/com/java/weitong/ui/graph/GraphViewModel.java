package com.java.weitong.ui.graph;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class GraphViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GraphViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is graph fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}