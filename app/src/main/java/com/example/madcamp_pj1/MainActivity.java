package com.example.madcamp_pj1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //for adjusting
    int standardSize_X, standardSize_Y;
    float density;

    private ListView listView;
    ArrayList<String> arrayList;
    ArrayList<Singleitem> oriData;
    EditText et;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //id로 list view, edit text 찾기 , 어댑터 객체 생성
        listView = (ListView) findViewById(R.id.listView);
        et= (EditText)findViewById(R.id.editText);
        SingleAdapter adapter = new SingleAdapter();

        //검색어 입력창의 사이즈를 화면 크기에 맞춰서 설정해준다.
        //getStandardSize();
        //et.getLayoutParams().width=((int) (standardSize_X*2.5));

        //전화 번호부를 가져오기 위한 메모리 초기화
        arrayList = new ArrayList<>();

        // for permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED)
            //request the permission
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    1);

        else {
            getcontact();
        }

        for (int i=0; i<arrayList.size(); i++) {
            //사람 목록을 추가해줌
            String s = arrayList.get(i);
            String[] info= s.split(",");
            adapter.addItem(new Singleitem(info[0], info[1], R.drawable.img1));
        }
        listView.setAdapter(adapter);
        oriData = adapter.getItems();

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String filterText= editable.toString();
                /*if(filterText.length()>0) {
                    listView.setFilterText(filterText);
                }
                else { listView.clearTextFilter();}
                 */

                ((SingleAdapter)listView.getAdapter()).getFilter().filter(filterText) ;
            }
        });

        //item을 클릭했을시 이벤트가 발생한다.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(MainActivity.this,ItemInfo.class);
                Singleitem senditem= (Singleitem) adapter.getItem(i);
                intent.putExtra("name", senditem.getName());
                intent.putExtra("phonenumber", senditem.getMobile());

                startActivity(intent);
            }
        });
    }

    class SingleAdapter extends BaseAdapter implements Filterable{
        //view를 담고 관리하는 어댑터 클래스
        ArrayList<Singleitem> items = new ArrayList<Singleitem>();
        ArrayList<Singleitem> data = items;
        Filter listFilter;

        @Override
        public int getCount() {
            return data.size();
        }

        public void addItem(Singleitem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        public ArrayList<Singleitem> getItems() { return items; }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //view를 설정해주는 함수
        public View getView(int position, View convertView, ViewGroup parent) {
            SinglerItemView singlerItemView = null;

            //코드 재사용을 위한 조건문
            if (convertView == null) {
                singlerItemView = new SinglerItemView(getApplicationContext());
            } else {
                singlerItemView = (SinglerItemView) convertView;
            }
            Singleitem item = data.get(position);
            singlerItemView.setName(item.getName());
            singlerItemView.setMobile(item.getMobile());
            singlerItemView.setImage(item.getResld());
            return singlerItemView;
        }

        //검색을 위한 filter 메소드들 정의.
        @Override
        public Filter getFilter() {
            if (listFilter == null) {
                listFilter = new ListFilter() ;
            }
            return listFilter ;
        }

        private class ListFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                //검색하는 문자열이 없다면 그냥 전체 리스트 보여준다.
                if (constraint == null || constraint.length() == 0) {
                    results.values =oriData;
                    results.count = oriData.size();
                } else {
                    //전체 item들 중 이름에 검색어를 포함하는 애들만 itemList에 넣는다.
                    ArrayList<Singleitem> itemList = new ArrayList<>();
                    for (Singleitem item : oriData) {
                        if (item.getName().toUpperCase().contains(constraint.toString().toUpperCase()))
                            itemList.add(item);
                    }
                    results.values = itemList;
                    results.count = itemList.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (ArrayList<Singleitem>) results.values;
                if(results.count>0){
                    notifyDataSetChanged();
                }
                else {notifyDataSetInvalidated();}
            }

        }

    }

    //view size 조절을 위한 api
    public Point getScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return  size;
    }


    public void getStandardSize() {
        Point ScreenSize = getScreenSize(this);
        density  = getResources().getDisplayMetrics().density;

        standardSize_X = (int) (ScreenSize.x / density);
        standardSize_Y = (int) (ScreenSize.y / density);
    }



    //연락처 로딩을 위한 API
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getcontact() {
        //to pass all the phonebook to cursor
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null);
        //to fetch all the contact from cursor
        while (cursor.moveToNext()) {
            //pass the data into string from cursor
            // cursor은 column으로 넘어감
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String mobile = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            arrayList.add(name+","+mobile);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) ;
            {
                // now permission is granted
                getcontact();
            }
        }
    }


}