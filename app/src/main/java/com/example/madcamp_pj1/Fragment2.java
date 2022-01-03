package com.example.madcamp_pj1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends Fragment implements View.OnClickListener {
    Button button;
    GridView gridview;
    private final int PICK_IMAGE = 1;
    public String basePath = null;
    ImageView imageView;

//    private List<Object> mThumbIds = new ArrayList<Object>();
//    mThumbIds.add(R.drawable.sample1);
//    mThumbIds.add(R.drawable.sample2);

    private Object [] mThumbIds = {
            R.drawable.sample1,
            R.drawable.sample2,
            R.drawable.sample3,
            R.drawable.sample4


    };
//    private List<Object> campic = new ArrayList<Object>();

    ImageAdapter imageAdapter = new ImageAdapter(getActivity());
    DisplayMetrics mMetrics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_2, container, false);

        gridview = view.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity()));

        imageView = view.findViewById(R.id.imageView);
        imageView.bringToFront();

        mMetrics = new DisplayMetrics();

        button = view.findViewById(R.id.button);
        button.setOnClickListener(this);
//        imageAdapter.notifyDataSetChanged();
        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                if (mThumbIds[i] instanceof Integer){
                    imageAdapter.deleteItem_image(mThumbIds[i]);

                }
                else if (mThumbIds[i] instanceof Uri){
                    imageAdapter.deleteItem_uri(mThumbIds[i]);

                    imageAdapter.notifyDataSetChanged();
                }
                Intent intent = new Intent(getActivity(), Empty.class);
                startActivity(intent);
                return true;
            }
        });

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(), "Click", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), BIgImage.class);
                if (mThumbIds[i] instanceof Integer){
                    intent.putExtra("image_integer", (Integer) mThumbIds[i]);

                }
                else if (mThumbIds[i] instanceof Uri){
                    intent.putExtra("image_uri", (String) mThumbIds[i].toString());

                }
                startActivity(intent);
            }
        });




        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.button):
                doTakeAlbumAction();
        }
    }

    private void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //String[] mimeTypes = {"image/jpeg", "image/png"};
        //intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent, PICK_IMAGE);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri uri = data.getData();
//                Intent intent = new Intent(getApplicationContext(), BIgImage.class);
//                intent.putExtra("image", uri);
//                startActivity(intent);
//                imageView.setImageURI(uri);
                Log.e("check1", String.valueOf(mThumbIds.length));
                imageAdapter.addItem(uri);
//                Toast.makeText(this,"hello11",Toast.LENGTH_LONG).show();
                Log.e("check2", String.valueOf(mThumbIds.length));
                imageAdapter.notifyDataSetChanged();

//                try {
//                    imageAdapter.addItem(uri);
//                    imageAdapter.notifyDataSetChanged();
//                } catch(Exception e){
                Toast.makeText(getActivity().getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
//                }
            }


        }


    }
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {

            mContext = c;
        }

        public int getCount() {

            return mThumbIds.length;
        }

        public Object getItem(int position) {

            return mThumbIds[position];
        }

        public long getItemId(int position) {

            return position;
        }

        public void addItem(Object uri) {
            List<Object> list = new ArrayList<>();
            for ( int i = 0 ; i< mThumbIds.length ; i++){
                list.add(mThumbIds[i]);
            }
            list.add(uri);

            Object[] array = new Object[list.size()];
            int size = 0;
            for(Object temp: list){
                array[size++] = temp;
            }
            mThumbIds = array;
        }

        public void deleteItem_uri(Object uri){
            List<Object> list = new ArrayList<>();
            for ( int i = 0 ; i< mThumbIds.length ; i++){
                list.add(mThumbIds[i]);
            }
            list.remove(uri);

            Object[] array = new Object[list.size()];
            int size = 0;
            for(Object temp: list){
                array[size++] = temp;
            }
            mThumbIds = array;
        }

        public void deleteItem_image(Object integer){
            List<Object> list = new ArrayList<>();
            for ( int i = 0 ; i< mThumbIds.length ; i++){
                list.add(mThumbIds[i]);
            }
            list.remove(integer);

            Object[] array = new Object[list.size()];
            int size = 0;
            for(Object temp: list){
                array[size++] = temp;
            }
            mThumbIds = array;
        }

//        public void refresh(){
//
//        }


        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {

            int rowWidth = (mMetrics.widthPixels) / 3;

            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(rowWidth,rowWidth));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(3, 3, 3, 3);
            } else {
                imageView = (ImageView) convertView;
            }


//            if(mThumbIds[position] instanceof Integer){
//                imageView.setImageResource((Integer) mThumbIds[position]);
//            }
//            else if (mThumbIds[position] instanceof Uri){
//                imageView.setImageURI((Uri)mThumbIds[position]);
//            }
//            imageView.setImageResource((Integer) mThumbIds[position]);
            if(mThumbIds[position] instanceof Integer){
                imageView.setImageResource((Integer) mThumbIds[position]);
            }
            else if(mThumbIds[position] instanceof Uri){
                imageView.setImageURI((Uri)mThumbIds[position]);
            }
            return imageView;
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        gridview = getView().findViewById(R.id.gridview);
        gridview = getView().findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity()));
        imageAdapter.notifyDataSetChanged();

    }
}

