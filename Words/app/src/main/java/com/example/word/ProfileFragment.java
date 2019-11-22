package com.example.word;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        view.findViewById(R.id.mc).setOnClickListener(this);
        view.findViewById(R.id.so).setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.mc:
                startActivity(new Intent(getActivity(),ErrorActivity.class));
                break;
            case R.id.so:
                SharedPreferences sp = getActivity().getSharedPreferences("sign", Context.MODE_PRIVATE);
                sp.edit().putBoolean("login",false).commit();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();;
                break;
        }
    }
}
