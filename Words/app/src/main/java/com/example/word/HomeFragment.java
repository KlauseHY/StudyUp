package com.example.word;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        view.findViewById(R.id.ll_bs).setOnClickListener(this);
        view.findViewById(R.id.ll_exam).setOnClickListener(this);
        view. findViewById(R.id.ll_read).setOnClickListener(this);
        view.findViewById(R.id.ll_word).setOnClickListener(this);
        view.findViewById(R.id.ll_search).setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_bs:
                startActivity(new Intent(getActivity(), WordRandomActivity.class));
                break;
            case R.id.ll_exam:
                startActivity(new Intent(getActivity(),ExamActivity.class));
                break;
            case R.id.ll_read:
                startActivity(new Intent(getActivity(),ReadListActivity.class));
                break;
            case R.id.ll_word:
                startActivity(new Intent(getActivity(),FavouriteActivity.class));
                break;
            case R.id.ll_search:
                startActivity(new Intent(getActivity(),WordSearchActivity.class));
                break;
        }
    }


}
