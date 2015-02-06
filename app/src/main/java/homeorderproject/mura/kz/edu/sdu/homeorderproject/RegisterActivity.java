package homeorderproject.mura.kz.edu.sdu.homeorderproject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

public class RegisterActivity extends Activity {

	private Button Reg;
	private EditText log,pass,repass;
    private ParseObject parse;
	private Intent intent;
	String name,pwrd,pwrd2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);

        //Parse.enableLocalDatastore(this);

        Parse.initialize(this, "EMGeIYHdztJQFaraRezQlBKEp7DaRAwo6GcqNoj5",
                "o3ipDHGnA33ROnJOUkPciBt6QBfMsF9tNlb5csfa");

        parse = new ParseObject("users");


        intent = new Intent();
		work();

        ActionBar actionBar = getActionBar();
		if(actionBar != null){ 
			actionBar.setTitle("Registration"); 
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				setResult(RESULT_OK, intent);
				finish();
				break;
			
			default:
				return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

	private void work() {
		Reg = (Button) findViewById(R.id.RegSign);
		
		Reg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					log = (EditText) findViewById(R.id.RegLogin);
					pass = (EditText) findViewById(R.id.RegPass);
					repass = (EditText) findViewById(R.id.RegPass2);
					
					name = log.getText().toString();
					pwrd = pass.getText().toString();
					pwrd2 = repass.getText().toString();
					if (pwrd.equals(pwrd2)){
						Log.d("OK", "True");
                        parse.put("Name",name);
                        parse.put("Password",pwrd);
                        parse.saveInBackground();
						finish();
					}
					else Toast.makeText(RegisterActivity.this, "passwords don't match", Toast.LENGTH_LONG).show();
					
				}
				catch (Exception e){
		
				}
				
			}
		});
	}

	
}
