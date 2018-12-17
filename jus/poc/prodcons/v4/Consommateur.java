package jus.poc.prodcons.v4;

import java.util.Random;

/**
 * Consumer Class
 */
public class Consommateur implements Runnable {

	ProdConsBuffer m_buff;
	int m_id;
	Thread t;
	int m_consTime;
	int m_nbconso;

	public Consommateur(ProdConsBuffer buff, int id, int consTime, int nbconso) {
		/* Time Calculation - Same idea as a Producer */
		Random r = new Random();
		int maxTime = (int) (2 * consTime);
		m_consTime = r.nextInt(maxTime);
		
		m_buff = buff;
		m_id = id;
		m_nbconso = nbconso;
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		try {
			m_buff.multi_cons.acquire();
			for (int i = 0; i < m_nbconso; i++) {
				Message m = m_buff.get(m_nbconso);
				System.out.println("got msg: " + m + " by cons " + m_id);
				System.out.flush();
				Thread.sleep(m_consTime * 1000);
			}
			m_buff.multi_cons.release();
		} catch (InterruptedException e) {
		}
	}

	public void join() throws InterruptedException {
		t.join();
	}
}
