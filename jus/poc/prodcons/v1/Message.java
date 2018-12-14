package jus.poc.prodcons.v1;

/**
 * 
 * Classe Message
 *
 */
public class Message {
	int m_message;
	
	public Message (int m) {
		m_message = m;
	}
	public String toString() {
		return Integer.toString(m_message);
	}
}
