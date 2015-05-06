package com.carpediam.timkao.gcm;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GCMActivity extends Activity {

    private GCMClientManager pushClientManager;
    private TextView GCMmsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcm);
        build_UI();

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            String GCM_MSG = this.getIntent().getExtras().getString("GCM_MSG");
            if(GCM_MSG != null)
            {
                GCMmsg.setText(GCM_MSG);
            }
        } else {
            //setting
            GCMConfiguration.PROJECT_NUMBER = "1063942889959";


            pushClientManager = new GCMClientManager(this, GCMConfiguration.PROJECT_NUMBER);
            pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
                @Override
                public void onSuccess(String registrationId, boolean isNewRegistration) {
                    Toast.makeText(GCMActivity.this, registrationId,
                            Toast.LENGTH_SHORT).show();
                    // SEND async device registration to your back-end server
                    // linking user with device registration id
                    // POST https://my-back-end.com/devices/register?user_id=123&device_id=abc
                }

                @Override
                public void onFailure(String ex) {
                    super.onFailure(ex);
                    // If there is an error registering, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off when retrying.
                }
            });
        }
    }

       private void build_UI()
    {
        TextView source = (TextView)findViewById(R.id.source);
        GCMmsg =  (TextView)findViewById(R.id.gcmMSG);

        //set data
        source.setText(GCMConfiguration.GCM_SERVER_URL);
    }

}
