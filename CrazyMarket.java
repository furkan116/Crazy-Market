package com.company;

import java.util.Iterator;
import java.util.Random;

public class CrazyMarket implements MyQueue<Customer>{
	/**default tekerleme
	 * */
	String tekerleme = "O piti piti karamela sepeti "
			+ "\nTerazi lastik jimnastik "
			+ "\nBiz size geldik bitlendik Hamama gittik temizlendik.";
			
	/**
	 *  numberOfCustumer ile verilen sayida  
	 * musteri hizmet gorene kadar calismaya devam eder*/

	int systemTime =  0;
	boolean isEmpty1 = false;
	int[] allCutomersArriveTime;
	int[] allCutomersRemovalTime;

	public CrazyMarket(int numberOfCustomer) {
		allCutomersArriveTime = new int[numberOfCustomer + 1];
		allCutomersArriveTime[0] = 0;

		allCutomersRemovalTime = new int[numberOfCustomer + 1];
		allCutomersRemovalTime[0] = 0;

		for (int i = 1; i <= numberOfCustomer; i++) {
			Customer customer = new Customer();
			Random r = new Random();
			customer.arrivalTime = r.nextInt(21);
			allCutomersArriveTime[i] = allCutomersArriveTime[i-1] + customer.arrivalTime;

			if (i == 1) {
				allCutomersRemovalTime[0] = customer.arrivalTime;
			}

			customer.removalTime = (r.nextInt(21)+10);
			allCutomersRemovalTime[i] = allCutomersRemovalTime[i-1] + customer.removalTime;
		}
	}

	/**
	 *  numberOfCustumer ile verilen sayida  
	 * musteri hizmet gorene kadar calismaya devam eder, 
	 * calisirken verilen tekerlemeyi kullanir*/
	public CrazyMarket(int numberOfCustomer, String tekerleme) {
		
	}

	/** kuyrugun basindaki musteriyi yada tekerleme 
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

	public void start(int numberOfCustomer) {
		while (isEmpty1 == false) {

			//Kasaya ulaşma süreleri sistem süresiyle aynı olanları queue ekler
			/*for (int i = 1; i <= numberOfCustomer; i++) {
				if (allCutomersArriveTime[i] == systemTime) {
					enqueue(i);
				}
				else if (allCutomersArriveTime[i] > systemTime) {
					break;
				}
			}*/

			//Kasada bekleme süreleri sistem süresiyle aynı olanları queueden çıkarır
			for (int i = 1; i <= size(); i++) {

				if (allCutomersRemovalTime[i] == systemTime) {
					System.out.print("Customer: " + i + " ");
					System.out.print("Bekleme Suresi: " + ((allCutomersRemovalTime[i] - allCutomersArriveTime[i])/10.0) + " ");
					System.out.print("Kasaya geldigi zaman: " + (double)(allCutomersArriveTime[i]/10.0) + " ");
					System.out.print("Kasaya ayrildigi zaman: " + (double)(allCutomersRemovalTime[i]/10.0) + "\n");

				}
				else if (allCutomersRemovalTime[i] > systemTime) {
					break;
				}
			}

			if (systemTime > 50) {
				isEmpty();
			}

			systemTime();
		}
	}

	@Override
	public void systemTime() {
		systemTime = systemTime + 1;
	}

	private Node top;
	private int size;
	private class Node{
		Node next;
		Customer item;
		public Node(Customer item) {
			this.item = item;
			this.next = null;
			size = 0;
		}
	}

	@Override
	public int size() {
		return 300;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean enqueue(Customer item) {

		Node newTop = new Node(item);
		newTop.next = top;
		top = newTop;
		size++;
		return true;
	}

	@Override
	public Customer dequeuNext() {
		return null;
	}

	@Override
	public Customer dequeuWithCounting(String tekerleme) {
		return null;
	}

	@Override
	public Iterator<Customer> iterator() {
		return new StackIterator();
	}

	private class StackIterator implements Iterator<Customer>{
		private Node itr = top;
		@Override
		public boolean hasNext() {
			return itr != null;
		}

		@Override
		public Customer next() {
			Customer item = itr.item;
			itr = itr.next;
			return item;
		}
	}
}
