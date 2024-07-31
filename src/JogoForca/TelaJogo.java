package JogoForca;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class TelaJogo {
	private JogoDaForca jogo;
	private ArrayList<Integer> ocorrencia = new ArrayList<>();

	private JFrame frame;
	private JButton button;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JTextField textField;
	private JButton button_1;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JButton button_3;
	private JLabel label_7;
	private JLabel label_8;
	private JLabel label_9;
	
	public void playMusic() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(TelaJogo.class.getResource("/JogoForcaMsc/musica.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}catch(Exception ex){
			 ex.printStackTrace();
		}
	}
	
	public void destruir_conteudo() {
		jogo = null;
		frame.getContentPane().removeAll();
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();	
	}
	
	
	public void telaPrincipal() {	
		
		try {jogo = new JogoDaForca();
		jogo.iniciar();
		} catch (Exception e1) {e1.getMessage();}
		
		//adicionando label dicas:
		label_1 = new JLabel("DICA : É UM(A) " + jogo.getDica());
		label_1.setFont(new Font("Arial Black", Font.PLAIN, 12));
		label_1.setBounds(7, 14, 290, 13);
		frame.getContentPane().add(label_1);
		
		
		int tam = jogo.getTamanho();
		label_2 = new JLabel("*".repeat(tam) );
		label_2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		label_2.setBounds(140, 100, 230, 13);
		frame.getContentPane().add(label_2);
		
		//textField
		textField = new JTextField();
		textField.requestFocus();
		textField.setFont(new Font("Arial Black", Font.PLAIN, 12));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(75, 145, 96, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		//label de resultados
		label_3 = new JLabel("");
		label_3.setFont(new Font("Arial Black", Font.PLAIN, 12));
		label_3.setBounds(10, 180, 277, 26);
		frame.getContentPane().add(label_3);
		
		//label de acertos e erros!
		label_4 = new JLabel("ACERTOS  " + jogo.getAcertos());
		label_4.setForeground(new Color(0, 153, 51));
		label_4.setFont(new Font("Arial Black", Font.PLAIN, 12));
		label_4.setBounds(331, 10, 95, 20);
		frame.getContentPane().add(label_4);
		
		label_5 = new JLabel("ERROS      " + jogo.getNumeroPenalidade());
		label_5.setForeground(new Color(255, 0, 0));
		label_5.setFont(new Font("Arial Black", Font.PLAIN, 12));
		label_5.setBounds(331, 30, 97, 26);
		frame.getContentPane().add(label_5);
		
		//LABEL IMAGEM
		label_6 = new JLabel("New label");
		label_6.setBounds(299, 72, 114, 134);
		frame.getContentPane().add(label_6);
		ImageIcon icon = new ImageIcon(TelaJogo.class.getResource("/JogoForcaImagens/0.png"));
		icon.setImage(icon.getImage().getScaledInstance(
				label_6.getWidth(),
				label_6.getHeight(),
				Image.SCALE_DEFAULT));
		label_6.setIcon(icon);
		
		//NOME DA PENALIDADE
		label_7 = new JLabel("");
		label_7.setFont(new Font("Arial Black", Font.PLAIN, 11));
		label_7.setBounds(10, 232, 344, 21);
		frame.getContentPane().add(label_7);
		
		//labeltamanhodapalavra
		label_8 = new JLabel(" COM "+ jogo.getTamanho()+" LETRAS");
		label_8.setFont(new Font("Arial Black", Font.PLAIN, 12));
		label_8.setBounds(4, 50, 277, 13);
		frame.getContentPane().add(label_8);
		
		//BOTAO DE SUBMETER
		button_1 = new JButton("OK");
		frame.getRootPane().setDefaultButton(button_1);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ocorrencia = jogo.getOcorrencias(textField.getText());
					//CASO ACERTE 
					if(ocorrencia.size() > 0) {
						label_3.setForeground(new Color(0, 153, 51));
						label_3.setText("ACERTOU!");						
						textField.setText(null);
						label_4.setText("ACERTOS  " + jogo.getAcertos());
						label_2.setText(jogo.getPalavraAdivinhada());
					}
					else {
						//CASO ERRE
						label_3.setForeground(new Color(255, 0, 0));
						label_3.setText("ERROU!");
						label_5.setText("ERROS      " +jogo.getNumeroPenalidade());
						textField.setText(null);
						label_7.setText(jogo.getNomePenalidade());
						//ALTERANDO A IMAGEM A CADA PENALIDADE
						int n = jogo.getNumeroPenalidade();
						String caminho = "/JogoForcaImagens/"+n+".png";
						ImageIcon icon = new ImageIcon(TelaJogo.class.getResource(caminho));
						icon.setImage(icon.getImage().getScaledInstance(
								label_6.getWidth(),
								label_6.getHeight(),
								Image.SCALE_DEFAULT));
						label_6.setIcon(icon);
														
						}
					//Verificando se o Jogo acabou
					if(jogo.terminou()) {
						//MOSTRANDO O RESULTADO E REMOVENDO COMPONENTES						
						label_3.setText(jogo.getResultado());
						frame.getContentPane().remove(button_1);
						frame.getContentPane().revalidate();
						frame.getContentPane().repaint();
						
						button_3 = new JButton("REINICIAR");
						button_3.setFont(new Font("Arial Black", Font.PLAIN, 8));
						button_3.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							//REMONTANDO A TELA PARA RECOMEÇAR;
								destruir_conteudo();
								telaPrincipal();
							}
						});
						button_3.setBounds(180, 145, 85, 21);
						frame.getContentPane().add(button_3);	
					}
				} catch (Exception e2) {
					label_3.setText(e2.getMessage());
				}
			}});
		button_1.setFont(new Font("Arial Black", Font.PLAIN, 12));
		button_1.setBounds(180, 145, 85, 21);
		frame.getContentPane().add(button_1);
	}
	
	public void telaInicio() {	
				button = new JButton("INICIAR");
				button.setFont(new Font("Arial Black", Font.PLAIN, 12));
				frame.getRootPane().setDefaultButton(button);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {		
						frame.getContentPane().removeAll();
						frame.getContentPane().revalidate();
						frame.getContentPane().repaint();
						telaPrincipal();
						}});
				button.setBounds(180, 150, 96, 26);
				frame.getContentPane().add(button);
				
				label = new JLabel("APERTE O BOTÃO QUANDO ESTIVER PRONTO!");
				label.setBounds(99,190, 400, 40);
				label.setFont(new Font("Arial Black", Font.PLAIN, 10));
				frame.getContentPane().add(label);
				
				label_9 = new JLabel("imagem");
				label_9.setBounds(0, 0, 320, 300);
				frame.getContentPane().add(label_9);
				ImageIcon icon = new ImageIcon(TelaJogo.class.getResource("/JogoForcaImagens/forca.gif"));
				icon.setImage(icon.getImage().getScaledInstance(
						label_9.getWidth(),
						label_9.getHeight(),
						Image.SCALE_DEFAULT));
				label_9.setIcon(icon);
				//playMusic();
				
	}

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaJogo window = new TelaJogo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaJogo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(230, 230, 250));
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				ImageIcon icon = new ImageIcon(TelaJogo.class.getResource("/JogoForcaImagens/iconeForcaTransparente.jpeg"));
				Image resizedImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
				frame.setIconImage(resizedImage);
				frame.setForeground(new Color(255, 255, 255));
				frame.setBackground(Color.WHITE);
				frame.setResizable(false);
				frame.setTitle("TelaJogo");
				frame.setBounds(100, 100, 450, 300);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(null);
				telaInicio();
			}
		});	
	}
}

