package com.lindauswatun.final2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.lindauswatun.final2.AboutActivity;
import com.lindauswatun.final2.Admin.LoginAdmin;
import com.lindauswatun.final2.MainActivity;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.Staff.LoginStaff;
import com.lindauswatun.final2.User.LoginUser;
import com.lindauswatun.final2.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

//    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Button btnCategory = view.findViewById(R.id.btn_category);
        Button btnUser = view.findViewById(R.id.btn_user1);
        Button btnAdmin = view.findViewById(R.id.btn_admin);
        Button btnStaff = view.findViewById(R.id.btn_staff);
        Button btnAbout = view.findViewById(R.id.btn_about);


        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_loginUser);
            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_loginAdmin);
            }
        });

        btnStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_loginStaff);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_aboutActivity);
            }
        });



//        binding.btnUser.setOnClickListener(this);
//        binding.btnAdmin.setOnClickListener(this);
//        binding.btnStaff.setOnClickListener(this);
//        binding.btnAbout.setOnClickListener(this);

//        btnCategory.setOnClickListener(this);
    }


//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.btn_user) {
//            startActivity(new Intent(getActivity(),HomeFragment.this, LoginUser.class));
//        } else if (v.getId() == R.id.btn_admin) {
//            startActivity(new Intent(HomeFragment.this, LoginAdmin.class));
//        } else if (v.getId() == R.id.btn_staff) {
//            startActivity(new Intent(HomeFragment.this, LoginStaff.class));
//        } else if (v.getId() == R.id.btn_about) {
//            startActivity(new Intent(HomeFragment.this, AboutActivity.class));
//        }
//    }
}