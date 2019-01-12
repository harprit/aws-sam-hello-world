package com.harprit.aws.sam.helloworldapi;

import java.util.HashMap;
import java.util.Map;

import com.harprit.aws.sam.helloworldapi.App.Response;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testApp() {
		// prepare test pathParameters
		Map<String, String> pathParameters = new HashMap<>();
		pathParameters.put("name", "World");

		// prepare test Request
		HashMap<Object, Object> request = new HashMap<>();
		request.put("pathParameters", pathParameters);

		// test
		App underTest = new App();
		Response response = (Response) underTest.handleRequest(request, null);

		// check
		assertEquals("Hello World!\n", response.getBody());
		assertEquals(200, response.getStatusCode());
	}
}
