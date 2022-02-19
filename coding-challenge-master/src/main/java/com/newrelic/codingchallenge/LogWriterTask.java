package com.newrelic.codingchallenge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

public final class LogWriterTask implements Runnable {

	private static final int SUMMARY_WAIT_PERIOD = 10_000;
	private final BlockingQueue<Integer> blockingQueue;
	private final BitSet bitSet = new BitSet(1_000_000_000);
	private final Object lock = new Object();
	private int uniqueCount = 0;
	private int duplicateCount = 0;
	private int uniqueTotal = 0;

	public LogWriterTask(BlockingQueue<Integer> blockingQueue) {
		this.blockingQueue = blockingQueue;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new SummaryTask(), 0, SUMMARY_WAIT_PERIOD);
	}

	@Override
	public void run() {

		try (FileWriter fileWriter = new FileWriter("numbers.log", true);
				BufferedWriter outputWriter = new BufferedWriter(fileWriter)) {
			while (true) {
				int number = blockingQueue.take();
				System.out.println();
				synchronized (lock) {
					if (bitSet.get(number)) {
						duplicateCount++;
						continue;
					}
					bitSet.set(number);
					uniqueCount++;
					uniqueTotal++;
					try {
						outputWriter.write(String.format("%09d", number));
						outputWriter.newLine();
						outputWriter.flush();
					} catch (IOException e) {
						System.err.println(e);						
					}
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		} catch (InterruptedException e) {
			System.err.println("LogWriterTask interrupted");
		}
	}

	final class SummaryTask extends TimerTask {
		@Override
		public void run() {
			synchronized (lock) {
				System.out.printf("Received %d unique numbers, %d duplicates. Unique total: %d\n", uniqueCount,
						duplicateCount, uniqueTotal);
				uniqueCount = 0;
				duplicateCount = 0;
			}
		}
	}
}
