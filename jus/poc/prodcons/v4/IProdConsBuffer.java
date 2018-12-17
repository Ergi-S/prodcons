package jus.poc.prodcons.v4;

public interface IProdConsBuffer {
	/**
	* put m in the prodcons buffer
	**/
	public void put(Message m) throws InterruptedException;
	/**
	* retrieve a message from the prodcons buffer, following a fifo order
	**/
	public Message get(int nbmsg) throws InterruptedException;
	/**
	* returns the number of messages currently available in the prodcons buffer
	**/
	public int nmsg();
}
