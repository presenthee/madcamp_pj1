package com.example.madcamp_pj1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Fragment4 extends Fragment {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference databaseReference = database.getReference("User");

    Field field;

    String name;
    TextView nameview;
    TextView schoolview;
    TextView timeview;

    String sitnumber;
    Integer sitnumber_int;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_4, container, false);

        EditText editText = view.findViewById(R.id.edit_time);
        ;
        Button btn_time = view.findViewById(R.id.btn_time);
        Integer[] resId = {R.id.name1,R.id.name2,R.id.name3,R.id.name4,R.id.name5,R.id.name6,
                R.id.name7,R.id.name8,R.id.name9,R.id.name10,R.id.name11,R.id.name12,R.id.name13,R.id.name14,
                R.id.name15,R.id.name16};
        Integer[] resId_school = {R.id.school1,R.id.school2,R.id.school3,R.id.school4,R.id.school5,R.id.school6,
                R.id.school7,R.id.school8,R.id.school9,R.id.school10,R.id.school11,R.id.school12,R.id.school13,R.id.school14,
                R.id.school15,R.id.school16};
        Integer[] resId_time = {R.id.time1,R.id.time2,R.id.time3,R.id.time4,R.id.time5,R.id.time6,R.id.time7,R.id.time8,R.id.time9,R.id.time10,
                R.id.time11,R.id.time12,R.id.time13,R.id.time14,R.id.time15,R.id.time16};

        if(getArguments() != null){
            name = getArguments().getString("name");
//            Log.d("Fragment4", "get");
             }



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);

                    sitnumber = user.getSit();
                    int sitnumber_int = Integer.parseInt(sitnumber);
                    nameview = view.findViewById(resId[sitnumber_int-1]);
                    schoolview = view.findViewById(resId_school[sitnumber_int-1]);
                    timeview = view.findViewById(resId_school[sitnumber_int-1]);
                    if(user.getTime().equals("-1")){
                        timeview.setText("미등록");
                    }
                    else{
                        timeview.setText(user.getTime());
                    }

                    nameview.setText(user.getName());
                    schoolview.setText(user.getSchool());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> ht = new HashMap<String, Object>();
                String att_time = editText.getText().toString();
                ht.put(name+"/"+"time", att_time);
                databaseReference.updateChildren(ht);

            }
        });
        return view;
    }

//    public class attendence_time{
//        private String sit_time;
//        private String name_sit;
//
////        public attendence_time(String sit_time, String name){};
//
//
//        public String getSit_time() {
//            return sit_time;
//        }
//
//        public void setSit_time(String sit_time) {
//            this.sit_time = sit_time;
//        }
//
//
//        public String getName_sit() {
//            return name_sit;
//        }
//
//        public void setName_sit(String name_sit) {
//            this.name_sit = name_sit;
//        }
//
//        public attendence_time(String sit_time, String name_sit)
//        {
//            this.sit_time = sit_time;
//            this.name_sit = name_sit;
//        }
//
//    }
//
//
//
//    public void addtime(String sit_time, String name_sit) {
//
//        attendence_time time = new attendence_time(sit_time,name_sit);
//
//        databaseReference.child(name_sit).updateChildren((Map<String, Object>) time);
//
//    }

//    public class student {
//        private String name; //동물 이름
//        private String pw; //동물 종류
//        private String school;
//        private String sit;
//        private String time;
//
//        public student(){} // 생성자 메서드
//
//
//        //getter, setter 설정
//
//        public String getPw() {
//            return pw;
//        }
//
//        public void setPw(String pw) {
//            this.pw = pw;
//        }
//
//        public String getSchool() {
//            return school;
//        }
//
//        public void setSchool(String school) {
//            this.school = school;
//        }
//
//        public String getSit() {
//            return sit;
//        }
//
//        public void setSit(String sit) {
//            this.sit = sit;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//
//        //값을 추가할때 쓰는 함수, MainActivity에서 addanimal함수에서 사용할 것임.
//        public student(String name, String pw, String school, String sit, String time){
//            this.name = name;
//            this.pw = pw ;
//            this.school = school;
//            this.sit = sit;
//            this.time = time;
//        }
//
//        public String getTime() {
//            return time;
//        }
//
//        public void setTime(String time) {
//            this.time = time;
//        }
//    }
//
//    public void addstudent(String name, String pw, String school, String sit, String time) {
//
//        //여기에서 직접 변수를 만들어서 값을 직접 넣는것도 가능합니다.
//        // ex) 갓 태어난 동물만 입력해서 int age=1; 등을 넣는 경우
//
//        //animal.java에서 선언했던 함수.
//        RegisterActivity.student studentt = new RegisterActivity.student(name,pw,school,sit,time);
//
//        //child는 해당 키 위치로 이동하는 함수입니다.
//        //키가 없는데 "zoo"와 name같이 값을 지정한 경우 자동으로 생성합니다.
//        databaseReference.child("User").child(name).setValue(studentt);
//
//    }
}