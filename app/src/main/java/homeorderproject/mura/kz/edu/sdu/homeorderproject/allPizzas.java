package homeorderproject.mura.kz.edu.sdu.homeorderproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Mura on 07.02.2015.
 */
public class allPizzas extends Activity{

    private ListView listview;
    private List<ParseObject> ob;
    private ProgressDialog mProgressDialog;
    private ArrayAdapter<String> adapter;
    private ParseObject pizza;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_allpizzas);

        Parse.initialize(this, "EMGeIYHdztJQFaraRezQlBKEp7DaRAwo6GcqNoj5",
                "o3ipDHGnA33ROnJOUkPciBt6QBfMsF9tNlb5csfa");

        new RemoteDataTask().execute();
    }
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(allPizzas.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Parse.com Simple ListView Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the class table named "Country" in Parse.com
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "pizzas");
            query.orderByDescending("pizzasName");
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
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.allPizzas);
            // Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(allPizzas.this,
                    R.layout.listview_item);
            // Retrieve object "name" from Parse.com database
            /*
            for (ParseObject pizza : ob) {
                pizza = ob.get(count);
                count++;
                String Name = pizza.getString("Name");
                adapter.add(Name);
                Log.d("name", "Name " + Name + " names");
            }
            */
            ParseQuery<ParseObject> query = ParseQuery.getQuery("pizzas");
            query.whereNotEqualTo("pizzasName", "");

            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> users, com.parse.ParseException e) {
                    if (e == null) {
                        if (users.size() > 0) {
                            for (int i = 0; i < users.size(); i++) {
                                pizza = users.get(i);
                                String Name = pizza.getString("pizzasName");
                                adapter.add(Name);
                                Log.d("name", "Name " + Name + " names");
                            }
                        }
                    } else {
                        Toast.makeText(allPizzas.this, e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
            // Capture button clicks on ListView items
            listview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    //Intent i = new Intent(allPizzas.this,
                      //      SingleItemView.class);
                    // Pass data "name" followed by the position
                    //i.putExtra("name", ob.get(position).getString("name")
                      //      .toString());
                    //startActivity(i);
                }
            });
        }
    }
}