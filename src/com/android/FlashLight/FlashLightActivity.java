package com.android.FlashLight;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FlashLightActivity extends Activity {
    private final static String LOG_TAG = "FlashLight";
    private Button mToggleBtn;
    private Camera mCamera;
    private boolean isCameraON;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mToggleBtn = (Button) findViewById(R.id.toggleButton);
        mToggleBtn.setOnClickListener(new OnClickListener() {
            //@Override
            public void onClick(View v) {
            	toggleLED();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        isCameraON = false;
        try{
            mCamera = Camera.open();
            //mCamera.startPreview();
        } catch( Exception e ){
            Log.e(LOG_TAG, "Cannot open camera.");
        }
        if (mCamera == null){
        	Log.e(LOG_TAG, "Camera object is NULL");
        }
    }
    @Override
    protected void onPause() {
        if( mCamera != null ){
        	//mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        super.onPause();
    }
    private void toggleLED(){
    	if( mCamera == null){
    		return;
    	}
    	Parameters mParams = mCamera.getParameters();
    	if( isCameraON == false ){
    		mParams.setFlashMode( Parameters.FLASH_MODE_TORCH );
            mCamera.setParameters( mParams );
            isCameraON = true;
    	}
    	else{
            mParams.setFlashMode( Parameters.FLASH_MODE_OFF );
            mCamera.setParameters( mParams );
            isCameraON = false;
    	}
    }
}