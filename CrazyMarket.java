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

	private class Node {
		Node next;
		Customer item;

		public Node(Customer item) {
			this.item = item;
			this.next = null;
		}

	}

	/**
	 * numberOfCustumer ile verilen sayida
	 * musteri hizmet gorene kadar calismaya devam eder
	 */

	public CrazyMarket(int numberOfCustomer) {//Gönderilen sayıda customer oluşturur
		Random r = new Random();

		for (int i = 1; i <= numberOfCustomer; i++) {
			Customer customer = new Customer();
			customer.arrivalTime = r.nextInt(21);
			customer.removalTime = r.nextInt(21) + 10;

			if (i > 1) {//Customerlar için kasaya ulaşma sürelerini bulur
				Customer previosCustomer = get(i - 1);
				customer.arrivalTime = previosCustomer.arrivalTime + customer.arrivalTime;
			}

			if (i == 1) {
				systemTime += customer.removalTime;
			}

			enqueue(customer);//oluşturulan müşteriyi sıraya ekler

			//Burada oluşturulan müşterilerin biribirleriyle bağlanma işlemleri yapılır
			if (head == null) {
				head = top;
				tail = top;
			}
			else {
				if (i == numberOfCustomer) {
					tail.next = null;
				}
				else {
					tail.next = top;
				}

				tail = top;
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
		MyQueue customers = new CrazyMarket(50);
		customers.start();
	}

	@Override
	public void showInfos() {
		int removalTime = calculateRemovalTime(head.item);
		System.out.println("Müsterinin bekleme süresi: " + (double) (systemTime - head.item.arrivalTime)/10.0 + " Müsterinin gelme zamani: " + (double) head.item.arrivalTime/10.0 + " Müsterini ayrildigi zaman: " + (double) systemTime/10.0);
	}

	public void start() {
		Node current = head;

		while (current != null) {
			//Kasadaki müşterilerin toplam bekleme sürelerini hesaplar ve süreye göre yapılması gereken çıkarma işlemini seçer
			Customer customer = current.item;
			int removalTime = calculateRemovalTime(customer);

			if (removalTime > 10) {
				dequeuNext();
			} else {
				Customer customerToRemove = dequeuWithCounting(tekerleme);
				int customerIndexToRemove = indexOf(customerToRemove);

				if (customerIndexToRemove > -1) {
					deleteNode(customerIndexToRemove);
				}
			}

			current = current.next;
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

		}

		return removalTime;
	}

	@Override
	public int size() {//Sıradaki müşteri sayısını söyler
		return size;
	}

	@Override
	public boolean isEmpty() {//Sıradaki müşteri olup olmadığını belirtir
		if (size == 0) {
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

		systemTime += head.item.removalTime;

		showInfos();
		head = head.next;

		size--;
	}

	@Override
	public Customer dequeuWithCounting(String str) {//Tekerlemedeki sesli harf sayısına göre müşteriyi bulur
		int vowels = countVowels(str);
		int randomCustomerIndexToRemove = (vowels - size * (vowels / size));

		return get(randomCustomerIndexToRemove);
	}

	public void deleteNode(int position) {//Tekerlemedeki sesli harf sayısına göre bulunan müşteriyi çıkarır
		if (head == null)
			return;

		showInfos();
		Node temp = head;

		if (position == 1)
		{
			head = temp.next;
			size--;
			return;
		}

		for (int i=1; temp!=null && i < position; i++) {
			temp = temp.next;
		}

		if (temp == null || temp.next == null) {
			return;
		}

		systemTime += temp.item.removalTime;

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
		int i = 1;

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