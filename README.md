##### 파일 경로를 통해 bitmap 파일 읽어 오는 법
##### BitmapFactory를 통해 options 값을 주고 BitmapFactory.decodeFile에 String값인 file 경로와 options를 넘겨주면 된다.
```java
public Bitmap getBitmap(String fileName){
        Bitmap bitmap = null;

        try {
            File file = new File(fileName);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeFile(fileName, options);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
```

##### ls-al을 통해 파일이 어떻게 저장되는지 확인한 후 불러올 때 정확한 파일 이름을 불러와야 한다.
##### .jpg를 붙였더니 파일이 불러오지 않음. 그 이유는 저장할 때 jpg를 붙이지 않았기 때문
```java
String filename = String.valueOf(System.currentTimeMillis());
drawingNote.setBitmap_path(filename);
```
```java
public String filePathNamer(DrawingNote drawingNote) {
        return context.getFilesDir().toString()+"/" + drawingNote.getBitmap_path();

    }
```

##### 그림이 그려지는 framelayout을 정의할 때 백그라운드를 흰색으로 설정하지 않으면 그림의 배경이 검은색으로 저장됨

##### table이 없을 시 최초 한번만 onCreate가 호출된다.
```java
@Override
public class DBHelper extends OrmLiteSqliteOpenHelper {
  ...
public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    try {
        TableUtils.createTable(connectionSource, DrawingNote.class);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
```

##### 결국 데이터베이스의 입력, 출력을 담당하는 클래스는 DAO 클래스임

##### FileInputStream을 사용할 때 유의할 점
##### FileInputStream의 객체를 받아올 때 context.openFileInput()을 사용하게 된다면
##### SD카드에 해당 이름이 있는 모든 파일을 다 찾아준다.
```java
public static Bitmap read(Context context, String fileName) throws IOException {
  FileInputStream fis = null;
  try {
            fis = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fis);
          }
        }
```

##### 권한 설정하는 방법

권한 획득을 위한 주요 API
 안드로이드 M Preview의 예제코드 중 주요 코드를 살펴보겠습니다.
 권한 획득하기 전 권한 유효성 체크
```
 checkSelfPermission(String) != PackageManager.PERMISSION_GRANTED
```
위와 같은 코드를 통해 허용돼 사용 가능한지 불가능한지에 대한 permission을 체크할 수 있습니다. 현재 API에서는 아쉽게도 1번에 1개밖에 하지 못하는군요.

 설명이 필요할 경우 처리
```
 shouldShowRequestPermissionRationale(String)
```
 권한 획득이 필요한 이유를 설명해야 한다면 다음 옵션을 추가하여 별도 처리가 가능합니다.

 권한 획득을 위한 API
```
 Activity.requestPermissions(String[], int) 
```
위의 권한 중 Group과 permission 2가지를 선택적으로 던질 수 있습니다. 한 번에 1개가 아닌 String[] 배열로 넘겨 한 번에 필요한 permission을 한 번에 획득할 수 있습니다.
 
 requestPermission의 경우 callback으로 return 됩니다. 
```
 onRequestPermissionResult(int, String[], int[])
```
 권한 획득에 대한 성공/실패에 대한 정보를 담은 callback입니다. 다음 함수 내에서 배열로 전달되므로 필요한 퍼미션이 잘 받아졌는지 확인하여 이후 처리가 가능합니다.
[출처 꿈많은 개발자가 되자 http://thdev.net/634]

서포트 라이브러리
앱 내에서 런타임 권한을 요청해야 하는 경우, 개발자 여러분이 하위 호환성 걱정 없이 새로운 API를 활용할 수 있도록 서포트 라이브러리(support-v4 library, revision 23)에 관련된 API가 추가되었습니다. ContextCompat.checkSelfPermission() 메서드를 이용해 현재 앱이 특정 권한을 갖고 있는지를 확인할 수 있고, ActivtiyCompat.requestPermissions() 메서드를 사용해, 안드로이드 시스템이 사용자에게 권한 요청 대화창을 표시하도록 할 수 있습니다. 사용자가 한 번 권한을 수락한 후에도, 시스템 설정 메뉴를 통해 기존 권한을 제거할 수도 있습니다. 이 점 꼭 기억하시기 바랍니다. 
[출처 Google Developers Korea https://developers-kr.googleblog.com/2015/09/android60runtimepermission.html]

##### 이 앱에서 권한 설정 과정
##### 1. 먼저 PermissionUtil이라는 클래스를 만들고 MainActivity에서 객체를 생성한다. 파라미터값으로는 Request Code와 Permission값을 넘겨준다. Request Code는 콜백함수를 호출했을 때 필요하며 Permission에는 권한값이 String으로 들어있다.
```java
private static final int REQ_CODE = 999;
private String permissions[] = {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
pUtil = new PermissionUtil(REQ_CODE, permissions);
```

##### 2. MainActivity에서 안드로이드 버전이 23 이상인지 확인한다. 만약 아니라면 true를 반환하여 각종 위젯 및 레이아웃을 초기화 한다.
```java
public class MainActivity extends AppCompatActivity {
if(pUtil.checkPermission(this)) {
    init();
}
}
```
```java
public boolean checkPermission(Activity activity){
        // 2. 버전 체크 후 권한 요청
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return requestPermission(activity);
        }else{
            return true;
        }
    }
```

##### 3. 버전이 23 이상이라면 requestPermission(activity) 함수를 호출하는데 이 함수에는 앱이 특정 권한을 가지고 있는지 확인하는 checkSelfPermission() 메서드가 있다.
```java
List<String> requires = new ArrayList<>();
        for(String permission : permissions){
            if(activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
                requires.add(permission);
            }
        }
```
앱에 우리가 추가하고자 하는 권한(String 배열로 만든 값)이 없다면  requestPermissions(String[], Request_Code)를 통해 권한을 추가할 것인지에 대한 팝업을 띄워준다.
```java
if(requires.size() > 0){
            String perms[] = requires.toArray(new String[requires.size()]);
            activity.requestPermissions(perms, req_code);
            return false;
        }else {
            return true;
        }
```
이 requestPermission 함수가 끝나게 되면 콜백 메서드로 MainActivity에 onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) 함수가 호출이 되는데
```java
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(pUtil.afterPermissionResult(requestCode, grantResults)){
        init();
    }else{
        finish();
    }
}
```
권한의 승인 여부는 int grantResults에 저장이되어 이 int 값들이 PackageManager.PERMISSION_GRANTED 인지를 확인해주면 된다.
```java
public boolean afterPermissionResult(int requestCode, int grantResults[]){
        if(requestCode == req_code){
            boolean granted = true;
            for(int grant : grantResults){
                if(grant != PackageManager.PERMISSION_GRANTED){
                    granted = false;
                    break;
                }
            }
            if(granted){
                return true;
            }
        }
        return false;
    }
```
