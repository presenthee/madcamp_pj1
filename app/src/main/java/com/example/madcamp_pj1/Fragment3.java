package com.example.madcamp_pj1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import static com.example.madcamp_pj1.MainActivity.is_Login;

public class Fragment3 extends Fragment {
    //로그인 창 fragment.
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    User ur;
    EditText namelogin;
    EditText pwlogin;
    Button btn_register;
    Button btn_login;
    ArrayList<User> arrayList;

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
        namelogin=(EditText)view.findViewById(R.id.namelogin);
        pwlogin=(EditText)view.findViewById(R.id.pwlogin);

        arrayList=new ArrayList<>();

        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("User"); //DB테이블 연결
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //데이터를 받아오는 곳
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //유저 데이터 추출
                    User user = dataSnapshot.getValue(User.class);
                    arrayList.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //에러 발생 처리
                Log.e("ClassActivity",String.valueOf(error.toException()));
            }
        });

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
                String id = namelogin.getText().toString();
                String password= pwlogin.getText().toString();
                //입력값이 없을 때
                if(id.equals("") || password.equals("")) {
                    Toast nToast = Toast.makeText(getActivity().getApplicationContext(),
                            "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT);
                    nToast.show();
                }
                //리스트가 비었을 때 -> 아직 가져온 데이터 initialized x
                else if (arrayList==null || (arrayList.size()==0)){
                    Toast aToast = Toast.makeText(getActivity().getApplicationContext(),
                            "Loading...", Toast.LENGTH_SHORT);
                    aToast.show();
                }
                //비교
                else {
                    for(int i=0; i<arrayList.size(); i++) {
                        ur=arrayList.get(i);
                        if(ur.getName()==null) {
                            Log.d("Fragment3", "empty");
                            //user에 이름 값이 없는경우
                        }

                        if(ur.getPw()==null) {
                            Log.d("Fragment3", "pwempty");
                            //user에 패스워드 값이 없는 경우
                        }

                        if (((ur.getName().equals(id))==true) && (ur.getPw().equals(password))==true) {
                            //비밀번호랑 아이디 일치시 fragment4(교실 창) 호출 - 로그인 성공 케이스
                            is_Login=true;
                            Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                            bundle.putString("name",ur.getName());//번들에 넘길 값 저장
                            bundle.putString("sit",ur.getSit());
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            Fragment4 fragment4 = new Fragment4();//프래그먼트4 선언
                            fragment4.setArguments(bundle);//번들을 프래그먼트4로 보낼 준비
                            transaction.replace(R.id.container, fragment4);
                            transaction.commitNow();
                        }
                    }
                }
                //로그인 실패
            }
        });

        return view;
    }
}