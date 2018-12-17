package jus.poc.prodcons.v4;

import java.util.Random;

/**
 * Producer Class
 */
public class Producteur extends Thread {
	ProdConsBuffer m_buff;
	int m_id;
	int m_prodTime;
	int m_nmsg;

	/* According to the javadoc, nextInt returns an uniformly distributed random between 
	 * 0 (included) and argument (excluded). The mean (average) of an uniform distribution 
	 * is avg=(min+max)/2 . Knowing the average (from the xml) and the min (at least 1 msg 
	 * per prod) we can find the max=2*avg - min. Finally we add 1 to pass from [0;max[ to 
	 * [1;max+1[ = [1;max]
	 */
	public Producteur(ProdConsBuffer buff, int id, int mavg, int prodTime) {
		Random r = new Random();
		
		int maxMsg = (int) ((2 * mavg) - 1); 
		m_nmsg = r.nextInt(maxMsg) + 1; 
		
		int maxTime = (int) (2 * prodTime);
		m_prodTime = r.nextInt(maxTime)+1;
		
		m_buff = buff;
		m_id = id;
		start();
	}

	@Override
	public void run() {
		for (int i = 0; i <= m_nmsg; i++) {
			Message m = new Message(i, m_id);
			try {	
				m_buff.put(m);
				System.out.println("Putted msg: " + i + " by prod " + m_id);
				System.out.flush();
				Thread.sleep(m_prodTime*1000); // milliseconds to seconds
			} catch (InterruptedException e) {
			}
		}
	}
}
