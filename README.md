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
