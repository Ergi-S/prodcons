package jus.poc.prodcons.v1;

public class ProdConsBuffer implements IProdConsBuffer {

	Message buff[];

	public ProdConsBuffer(int size) {
		buff = new Message[size];
	}

	@Override
	synchronized public void put(Message m) throws InterruptedException {
		/* If the buffer is full, the producer must wait */
		while (nmsg() >= buff.length)
			wait();
		
		/* Insertion in the last position */
		for (int i = 0; i < buff.length; i++) {
			if (buff[i] == null) {
				buff[i] = m;
				break;
			}
		}
		
		notifyAll();
	}

	@Override
	synchronized public Message get() throws InterruptedException {
		/* If the buffer is empty, the consumer must wait */
		while(nmsg() == 0)
			wait();
		
		/* FIFO order, insertion at the beginning of the buffer */
		Message m = buff[0];
		for (int i = 1; i < buff.length; i++) {
			buff[i-1] = buff[i];
			if(buff[i] == null)
				break;
		}
		buff[buff.length-1] = null;
		
		notifyAll();
		
		return m;
	}

	@Override
	synchronized public int nmsg() {
		int n = 0;
		for(int i = 0; i < buff.length; i++) {
			if (buff[i] != null)
				n++;
			else break;
		}
		return n;
	}
}
