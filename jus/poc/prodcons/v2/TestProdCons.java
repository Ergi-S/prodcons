package jus.poc.prodcons.v2;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Random;

public class TestProdCons {
	public static void main(String args[]) throws InvalidPropertiesFormatException, IOException, InterruptedException {
		Properties properties = new Properties();
		properties.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream("o.xml"));

		int nbP = Integer.parseInt(properties.getProperty("nbP"));
		int nbC = Integer.parseInt(properties.getProperty("nbC"));
		int bufSz = Integer.parseInt(properties.getProperty("BufSz"));
		int prodTime = Integer.parseInt(properties.getProperty("ProdTime"));
		int consTime = Integer.parseInt(properties.getProperty("ConsTime"));
		int mavg = Integer.parseInt(properties.getProperty("Mavg"));

		ProdConsBuffer buff = new ProdConsBuffer(bufSz);

		Consommateur cons[] = new Consommateur[nbC];
		Producteur prod[] = new Producteur[nbP];

		// Commutation
		Random r = new Random();
		int i, c, p;
		i = c = p = 0;
		while (i < nbC + nbP) {
			double t = r.nextDouble();
			if (t <= 0.5 && c < nbC) {
				cons[c] = new Consommateur(buff, c, consTime);
				c++;
				i++;
			}
			if (t > 0.5 && p < nbP) {
				prod[p] = new Producteur(buff, p, mavg, prodTime);
				p++;
				i++;
			}
		}
		// Termination
		int nbMsgTot = 0;
		for (i = 0; i < nbP; i++) {
			nbMsgTot += prod[i].m_nmsg;
		}
		// less messages than consumers => program ends when the producers have finished
		if (nbMsgTot < nbC) {
			for (i = 0; i < nbP; i++) {
				prod[i].join(10000);
			}
			System.exit(0);
		}
		// more messages than consumers => program ends when the consumers have finished
		if (nbMsgTot > nbC) {
			for (i = 0; i < nbC; i++) {
				cons[i].join();
			}
			System.exit(0);
		}
	}
}
