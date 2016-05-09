package com.iboxpay.test;

public class TestWatchFile {

	public static void main(String[] args) {
		WatchFile watchFile = new WatchFile();
		new Thread(watchFile).start();

		System.out.println("hello");
	}
}
