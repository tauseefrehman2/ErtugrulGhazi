package com.example.ertugrulghazi.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.activities.MainActivity;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment {

    public DownloadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar())
                .setTitle("Ertugrul Ghazi");
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar())
                .setSubtitle("Download");

        return inflater.inflate(R.layout.fragment_download, container, false);
    }
}
