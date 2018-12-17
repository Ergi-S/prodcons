package jus.poc.prodcons.v2;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer {

	Message buff[];
	Semaphore sCons;
	Semaphore sProd;
	Semaphore mutex = new Semaphore(1);

	public ProdConsBuffer(int size) {
		buff = new Message[size];
		sCons = new Semaphore(0);
		sProd = new Semaphore(size);
	}

	@Override
	 public void put(Message m) throws InterruptedException {
		try {
			sProd.acquire();
			mutex.acquire();
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
		mutex.release();
		sCons.release();
	}

	@Override
	 public Message get() throws InterruptedException {
		try {
			sCons.acquire();
			mutex.acquire();
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
		mutex.release();
		sProd.release();

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
