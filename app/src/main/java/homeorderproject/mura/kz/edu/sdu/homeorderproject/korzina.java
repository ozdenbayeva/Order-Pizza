package homeorderproject.mura.kz.edu.sdu.homeorderproject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.Parse;

import java.util.ArrayList;

import database.DatabaseOpenHelper;
import swipe.SwipeDismissListViewTouchListener;

/**
 * Created by Mura on 07.02.2015.
 */

public class korzina extends Activity{

    private ListView listview;
    private ArrayList<AllPizza> list = new ArrayList<AllPizza>();
    private Context context;
    private DatabaseOpenHelper helper;
    private SQLiteDatabase mDB = null;
    private int check;
    private ArrayAdapter<AllPizza> adapter;
    private String pizzaName[] = new String[100];
    private String pizzaAmount[] = new String[100];
    private String pizzaCost[] = new String[100];
    private String pizzaTotalCost[] = new String[100];

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
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        context = korzina.this;

        helper = new DatabaseOpenHelper(this);
        mDB = helper.getWritableDatabase();


    }

    private void initAdapter(){
        adapter = new MyListAdapter();
        listview = (ListView) findViewById(R.id.korzinaList);
        listview.setAdapter(adapter);

        SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(
                listview, new SwipeDismissListViewTouchListener.DismissCallbacks(){

            @Override
            public boolean canDismiss(int position) {
                return true;
            }

            @Override
            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                for (int position : reverseSortedPositions){
                    adapter.remove(adapter.getItem(position));
                    Log.d("name",pizzaName[position].toString());

//                    pizzaName[position] = "0";
//                    pizzaCost[position] = "0";
//                    pizzaTotalCost[position] = "0";
//                    pizzaAmount[position] = "0";
                    deleteFromDb(pizzaName[position]);
                }
                adapter.notifyDataSetChanged();
            }
        });
        listview.setOnTouchListener(touchListener);
        listview.setOnScrollListener(touchListener.makeScrollListener());


    }
    private void getData(){
        Cursor c = mDB.query(DatabaseOpenHelper.DATABASE_TABLE1, null, null, null, null, null, null);
        check = 0;
        if (c != null){
            if(c.moveToFirst()){
                do{
                    list.add(new AllPizza(
                            c.getString(c.getColumnIndex("Name")),
                            c.getString(c.getColumnIndex("amount")),
                            c.getString(c.getColumnIndex("cost")),
                            c.getString(c.getColumnIndex("totalCost"))
                    ));




//                  инициализация массивов для удаления
                    pizzaName[check] = c.getString(c.getColumnIndex("Name"));
                    Log.d("check", pizzaName[check]);
                    pizzaCost[check] = c.getString(c.getColumnIndex("cost"));
                    pizzaTotalCost[check] = c.getString(c.getColumnIndex("totalCost"));
                    pizzaAmount[check] = c.getString(c.getColumnIndex("amount"));

                    check++;
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
            if (itemView == null) itemView = getLayoutInflater().inflate(
                    R.layout.listview_for_item_korzina, parent, false);



            final AllPizza information = list.get(position);


/*
 *
 * 			vstavlyaem text v list
 *
 */
            TextView makeText = (TextView) itemView.findViewById(R.id.namePizza2);
            TextView makeCost = (TextView) itemView.findViewById(R.id.cost2);
            TextView makeAmount = (TextView) itemView.findViewById(R.id.amount2);
            TextView makeTotalCost = (TextView) itemView.findViewById(R.id.totalCost2);

            makeText.setText(information.getMake());
            makeCost.setText(information.getCost());
            makeAmount.setText(information.getAmount());
            makeTotalCost.setText(information.getTotalCost());


            return itemView;
        }
    }

    private class AllPizza{
        private String make;
        private String cost;
        private String totalCost;
        private String amount;
        public AllPizza(String make, String cost, String totalCost, String amount){
            super();
            this.make = make;
            this.cost = cost;
            this.totalCost = totalCost;
            this.amount = amount;
        }
        public String getMake(){
            return make;
        }
        public String getAmount(){
            return amount;
        }
        public String getCost(){
            return cost;
        }
        public String getTotalCost(){
            return totalCost;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater;
        inflater = getMenuInflater();
        inflater.inflate(R.menu.order_pizza, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:

                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);


    }

    private void  deleteFromDb(String name) {
        mDB.delete(DatabaseOpenHelper.DATABASE_TABLE1,DatabaseOpenHelper.Pizza_name + "=?",
                new String[]{name});
        Log.d("deleted", name + " successful");
    }
}