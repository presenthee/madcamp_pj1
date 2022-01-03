package com.example.madcamp_pj1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference databaseReference = database.getReference();

    Button btn;
    EditText edit1, edit2,edit3,edit4;
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonSignup;
    TextView textviewSingin;
    TextView textviewMessage;
    ProgressDialog progressDialog;
    //define firebase object
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn = findViewById(R.id.btn);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
        edit4 = findViewById(R.id.edit4);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!= null){
            finish();

        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addstudent(edit1.getText().toString(), edit2.getText().toString(),edit3.getText().toString(),edit4.getText().toString(),"-1");
                Toast myToast = Toast.makeText(getApplicationContext(),"가입 되었습니다.", Toast.LENGTH_SHORT);
                myToast.show();
                onBackPressed();
            }
        });


    }

    public static class student {
        private String name; //동물 이름
        private String pw; //동물 종류
        private String school;
        private String sit;
        private String time;

        public student(){} // 생성자 메서드


        //getter, setter 설정

        public String getPw() {
            return pw;
        }

        public void setPw(String pw) {
            this.pw = pw;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getSit() {
            return sit;
        }

        public void setSit(String sit) {
            this.sit = sit;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        //값을 추가할때 쓰는 함수, MainActivity에서 addanimal함수에서 사용할 것임.
        public student(String name, String pw, String school, String sit, String time){
            this.name = name;
            this.pw = pw ;
            this.school = school;
            this.sit = sit;
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public void addstudent(String name, String pw, String school, String sit, String time) {

        //여기에서 직접 변수를 만들어서 값을 직접 넣는것도 가능합니다.
        // ex) 갓 태어난 동물만 입력해서 int age=1; 등을 넣는 경우

        //animal.java에서 선언했던 함수.
        student studentt = new student(name,pw,school,sit,time);

        //child는 해당 키 위치로 이동하는 함수입니다.
        //키가 없는데 "zoo"와 name같이 값을 지정한 경우 자동으로 생성합니다.
        databaseReference.child("User").child(name).setValue(studentt);

    }
}