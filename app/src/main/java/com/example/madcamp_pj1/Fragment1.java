package com.example.madcamp_pj1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Fragment1 extends Fragment {
    private ListView listView;
    ArrayList<String> arrayList;
    ArrayList<Singleitem> oriData;
    EditText et;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        //id로 list view, edit text 찾기 , 어댑터 객체 생성
        listView = (ListView) view.findViewById(R.id.listView);
        EditText et= (EditText)view.findViewById(R.id.editText);
        SingleAdapter adapter = new SingleAdapter();


        //전화 번호부를 가져오기 위한 메모리 초기화
        arrayList = new ArrayList<>();

        // for permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED)
            //request the permission
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);

        else {
            getcontact();
        }

        for (int i=0; i<arrayList.size(); i++) {
            //사람 목록을 추가해줌
            String s = arrayList.get(i);
            String[] info= s.split(",");
            Bitmap bitmap=new QuickContactHelper(getActivity().getApplicationContext(), info[1]).addThumbnail();
            if(bitmap==null) {
                bitmap=BitmapFactory.
                        decodeResource(getActivity().getApplicationContext().getResources(), R.drawable.img1);}
            bitmap=getCroppedBitmap(bitmap);
            adapter.addItem(new Singleitem(info[0],info[1],info[2],bitmap));
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
                ((SingleAdapter)listView.getAdapter()).getFilter().filter(filterText) ;
            }
        });

        //item을 클릭했을시 상세 정보 창으로 이동한다.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(getActivity(),ItemInfo.class);
                Singleitem senditem= (Singleitem) adapter.getItem(i);
                intent.putExtra("name", senditem.getName());
                intent.putExtra("phonenumber", senditem.getMobile());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                senditem.getImage().compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("image",byteArray);
                startActivity(intent);
            }
        });

        //길게 클릭했을시 기능 구현
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                int p = i;
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                dialog.setMessage("추가하시겠습니까?");
                dialog.setCancelable(true);

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Creates a new Intent to insert a contact
                        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                        // Sets the MIME type to match the Contacts Provider
                        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                        startActivity(intent);

                        /*adapter.delItem(p);
                        adapter.notifyDataSetChanged();*/ //for deleteing item.
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.show();
                return true;
            }

        });

        return view;
    }

    class SingleAdapter extends BaseAdapter implements Filterable {
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
            Collections.sort(items, cmp);
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        public void delItem(int position) {
            data.remove(position);
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
                singlerItemView = new SinglerItemView(getActivity().getApplicationContext());
            } else { singlerItemView = (SinglerItemView) convertView;}

            Singleitem item = data.get(position);
            singlerItemView.setName(item.getName());
            singlerItemView.setMobile(item.getMobile());
            singlerItemView.setImage(item.getImage());
            return singlerItemView;
        }

        //검색을 위한 filter 메소드들 정의.
        @Override
        public Filter getFilter() {
            if (listFilter == null) {
                listFilter = new SingleAdapter.ListFilter() ;
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
                Collections.sort(data, cmp);
                if(results.count>0){
                    notifyDataSetChanged();
                }
                else {notifyDataSetInvalidated();}
            }

        }

    }

    public static Comparator<Singleitem> cmp=new Comparator<Singleitem>() {
        @Override
        public int compare(Singleitem t1, Singleitem t2) {
            String name1=t1.getName();
            String name2=t2.getName();
            return name1.compareTo(name2);
        }
    } ;

    //연락처 로딩을 위한 API
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getcontact() {
        //to pass all the phonebook to cursor
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null);
        //to fetch all the contact from cursor
        while (cursor.moveToNext()) {
            //pass the data into string from cursor
            // cursor은 column으로 넘어감
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String mobile = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

            arrayList.add(name+","+mobile+","+id);
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

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        // Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        // return _bmp;
        return output;
    }



}