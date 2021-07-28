package com.example.applicationtest001.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.applicationtest001.Class.User;
import com.example.applicationtest001.UI.Function.EditActivity;
import com.example.applicationtest001.UI.Function.ModifypasswordActivity;
import com.example.applicationtest001.R;
import com.example.applicationtest001.UI.Login.MainActivity;

public class FragmentPerson extends Fragment {
    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person,container,false);



        return v;
    }

    public void onStart() {

        super.onStart();
        Button btn1 = (Button)getActivity().findViewById(R.id.editperson);

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getContext(), EditActivity.class);
                startActivity(i);
            }
        });

        Button btn2 = (Button)getActivity().findViewById(R.id.exitlogin);

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        Button btn3 = (Button)getActivity().findViewById(R.id.modifypassword);

        btn3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getContext(), ModifypasswordActivity.class);
                startActivity(i);
            }
        });

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
