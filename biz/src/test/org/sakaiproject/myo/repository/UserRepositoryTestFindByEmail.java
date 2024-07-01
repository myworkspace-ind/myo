package org.sakaiproject.myo.repository;

import static org.junit.Assert.*;

import java.util.UUID;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Test;
import org.sakaiproject.myo.entity.OkrUser;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTestFindByEmail {
	@Autowired
	private UserRepository up;

	@Test
	public void testFindByEmail() {
		OkrUser user = new OkrUser();
		user.setEmail("nhan@gmail.com");
		
		try {
			OkrUser obj = up.findByEmail("nhan@gmail.com");
			Assert.assertNotNull(obj);
			Assert.assertEquals(obj,user);
			
		}catch (NoResultException e) {
            fail("User not found for email: nhan@gmail.com");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
	}

}
