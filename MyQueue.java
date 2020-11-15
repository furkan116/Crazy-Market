package com.company;

import java.util.LinkedList;

public interface MyQueue<T> extends Iterable<T>{

	LinkedList<Integer> queue = new LinkedList<Integer>();

	void start(int numberOfCustomers);
	void systemTime();

	/**kuruktaki toplam eleman sayisi*/
	int size();
	boolean isEmpty();
	/**kuyrugun sonuna item ekler*/
	void enqueue(int ID);//Değişiklikler yaptım
	
	/** kuyrugun basindan eleman cikarir*/
	T dequeuNext(int ID);
	/** tekerleme metnini kullanarak bir sonraki elemani secer*/
	T dequeuWithCounting(String tekerleme);

}
