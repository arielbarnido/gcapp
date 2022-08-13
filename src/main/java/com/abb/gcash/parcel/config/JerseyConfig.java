package com.abb.gcash.parcel.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.abb.gcash.parcel.resource.ParcelCostResource;

@Configuration
public class JerseyConfig extends ResourceConfig{

	public JerseyConfig(){
		register(ParcelCostResource.class);
	}
}
