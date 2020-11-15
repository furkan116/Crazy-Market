package com.company;
import java.util.Iterator;
import java.util.Random;

public class CrazyMarket<T> implements MyQueue<T>{
	/**
	 *  numberOfCustomer ile verilen sayida
	 * musteri hizmet gorene kadar calismaya devam eder*/

	float systemTime = (float) 0.0;
	boolean isEmpty1 = false;
	float[] allCutomersArriveTime;
	float[] allCutomersRemovalTime;

	public CrazyMarket(int numberOfCustomer) {//Burada array kullandım çünkü sayılar arraya sırayla gidecek ve bu sıra onların IDleri gibi davranacak
		allCutomersArriveTime = new float[numberOfCustomer+1];
		allCutomersArriveTime[0] = (float) 0.0;

		allCutomersRemovalTime = new float[numberOfCustomer+1];
		allCutomersRemovalTime[0] = (float) 0.0;

		for (int i = 1; i <= numberOfCustomer; i++) {
			Customer customer = new Customer();
			Random r = new Random();
			customer.arrivalTime = (r.nextInt(21)/10.0);
			allCutomersArriveTime[i] = allCutomersArriveTime[i-1] + (float) customer.arrivalTime;

			if (i == 1) {
				allCutomersRemovalTime[0] = (float) customer.arrivalTime;
			}

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
		Random r = new Random();
		int numberOfCustomer = 10/*r.nextInt(1001)*/;
        MyQueue customers = new CrazyMarket(numberOfCustomer);
        customers.start(numberOfCustomer);

	}

	@Override
	public void start(int numberOfCustomer) {
		while (isEmpty1 == false) {

			//Kasaya ulaşma süreleri sistem süresiyle aynı olanları queue ekler
			for (int i = 1; i <= numberOfCustomer; i++) {
				if (allCutomersArriveTime[i] == systemTime) {
					enqueue(i);
				}
				else if (allCutomersArriveTime[i] > systemTime) {
					break;
				}
			}

			//Kasada bekleme süreleri sistem süresiyle aynı olanları queueden çıkarır
			for (int i = 1; i <= numberOfCustomer; i++) {
				if (allCutomersRemovalTime[i] == systemTime) {
					System.out.println("Customer" + i + " Bekleme Suresi: " + (allCutomersRemovalTime[i] - allCutomersArriveTime[i]));
					dequeuNext(i);
				}
				else if (allCutomersRemovalTime[i] > systemTime) {
					break;
				}
			}

			if (systemTime > 5.0) {
				isEmpty();
			}

			systemTime();
		}
	}

	@Override
	public void systemTime() {
		systemTime += 0.1;
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public boolean isEmpty() {

		if (size() == 0) {
			return isEmpty1 = true;
		}
		return isEmpty1 = false;
	}

	@Override
	public void enqueue(int ID) {
		queue.add(ID);
	}

	@Override
	public T dequeuNext(int ID) {
		queue.remove(ID);
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
