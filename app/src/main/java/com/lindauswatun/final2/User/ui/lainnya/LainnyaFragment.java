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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LainnyaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LainnyaFragment extends Fragment {
    ImageView book;
    ImageView other;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LainnyaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LainnyaFragment.
     */
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
        View view =  inflater.inflate(R.layout.fragment_lainnya, container, false);
        book = view.findViewById(R.id.book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buku = new Intent(getActivity(), Book.class);
                startActivity(buku);
            }
        });
        other = view.findViewById(R.id.other);
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lain = new Intent(getActivity(), Other.class);
                startActivity(lain);
            }
        });
        return view;
    }
}