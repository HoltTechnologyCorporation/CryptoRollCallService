package com.crc.service;

import org.junit.Assert;
import org.junit.Test;

import com.crc.models.GetFolioRequest;

public class FolioServiceTest {

	FolioServiceImpl folioService = new FolioServiceImpl();
	
	@Test
	public void validateHashTest() {
		GetFolioRequest request = new GetFolioRequest();
		request.setName("MyFolio");
		request.setEncodedCreds("freest attal unnaturalized");
		Assert.assertNotNull(folioService.validateHash(request));
	}
}
