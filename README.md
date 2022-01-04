# madcamp_pj1


# Abstract 


하단에 위치한 3개의 버튼을 통해 각 tab으로 이동이 가능한 간단한 앱.

Tab 1: 연락처
Tab 2: 갤러리
Tab 3: 출석 시스템

# TAB1 : My phonebook

Features
1.휴대전화 연락처 데이터&사진 로딩
2.Listview를 이용해서 linear한 전화번호부 구성.
3.상세정보 표시 기능

# TAB2 : My gallery

Features
1. Gridview를 이용해서 격자식 gallery 구성
2. 갤러리에서 사진 다운로드를 하여 My gallery 에 upload 기능 구현
3. Image 확대 축소 기능
4. Image 삭제 기능

1.Gridview를 이용해서 격자식 gallery 구성

```Java
 //xml
 <GridView
    android:id="@+id/gridview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_gravity="center"
    android:columnWidth="90dp"
    android:horizontalSpacing="10dp"
    android:numColumns="3"
    android:verticalSpacing="10dp"
    tools:context=".MainActivity"></GridView>
///fragment2
gridview = view.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity()));
```
2. 갤러리에서 사진 다운도륻 하여 My gallery에 upload 기능 구현
```Java
public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri uri = data.getData();
                imageAdapter.addItem(uri);
                imageAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity().getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
            }
        }
    }
```
Gallery 접근권한을 받아 Uri 타입으로 이미지를 가져온다.
addItem을 통해 이미지가 추가할때 마다 notifyDatasetChanged();를 통해 Gallery를 Update 해준다.

3.Image 확대 축소 기능
```
///Build.gradle(Module)에서 코드 추가
implementation 'com.davemorrissey.labs:subsampling-scale-image-view:3.10.0'

//xml 파일, subscaleview 를 통해 확대 축소가 가능한 type으로 setting
<com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
        android:id="@+id/image"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        android:layout_centerHorizontal="true"
        android:scaleType="centerInside"
    tools:ignore="MissingConstraints">
    </com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView>

//BIgimage에서 image type을 아래와 같이 선언
SubsamplingScaleImageView image;
```
SubsamplingScaleImageView type를 통해 function을 사용하지 않고도 Image 확대 축소가 가능하다. 

4. Image 삭제 기능
```
dialog.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int k) {
                        dialogInterface.dismiss();
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
```
기존 배열에 있는 이미지는 Integer, 다운받은 이미지는 Uri 타입이기 때문에, 두 가지 경우를 나누어 image 추가 삭제 기능을 구현하였다.




# TAB3: 출석 시스템

Firebase Database를 사용하여 사용자들로 부터, {name,password,school,sit_number} 을 받고 database에 기록을 한다.
처음 계정이 생성될때, 출석시간(time)은 -1로 setting이 된다. 
출석을 하고 출석시간을 입력하면, database에 시간이 기록이 되고 자기 자리에 빨간색 불이 들어온다. 
퇴근버튼을 누르면 '퇴근'이라는 text가 뜨고 주황색 불로 바뀐다.
계정을 등록하지 않은 경우에는, 모든 text가 미등록이라고 뜬다. 

Features
1. 로그인 및 회원가입
2. 출퇴근
3. error handling

1.로그인 및 회원가입

2. 출퇴근
```public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);

                    sitnumber = user.getSit();
                    int sitnumber_int = Integer.parseInt(sitnumber);
                    nameview = view.findViewById(resId[sitnumber_int-1]);
                    schoolview = view.findViewById(resId_school[sitnumber_int-1]);
                    timeview = view.findViewById(resId_time[sitnumber_int-1]);
                    // time = -1 이면 퇴근을 했거나, 계정이 생성되었지만 시간을 등록하지 않은 상태이다. 
		    if(user.getTime().equals("-1")){
                        timeview.setText("퇴근");
                        Drawable img = getActivity().getResources().getDrawable(R.drawable.dot2);
                        img.setBounds(0,0,60,60);
                        nameview.setCompoundDrawables(img,null,null,null);
                    }
                    else // time=-1이 아닌 경우에 text에 입력한 시간을 띄운다. 
		    {
                        timeview.setText(user.getTime());
                        Drawable img = getActivity().getResources().getDrawable(R.drawable.dot);
                        img.setBounds(0,0,60,60);
                        nameview.setCompoundDrawables(img,null,null,null);
                    }
```






