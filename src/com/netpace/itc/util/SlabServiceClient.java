package com.netpace.itc.util;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;

import com.netpace.itc.model.Slab;
import com.netpace.itc.service.SlabService;

public class SlabServiceClient {

	private static final String API_URL = "http://192.168.1.193:8080/itc";
	public RestAdapter restAdapter;
	private SlabService slabService; 	// variable of client's service type
	

	public List<Slab> getAll() {
		// Create a very simple REST adapter which points the GitHub API endpoint.
		restAdapter = new RestAdapter.Builder()
					.setEndpoint(API_URL).build();
		
		 // Create an instance of our SlabService API interface.
		slabService = restAdapter.create(SlabService.class);
	
		// get all slabs
		ArrayList<Slab> slabs = (ArrayList<Slab>) slabService.getAll();
		return slabs;
	}
}
