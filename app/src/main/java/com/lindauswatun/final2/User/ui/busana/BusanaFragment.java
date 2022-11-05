package com.lindauswatun.final2.User.ui.busana;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lindauswatun.final2.R;
import com.lindauswatun.final2.User.List.Pria;
import com.lindauswatun.final2.User.List.Wanita;

public class BusanaFragment extends Fragment {
    ImageView pria;
    ImageView wanita;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public BusanaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusanaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusanaFragment newInstance(String param1, String param2) {
        BusanaFragment fragment = new BusanaFragment();
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
        View view = inflater.inflate(R.layout.fragment_busana, container, false);
        pria = view.findViewById(R.id.pria);
        pria.setOnClickListener(view1 -> startActivity(new Intent(getActivity(),Pria.class)));
        wanita = view.findViewById(R.id.wanita);
        wanita.setOnClickListener(view12 -> startActivity(new Intent(getActivity(), Wanita.class)));
        return view;
    }
}