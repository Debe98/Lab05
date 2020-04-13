package it.polito.tdp.anagrammi.model;

import java.awt.FontFormatException;
import java.util.*;

import it.polito.tdp.anagrammi.DAO.AnagrammiDAO;

public class Model {
	AnagrammiDAO dao = new AnagrammiDAO();
	Set <String> anagrammiCorretti = new HashSet<>();
	Set <String> anagrammiErrati = new HashSet<>();
	Set[] anagrammi = {anagrammiCorretti, anagrammiErrati};
	Set <String> vocabParziale = new LinkedHashSet<>();
	
	public Set<String>[] getAnagrammi (String originale) throws Exception {
		//Reset
		anagrammiCorretti.clear();
		anagrammiErrati.clear();
		vocabParziale.clear();
		
		//Controllo errore
		originale = originale.trim().toUpperCase();
		
		if (originale.equals(""))
			throw new RuntimeException("Inserisci una parola!");
		
		if (verSeInvalida(originale))
			throw new RuntimeException("Quella fornita non è una parola valida!");
		
		int lunghezza = originale.length();
		
		vocabParziale = dao.getParoleDataLunghezza(lunghezza);
		
		Map <Character, Integer> lettere = disgregaParola(originale);
		
		if (getNumeroAnagrammi(originale)[1] > 30000) {
			creaAnagrammiCorretti(lettere);
			return new Set[] {anagrammiCorretti, null};
		}
		
		creaAnagrammi("", lettere);
		
		return anagrammi;
	}
	
	private Map <Character, Integer> disgregaParola (String parola) {
		Map <Character, Integer> lettere = new TreeMap<>();
		for (int i = 0; i <parola.length(); i++) {
			char lett = parola.charAt(i);
			if (lettere.get(lett)==null) {
				lettere.put(lett, 1);
			}
			else lettere.replace(lett, lettere.get(lett)+1);
		}
		return lettere;
	}

	private void creaAnagrammi(String intermedio, Map<Character, Integer> rimanenti) {
		if (rimanenti.size() == 0) {
			if (vocabParziale.contains(intermedio))
				anagrammiCorretti.add(intermedio);
			else anagrammiErrati.add(intermedio);
			return;
		}
		
		else {
			for (Character c : rimanenti.keySet()) {
				creaAnagrammi(intermedio+c, aggiornaCaratteri(c, rimanenti));
			}
		}
	}

	private void creaAnagrammiCorretti(Map <Character, Integer> lettereOriginale) {
		Map <Character, Integer> lettereDaConfrontare;
		inizio:
		for (String daConfrontare : vocabParziale) {
			lettereDaConfrontare = disgregaParola(daConfrontare);
			
			for (char c : lettereOriginale.keySet()) {
				if (lettereOriginale.get(c) != lettereDaConfrontare.get(c))
					continue inizio;
			}
			anagrammiCorretti.add(daConfrontare);
		}
	}
	
	private Map<Character, Integer> aggiornaCaratteri (char c, Map<Character, Integer> disponibili) {
		Map<Character, Integer> aggiornato = new TreeMap<>(disponibili);
		if (disponibili.get(c)==1)
			aggiornato.remove(c);
		else
			aggiornato.replace(c, disponibili.get(c)-1);
		return aggiornato;
	}
	
	private boolean verSeInvalida(String originale) {
		
		for (int i = 0; i< originale.length(); i++) {
			if (!Character.isAlphabetic(originale.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	public long [] getNumeroAnagrammi (String parola) {
		parola = parola.trim().toUpperCase();
		Map <Character, Integer> letterediverse = disgregaParola(parola);
		long max = getFactorial(parola.length());
		long nonRipetuti = max;
		
		for (char c : letterediverse.keySet()) {
			nonRipetuti /= getFactorial(letterediverse.get(c));
		}
		return new long[] {max, nonRipetuti, max- nonRipetuti};
		
	}
	
	public long getFactorial (int num) {
		long factorial = 1;
		for (int i = num; i>1; i--)
			factorial*=i;
		return factorial;
	}

}
