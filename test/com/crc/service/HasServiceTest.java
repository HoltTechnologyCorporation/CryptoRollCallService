package com.crc.service;

import org.junit.Assert;
import org.junit.Test;

public class HasServiceTest {

	HashServiceImpl hashService = new HashServiceImpl();
	
	@Test
	public void testHash() {
		try {
			String hash = hashService.generateHash("dog cat man");
			System.out.println(hash);
			System.out.println(hashService.generateHash("asdasdsadas ad sdf"));
			System.out.println(hashService.generateHash("asdasdsadas ad sdf"));
			System.out.println(hashService.generateHash("asdasdsadas ad sdf"));

			Assert.assertNotNull(hash);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testOneTimeSaltGen() {
		try {
			hashService.generateOneTimeSalt();
		} catch(Exception ex) {
			Assert.fail();
		}
		
	}
}
