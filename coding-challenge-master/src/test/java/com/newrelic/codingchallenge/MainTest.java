package com.newrelic.codingchallenge;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class MainTest {

	private Client getClient() {
		Client client = new Client();
		client.startConnection("127.0.0.1", 4000);
		return client;
	}

	@Test
	public void nineDigitNumber() throws IOException {
		Client client = getClient();
		client.sendMessage("888888888");
		client.stopConnection();
	}
	
	@Test
	public void nineDigitNumberDuplicate() throws IOException {
		Client client = getClient();
		client.sendMessage("888888888");
		client.stopConnection();
	}
	
	@Test
	public void nineDigitNumberSecond() throws IOException {
		Client client = getClient();
		client.sendMessage("123456789");
		client.stopConnection();
	}

	@Test
	public void negative() throws IOException {
		Client client = getClient();
		client.sendMessage("-23456789");
		client.stopConnection();
	}
	
	@Test
	public void allZeores() throws IOException {
		Client client = getClient();
		client.sendMessage("000000000");
		client.stopConnection();
	}
	
	@Test
	public void eightDigitNumber() throws IOException {
		Client client = getClient();
		client.sendMessage("88888888");
		client.stopConnection();
	}

	@Test
	public void alphaNumeric() throws IOException {
		Client client = getClient();
		client.sendMessage("8888sde");
		client.stopConnection();
	}

	@Test
	public void terminate() throws IOException {
		Client client = getClient();
		client.sendMessage("terminate");
		client.stopConnection();
	}

}
