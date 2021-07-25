package com.example.applicationtest001;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class FragmentPerson extends Fragment {
    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person,container,false);

        return v;
    }
    @Override
    public void onStart() {

        super.onStart();
        TextView t1=getView().findViewById(R.id.textView12);
        TextView t2=getView().findViewById(R.id.textView14);
        TextView t3=getView().findViewById(R.id.textView16);
        TextView t4=getView().findViewById(R.id.textView22);
        t1.setText(User.user.getPhone_number());
        t2.setText(User.user.getNickname());
        if(User.user.getGender()==1)
            t3.setText("男");
        else
            t3.setText("女");
        t4.setText(User.user.getSignature());
    }
}
