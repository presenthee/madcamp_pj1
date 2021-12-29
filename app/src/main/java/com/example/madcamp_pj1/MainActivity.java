package com.example.madcamp_pj1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //id로 list view 찾기 , 어댑터 객체 생성
        listView = (ListView) findViewById(R.id.listView);
        SingleAdapter adapter = new SingleAdapter();

        //사람 목록을 추가해줌
        adapter.addItem(new Singleitem("김뫄뫄", "010-0000-0000", R.drawable.img));
        adapter.addItem(new Singleitem("김솨솨", "010-0000-0000", R.drawable.img));
        adapter.addItem(new Singleitem("김나나", "010-0000-0000", R.drawable.img));
        listView.setAdapter(adapter);
    }

    class SingleAdapter extends BaseAdapter {
        //view를 담고 관리하는 어댑터 클래스
        ArrayList<Singleitem> items= new ArrayList<Singleitem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Singleitem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return  items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            SinglerItemView singlerItemView =null;

            //코드 재사용을 위한 조건문
            if(convertView==null) {
                singlerItemView = new SinglerItemView(getApplicationContext());
            }
            else {
                singlerItemView =(SinglerItemView)convertView;
            }
            Singleitem item = items.get(position);
            singlerItemView.setName(item.getName());
            singlerItemView.setMobile(item.getMobile());
            singlerItemView.setImage(item.getResld());
            return singlerItemView;
        }

    }
}