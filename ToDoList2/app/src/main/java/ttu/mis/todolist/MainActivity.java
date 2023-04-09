package ttu.mis.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnItemClickListener {
    DBAccess access;
    private static final String DB_FILE = "friends.db";
    private static final String DB_TABLE = "friends";
    private FriendDbOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private Cursor c;
    private TextView mEdtName;
    private TextView mEdtSex;
    private String[] name1;
    private TextView mEdtAddr;
    private SimpleAdapter adapter=null;
    private RecyclerView recyclerViewFriends;
    // private MyFriendItemRecyclerViewAdapter myFriendItemRecyclerViewAdapter;
    private List <MyFriend> friendList = new ArrayList <>();
    ListView lv;
    //cursor資料指標物件與ListView連接的adapter，cursor中必須要有_id欄位資料


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找出列表和按鈕物件與設定事件監聽器
        lv = findViewById(R.id.listView1);


        Button btn = findViewById(R.id.button1);
        btn.setOnClickListener(this);
        //設定ListView選項點選事件
        lv.setOnItemClickListener(this);
        //產生自訂DBAccess物件，以建立資料庫、表和進行資料表操作
        //資料庫檔案名稱為schedule,版本為1
        //access = new DBAccess(this, "schedule", null, 1);
        dbOpenHelper = new FriendDbOpenHelper(this, DB_FILE, null, 1);
        db = dbOpenHelper.getWritableDatabase();
        // 亦可在 FriendDbOpenHelper 之 onCreate() 中即 CREATE TABLE
        db.execSQL("CREATE TABLE IF NOT exists " + DB_TABLE + " (" +
                "_id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "sex TEXT," +
                "address TEXT);");

        // Not use for now (可略)
        c = db.rawQuery("select * from " + DB_TABLE, null);

        db.close();
    }

    @Override
    protected void onResume() {
        /*取得資料表中大於等於今天日期(strftime('%Y-%m-%d','now')方法是
         * SQLite內建的語法，now表示取得現在日期時間，strftime表示將日期進行
         * 格式化，%Y表示顯示四位數年分，%m表示顯示兩位數月份、%d表示顯
         *示兩位數日期，詳細資料可參考SQLite官網)，並依據日期時間遞增排序*/
     /*   Cursor c = access.getData(
                DBAccess.DATE_FIELD + ">= strftime('%Y-%m-%d','now')",
                DBAccess.DATE_FIELD + " ," + DBAccess.TIME_FIELD);

        if (adapter == null) { //表示第一次執行，需建立SimpleCursorAdapter物件
		/*產生cursor資料指標物件與ListView連接的adapter物件，
		R.layout.list為自訂列表的一列畫面*/
            /*adapter = new SimpleCursorAdapter(this, R.layout.list, c,
                    new String[]{DBAccess.TITLE_FIELD,DBAccess.DATE_FIELD, DBAccess.TIME_FIELD, DBAccess.TITLE_FIELD},
                    new int[]{R.id.textView2, R.id.textView3, R.id.textView1},
                    0);
            lv.setAdapter(adapter);
        } else
            adapter.changeCursor(c);//更新ListView呈現的資料*/
        listAll();
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
        //當列表資料點選時會執行此方法，arg3為此筆資料的_id值
        db = dbOpenHelper.getWritableDatabase();

        c = db.rawQuery("SELECT * FROM " + DB_TABLE , null);
        c.moveToFirst();
        for (int i=0;i<position;i++)
        {
            c.moveToNext();
        }


        Intent intent = new Intent();
        intent.setClass(this, ModifyActivity.class); //設定新活動視窗類別

        Bundle bdl = new Bundle();
        bdl.putString("id", c.getString(0) + "");//將arg3傳遞到新的活動視窗中
        db.close();
        intent.putExtras(bdl);
        startActivity(intent); //開啟新的活動視窗


    }

    @Override
    public void onClick(View arg0) {

        //找出輸入日期、時間、標題的文字框物件和取得輸入的文字
       /* EditText ed = findViewById(R.id.editText1);
        ImageView img=findViewById(R.id.editImage);
        Byte im= Byte.valueOf(img.toString());
        String date = ed.getText() + "";
        ed = findViewById(R.id.editText2);
        String time = ed.getText() + "";
        ed = findViewById(R.id.editText3);
        String title = ed.getText() + "";
        long result = access.add(im,date, time, title);//執行新增資料操作
        if (result >= 0) {//新增成功
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            Cursor c = access.getData(
                    DBAccess.DATE_FIELD + ">= strftime('%Y-%m-%d','now')",
                    DBAccess.DATE_FIELD + " ," + DBAccess.TIME_FIELD);

            adapter.changeCursor(c);//更新ListView呈現的資料
        } else//失敗
            Toast.makeText(this, "新增資料失敗", Toast.LENGTH_LONG).show();*/
        mEdtName=findViewById(R.id.editText1);
        mEdtSex=findViewById(R.id.editText2);
        mEdtAddr=findViewById(R.id.editText3);
        String name = mEdtName.getText().toString();
        String sex = mEdtSex.getText().toString();
        String addr = mEdtAddr.getText().toString();

        addData(name, sex, addr);
    }
       public void listAll() {
        db = dbOpenHelper.getWritableDatabase();
        c = db.rawQuery("SELECT * FROM " + DB_TABLE, null);
        getAndDisplayData(c);
        db.close();
    }
    private void getAndDisplayData(Cursor c) {
        if (c == null) {
            //Toast.makeText(this,"0",Toast.LENGTH_LONG).show();
            return;
        }
        friendList.clear();

        if (c.getCount() == 0) {
            // mTxtList.setText("");
            Toast.makeText(MainActivity.this, "沒有這筆資料", Toast.LENGTH_LONG)
                    .show();
        } else {
            c.moveToFirst();
            // mTxtList.setText(c.getString(1) + " / " + c.getString(2) + " / " + c.getString(3));
            friendList.add(new MyFriend(c.getString(1),  c.getString(2), c.getString(3)));

            while (c.moveToNext()) {
                // mTxtList.append("\n" + c.getString(1) + " / " + c.getString(2) + " / " + c.getString(3));
                friendList.add(new MyFriend(c.getString(1),  c.getString(2), c.getString(3)));
            }
            List<String> name=new ArrayList<>();
            List <String>sex=new ArrayList<>();
            List <String>address=new ArrayList<>();
            for (int i=0;i<friendList.size();i++)
            {
                name.add(friendList.get(i).getName());
                sex.add(friendList.get(i).getSex());
                address.add(friendList.get(i).getAddr());
            }
             name1=name.toArray(new String[name.size()]);
            String[] sex1=sex.toArray(new String[sex.size()]);
            String[] address1=address.toArray(new String[address.size()]);
            //t.makeText(this,"1",Toast.LENGTH_LONG).show();
            //lv.setAdapter((ListAdapter) new MyRecyclerViewAdapter(name1,sex1,address1));
            List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
            for (int i=0;i < name1.length;i++){
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("name","名字:"+ name1[i]);
                item.put("sex", "性別:"+sex1[i]);
                item.put("address","地址:"+address1[i]);
                items.add(item);
            }
             adapter = new SimpleAdapter(
                    this,
                    items,
                    R.layout.list,
                    new String[]{"name", "sex","address"},
                    new int[]{R.id.textView2, R.id.textView3,R.id.textView1}
            );
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }


    }
    private void addData(String name, String sex, String addr) {

        db = dbOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("sex", sex);
        cv.put("address", addr);
        db.insert(DB_TABLE, null, cv);
        //Toast.makeText(this,cv.toString(),Toast.LENGTH_SHORT).show();
        db.close();
        listAll();
    }
}