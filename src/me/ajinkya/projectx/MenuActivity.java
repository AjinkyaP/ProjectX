package me.ajinkya.projectx;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		ListView menu = populateMenu();
		menu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(MenuActivity.this, MapActivity.class);
				startActivity(intent);				
			}
		});
		
	}
	
	private ListView populateMenu(){
		String[] menuItems = new String[]{"Find Auto", "Get Cab", "Check bus timing", "Calculate Distance", "Traffic Information"};
		ArrayAdapter<String> menuArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems);
		ListView menuList = (ListView) findViewById(R.id.menuList);
		menuList.setAdapter(menuArrayAdapter);
		return menuList;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
