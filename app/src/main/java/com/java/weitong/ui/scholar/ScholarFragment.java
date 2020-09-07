package com.java.weitong.ui.scholar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.weitong.R;

public class ScholarFragment extends Fragment {

    private ScholarViewModel scholarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scholarViewModel =
                ViewModelProviders.of(this).get(ScholarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_scholar, container, false);
        final TextView textView = root.findViewById(R.id.text_scholar);
        scholarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}