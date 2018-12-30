package com.harprit.aws.sam.helloworld;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

@SuppressWarnings("unchecked")
public class App implements RequestHandler<Object, Object> {

	@Override
	public Object handleRequest(Object input, Context context) {
		// analyze request
		Map<Object, Object> request = (HashMap<Object, Object>) input;
		Map<String, String> pathParameters = (HashMap<String, String>) request.get("pathParameters");
		String name = pathParameters.get("name");

		// prepare and send back response
		String message = String.format("Hello %s!", name);
		return new Response(200, message);
	}

	public static class Response {
		public int statusCode;
		public String body;

		public Response(int statusCode, String body) {
			this.statusCode = statusCode;
			this.body = body;
		}

		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public int getStatusCode() {
			return statusCode;
		}

		public String getBody() {
			return body;
		}
	}
}
