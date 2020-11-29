package com.company;

public interface MyQueue<T> extends Iterable<T>{

	void start();
	/**kuruktaki toplam eleman sayisi*/
	int size();
	boolean isEmpty();
	/**kuyrugun sonuna item ekler*/
	boolean enqueue(Customer item);

	T get(int index);

	int indexOf(T cust);
	
	/** kuyrugun basindan eleman cikarir*/
	T dequeuNext();

	/** tekerleme metnini kullanarak bir sonraki elemani secer*/
	T dequeuWithCounting(String tekerleme);

}
