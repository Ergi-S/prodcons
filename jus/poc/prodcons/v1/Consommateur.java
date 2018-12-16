package jus.poc.prodcons.v1;

import java.util.Random;

/**
 * Classe Consommateur
 */
public class Consommateur implements Runnable {

	ProdConsBuffer m_buff;
	int m_id;
	Thread t;
	int m_consTime;

	public Consommateur(ProdConsBuffer buff, int id, int consTime) {
		Random r = new Random();
		int maxTime = (int) (2 * consTime);
		m_consTime = r.nextInt(maxTime);
		m_buff = buff;
		m_id = id;
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		try {
			Message m = m_buff.get();
			System.out.println("got msg: " + m + " by cons " + m_id);
			System.out.flush();
			Thread.sleep(m_consTime * 1000);
		} catch (InterruptedException e) {
		}
	}
	
	public void join()throws InterruptedException {
		t.join();     
	}
}
