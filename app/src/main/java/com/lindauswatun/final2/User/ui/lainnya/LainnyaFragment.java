package com.lindauswatun.final2.User.ui.lainnya;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lindauswatun.final2.R;
import com.lindauswatun.final2.User.List.Book;
import com.lindauswatun.final2.User.List.Other;


public class LainnyaFragment extends Fragment {
    ImageView book;
    ImageView other;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public LainnyaFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LainnyaFragment newInstance(String param1, String param2) {
        LainnyaFragment fragment = new LainnyaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_lainnya, container, false);
        book = view.findViewById(R.id.book);

        book.setOnClickListener(view1 -> {
            Intent buku = new Intent(getActivity(), Book.class);
            startActivity(buku);
        });
        other = view.findViewById(R.id.other);
        other.setOnClickListener(view12 -> {
            Intent lain = new Intent(getActivity(), Other.class);
            startActivity(lain);
        });
        return view;
    }
}