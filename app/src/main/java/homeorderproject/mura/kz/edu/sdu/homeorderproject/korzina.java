package homeorderproject.mura.kz.edu.sdu.homeorderproject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mura on 07.02.2015.
 */

public class korzina extends Activity{

    private ListView listview;
    private ArrayList<AllPizza> list = new ArrayList<AllPizza>();
    private Context context;
    private DatabaseOpenHelper helper;
    private SQLiteDatabase mDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_korzina);

        initialize();
        initAdapter();
        getData();

        Parse.initialize(this, "EMGeIYHdztJQFaraRezQlBKEp7DaRAwo6GcqNoj5",
                "o3ipDHGnA33ROnJOUkPciBt6QBfMsF9tNlb5csfa");

    }

    private void initialize(){
        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setTitle("Корзина");
        }

        context = korzina.this;

        helper = new DatabaseOpenHelper(this);
        mDB = helper.getWritableDatabase();


    }


    private void initAdapter(){
        ArrayAdapter<AllPizza> adapter = new MyListAdapter();
        listview = (ListView) findViewById(R.id.korzinaList);
        listview.setAdapter(adapter);
    }

    private void getData(){
        Cursor c = mDB.query(DatabaseOpenHelper.DATABASE_TABLE1, null, null, null, null, null, null);

        if (c != null){
            if(c.moveToFirst()){
                do{
                    list.add(new AllPizza(c.getString(c.getColumnIndex("Name"))));
                    Log.d("qwe","data");
                }while(c.moveToNext());
            }
        }
    }

    private class MyListAdapter extends ArrayAdapter<AllPizza>{
        public MyListAdapter(){
            super(korzina.this, R.layout.listview_item,list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

/*
 *
 * 			Proverka na view, rabotaet ili net
 */
            View itemView = convertView;
            if (itemView == null) itemView = getLayoutInflater().inflate(R.layout.listview_item, parent, false);



            final AllPizza information = list.get(position);


/*
 *
 * 			vstavlyaem text v list
 *
 */
            TextView makeText = (TextView) itemView.findViewById(R.id.namePizza);
            makeText.setText(information.getMake());


            return itemView;
        }
    }

    private class AllPizza{
        private String make;
        public AllPizza(String make){
            super();
            this.make = make;
        }
        public String getMake(){
            return make;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater;
        inflater = getMenuInflater();
        inflater.inflate(R.menu.order_pizza, menu);
        return super.onCreateOptionsMenu(menu);

    }
}