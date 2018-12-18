package jus.poc.prodcons.v4;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer {

	Message buff[];
	Semaphore sCons; // lock for consumers 
	Semaphore sProd; // lock for producers
	Semaphore mutex = new Semaphore(1); // lock to synchronize buffer
	Semaphore multi_cons = new Semaphore(1); // lock to assure that a consumer gets all his messages

	public ProdConsBuffer(int size) {
		buff = new Message[size];
		sCons = new Semaphore(0);
		sProd = new Semaphore(size);
	}

	@Override
	 public void put(Message m) throws InterruptedException {
		try {
			sProd.acquire(); //Take a producer lock
			mutex.acquire(); //Take a lock to assure the buffer synchronization
		} catch (InterruptedException e) {
			System.out.println("erreur");
		}
		;
		for (int i = 0; i < buff.length; i++) {
			if (buff[i] == null) {
				buff[i] = m;
				break;
			}
		}
		mutex.release(); // release the lock for synchronization
		sCons.release(); // release a consumer lock
	}

	@Override
	 public Message get(int nbmsg) throws InterruptedException {
		try {
			sCons.acquire(); // take a consumer lock
			mutex.acquire(); //Take a lock to assure the buffer synchronization
		} catch (InterruptedException e) {
			System.out.println("erreur sCons acquire");
		}
		Message m = buff[0];
		for (int i = 1; i < buff.length; i++) {
			buff[i - 1] = buff[i];
			if (buff[i] == null)
				break;
		}
		buff[buff.length - 1] = null;
		mutex.release(); // release the lock for synchronization
		sProd.release(); // release a producer lock

		return m;
	}

	@Override
	synchronized public int nmsg() {
		int n = 0;
		for (int i = 0; i < buff.length; i++) {
			if (buff[i] != null)
				n++;
			else
				break;
		}
		return n;
	}
}
