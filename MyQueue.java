package com.company;

public interface MyQueue<T> extends Iterable<T>{

	void start(int numberOfCustomers);
	void systemTime();
	/**kuruktaki toplam eleman sayisi*/
	int size();
	boolean isEmpty();
	/**kuyrugun sonuna item ekler*/
	boolean enqueue(Customer item);
	
	/** kuyrugun basindan eleman cikarir*/
	T dequeuNext();
	/** tekerleme metnini kullanarak bir sonraki elemani secer*/
	T dequeuWithCounting(String tekerleme);

}
