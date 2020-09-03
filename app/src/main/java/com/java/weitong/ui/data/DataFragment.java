package com.java.weitong.ui.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.java.weitong.R;

public class DataFragment extends Fragment {

    private DataViewModel dataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dataViewModel =
                ViewModelProviders.of(this).get(DataViewModel.class);
        View root = inflater.inflate(R.layout.fragment_data, container, false);
        final TextView textView = root.findViewById(R.id.text_data);
        dataViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}