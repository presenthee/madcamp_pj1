# madcamp_pj1
### 팀원
강현희, 장준형
## Abstract 


하단에 위치한 3개의 버튼을 통해 각 tab으로 이동이 가능한 간단한 앱.

Tab 1: 연락처
Tab 2: 갤러리
Tab 3: 출석 시스템

## MainActivity.java

세 개의 탭간 전환을 실행한다. tab3의 경우 로그인 상태 여부를 판단하여 로그인 창 또는 출석 창으로 보낸다.



## TAB1 : My phonebook
Fragment1

### Features

1. 휴대전화 전화번호부 데이터/이미지 로딩
2. ListView를 이용해서 linear하게 전화번호 구성
3. 상세정보 표시 기능
4. 검색 

### 1.휴대전화 전화번호부 데이터/이미지 로딩

```Java
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
```
getcontact 함수를 이용하여 이름, 전화번호, 아이디를 가져온다. 이 정보를 이용하여 리스트 뷰에 텍스트를 띄울 때 사용한다.

```Java

final Bitmap fetchThumbnail(final int thumbnailId)
private Integer fetchThumbnailId()
public Bitmap addThumbnail()

```
QuickContacthelper의 위 세가지 메소드를 이용해 휴대폰 연락처에 등록된 비트맵을 가져온다.

### 2.ListView를 이용해서 linear하게 전화번호 구성
Custom List view, adapter를 구성하였다.

```Java
class SingleAdapter extends BaseAdapter implements Filterable {
        //view를 담고 관리하는 어댑터 클래스
        ArrayList<Singleitem> items = new ArrayList<Singleitem>(); //item의 리스트를 관리한다
	ArrayList<Singleitem> data = items;
        Filter listFilter; //
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
```
각 item이 가지고 있는 고유의 name, mobile, image를 세팅해준다. 
각 아이템은 Singleitem class가 나타내고, view는 SinglerItemView가 설정한다.


전체적인 레이아웃은 다음과 같다. (singler_item_list.xml 및 fragment_1.xml 참고)

### 3. 상세정보 표시 기능
리스트 뷰의 각 아이템 클릭시 intent를 이용하여 Iteminfo Activity로 이동. 해당 인물에 대한 상세 정보 창을 띄워준다.

```Java
//이전 액티비티에서 이름과 번호 정보, 사진 가져오기
        name= intent.getStringExtra("name");
        phonenumber=intent.getStringExtra("phonenumber");
        arr = getIntent().getByteArrayExtra("image");

        sendname.setText(name);
        sendphone.setText(phonenumber);
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        mainimage.setImageBitmap(image);
```


전체적인 레이아웃은 다음과 같다. (activity_item_info.xml 참고)

### 4. 검색 
adapter 가 filterable 인터페이스를 구현하도록 하여 검색 기능을 만들었다.

```Java
    public static Comparator<Singleitem> cmp=new Comparator<Singleitem>() {
        @Override
        public int compare(Singleitem t1, Singleitem t2) {
            String name1=t1.getName();
            String name2=t2.getName();
            return name1.compareTo(name2);
        }
    } ;
```
singleitem을 비교시 이름을 기준으로 비교하도록 Comparator를 오버라이딩 한다.
```Java
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
}
```
ListFilter inner class를 만들어서 필터링 수행 메소드를 오버라이드 한다. 검색어를 받고, 
검색어를 이름에 포함하는 item만 보여지도록 새 itemList를 제작한다.

### 5.기타
전화 걸기, 메세지 보내기, 전화번호 공유, 추가 기능 등이 있다.

다만, 전화번호부와 완전 동기화가 되는 것이 아니다 보니 추가/삭제를 하려면 연락처에 들어가서 해야한다는 문제점이 존재한다.
이 부분은 따로 전화번호를 저장하는 데이터베이스가 존재해야 해결 가능할 것 같다.



## TAB2 : My gallery
Fragment2

### Features
1. Gridview를 이용해서 격자식 gallery 구성
2. 갤러리에서 사진 다운로드를 하여 My gallery 에 upload 기능 구현
3. Image 확대 축소 기능
4. Image 삭제 기능

### 1.Gridview를 이용해서 격자식 gallery 구성

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
### 2. 갤러리에서 사진 다운도륻 하여 My gallery에 upload 기능 구현
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

### 3. Image 확대 축소 기능
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

### 4. Image 삭제 기능
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



## TAB3: 출석 시스템
Fragment3(로그인 창) / Fragment4(출석)

Firebase Database를 사용하여 사용자들로부터, {name,password,school,sit_number} 을 받고 database에 기록 한다.
처음 계정이 생성될때, 출석시간(time)은 -1로 setting이 된다. 
출석을 하고 출석시간을 입력하면, database에 시간이 기록이 되고 자기 자리에 빨간색 불이 들어온다. 
퇴근버튼을 누르면 '퇴근'이라는 text가 뜨고 주황색 불로 바뀐다.
계정을 등록하지 않은 경우에는, 모든 text가 미등록이라고 뜬다. 

### Features
1. 로그인 및 회원가입
2. 출퇴근
3. error handling

### 1.로그인 및 회원가입
firebase Database에 등록된 정보를 이용하여, 아이디와 비밀번호 비교 후 로그인을 수행한다.
로그인 성공시 fragment4(출석창)으로 화면이 전환된다.

새 정보를 등록하고 싶다면 회원가입 창을 이용한다. 버튼을 누르면 firebase에 정보가 전송된다.

### 2. 출퇴근
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

계정이 생성되는 순간 time= -1 로 설정이 되고, -1인 경우에는 '퇴근'이라는 text가 나온다
출근에 시간을 기입하고 버튼을 누르면 fireabase에 내장된 time이 바뀌면서 화면에 나오게 된다.
database에 정보가 바뀔때 마다 onDataChange 함수가 호출되며, 값을 새롭게 갱신시킨다.

### 3. error handling ;ㅅ;
데이터 베이스를 쓰는 경험이 처음이다 보니, 앱이 보안에 취약해졌다.

크게 두가지의 문제점이 있었는데,
	1. 로그인을 한 상태에서 다른 탭을 클릭하고 돌아온다. 그 후 출근/퇴근 버튼을 눌렀더니 앱이 꺼지는 현상.
	2. null user가 추가되는 현상. 
	
1의 경우 getActivity()가 fragment에서 null값을 리턴하는 현상 때문에 발생한다. 
이 문제는 onAttach() 함수를 오버라이딩 하여 activity를 저장하는 식으로 해결하였다.

2의 경우 출퇴근 기능을 구현할 때 database수정시 유저 정보를 체크하지 않아서 발생하였다. user값이 존재하지 않을 시 time을
변경하지 않는 방법으로 해결하였다.




