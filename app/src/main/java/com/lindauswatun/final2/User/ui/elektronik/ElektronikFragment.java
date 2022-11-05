package com.lindauswatun.final2.User.ui.elektronik;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lindauswatun.final2.R;
import com.lindauswatun.final2.User.List.Komputer;
import com.lindauswatun.final2.User.List.Handphone;

public class ElektronikFragment extends Fragment {
    ImageView komputer;
    ImageView handphone;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ElektronikFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ElektronikFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ElektronikFragment newInstance(String param1, String param2) {
        ElektronikFragment fragment = new ElektronikFragment();
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
        View view =  inflater.inflate(R.layout.fragment_elektronik, container, false);
        komputer = view.findViewById(R.id.a);
        handphone = view.findViewById(R.id.b);
        komputer.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), Komputer.class);
            startActivity(intent);
        });
        handphone.setOnClickListener(view1 -> {
            Intent hp = new Intent(getActivity(), Handphone.class);
            startActivity(hp);
        });

        return view;
    }
}