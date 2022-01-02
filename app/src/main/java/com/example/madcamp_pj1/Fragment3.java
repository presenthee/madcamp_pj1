package com.example.madcamp_pj1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Fragment3 extends Fragment {
    EditText namelogin;
    EditText pwlogin;
    Button btn_register;
    Button btn_login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        btn_register=(Button) view.findViewById(R.id.btn_register);
        btn_login=(Button) view.findViewById(R.id.btn_login);

        //회원가입 버튼 눌렀을 때 기능 구현
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        //로그인 버튼 눌렀을 때 기능 구현 --> 클래스 룸으로 화면 전환?
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}