package com.filesystem;
import java.util.Scanner;

public class Program {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Main main = new Main();
		while (true) {
			System.out.println("Create new file: create [filename] [file data]");
			System.out.println("Delete existing file: delete [filename]");
			System.out.println("Find file: get [filename]");
			String input = scanner.nextLine();
			if (input.equals("stop")) {
				break;
			}
			String[] splitted = input.split(" ");
			if (splitted[0].equals("create") && splitted.length == 3) {
				File newFile = new File(splitted[1], splitted[2].toCharArray());
				main.writeFile(newFile);
				continue;
			}
			if (splitted[0].equals("delete") && splitted.length == 2) {
				main.deleteFile(splitted[1]);
				continue;
			}
			if (splitted[0].equals("get") && splitted.length == 2) {
				File file = main.getFile(splitted[1]);
				System.out.println(String.valueOf(file.getData()));
				continue;
			}
			System.out.println("Wrong format!");
		}
		scanner.close();
	}
}
