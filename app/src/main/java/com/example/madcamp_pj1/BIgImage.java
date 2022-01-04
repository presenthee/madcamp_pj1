package com.example.madcamp_pj1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class BIgImage extends AppCompatActivity {

    ImageView image;

    enum TOUCH_MODE {
        NONE,   // 터치 안했을 때
        SINGLE, // 한손가락 터치
        MULTI   //두손가락 터치
    }

    private TOUCH_MODE touchMode;

    private Matrix matrix;      //기존 매트릭스
    private Matrix savedMatrix; //작업 후 이미지에 매핑할 매트릭스

    private PointF startPoint;  //한손가락 터치 이동 포인트

    private PointF midPoint;    //두손가락 터치 시 중신 포인트
    private float oldDistance;  //터치 시 두손가락 사이의 거리

    private double oldDegree = 0; // 두손가락의 각도

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
//        byte [] arr = getIntent().getByteArrayExtra("image");
//        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
//        ImageView BigImage = (ImageView) findViewById(R.id.image);
//        BigImage.setImageBitmap(image);
        setContentView(R.layout.simple_image);

        matrix = new Matrix();
        savedMatrix = new Matrix();

        image = findViewById(R.id.image);
        Intent intent = getIntent();
        final Integer integer = intent.getIntExtra("image_integer",0);
        final String uri = intent.getStringExtra("image_uri");

//        if (intent instanceof Integer){
//        image.setImageResource(intent.getIntExtra("image",0));
//        }
        if (integer instanceof Integer) {
            image.setImageResource(integer);
//            image.setOnTouchListener(onTouch);
//            image.setScaleType(ImageView.ScaleType.MATRIX);
//            image.setScaleType(ImageView.ScaleType.CENTER);



        }
        if (uri instanceof String){
            Uri s_uri = Uri.parse(uri);
//            try {
//                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),s_uri);
//                image.setImageBitmap(getResizedBitmap(bm,image.getWidth(),image.getHeight()));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            image.setImageURI(s_uri);
//            image.setScaleType(ImageView.ScaleType.CENTER);
//            image.setOnTouchListener(onTouch);
//            image.setScaleType(ImageView.ScaleType.CENTER);




        }
//        int width = image.getWidth();
//        int height = image.getHeight();

        image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


        image.setOnTouchListener(onTouch);
//        image.setScaleType(ImageView.ScaleType.MATRIX);
//

    }
    private View.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
//            image.setScaleType(ImageView.ScaleType.MATRIX);
//            image.setTranslationX(10);
//            image.setTranslationY(10);
            if (v.equals(image)) {
                int action = event.getAction();
                switch (action & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        touchMode = TOUCH_MODE.SINGLE;
                        donwSingleEvent(event);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (event.getPointerCount() == 2) { // 두손가락 터치를 했을 때
                            touchMode = TOUCH_MODE.MULTI;
                            downMultiEvent(event);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (touchMode == TOUCH_MODE.SINGLE) {
                            moveSingleEvent(event);
                        } else if (touchMode == TOUCH_MODE.MULTI) {
                            moveMultiEvent(event);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        touchMode = TOUCH_MODE.NONE;
                        break;
                }
            }


            return true;
        }
    };

    private PointF getMidPoint(MotionEvent e) {
        //두 손가락 사이의 중간점
        float x = (e.getX(0) + e.getX(1)) / 2;
        float y = (e.getY(0) + e.getY(1)) / 2;

        return new PointF(x, y);
    }

    private float getDistance(MotionEvent e) {
        //두 손가락 사이의 거리
        float x = e.getX(0) - e.getX(1);
        float y = e.getY(0) - e.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void donwSingleEvent(MotionEvent event) {
        //한손가락 눌렀을 경우 시작지점 저장
        savedMatrix.set(matrix);
        startPoint = new PointF(event.getX(), event.getY());
    }

    private void downMultiEvent(MotionEvent event) {
        // 두 손가락 눌렀을 경우 중심점과 각도 저장
        oldDistance = getDistance(event);
        if (oldDistance > 5f) {
            savedMatrix.set(matrix);
            midPoint = getMidPoint(event);
            double radian = Math.atan2(event.getY() - midPoint.y, event.getX() - midPoint.x);
            oldDegree = (radian * 180) / Math.PI;
        }
    }

    private void moveSingleEvent(MotionEvent event) {
        matrix.set(savedMatrix);
        matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
        image.setImageMatrix(matrix);
    }

    private void moveMultiEvent(MotionEvent event) {
        float newDistance = getDistance(event);
        if (newDistance > 5f) {
            matrix.set(savedMatrix);
            float scale = newDistance / oldDistance;
            matrix.postScale(scale, scale, midPoint.x, midPoint.y);

            double nowRadian = Math.atan2(event.getY() - midPoint.y, event.getX() - midPoint.x);
            double nowDegress = (nowRadian * 180) / Math.PI;
            float degree = (float) (nowDegress - oldDegree);
            matrix.postRotate(degree, midPoint.x, midPoint.y);


            image.setImageMatrix(matrix);

        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int layoutWidth, int layoutHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        Toast.makeText(getApplicationContext(), "layout width : " + layoutWidth + " layout height : " + layoutHeight + " width : " + width + " height : " + height, Toast.LENGTH_SHORT).show();
        float scaleWidth = ((float) layoutWidth) / width;
        float scaleHeight = ((float) layoutHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix(); // RESIZE THE BIT MAP
        if(width > layoutWidth && height > layoutHeight) {
            // 레이아웃보다 큰 이미지인 경우
            matrix.postScale(scaleWidth, scaleHeight);
        } else if(width > layoutWidth) { // 가로만 레이아웃보다 큰 경우
            matrix.postScale(scaleWidth, scaleWidth);
        } else if(height > layoutHeight) { //세로만 레이아웃보다 큰 경우
            matrix.postScale(scaleHeight, scaleHeight);
        } else { // 레이아웃보다 작은 이미지인 경우
            return bm;
        } // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap( bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }



}
