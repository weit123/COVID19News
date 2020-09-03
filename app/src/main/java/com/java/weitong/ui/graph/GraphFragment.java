package com.java.weitong.ui.graph;

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

public class GraphFragment extends Fragment {

    private GraphViewModel graphViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        graphViewModel =
                ViewModelProviders.of(this).get(GraphViewModel.class);
        View root = inflater.inflate(R.layout.fragment_graph, container, false);
        final TextView textView = root.findViewById(R.id.text_graph);
        graphViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}