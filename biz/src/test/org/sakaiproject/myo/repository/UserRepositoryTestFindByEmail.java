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
		UUID user_id = UUID.fromString("1111");
		OkrUser user = new OkrUser();
		user.setUserId(user_id);
		user.setEmail("nhan@gmail.com");
		
		try {
			OkrUser obj = up.findByEmail("nhan@gmail.com");
			Assert.assertNotNull(obj);
			Assert.assertEquals(obj.getUserId(),user.getUserId());
		}catch (NoResultException e) {
            fail("User not found for email: nhan@gmail.com");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
	}

}
