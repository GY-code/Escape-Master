package com.example.applicationtest001;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class FragmentRoom extends Fragment {
    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_room,container,false);

//        Button btn1 = (Button)getActivity().findViewById(R.id.button12);
//
//        btn1.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent i = new Intent(getContext(),PlayActivity.class);
//                startActivity(i);
//            }
//        });
        return v;

    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Button btn1 = (Button)getActivity().findViewById(R.id.button12);

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getContext(),PlayActivity.class);
                startActivity(i);
            }
        });
    }
}