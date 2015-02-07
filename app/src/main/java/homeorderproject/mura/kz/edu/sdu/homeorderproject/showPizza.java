package homeorderproject.mura.kz.edu.sdu.homeorderproject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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

public class showPizza extends Activity {

    private ProgressDialog mProgressDialog;
    private List<ParseObject> ob;
    private ParseObject pizza;
    private ParseQuery<ParseObject> query2;
    private ParseQuery<ParseObject> query3;
    private String pizzaName;
    private Bitmap bmp;
    private String Description;
    private String objectID;
    private String object;
    private ImageView image;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_pizza);

        initialzie();

        new RemoteDataTask().execute();
    }

    private void initialzie(){

        pizzaName = getIntent().getExtras().getString("name").toString();
        image = (ImageView)findViewById(R.id.pizza);
        text = (TextView) findViewById(R.id.sostav);

        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setTitle(pizzaName);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
            mProgressDialog.setMessage("Loading...");
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
}
