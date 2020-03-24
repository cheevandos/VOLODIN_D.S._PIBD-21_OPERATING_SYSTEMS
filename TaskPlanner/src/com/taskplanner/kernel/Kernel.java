package com.taskplanner.kernel;

import java.util.ArrayDeque;
import java.util.Random;

public class Kernel {
	
	private int quant;

	private ArrayDeque<ProcessModel> processes;
	
	public Kernel() {
		processes = new ArrayDeque<ProcessModel>();
		quant = 15;
		Random rnd = new Random();
		int processesNumber = rnd.nextInt(10) + 1;
		for (int i = 0; i < processesNumber; i++) {
			ProcessModel process = new ProcessModel(i);
			processes.addLast(process);
		}
	}
	
	public void start() {
		while (!processes.isEmpty()) {
			ProcessModel process = processes.pollFirst();
			perform(process);
			process.checkProgress();
			if (!process.isDone()) {
				processes.addLast(process);
				System.out.println("Процесс " + process.getID() + " прерван");
			} else {
				System.out.println("Процесс " + process.getID() + " завершен");
			}
		}
	}
	
	private void perform(ProcessModel process) {
		System.out.println("Процесс " + process.getID() + " выполняется");
		ArrayDeque<ThreadModel> threads = process.getThreads();
		ThreadModel thread = threads.pollFirst();
		thread.perform(quant);
		threads.addLast(thread);
	}
}
