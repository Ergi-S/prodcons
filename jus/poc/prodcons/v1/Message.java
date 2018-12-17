package jus.poc.prodcons.v1;

/**
 * Message Class
 */
public class Message {
	int m_prod;
	int m_message;

	public Message(int msgId, int prodId) {
		m_message = msgId;
		m_prod = prodId;
	}

	public String toString() {
		return "m" + Integer.toString(m_message) + "-p" + Integer.toString(m_prod);
	}
}
