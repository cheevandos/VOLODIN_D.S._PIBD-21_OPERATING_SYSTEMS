package com.taskplanner.kernel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public class Kernel {
	
	private int quant;
	private int progress;

	private ArrayList<ProcessModel> processes;
	private ArrayDeque<ThreadModel> threads;
	
	public Kernel() {
		processes = new ArrayList<ProcessModel>();
		threads = new ArrayDeque<ThreadModel>();
		progress = 0;
		quant = 15;
		Random rnd = new Random();
		int processesNumber = rnd.nextInt(10) + 1;
		for (int i = 0; i < processesNumber; i++) {
			ProcessModel process = new ProcessModel(i);
			processes.add(process);
			threads.addAll(process.getThreads());
		}
	}
	
	public void start() {
		while (!check()) {
			ThreadModel thread = threads.pollFirst();
			thread.perform(quant);
			if (!thread.isDone()) {
				threads.addLast(thread);
			}
			check();
		}
	}
	
	public boolean check() {
		progress = 0;
		for (ProcessModel process : processes) {
			process.checkProgress();
			progress += process.getProgress();
		}
		progress /= processes.size();
		return progress >= 100;
	}
}
