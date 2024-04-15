package org.sakaiproject.myo.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;


public class OkrRepositoryAddOKRTest {
//	@Mock
//	private final OkrRepository or;
	
//	@InjectMocks 
//	public OkrRepositoryAddOKRTest (OkrRepository or) {
//		this.or = or;
//	}
//

	@Autowired
	private OkrRepository or;
	@Test
	public void testInsertNewOkr() {
        String name = "TestName";
        String status = "InProgress";
        String complete = "No";
        String notes = "Test notes";
		or.insertnewOkr(name, status, complete, notes);
		
		
		
		fail("something wrong");
	}

}
