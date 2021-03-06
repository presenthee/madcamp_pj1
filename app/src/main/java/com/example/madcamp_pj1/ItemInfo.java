package com.example.madcamp_pj1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemInfo extends AppCompatActivity {
    TextView sendname;
    TextView sendphone;
    de.hdodenhof.circleimageview.CircleImageView mainimage;
    ImageButton back;
    ImageButton phone_button;
    ImageButton comment;
    Button share;
    String name;
    String phonenumber;
    byte[] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        Intent intent = getIntent();

        sendname = (TextView) findViewById(R.id.send_name);
        sendphone = (TextView) findViewById(R.id.send_phone);
        back = (ImageButton) findViewById(R.id.back);
        phone_button = (ImageButton) findViewById(R.id.phone_button);
        comment = (ImageButton) findViewById(R.id.comment);
        mainimage = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.mainimage);
        share = (Button) findViewById(R.id.share);

        //이전 액티비티에서 이름과 번호 정보, 사진 가져오기
        name= intent.getStringExtra("name");
        phonenumber=intent.getStringExtra("phonenumber");
        arr = getIntent().getByteArrayExtra("image");

        sendname.setText(name);
        sendphone.setText(phonenumber);
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        mainimage.setImageBitmap(image);


        //뒤로가기 버튼 앞으로 가져오기
        back.bringToFront();

        //전화 걸기 버튼 기능 구현
        phone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+phonenumber));
                startActivity(callIntent);
            }
        });

        //문자 보내기 버튼 기능 구현
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
                startActivity(intent);*/
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address",phonenumber);
                startActivity(smsIntent);
            }
        });

        //뒤로가기 버튼 기능 구현
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //공유 버튼 기능 구현
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareintent = new Intent(Intent.ACTION_SEND);
                shareintent.setType("text/plain");
                shareintent.putExtra(Intent.EXTRA_TEXT, name+":"+phonenumber);

                Intent Sharing = Intent.createChooser(shareintent, "공유하기");
                startActivity(Sharing);
            }
        });

    }
}