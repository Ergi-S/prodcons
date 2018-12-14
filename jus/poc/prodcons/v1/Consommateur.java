package jus.poc.prodcons.v1;

/**
 * Classe Consommateur
 */
public class Consommateur implements Runnable {

	ProdConsBuffer m_buff;
	int m_id;
	Thread t;

	public Consommateur(ProdConsBuffer buff, int id) {
		m_buff = buff;
		m_id = id;
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		try {
			Message m = m_buff.get();
			System.out.println("got msg: " + m + " par cons " + m_id);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}
}
