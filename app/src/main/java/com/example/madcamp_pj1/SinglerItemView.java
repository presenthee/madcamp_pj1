package com.example.madcamp_pj1;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SinglerItemView extends LinearLayout {
    // 정의한 레이아웃을 설정해주는 클래스
    TextView textView, textView2;
    ImageView imageView;

    public SinglerItemView(Context context) {
        super(context);
        init(context);
    }

    public SinglerItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /*짜놓은 아이템 레이아웃 참조*/
        inflater.inflate(R.layout.singler_item_list,this,true);

        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        imageView=findViewById(R.id.imageView);
    }

    /*실제로 이미지와 텍스트를 넣는 메서드*/
   public void setName(String name) {
        textView.setText(name);
    }

    public void setMobile(String mobile) {
       textView2.setText(mobile);
    }

    public void setImage(int resld) {

       imageView.setImageResource(resld);
       imageView.setBackground(new ShapeDrawable(new OvalShape()));
       imageView.setClipToOutline(true);


    }
}
