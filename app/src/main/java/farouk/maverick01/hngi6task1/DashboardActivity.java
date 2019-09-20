package farouk.maverick01.hngi6task1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
	// Our SharedPreference Filename
	private static final String PREF_NAME = "HNGiTask1";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		
		// Creation of our SharedPreferences handler...
		SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		
		// We link the TextViews in the xml file with the one declared here
		TextView nameText = findViewById(R.id.dbd_tv_name);
		TextView emailText = findViewById(R.id.dbd_tv_email);
		TextView phoneText = findViewById(R.id.dbd_tv_mobile);
		TextView briefText = findViewById(R.id.dbd_tv_brief_info);
		
		// We retrieve the email we pass from either the Register Screen or Login Screen
		String key = getIntent().getStringExtra("key");
		nameText.setText(sharedPreferences.getString(key + "NAM", "")); // We get the name stored for the user and display it
		emailText.setText(sharedPreferences.getString(key + "EML", "")); // We get the email stored for the user and display it
		phoneText.setText(sharedPreferences.getString(key + "PHN", "")); // We get the mobile stored for the user and display it
		briefText.setText("Lorem Ipsum Text....\nLorem Ipsum Text....\nLorem Ipsum Text....\nLorem Ipsum Text....\nLorem Ipsum" +
				" Text....\nLorem Ipsum Text....\nLorem Ipsum Text....\nLorem Ipsum Text....\nLorem Ipsum Text....\nLorem Ipsum" +
				" Text....\nLorem Ipsum Text...."); // We set our brief info here
		
		// Upon pressing the logout button
		Button logOutButton = findViewById(R.id.dbd_btn_logout);
		logOutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// We declare the Intent to open the Login Screen
				Intent mainIntent = new Intent(DashboardActivity.this, MainActivity.class);
				// We use this to close all other screen opened previously
				mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				// We use this to open the Login Screen
				startActivity(mainIntent);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// We declare the Intent to open the Login Screen
		Intent mainIntent = new Intent(DashboardActivity.this, MainActivity.class);
		// We use this to close all other screen opened previously
		mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		// We use this to open the Login Screen
		startActivity(mainIntent);
	}
}
