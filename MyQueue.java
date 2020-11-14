package com.company;

public interface MyQueue<T> extends Iterable<T>{
	
	/**kuruktaki toplam eleman sayisi*/
	int size();
	boolean isEmpty();
	/**kuyrugun sonuna item ekler*/
	boolean enqueue(T item);

	void yazdir(int numberOfCustomer);//TEST AMACLI
	
	/** kuyrugun basindan eleman cikarir*/
	T dequeuNext();
	/** tekerleme metnini kullanarak bir sonraki elemani secer*/
	T dequeuWithCounting(String tekerleme);

}
