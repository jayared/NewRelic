package com.newrelic.codingchallenge;

public class Main {
	private static final int PORT = 4000;

	public static void main(String[] args) {		
		Server server = new Server();
		server.deleteNumbersLog();
		System.out.println("Starting server");
		server.start(PORT);
	}

}
