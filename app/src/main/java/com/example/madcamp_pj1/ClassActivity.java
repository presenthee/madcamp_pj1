package com.example.madcamp_pj1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    TextView nameview;
    TextView schoolview;
    ArrayList<User> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("User"); //DB테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //데이터를 받아오는 곳
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //유저 데이터 추출
                    User user = dataSnapshot.getValue(User.class);
                    //arrayList.add(user);
                    nameview=(TextView) findViewById(R.id.name1);
                    schoolview=(TextView) findViewById(R.id.school1);

                    nameview.setText(user.getName());
                    schoolview.setText(user.getSchool());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //에러 발생 처리
                Log.e("ClassActivity",String.valueOf(error.toException()));
            }
        });

        //Log.d("ClassActivity",String.valueOf(arrayList.size()));


    }
}