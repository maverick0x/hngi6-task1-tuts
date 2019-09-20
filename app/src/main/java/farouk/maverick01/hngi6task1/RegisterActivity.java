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

public class RegisterActivity extends AppCompatActivity {
	// Our SharedPreference Filename
	private static final String PREF_NAME = "HNGiTask1";
	
	// Variables to access our TextFields...
	private EditText nameText;
	private EditText emailText;
	private EditText phoneText;
	private EditText pwordText;
	private EditText cPwordText;
	
	// We declare the handler for our SharedPreferences
	private SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		// Creation of our SharedPreferences handler...
		sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		
		// we link the EditTexts in the xml file with the one declared here
		nameText = findViewById(R.id.reg_et_name);
		emailText = findViewById(R.id.reg_et_email);
		phoneText = findViewById(R.id.reg_et_mobile);
		pwordText = findViewById(R.id.reg_et_pword);
		cPwordText = findViewById(R.id.reg_et_cpword);
		
		// Upon clicking the register button
		Button registerButton = findViewById(R.id.reg_btn_reg);
		registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Check if the user filled the fields correctly and store the data
				if (validateAndRegister()) {
					// We declare the Intent to open the Dashboard Screen
					Intent dbdIntent = new Intent(RegisterActivity.this, DashboardActivity.class);
					// Pass the email to the Dashboard Screen so as to get the details of the user
					dbdIntent.putExtra("key", emailText.getText().toString());
					// Open Dashboard Screen
					startActivity(dbdIntent);
				}
			}
		});
	}
	
	private boolean validateAndRegister() {
		// Retrieve each field and put them in a String variable
		String name = nameText.getText().toString(); // The name field
		String email = emailText.getText().toString(); // The email field
		String phone = phoneText.getText().toString(); // The mobile field
		String pword = pwordText.getText().toString(); // The password field
		String cpword = cPwordText.getText().toString(); // The confirm password field
		
		// Check if any of the field is not empty
		if (name.length() != 0 && email.length() != 0 && phone.length() != 0 && pword.length() != 0 && cpword.length() != 0) {
			// Check if text in the password field and confirm password field match
			if (pword.equals(cpword)) {  // If it matches, we proceed to store the data...
				// We check if the email is not used previously for any user
				if (!email.equals(sharedPreferences.getString(email + "EML", ""))) {
					// The SharedPreferences Editor that'll be  used to store store the data
					SharedPreferences.Editor editor = sharedPreferences.edit();
					// Now we store each data in a string format
					// We use email and suffix for each data as the key to store our data
					editor.putString(email + "NAM", name)         // Store the name
							.putString(email + "EML", email)      // Store the email
							.putString(email + "PWD", pword)      // Store the password
							.putString(email + "PHN", phone)      // Store the Mobile Number
							.apply();       // Then we apply to save the changes to the SharedPreferences file
					
					return true;  // We return true here so that the code to register and proceed to DashboardActivity works
				} else {
					// We alert the user that the email has been used by another User
					 AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
					 builder.setMessage("Email is already used...!\nLogin Instead");
					 builder.setTitle("Error");
					 builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
					 	@Override
					 	public void onClick(DialogInterface dialog, int which) {
					 		dialog.dismiss();
					 		// We declare the Intent to open the Login Screen
					 		Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
					 		// We use this to close all other screen opened previously
					 		mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
					 		// We use this to open the Login Screen
					 		startActivity(mainIntent);
				
					 	}
					 });
					 AlertDialog dialog = builder.create();
					 dialog.show();
				}
			} else {
				// If the passwords do not match... we display alert to the user to notify error in passwords
				AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
				builder.setMessage("Passwords do not match...!");
				builder.setTitle("Error");
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		} else {
			// if the user did not fill all the fields... We display alert to notify
			AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
		// If we press the back key....
		// We declare the Intent to open the Login Screen
		Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
		// We use this to close all other screen opened previously
		mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		// We use this to open the Login Screen
		startActivity(mainIntent);
	}
}
