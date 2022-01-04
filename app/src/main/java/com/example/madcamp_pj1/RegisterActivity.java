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
    //회원 가입 창. 받은 정보를 firebase에 추가한다.
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference databaseReference = database.getReference();

    Button btn;
    EditText edit1, edit2,edit3,edit4;
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


        //버튼 클릭시 정보를 파이어 베이스에 업로드 한다.
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input_name = edit1.getText().toString();
                String input_pw = edit2.getText().toString();
                String input_school = edit3.getText().toString();
                String input_sit = edit4.getText().toString();

                //입력 값을 모두 입력했는지 체크한다.
                if(input_name==null||input_pw==null||input_school==null||input_sit==null) {
                    Toast iToast = Toast.makeText(getApplicationContext(),
                            "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT);
                    iToast.show();
                }

                if(input_name.length()==0||input_name.length()==0||input_name.length()==0||input_sit.length()==0){
                    Toast iToast = Toast.makeText(getApplicationContext(),
                            "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT);
                    iToast.show();
                }

                if(Integer.parseInt(input_sit)>16 || Integer.parseInt(input_sit)<1 ) {
                    Toast iToast = Toast.makeText(getApplicationContext(),
                            "자릿값은 1-16이어야 합니다.", Toast.LENGTH_SHORT);
                    iToast.show();
                }

                addstudent(input_name, input_pw, input_school, input_sit,"-1");
                Toast myToast = Toast.makeText(getApplicationContext(),"가입 되었습니다.", Toast.LENGTH_SHORT);
                myToast.show();
                onBackPressed();
            }
        });


    }

    public void addstudent(String name, String pw, String school, String sit, String time) {

        //여기에서 직접 변수를 만들어서 값을 직접 넣는것도 가능
        User studentt = new User(name,pw,school,sit,time);

        //child는 해당 키 위치로 이동하는 함수
        //키가 없는데 "zoo"와 name같이 값을 지정한 경우 자동으로 생성
        databaseReference.child("User").child(name).setValue(studentt);

    }
}