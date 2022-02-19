package com.newrelic.codingchallenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

	private static final int MAX_CLIENTS = 5;
	private final BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
	private final ExecutorService executorService = Executors.newFixedThreadPool(MAX_CLIENTS);
	private ServerSocket serverSocket;
	private AtomicBoolean running = new AtomicBoolean(true);

	public void start(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println(e);
			;
		}

		LogWriterTask logWriterTask = new LogWriterTask(blockingQueue);
		executorService.submit(logWriterTask);

		while (true) {
			try {
				ClientHandler clientHandler = new ClientHandler(serverSocket.accept(), blockingQueue, executorService);
				executorService.submit(clientHandler);
			} catch (IOException e) {
				System.err.println(e);
				;
			}
		}
	}

	public void deleteNumbersLog() {
		Path pathOfFile = Paths.get("numbers.log");
		try {
			if (Files.deleteIfExists(pathOfFile)) {
				System.out.println("File is deleted");
			} else {
				System.out.println("File does not exists");
			}

		} catch (IOException e) {
			System.err.println(e);

		}
	}

	private class ClientHandler implements Runnable {

		private final BlockingQueue<Integer> blockingQueue;
		private final Socket clientSocket;
		private final ExecutorService executorService;

		public ClientHandler(Socket socket, BlockingQueue<Integer> blockingQueue, ExecutorService executorService) {
			this.clientSocket = socket;
			this.blockingQueue = blockingQueue;
			this.executorService = executorService;
		}

		@Override
		public void run() {

			try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

				while (running.get()) {
					if (Thread.currentThread().isInterrupted()) {
						break;
					}

					String inputLine = "";
					try {
						inputLine = in.readLine();
					} catch (SocketException e) {
						System.err.println(e);
						break;
					}

					if (inputLine == null) {
						break;
					}

					if (inputLine.equalsIgnoreCase("terminate")) {
						shutDown();
						break;
					}

					blockingQueue.add(parseInt(inputLine));
				}
			} catch (IOException | NumberFormatException e) {
				System.err.println(e);				
			} finally {
				closeSocket();
			}
		}

		private int parseInt(String inputLine) throws NumberFormatException {

			if (inputLine.length() != 9) {
				throw new NumberFormatException("Input has invalid length: '" + inputLine + "'");
			}

			int number = Integer.parseInt(inputLine);

			if (number < 0) {
				throw new NumberFormatException("Input contained a minus sign: '" + inputLine + "'");
			}

			return number;
		}

		private void closeSocket() {
			try {
				clientSocket.close();
			} catch (IOException e) {
				System.err.println(e);
				;
			}
		}

		private void shutDown() {
			executorService.shutdown();
			try {
				if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
					running.set(false);
					executorService.shutdownNow();
				}
			} catch (InterruptedException e) {
				executorService.shutdownNow();
			}
		}

	}
}
