package com.company;
import java.util.Iterator;
import java.util.Random;

public class CrazyMarket<T> implements MyQueue<T>{
	/**
	 *  numberOfCustomer ile verilen sayida
	 * musteri hizmet gorene kadar calismaya devam eder*/

	float systemTime;
	float[] allCutomersArriveTime;
	float[] allCutomersRemovalTime;

	public CrazyMarket(int numberOfCustomer) {
		allCutomersArriveTime = new float[numberOfCustomer+1];
		allCutomersArriveTime[0] = (float) 0.0;

		allCutomersRemovalTime = new float[numberOfCustomer+1];
		allCutomersRemovalTime[0] = (float) 0.0;

		for (int i = 1; i <= numberOfCustomer; i++) {
			Customer customer = new Customer();
			Random r = new Random();
			customer.arrivalTime = (r.nextInt(21)/10.0);
			allCutomersArriveTime[i] = allCutomersArriveTime[i-1] + (float) customer.arrivalTime;

			customer.removalTime = ((r.nextInt(21)+10)/10.0);
			allCutomersRemovalTime[i] = allCutomersRemovalTime[i-1] + (float) customer.removalTime;
		}

	}


	/** kuyrugun basindaki musteriyi ya da tekerleme
	 * ile buldugu musteriyi return eder*/
	public Customer chooseCustomer() {
		return null;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        MyQueue customers = new CrazyMarket(300);
        customers.yazdir(300);

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

	@Override
	public void yazdir(int numberOfCustomer) {//TEST AMACLI
		for (int i = 1; i <= numberOfCustomer; i++) {
			System.out.println(allCutomersRemovalTime[i] - allCutomersArriveTime[i]);
		}
	}

}
