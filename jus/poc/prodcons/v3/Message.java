package jus.poc.prodcons.v3;

import java.util.Random;

/**
 * Message Class
 */
public class Message {
	int m_prod;
	int m_message;
	int m_exempl; // copies

	public Message(int msgId, int prodId) {
		m_message = msgId;
		m_prod = prodId;
		
		/* At most 3 copies of the same message*/
		Random r = new Random();
		m_exempl = r.nextInt(3) + 1;
	}

	public String toString() {
		return "m" + Integer.toString(m_message) + "-p" + Integer.toString(m_prod);
	}
	
	public void decr () {
		m_exempl--;
	}
}
