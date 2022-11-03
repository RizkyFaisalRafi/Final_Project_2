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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ElektronikFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ElektronikFragment extends Fragment {
    ImageView komputer;
    ImageView handphone;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_elektronik, container, false);
        komputer = view.findViewById(R.id.a);
        handphone = view.findViewById(R.id.b);
        komputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Komputer.class);
                startActivity(intent);
            }
        });
        handphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hp = new Intent(getActivity(), Handphone.class);
                startActivity(hp);
            }
        });

        return view;
    }
}