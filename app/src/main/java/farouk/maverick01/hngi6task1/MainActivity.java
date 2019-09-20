package farouk.maverick01.hngi6task1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
	// Our SharedPreference Filename
	private static final String PREF_NAME = "HNGiTask1";
	
	// Variables to access our TextFields...
	private EditText emailText;
	private EditText pwordText;
	
	// We declare the handler for our SharedPreferences
	private SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Creation of our SharedPreferences handler...
		sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		
		// we link the EditText in the xml file with the one declared here
		emailText = findViewById(R.id.log_et_email);
		pwordText = findViewById(R.id.log_et_pword);
		
		// Link the login button as well
		Button loginButton = findViewById(R.id.log_btn_login);
		// Upon pressing the login button
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Check the email and password if they're stored already
				if (validateLogin()) { // If the user has registered before... then code below runs....
					// We declare the Intent to open the Dashboard Screen
					Intent dbdIntent = new Intent(MainActivity.this, DashboardActivity.class);
					// Pass the email to the Dashboard Screen so as to get the details of the user
					dbdIntent.putExtra("key", emailText.getText().toString());
					// Open Dashboard Screen
					startActivity(dbdIntent);
				}
			}
		});
		
		// Link the register button as well
		Button registerButton = findViewById(R.id.log_btn_register);
		// Upon pressing the register button
		registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// We declare the Intent to open the Register Screen
				Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
				// Open the Register Screen
				startActivity(registerIntent);
			}
		});
	}
	
	private boolean validateLogin() {
		// Retrieve each field and put them in a String variable
		String email = emailText.getText().toString(); // The name field
		String pword = pwordText.getText().toString(); // The email field
		if (pword.length() != 0 && email.length() != 0) {
			// get the stored password
			String storedPword = sharedPreferences.getString(email + "PWD", "empty");
			// Check if the password match the one that is stored previous
			if (pword.equals(storedPword))
				// If it matches
				return true;
			else {
				// If the passwords does not match the one stored when registering
				// Display an Alert to notify the user
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setMessage("Invalid Credentials...\n\nRecheck!");
				builder.setTitle("Error");
				builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		} else {
			// If either of the EditText field is empty
			// Alert the user to fill it up
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setMessage("Please enter the required fields...!");
			builder.setTitle("Please Fill");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		
		return false;
	}
	
	@Override
	public void onBackPressed() {
		// If we press back in our Login Screen
		// Exit the app
		finish();
	}
}
