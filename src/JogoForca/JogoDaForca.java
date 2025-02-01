package JogoForca;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class JogoDaForca {
	private ArrayList<String> palavra = new ArrayList<>();
	private ArrayList<String> dica = new ArrayList<>();
	private ArrayList<String> letras_usuario= new ArrayList<>();
	private ArrayList<String> palavra_cifrada = new ArrayList<>();
	private ArrayList<Integer> ocorrencias_atualizadas = new ArrayList<>();
	private String palavra_sorteada;
	private String dica_sorteada;	
	private int acertos = 0;
	private int erros = 0;

	public JogoDaForca() throws Exception {
		InputStream stream = this.getClass().getResourceAsStream("/JogoForcaDados/Arq.txt");
//		InputStream stream = JogoDaForca.class.getClassLoader().getResourceAsStream("jogoForcaDados/Arq.txt");
		if (stream == null)
			throw new Exception("Arquivo de texto para leitura inexistente");
		Scanner dados = new Scanner(stream);
		String linha;
		while (dados.hasNext()) {
			linha = dados.nextLine().toUpperCase();
			this.palavra.add(linha.split(";")[0]);
			this.dica.add(linha.split(";")[1]);
		}
		dados.close();
	}
	
	public void iniciar() {
		Random sorteio = new Random();
		int indice = sorteio.nextInt((palavra.size() -1));
		this.palavra_sorteada = palavra.get(indice);
		this.dica_sorteada = dica.get(indice);
	}
	
	public String getDica() {
		return dica_sorteada;
	}
	
	public int getTamanho() {
		int tamanho = palavra_sorteada.length();
		return tamanho;
	}

	public ArrayList<Integer> getOcorrencias(String letra) throws Exception {
		if (letra.length() > 1 || letra.length() == 0 || this.letras_usuario.contains(letra.toUpperCase()))
			throw new Exception("Por favor, UM (1) caracter válido");
		ArrayList<Integer> resposta = new ArrayList<>();
		letra = letra.toUpperCase();
		this.letras_usuario.add(letra);
		int cont = 1;
		if (palavra_sorteada.contains(letra)) {
			for (String c : palavra_sorteada.split("")) { 
				if (c.equals(letra)) {              
					resposta.add(cont);
					this.acertos += 1;
				}
				cont ++;
			}
		} else
			this.erros += 1;
		this.ocorrencias_atualizadas = resposta;
		return resposta;
	}
	
	
	public boolean terminou() {
		if (this.erros == 6 || this.getTamanho() == this.acertos)
			return true;
		else 
			return false;			
	}
	
	
	public String getPalavraAdivinhada() {
		String letra_atual = this.letras_usuario.getLast();
		if (this.palavra_cifrada.size() == 0) {
			for(int i = 0; i < this.getTamanho(); i++) {
			this.palavra_cifrada.add(i,"*"); }}
		
		for(int n: this.ocorrencias_atualizadas) {
			this.palavra_cifrada.set(n-1, letra_atual);
		}
		String saida = String.join("", this.palavra_cifrada);
		return saida;
	}
	

	public int getAcertos() {
		return this.acertos;}
	
	public int getNumeroPenalidade() {
		return this.erros;}
	
	public String getNomePenalidade() {
		int total = this.getNumeroPenalidade();
		String penalidade = "";
		switch(total) {
		case 0 : penalidade = "Nenhuma!"
				+ ""; break;	
		case 1: penalidade = "Cabeça pendurada..."; break;
		case 2: penalidade = "Tronco pendurado..."; break;
		case 3: penalidade = "Braço esquerdo pendurado..."; break;
		case 4: penalidade = "Braço direito pendurado..."; break;
		case 5: penalidade = "Perna esquerda pendurada..."; break;
		case 6: penalidade = "Perna direita pendurada..."; break;
		}
		return penalidade;}
	
	public String getResultado() {
		String status = "";
		if (this.terminou()){
			if(this.getAcertos() > this.getNumeroPenalidade()) {
				status = "Você Venceu!";}	
			else
				status = "Você perdeu :(";
			}
		else
			status = "Jogo em andamento";
		return status;
		 }
	
	public String getPalavraSorteada() { 
		return this.palavra_sorteada;
	}
}

