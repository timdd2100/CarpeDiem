package com.carpediam.timkao.gcmclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.carpediam.timkao.gcm.GCMClientManager;
import com.carpediam.timkao.gcm.GCMConfiguration;


public class MainActivity extends ActionBarActivity {

    private GCMClientManager pushClientManager;
    private TextView token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token = (TextView)this.findViewById(R.id.token);

        GCMConfiguration.PROJECT_NUMBER = "53431596771";
        pushClientManager = new GCMClientManager(this, GCMConfiguration.PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                token.setText(registrationId);
                Toast.makeText(MainActivity.this, registrationId,
                        Toast.LENGTH_LONG).show();
                // SEND async device registration to your back-end server
                // linking user with device registration id
                // POST https://my-back-end.com/devices/register?user_id=123&device_id=abc
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
                token.setText("Fail");
                // If there is an error registering, don't just keep trying to register.
                // Require the user to click a button again, or perform
                // exponential back-off when retrying.
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
