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

	private class Node {//Kuyruk için değer atamaları yapacak class
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

		for (int i = 1; i <= numberOfCustomer; i++) {//Başta oluşan değerleri diziye atıyorum ve zamanı gelince diziden çıkarıp kuyruğu atanıyor
			Customer customer = new Customer();
			customer.customerID = i;
			customer.arrivalTime = r.nextInt(21);
			customer.removalTime = r.nextInt(21) + 10;

			allCustomers[i][0] = customer.customerID;
			allCustomers[i][1] = allCustomers[i-1][1] + customer.arrivalTime;

			if (i == 1) {
				customer.removalTime = customer.arrivalTime + customer.removalTime;
			}

			allCustomers[i][2] = customer.removalTime;

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
	public Customer chooseCustomer() {//Kullanmadım ama aynı işi yapan void deleteNode(int position) var
		return null;

	}

	public static void main(String[] args) {
		Random r = new Random();
		int numberOfCustomer = r.nextInt(75) + 25;//Günde 25 ile 99 arası Müşteri bakabilsin diye böyle bir ayarı var
		MyQueue customers = new CrazyMarket(numberOfCustomer);
		customers.start(numberOfCustomer);
	}

	public void showInfos(Customer item) {//Müşteri Kuyruktan çıktığı anda değerlerini gösterir
		System.out.println("Müsterinin ID kodu: " + item.customerID + " Müsterinin kasada bekleme süresi: " + (double) ((systemTime + item.removalTime) - item.arrivalTime)/10.0 + " Müsterinin gelme zamani: " + (double) item.arrivalTime/10.0 + " Müsterini ayrildigi zaman: " + (double) (systemTime + item.removalTime)/10.0);
	}

	public void start(int numberOfCustomer) {
		Node current = head;

		while (!isEmpty()) {

			for(int j = 1; j <= numberOfCustomer; j++) {
				if (allCustomers[j][1] == systemTime) {//Dizideki zamanı gelen müşteriyi kuyruğa atar
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
						tail.next = top;
						tail = top;
					}
				}
				else if(systemTime < allCustomers[j][1]) {
					break;
				}
			}
			current = head;

			if ((deletingTime == systemTime || deletingTime == 0) && size > 0) {
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
			systemTime += 1;

			if ((deletingTime < systemTime) && deletingTime != 0) {
				deletingTime = systemTime;
			}
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
		int removalTime = 0;

		while (current != null) {
			removalTime += current.item.removalTime;
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
		if (size == 0 && systemTime > 200) {//normalde sadece size kontrol edip dönmeli ama benim kodum için ekstra olarak systemTime için kontrolde yapıyor çünkü systemTime daha başındakyan kuyrukta kimse yoktur o yüzden biraz bekliyor
			return true;
		}

		return false;
	}

	@Override
	public boolean enqueue(Customer item) {//Sıraya müşteri ekler
		Node newTop = new Node(item);
		newTop.next = null;
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

		if (size == 1){
			head = null;
		}
		else {
			head = head.next;
		}

		size--;
	}

	@Override
	public Customer dequeuWithCounting(String str) {//Tekerlemedeki sesli harf sayısına göre müşteriyi bulur
		int vowels = countVowels(str);
		int randomCustomerIndexToRemove = (vowels - size * (vowels / size));

		return get(randomCustomerIndexToRemove+1);
	}

	public void deleteNode(int position) {//Tekerlemedeki sesli harf sayısına göre bulunan müşteriyi çıkarır
		if (head == null) {
			return;
		}

		Node temp = head;

		if (position == 1) {
			showInfos(head.item);
			deletingTime += head.item.removalTime;
			head = temp.next;
			size--;
			return;
		}

		for (int i = 2; temp!=null && i < position; i++) {
			temp = temp.next;
		}

		if (temp.next == null || temp.next.next == null) {
			return;
		}

		deletingTime += temp.next.item.removalTime;

		showInfos(temp.next.item);

		Node next = temp.next.next;

		temp.next = next;

		size--;
	}

	@Override
	public Iterator<Customer> iterator() {
		return new StackIterator();
	}

	@Override
	public Customer get(int index) {//Kuyruktan çıkan müşteriyi bulur
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
	public int indexOf(Customer customer) {//Kuyruktan çıkan müşteriyi bulur
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

	private class StackIterator implements Iterator<Customer>{//burada kuyruktaki müşteriden sonra başka bir müşteri var mı onu kontrol eder
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