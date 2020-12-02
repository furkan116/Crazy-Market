package com.company;

import java.util.Iterator;
import java.util.Random;

public class CrazyMarket implements MyQueue<Customer> {
	/**
	 * default tekerleme
	 */
	String tekerleme = "O piti piti karamela sepeti "
			+ "\nTerazi lastik jimnastik "
			+ "\nBiz size geldik bitlendik Hamama gittik temizlendik.";

	int systemTime = 0;
	private Node top;
	private Node head;
	private Node tail;
	private int size;
	int[][] allCustomers;
	int deletingTime = 0;

	private class Node {
		Node next;
		Customer item;

		public Node(Customer item) {
			this.item = item;
			this.next = null;
		}
	}

	/**
	 * numberOfCostumer ile verilen sayida
	 * musteri hizmet gorene kadar calismaya devam eder
	 */

	public CrazyMarket(int numberOfCustomer) {//Gönderilen sayıda customer oluşturur
		Random r = new Random();
		allCustomers = new int[numberOfCustomer + 1][3];
		allCustomers[0][0] = 0;
		allCustomers[0][1] = 0;
		allCustomers[0][2] = 0;

		for (int i = 1; i <= numberOfCustomer; i++) {
			Customer customer = new Customer();
			customer.customerID = i;
			customer.arrivalTime = r.nextInt(21);
			customer.removalTime = r.nextInt(21) + 10;

			//if (i > 1) {//Customerlar için kasaya ulaşma sürelerini bulur
			//	Customer previosCustomer = get(i - 1);
			//	customer.arrivalTime = previosCustomer.arrivalTime + customer.arrivalTime;
			//}

			allCustomers[i][0] = customer.customerID;
			allCustomers[i][1] = allCustomers[i-1][1] + customer.arrivalTime;
			allCustomers[i][2] = customer.removalTime;

			if (i == 1) {
				customer.removalTime = customer.arrivalTime + customer.removalTime;
			}

		}
	}

	/**
	 * numberOfCustumer ile verilen sayida
	 * musteri hizmet gorene kadar calismaya devam eder,
	 * calisirken verilen tekerlemeyi kullanir
	 */
	public CrazyMarket(int numberOfCustomer, String tekerleme) {

	}

	/**
	 * kuyrugun basindaki musteriyi yada tekerleme
	 * ile buldugu musteriyi return eder
	 */
	public Customer chooseCustomer() {
		return null;

	}

	public static void main(String[] args) {
		int numberOfCustomer = 50;
		MyQueue customers = new CrazyMarket(numberOfCustomer);
		customers.start(numberOfCustomer);
	}

	public void showInfos(Customer item) {
		System.out.println("Müsterinin ID kodu: " + item.customerID + " Müsterinin bekleme süresi: " + (double) (systemTime - item.arrivalTime)/10.0 + " Müsterinin gelme zamani: " + (double) item.arrivalTime/10.0 + " Müsterini ayrildigi zaman: " + (double) systemTime/10.0);
	}

	public void start(int numberOfCustomer) {
		Node current = head;

		while (!isEmpty()) {

			for(int j = 1; j <= numberOfCustomer; j++) {
				if (allCustomers[j][1] == systemTime) {
					Customer customer = new Customer();
					customer.customerID = allCustomers[j][0];
					customer.arrivalTime = allCustomers[j][1];
					customer.removalTime = allCustomers[j][2];
					enqueue(customer);//oluşturulan müşteriyi sıraya ekler

					//Burada oluşturulan müşterilerin biribirleriyle bağlanma işlemleri yapılır
					if (head == null) {
						head = top;
						tail = top;
					}
					else {
						if (j == numberOfCustomer) {
							tail.next = null;
						}
						else {
							tail.next = top;
						}

						tail = top;
					}
				}
				else if(systemTime < allCustomers[j][1]) {
					break;
				}
			}
			current = head;

			for(int j = 1; j <= size; j++) {
				if (deletingTime == systemTime || deletingTime == 0) {
					//Kasadaki müşterilerin toplam bekleme sürelerini hesaplar ve süreye göre yapılması gereken çıkarma işlemini seçer
					int removalTime = calculateRemovalTime(current.item);

					if (removalTime > 100 || size == 1) {
						dequeuNext();
					}
					else {
						Customer customerToRemove = dequeuWithCounting(tekerleme);
						int customerIndexToRemove = indexOf(customerToRemove);

						if (customerIndexToRemove > -1) {
							deleteNode(customerIndexToRemove);
						}
					}
				}
			}

			systemTime += 1;
		}
	}

	public int countVowels(String str) {//Tekerlemedeki sesli harfleri sayar
		int count = 0;

		for(int i = 0; i < str.length(); i++){
			char ch = str.charAt(i);

			if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'){
				count ++;
			}
		}

		return count;
	}

	public int calculateRemovalTime(Customer customer) {//Müşterilerin ayrılma zamanlarını birbirine ekleyerek toplam ayrılma zamanını bulur
		Node current = head;
		int removalTime = customer.removalTime;

		while (current != null) {
			removalTime += current.item.removalTime;

			if (current.item.equals(customer)) {
				break;
			}

			current = current.next;

		}

		return removalTime;
	}

	@Override
	public int size() {//Sıradaki müşteri sayısını söyler
		return size;
	}

	@Override
	public boolean isEmpty() {//Sıradaki müşteri olup olmadığını belirtir
		if (size == 0 && systemTime > 100) {
			return true;
		}

		return false;
	}

	@Override
	public boolean enqueue(Customer item) {//Sıraya müşteri ekler
		Node newTop = new Node(item);
		newTop.next = top;
		top = newTop;
		size++;
		return true;
	}

	@Override
	public void dequeuNext() {//Sıranın başındaki müşteriyi çıkarır
		if (head == null) {
			return;
		}

		deletingTime += head.item.removalTime;
		showInfos(head.item);

		head = head.next;

		size--;
	}

	@Override
	public Customer dequeuWithCounting(String str) {//Tekerlemedeki sesli harf sayısına göre müşteriyi bulur
		int vowels = countVowels(str);
		int randomCustomerIndexToRemove = (vowels - size * (vowels / size));

		return get(randomCustomerIndexToRemove+1);
	}

	public void sizeTime() {

	}

	public void deleteNode(int position) {//Tekerlemedeki sesli harf sayısına göre bulunan müşteriyi çıkarır
		if (head == null) {
			return;
		}

		Node temp = head;

		if (position == 0)
		{
			showInfos(head.item);
			head = temp.next;
			size--;
			deletingTime += head.item.removalTime;
			return;
		}

		for (int i=0; temp!=null && i < position; i++) {
			temp = temp.next;
		}

		if (temp == null || temp.next == null) {
			return;
		}

		deletingTime += temp.item.removalTime;

		showInfos(temp.item);

		Node next = temp.next.next;

		temp.next = next;

		size--;
	}

	@Override
	public Iterator<Customer> iterator() {
		return new StackIterator();
	}

	@Override
	public Customer get(int index) {
		Node current = head;
		int i = 1;//BURAYA DİKKAT


		while (current != null) {
			if (i == index) {
				return current.item;
			}
			i++;
			current = current.next;
		}

		return null;
	}

	@Override
	public int indexOf(Customer customer) {
		Node current = head;
		int i = 1;
		Boolean found = false;

		while (current != null) {
			if (current.item.equals(customer)) {
				found = true;
				break;
			}

			i++;

			current = current.next;
		}

		if (found) {
			return i;
		} else {
			return -1;
		}
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