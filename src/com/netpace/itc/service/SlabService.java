package com.netpace.itc.service;

import java.util.List;
import retrofit.http.GET;
import retrofit.http.Path;

import com.netpace.itc.model.Slab;

public interface SlabService {
	
	@GET("/slab/all")
	List<Slab> getAll();
	
	@GET("/slab/{id}")
	Slab create(@Path("id") String id);
	
	@GET("/slab/{id}")
	Slab getOne(@Path("id") String id);
	
	@GET("/slab/{id}")
	Slab update(@Path("id") String id);
	
	@GET("/slab/{id}")
	Slab delete(@Path("id") String id);
}
