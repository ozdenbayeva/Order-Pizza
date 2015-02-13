package homeorderproject.mura.kz.edu.sdu.homeorderproject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Mura on 07.02.2015.
 */

@SuppressWarnings("ALL")
public class showPizza extends Activity {


    private DatabaseOpenHelper helper;
    private SQLiteDatabase mDB = null;
    private ProgressDialog mProgressDialog;
    private List<ParseObject> ob;
    private ParseObject pizza;
    private EditText numPizza;
    private AlertDialog.Builder dialog;
    private ParseQuery<ParseObject> query2;
    private ParseQuery<ParseObject> query3;
    private String pizzaName;
    private Bitmap bmp;
    private String Description;
    private String objectID;
    private String object;
    private String amount;
    private String title;
    private Context context;
    private ImageView image;
    private TextView text;
    private TextView cena;
    private String cost;
    private String totalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_pizza);

        initialize();

        new RemoteDataTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater;
        inflater = getMenuInflater();
        inflater.inflate(R.menu.add_pizza, menu);
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
            case R.id.add_pizza:
                Dialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);


    }

    private void initialize(){

        context = showPizza.this;

        pizzaName = getIntent().getExtras().getString("name").toString();
        image = (ImageView)findViewById(R.id.pizza);
        text = (TextView) findViewById(R.id.sostav);
        cena = (TextView) findViewById(R.id.cost);

        title = pizzaName;

        dialog = new AlertDialog.Builder(context);

        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setTitle(pizzaName);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        helper = new DatabaseOpenHelper(context);
        mDB = helper.getWritableDatabase();

        Parse.initialize(this, "EMGeIYHdztJQFaraRezQlBKEp7DaRAwo6GcqNoj5",
                "o3ipDHGnA33ROnJOUkPciBt6QBfMsF9tNlb5csfa");


    }


    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(showPizza.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Pizza");
            // Set progressdialog message
            mProgressDialog.setMessage("Загружается...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "pizzas");
            try {
                ob = query.find();
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            query2 = ParseQuery.getQuery("pizzas");

            query2.whereEqualTo("pizzasName", pizzaName);
            query2.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> users, com.parse.ParseException e) {
                    if (e == null) {
                        if (users.size() > 0) {
                            pizza = users.get(0);
                            Log.d("pizza Name", pizza.getObjectId().toString());
                            objectID = pizza.getObjectId().toString();
                            cost = pizza.get("cost").toString();
                            Description = pizza.get("Description").toString();
                            object = objectID;
                            Log.d("pizza descr", Description);
                            Log.d("pizza id", objectID);
                            query3 = ParseQuery.getQuery("pizzas");
                            query3.getInBackground(pizza.getObjectId().toString(), new GetCallback() {
                                @Override
                                public void done(ParseObject object, ParseException e) {
                                    if (object == null) {
                                    } else {
                                        Log.d("text", "working");
                                        ParseFile fileObject = (ParseFile) object.get("Photo");
                                        fileObject.getDataInBackground(new GetDataCallback() {
                                            public void done(byte[] data, ParseException e) {
                                                if (e == null) {
                                                    bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                    image.setImageBitmap(bmp);
                                                    Log.d("test", Description);
                                                    text.setText(Description);
                                                    cena.setText(cost);

                                                } else {
                                                    Log.d("test", "There was a problem downloading the data.");
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    } else {

                    }
                }
            });


            Log.d("test", "working1");
            Log.d("pizza id is ...", object + "");

            mProgressDialog.dismiss();


        }
    }

    private void Dialog(){
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        numPizza = new EditText(context);
        numPizza.setLayoutParams(lp);
        numPizza.setInputType(InputType.TYPE_CLASS_NUMBER);
        numPizza.setHint("Количество пиццы");

        dialog.setTitle(title);
        dialog.setView(numPizza);

        layout.addView(numPizza);

        dialog.setView(layout);

        dialog.setPositiveButton("Добавить в Корзину", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                amount = numPizza.getText().toString();
                totalCost = Integer.parseInt(amount) * Integer.parseInt(cost) + "";
                addToDb(amount,totalCost);
                finish(); // метод для  самого конца
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }
    private long addToDb(String amount, String totalCost){
        ContentValues values = new ContentValues();
        values.put(DatabaseOpenHelper.Pizza_amount, amount);
        values.put(DatabaseOpenHelper.Pizza_name, pizzaName);
        values.put(DatabaseOpenHelper.Pizza_cost, cost);
        values.put(DatabaseOpenHelper.Pizza_totalCost, totalCost);
        return  mDB.insert(DatabaseOpenHelper.DATABASE_TABLE1, null, values);
    }
}
