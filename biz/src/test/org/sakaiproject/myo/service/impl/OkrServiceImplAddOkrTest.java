package org.sakaiproject.myo.service.impl;

import static org.junit.Assert.*;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OkrServiceImplAddOkrTest {

	@Autowired 
	OkrServiceImpl test;
	
	@Test
	public void test() {
		try {
			test.addOKR("yes", "yes", "yes", "yes");
			
		} catch(Exception e){
			e.printStackTrace();
			Assert.fail(e.getMessage());
			
		}
		
	}

}
