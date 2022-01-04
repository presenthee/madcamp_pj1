package com.example.madcamp_pj1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    //바텀 네비게이션 뷰를 이용해 하단 탭 구현함
    public static Context cont;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;
    //로그인 확인을 위한 flag --> 이 값을 이용해 탭 이동시 화면을 조정한다.
    static public boolean is_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        is_Login=false;
        cont=getApplicationContext();
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment1).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1: //전화번호부
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment1).commit();
                        return true;

                    case R.id.tab2: //갤러리
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment2).commit();
                        return true;

                    case R.id.tab3: //로그인 창 or 교실 페이지
                        if(is_Login==true) { //로그인이 되어있을 경우
                            //fragment4 실행 - 교실창
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment4).commit();
                            return true;
                        }

                        else { //로그인이 x인 경우 fragment3 로그인 창
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment3).commit();
                            return true;
                        }

                }
                return false;
            }
        });
    }

}