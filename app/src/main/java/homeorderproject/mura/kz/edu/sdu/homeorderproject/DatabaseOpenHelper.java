package homeorderproject.mura.kz.edu.sdu.homeorderproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
	
	public static final String Order_id  = "Order_id";
	public static final String Pizza_name = "Name";
	public static final String Pizza_amount = "amount";
	public static final String Pizza_cost = "cost";
	public static final String Pizza_totalCost = "totalCost";




    public static final String Admin_name = "admin_name";
	public static final String Admin_password = "admin_password";
	public static final String Admin_id = "admin_id";

	
	
	
	
	public static final String DATABASE_NAME  = "journaldb";
	
	private static final int DATABASE_VERSION = 1;
	
	public static final String DATABASE_TABLE1 = "Order_Pizza";

	private final Context  mcontext;

	public DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mcontext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE1
				+ " ("
				+ Order_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ Pizza_name  + " TEXT NOT NULL, "
				+ Pizza_amount + " TEXT NOT NULL, "
				+ Pizza_cost + " TEXT NOT NULL, "
				+ Pizza_totalCost + " TEXT NOT NULL);"
		);


		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		onCreate(db);
	}
	
	void DeleteDataBase(){
		mcontext.deleteDatabase(DATABASE_NAME);
	}
}
