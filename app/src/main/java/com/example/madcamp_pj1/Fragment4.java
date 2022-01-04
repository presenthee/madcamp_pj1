package com.example.madcamp_pj1;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
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
    // 교실 화면 fragment.

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference databaseReference = database.getReference("User");

    Field field;
    private Activity mActivity;
    static String username; //static? 없어지는 것 같음(추측)
    TextView nameview;
    TextView schoolview;
    TextView timeview;

    String sitnumber;

    //null activity를 방지하기 위한 메소드. -->attach시 activity를 저장한다.
    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) { mActivity = (Activity)context; }
    }

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
        Button btn_gone = view.findViewById(R.id.btn_gone);
        Integer[] resId = {R.id.name1,R.id.name2,R.id.name3,R.id.name4,R.id.name5,R.id.name6,
                R.id.name7,R.id.name8,R.id.name9,R.id.name10,R.id.name11,R.id.name12,R.id.name13,R.id.name14,
                R.id.name15,R.id.name16};
        Integer[] resId_school = {R.id.school1,R.id.school2,R.id.school3,R.id.school4,R.id.school5,R.id.school6,
                R.id.school7,R.id.school8,R.id.school9,R.id.school10,R.id.school11,R.id.school12,R.id.school13,R.id.school14,
                R.id.school15,R.id.school16};
        Integer[] resId_time = {R.id.time1,R.id.time2,R.id.time3,R.id.time4,R.id.time5,R.id.time6,R.id.time7,R.id.time8,R.id.time9,R.id.time10,
                R.id.time11,R.id.time12,R.id.time13,R.id.time14,R.id.time15,R.id.time16};

        //fragment3(로그인 창)에서 name 정보를 받아온다.
        if(getArguments() != null){
            username = getArguments().getString("name");
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
                    timeview = view.findViewById(resId_time[sitnumber_int-1]);
                    if(user.getTime().equals("-1")){
                        timeview.setText("퇴근");
                        Drawable img = mActivity.getResources().getDrawable(R.drawable.dot2);
                        img.setBounds(0,0,60,60);
                        nameview.setCompoundDrawables(img,null,null,null);
                    }
                    else{
                        timeview.setText(user.getTime());
                        Drawable img = mActivity.getResources().getDrawable(R.drawable.dot);
                        img.setBounds(0,0,60,60);
                        nameview.setCompoundDrawables(img,null,null,null);
                    }

//                    DrawableCompat.setTint(img.mutate(), 12);
//                    img.setBounds(0,0,img.getIntrinsicWidth(), img.getIntrinsicHeight());
//                    nameview.setCompoundDrawables(img,null,null,null);
//                    btn_color.setColor(getResources().getColor(R.color.design_default_color_primary));



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
                String att_time = editText.getText().toString();
                // 입력이 없을경우 바꾸지 않는다.
                if (att_time!=null && username !=null) {
                    //입력한 시간 길이가 0인 경우 바꾸지 않음.
                    if(att_time.length()!=0) {
                        Map<String, Object> ht = new HashMap<String, Object>();
                        ht.put(username + "/" + "time", att_time);
                        databaseReference.updateChildren(ht);
                    }
                }

            }
        }
        );

        btn_gone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //name값이 없을 경우 바꾸지 않는다.
                if(username != null) {
                    Map<String, Object> ht = new HashMap<String, Object>();
                    ht.put(username + "/" + "time", "-1");
                    databaseReference.updateChildren(ht);
                }
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


}