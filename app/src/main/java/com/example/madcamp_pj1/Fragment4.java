package com.example.madcamp_pj1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;

public class Fragment4 extends Fragment {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference databaseReference = database.getReference("User");

    Field field;

    String name;
    TextView nameview;
    TextView schoolview;

    String sitnumber;
    Integer sitnumber_int;
    String name_sit;

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
        Button btn_time = view.findViewById(R.id.btn_time);
        Integer[] resId = {R.id.name1,R.id.name2,R.id.name3,R.id.name4,R.id.name5,R.id.name6,
                R.id.name7,R.id.name8,R.id.name9,R.id.name10,R.id.name11,R.id.name12,R.id.name13,R.id.name14,
                R.id.name15,R.id.name16};
        Integer[] resId_school = {R.id.school1,R.id.school2,R.id.school3,R.id.school4,R.id.school5,R.id.school6,
                R.id.school7,R.id.school8,R.id.school9,R.id.school10,R.id.school11,R.id.school12,R.id.school13,R.id.school14,
                R.id.school15,R.id.school16};

//        if(getArguments() != null){
//            String name_sit = getArguments().getString("name");
//             }



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);

                    sitnumber = user.getSit();
                    int sitnumber_int = Integer.parseInt(sitnumber);
                    nameview = view.findViewById(resId[sitnumber_int-1]);
                    schoolview = view.findViewById(resId_school[sitnumber_int-1]);

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
                addtime(editText.getText().toString(),name_sit);
            }
        });
        return view;
    }

    public class attendence_time{
        private String sit_time;
        private String name;

//        public attendence_time(String sit_time, String name){};


        public String getSit_time() {
            return sit_time;
        }

        public void setSit_time(String sit_time) {
            this.sit_time = sit_time;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public attendence_time(String sit_time, String name)
        {
            this.sit_time = sit_time;
            this.name = name;
        }

    }



    public void addtime(String sit_time, String name) {

        attendence_time time = new attendence_time(sit_time,name);

        databaseReference.child("User").child(name).setValue(time);

    }
}