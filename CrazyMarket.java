package com.company;
import java.util.Iterator;

public class CrazyMarket<T> implements MyQueue<T>{
	/**
	 *  numberOfCustumer ile verilen sayida  
	 * musteri hizmet gorene kadar calismaya devam eder*/
	public CrazyMarket(int numberOfCustomer) {
		
	}


	/** kuyrugun basindaki musteriyi yada tekerleme 
	 * ile buldugu musteriyi return eder*/
	public Customer chooseCustomer() {
		return null;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean enqueue(T item) {
		return false;
	}

	@Override
	public T dequeuNext() {
		return null;
	}

	@Override
	public T dequeuWithCounting(String tekerleme) {
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		return null;
	}
}
