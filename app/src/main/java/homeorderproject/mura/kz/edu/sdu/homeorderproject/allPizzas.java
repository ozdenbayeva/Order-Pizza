package homeorderproject.mura.kz.edu.sdu.homeorderproject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    private String Description;
    private TextView desciptionView;
    private TextView getNameText;
    private String pizzaName;
    private Bitmap bmp;
    private AlertDialog.Builder dialog;
    private ParseQuery<ParseObject> query2;
    private ParseQuery<ParseObject> query3;
    private Context context;
    private Intent intent;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_allpizzas);

        initialize();

        Parse.initialize(this, "EMGeIYHdztJQFaraRezQlBKEp7DaRAwo6GcqNoj5",
                "o3ipDHGnA33ROnJOUkPciBt6QBfMsF9tNlb5csfa");

        new RemoteDataTask().execute();


    }

    private void initialize(){
        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setTitle("Pizza");
        }

        context = allPizzas.this;
        dialog = new AlertDialog.Builder(context);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater;
        inflater = getMenuInflater();
        inflater.inflate(R.menu.korzina, menu);
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
            case R.id.korzina:
                intent = new Intent(context,korzina.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }



    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(allPizzas.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Виды пиццы...");
            // Set progressdialog message
            mProgressDialog.setMessage("Загрузка...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
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
            listview = (ListView) findViewById(R.id.allPizzas);
            adapter = new ArrayAdapter<String>(allPizzas.this,
                    R.layout.listview_item);

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
            listview.setAdapter(adapter);
            mProgressDialog.dismiss();
            listview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    getNameText = (TextView) view.findViewById(R.id.namePizza);
                    pizzaName = getNameText.getText().toString();

                    intent = new Intent(allPizzas.this,showPizza.class);
                    intent.putExtra("name",pizzaName);
                    startActivity(intent);
                }
            });
        }
    }

    private void Dialog(){
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);


        image = new ImageView(context);
        image.setLayoutParams(lp);
        image.setImageBitmap(bmp);

        desciptionView = new TextView(context);
        desciptionView.setLayoutParams(lp);
        desciptionView.setText(Description);

        dialog.setTitle(pizzaName);
        dialog.setView(image);
        dialog.setView(desciptionView);

        layout.addView(image);
        layout.addView(desciptionView);

        dialog.setView(layout);

        dialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }
}