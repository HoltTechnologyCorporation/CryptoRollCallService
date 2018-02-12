package com.crc.service;

import org.junit.Assert;
import org.junit.Test;


public class DatabaseServiceTest {

	DatabaseServiceImpl databaseService = new DatabaseServiceImpl();
	
	@Test
	public void testIsHashExists() {
		Assert.assertTrue(databaseService.isHashExists("test"));
	}
	
	@Test
	public void testCreateHashEntry() {
		Assert.assertFalse(databaseService.createHashEntry("name", "test"));
	}
	
	@Test
	public void testGetPassphrase() {
		String passPhrase = databaseService.getRandomPassPhrase(3);
		Assert.assertNotNull(passPhrase);
	}
}
