package jus.poc.prodcons.v1;

public class Main {
	public static void main (String args[]) {
		ProdConsBuffer buff = new ProdConsBuffer(5);
		
		Consommateur c1 = new Consommateur(buff, 1);
		Consommateur c2 = new Consommateur(buff, 2);
		Consommateur c3 = new Consommateur(buff, 3);
		Producteur p1 = new Producteur(buff, 1);
	}
}
