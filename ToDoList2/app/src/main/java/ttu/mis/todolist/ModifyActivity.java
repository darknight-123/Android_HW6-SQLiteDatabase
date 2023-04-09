package ttu.mis.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ModifyActivity extends Activity implements OnClickListener {
	DBAccess access;
	EditText dateET, timeET, titleET;
	private FriendDbOpenHelper dbOpenHelper;
	private static final String DB_FILE = "friends.db";
	private static final String DB_TABLE = "friends";
	private SQLiteDatabase db;
	String id;
	private Cursor c;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify);
		//找出按鈕物件與設定事件監聽器
		dateET=findViewById(R.id.editText1);
		timeET=findViewById(R.id.editText2);
		titleET=findViewById(R.id.editText3);
		Button btn=findViewById(R.id.button1);
		btn.setOnClickListener(this);
		btn=findViewById(R.id.button2);
		btn.setOnClickListener(this);
		//建立自訂資料庫操作(DBAccess)物件
		//access=new DBAccess(this, "schedule", null, 1);
		Bundle bdl=getIntent().getExtras();
		id=bdl.getString("id","0");

		//資料查詢，條件為_id等於上一個活動視窗傳遞過來的資料
		//Cursor c=access.getData(DBAccess.ID_FIELD+" ="+id, null);
		//c.moveToFirst();
		dbOpenHelper = new FriendDbOpenHelper(this, DB_FILE, null, 1);
		db = dbOpenHelper.getWritableDatabase();

		int ii=Integer.parseInt(id);
		c = db.rawQuery("SELECT * FROM " + DB_TABLE + " WHERE " + "_id='" + ii + "'", null);

		c.moveToFirst();

		dateET.setText(c.getString(1));
		timeET.setText(c.getString(2));
		titleET.setText(c.getString(3));

		//將資料呈現於EditText上
		/*c.getString(1), c.getString(2), c.getString(3)分別取出Cursor物件中第二個(date), 第三個(time), 第四個(title)欄位值*/

		db.close();
	}
	@Override
	public void onClick(View v) {
		int ii=Integer.parseInt(id);
		db = dbOpenHelper.getWritableDatabase();

		switch (v.getId()) {
			case R.id.button1://資料表中的資料修改
				/*access.update(dateET.getText()+"", timeET.getText()+"",
						titleET.getText()+"",DBAccess.ID_FIELD+" ="+id);*/

				ContentValues args = new ContentValues();
				args.put("name", String.valueOf(dateET.getText()));
				args.put("sex", String.valueOf(timeET.getText()));
				args.put("address", String.valueOf(titleET.getText()));
				db.update(DB_TABLE, args, "_id" + "=" + id , null);
				//c = db.rawQuery("SELECT * FROM " + DB_TABLE + " WHERE " + "_id='" + ii + "'", null);

				break;
			case R.id.button2://資料表中的資料刪除

				db.delete(DB_TABLE, "_id" + "=" + id , null);

				break;
		}

		db.close();

		finish();//關閉修改刪除活動視窗
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class); //設定新活動視窗類別
		startActivity(intent); //開啟新的活動視窗
	}
}
