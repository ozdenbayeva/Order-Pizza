package homeorderproject.mura.kz.edu.sdu.homeorderproject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.List;


public class MainActivity extends Activity {

    private AlertDialog.Builder dialog;
    private Button Login,About;
    private EditText name,password;
    private Context context;
    private String log, pass;
    private String title;
    private Intent intent;
    private EditText login;
    private EditText logPass;
    private Integer ADD = 1;
    private ParseObject parse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "EMGeIYHdztJQFaraRezQlBKEp7DaRAwo6GcqNoj5",
                "o3ipDHGnA33ROnJOUkPciBt6QBfMsF9tNlb5csfa");

        parse = new ParseObject("users");

        work();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater;
        inflater = getMenuInflater();
        inflater.inflate(R.menu.create_user, menu);
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
            case R.id.register_user:
                intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                //Register();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void work() {
        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setTitle("Order Pizza");
        }
        context = MainActivity.this;
        dialog = new AlertDialog.Builder(context);
        Login = (Button) findViewById(R.id.LogBtn);
        name = (EditText) findViewById(R.id.Login);
        password = (EditText) findViewById(R.id.Pathword);
        About = (Button) findViewById(R.id.about);

        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                log = name.getText().toString();
                pass = password.getText().toString();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("users");

                query.whereEqualTo("Name", log);
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> users, com.parse.ParseException e) {
                        if (e == null) {
                            parse = users.get(0);
                            String Name = parse.getString("Name");
                            String Password = parse.getString("Password");
                            Log.d("name", "Name " + Name + " names");
                            if (Name.equals(log) && Password.equals(pass)){
                                intent = new Intent(MainActivity.this, allPizzas.class);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

}
