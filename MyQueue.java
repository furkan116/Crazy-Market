package com.company;

public interface MyQueue<T> extends Iterable<T>{

	void showInfos();
	void start();
	/**kuruktaki toplam eleman sayisi*/
	int size();
	boolean isEmpty();
	/**kuyrugun sonuna item ekler*/
	boolean enqueue(Customer item);

	T get(int index);

	int indexOf(T cust);
	
	/** kuyrugun basindan eleman cikarir*/
	void dequeuNext();

	/** tekerleme metnini kullanarak bir sonraki elemani secer*/
	T dequeuWithCounting(String tekerleme);

}
