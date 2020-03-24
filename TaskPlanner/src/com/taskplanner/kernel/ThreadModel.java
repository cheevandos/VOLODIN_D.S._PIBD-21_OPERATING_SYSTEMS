package com.taskplanner.kernel;

import java.util.Random;

public class ThreadModel {

	private int ID;
	private int progress;
	private int speed;
	
	public ThreadModel(int ID) {
		this.ID = ID;
		progress = 0;
		Random rnd = new Random();
		speed = rnd.nextInt(20) + 1;
	}
	
	public void perform(int quantSource) {
		System.out.println("Поток " + ID + " выполняется");
		int step = quantSource * speed;
		progress += step;
		if (progress >= 100) {
			progress = 100;
			System.out.println("Поток " + ID + " завершен");
		} else {
			System.out.println("Поток " + ID + " прерван");
		}
	}
	
	public boolean isDone() {
		return progress >= 100;
	}
	
	public int getProgress() {
		return progress;
	}
}