package jus.poc.prodcons.v1;

/**
 * Classe Producteur
 *
 */

  /* test pour merge */
public class Producteur extends Thread {
	ProdConsBuffer m_buff;
	int m_id;

	public Producteur(ProdConsBuffer buff, int id) {
		m_buff = buff;
		m_id = id;
		start();
	}

	@Override
	public void run() {
		for (int i = 0; i < 2; i++) {
			Message m = new Message(i);
			try {
				System.out.println("Putting msg: " + i + " par prod " + m_id);
				m_buff.put(m);
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
}
