package com.netpace.itc.service;

import java.util.ArrayList;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.netpace.itc.model.Slab;
import com.netpace.itc.util.SlabServiceClient;

public class CallService extends IntentService {
	
	public static final String TAG = "CallService"; 
	public static final String URL_SLABS_ALL = "http://192.168.1.193:8080/itc/slab/all";
	public static final String URL_SLAB = "http://192.168.1.193:8080/itc/slab/1";

	public CallService() {
		super("CallService");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {		
		Log.i(TAG, "requestSlab");
		
		SlabServiceClient service = new SlabServiceClient();
		ArrayList<Slab> slabs = (ArrayList<Slab>) service.getAll();
		
		for (Slab slab : slabs) {
			Log.i("StartValue", slab.getStartValue().toString());
			Log.i("EndValue", slab.getEndValue().toString());
		}
		
	}
	
}
