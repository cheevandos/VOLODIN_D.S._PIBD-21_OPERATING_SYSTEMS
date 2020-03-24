package com.taskplanner.kernel;

import java.util.ArrayDeque;
import java.util.Random;

public class ProcessModel {

	private ArrayDeque<ThreadModel> threads;
	
	private int ID;
	private int progress;
	
	public ProcessModel(int ID) {
		threads = new ArrayDeque<ThreadModel>();
		this.ID = ID;
		Random rnd = new Random();
		int threadsNumber = rnd.nextInt(5) + 1;
		for (int i = 0; i < threadsNumber; i++) {
			ThreadModel thread = new ThreadModel(i);
			threads.addLast(thread);
		}
	}
	
	public ArrayDeque<ThreadModel> getThreads() {
		return threads;
	}
	
	public int getID() {
		return ID;
	}
	
	public void checkProgress() {
		progress = 0;
		for (ThreadModel thread : threads) {
			progress += thread.getProgress();
		}
		progress /= threads.size();
		System.out.println("Процесс " + ID + " выполнен на " + progress + "%");
	}
	
	public boolean isDone() {
		return progress >= 100;
	}
}
