package com.example.madcamp_pj1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    public static Context cont;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;
    //로그인 확인을 위한 fragment.
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
                    case R.id.tab1:
                        Toast.makeText(getApplicationContext(),"첫번째",Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment1).commit();
                        return true;

                    case R.id.tab2:
                        Toast.makeText(getApplicationContext(),"세번째",Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment2).commit();
                        return true;

                    case R.id.tab3:
                        Toast.makeText(getApplicationContext(),"두번째",Toast.LENGTH_SHORT).show();
                        if(is_Login==true) {
                            //fragment4 실행
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment4).commit();
                            return true;
                        }

                        else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment3).commit();
                            return true;
                        }

                }
                return false;
            }
        });
    }

    // 인덱스를 통해 해당되는 프래그먼트를 띄운다.
    public void fragmentChange(int index){
        if(index == 1){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Fragment1()).commit();
        }
        else if(index == 2){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Fragment2()).commit();
        }

        else if(index == 3){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Fragment3()).commit();
        }

        else if(index == 4){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Fragment4()).commit();
        }

    }
}