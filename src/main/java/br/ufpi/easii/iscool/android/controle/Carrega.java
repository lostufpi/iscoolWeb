package br.ufpi.easii.iscool.android.controle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONException;

import br.ufpi.easii.iscool.controle.InsigniaController;
import br.ufpi.easii.iscool.dao.ExplicacaoDao;
import br.ufpi.easii.iscool.dao.NotaDao;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.dao.QuestaoDao;
import br.ufpi.easii.iscool.dao.RespostaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Explicacao;
import br.ufpi.easii.iscool.entidade.Nota;
import br.ufpi.easii.iscool.entidade.Prova;
import br.ufpi.easii.iscool.entidade.Questao;
import br.ufpi.easii.iscool.entidade.Resposta;
import br.ufpi.easii.iscool.enuns.Letra;

public class Carrega {
	private QuestaoDao questaoDao;
	private ExplicacaoDao explicacaoDao;
	private UsuarioDao usuarioDao;
	private ProvaDao provaDao;
	private NotaDao notaDao;
	private RespostaDao respostaDao;
	private InsigniaController insignias;
	
	public Carrega(){
		this(null, null, null, null, null, null, null);
	}
	
	@Inject
	protected Carrega(QuestaoDao questaoDao, UsuarioDao usuarioDao, ProvaDao provaDao, NotaDao notaDao, RespostaDao respostaDao, InsigniaController insignias, ExplicacaoDao explicacaoDao){
		this.questaoDao = questaoDao;
		this.usuarioDao = usuarioDao;
		this.provaDao = provaDao;
		this.notaDao = notaDao;
		this.respostaDao = respostaDao;
		this.insignias = insignias;
		this.explicacaoDao = explicacaoDao;
	}
	
	public int notaAluno(BufferedImage image, long idProva,
			long idAluno) throws IOException, NumberFormatException,
			SQLException, JSONException {
		
		List<Questao> gabarito = questaoDao.listarQuestoesPorProva(idProva);

		try {
			BufferedImage gray = toGrayscale(image);
			BufferedImage binary = toBinary(gray, 150);
			BufferedImage apaga = apaga(binary, 150);
			BufferedImage marca = marcaVertical(apaga, 150);
			return corrige(marca, 150, gabarito, idProva, idAluno);
		} catch (IOException e) {
			System.out.println("ERRO!");
		}
		return 0;

	}

	private BufferedImage toGrayscale(BufferedImage image) throws IOException {
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g2d = output.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return output;
	}

	private BufferedImage toBinary(BufferedImage image, int t)
			throws IOException {
		int BLACK = Color.BLACK.getRGB();
		int WHITE = Color.WHITE.getRGB();

		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				Color pixel = new Color(image.getRGB(x, y));
				if (pixel.getRed() < t)
					output.setRGB(x, y, BLACK);
				else
					output.setRGB(x, y, WHITE);
			}
		}
		return output;
	}

	private BufferedImage apaga(BufferedImage image, int t) throws IOException {
		int BLACK = Color.BLACK.getRGB();
		int WHITE = Color.WHITE.getRGB();
		int yCabecalho = (int) ((image.getHeight() * 26.9) / 100);
		int x1Cabecalho = (int) ((image.getWidth() * 8.5) / 100);
		int x2Cabecalho = (int) ((image.getWidth() * 92) / 100);
		int x1coluna1 = (int) ((image.getWidth() * 8.7) / 100);
		int x2coluna1 = (int) ((image.getWidth() * 14.9) / 100);
		int x1coluna2 = (int) ((image.getWidth() * 50.7) / 100);
		int x2coluna2 = (int) ((image.getWidth() * 60.8) / 100);

		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				Color pixel = new Color(image.getRGB(x, y));
				if (((y >= 0 && y <= yCabecalho) && (x >= x1Cabecalho && x <= x2Cabecalho))
						|| ((y >= 0 && y <= image.getHeight()) && (x >= x1coluna1 && x <= x2coluna1))
						|| ((y >= 0 && y <= image.getHeight()) && (x >= x1coluna2 && x <= x2coluna2))) {
					output.setRGB(x, y, WHITE);
				} else {
					if (pixel.getRed() < t) {
						output.setRGB(x, y, BLACK);
					} else {
						output.setRGB(x, y, WHITE);
					}
				}
			}
		}
		return output;
	}

	private BufferedImage marcaVertical(BufferedImage image, int t)
			throws IOException {
		int BLACK = Color.BLACK.getRGB();
		int WHITE = Color.WHITE.getRGB();
		int y1 = (int) ((image.getHeight() * 30) / 100);
		int x1 = (int) ((image.getWidth() * 20) / 100);
		int x2 = (int) ((image.getWidth() * 63.7) / 100);

		int distanciaX = (int) ((image.getWidth() * 5.9) / 100);
		int distanciaY = (int) ((image.getHeight() * 6.25) / 100);

		BufferedImage output = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				Color pixel = new Color(image.getRGB(x, y));

				if (x == x1 || x == x1 + (1 * distanciaX)
						|| x == x1 + (2 * distanciaX)
						|| x == x1 + (3 * distanciaX)
						|| x == x1 + (4 * distanciaX) || x == x2
						|| x == x2 + (1 * distanciaX)
						|| x == x2 + (2 * distanciaX)
						|| x == x2 + (3 * distanciaX)
						|| x == x2 + (4 * distanciaX) || y == y1
						|| y == y1 + (1 * distanciaY)
						|| y == y1 + (2 * distanciaY)
						|| y == y1 + (3 * distanciaY)
						|| y == y1 + (4 * distanciaY)
						|| y == y1 + (5 * distanciaY)
						|| y == y1 + (6 * distanciaY)
						|| y == y1 + (7 * distanciaY)
						|| y == y1 + (8 * distanciaY)
						|| y == y1 + (9 * distanciaY)) {
					output.setRGB(x, y, BLACK);
				} else {
					if (pixel.getRed() < t)
						output.setRGB(x, y, BLACK);
					else 
						output.setRGB(x, y, WHITE);
				}
			}
		}
		return output;
	}

	private static boolean redondeza(BufferedImage image, int y, int x) {

		int BLACK = 0;
		int aux = 1;
		int cnt = 0;
		int raio = (int) ((image.getWidth() * 0.30) / 100);
		do {
			Color pixel1 = new Color(image.getRGB(x - aux, y - aux));
			Color pixel2 = new Color(image.getRGB(x - aux, y + aux));
			Color pixel3 = new Color(image.getRGB(x + aux, y + aux));
			Color pixel4 = new Color(image.getRGB(x + aux, y - aux));

			if ((pixel1.getRed() == BLACK) || (pixel2.getRed() == BLACK) || (pixel3.getRed() == BLACK) || (pixel4.getRed() == BLACK)) 
				cnt++;
			aux++;
		} while (aux <= raio);

		if (cnt > raio * 0.60)
			return true;
		return false;
	}

	private static Letra procuraAlternativa(BufferedImage image, int y, int xa, int xb, int xc, int xd, int xe) {

		int RED = Color.pink.getRGB();
		image.setRGB(xa, y, RED);
		image.setRGB(xb, y, RED);
		image.setRGB(xc, y, RED);
		image.setRGB(xd, y, RED);
		image.setRGB(xe, y, RED);

		if (redondeza(image, y, xa))
			return Letra.A;
		if (redondeza(image, y, xb)) 
			return Letra.B;
		if (redondeza(image, y, xc)) 
			return Letra.C;
		if (redondeza(image, y, xd))
			return Letra.D;
		if (redondeza(image, y, xe))
			return Letra.E;
		return Letra.NR;
	}

	private int corrige(BufferedImage image, int t, List<Questao> gabarito, Long idProva, Long idAluno) throws IOException, SQLException, JSONException {
		int y1 = (int) ((image.getHeight() * 30) / 100);
		int x1 = (int) ((image.getWidth() * 20) / 100);
		int x2 = (int) ((image.getWidth() * 63.7) / 100);
		int distanciaX = (int) ((image.getWidth() * 5.9) / 100);
		int distanciaY = (int) ((image.getHeight() * 6.25) / 100);
			
		List<Resposta> respostas = new ArrayList<Resposta>();
		List<Questao> respostasCorretas = questaoDao.listarQuestoesPorProva(idProva);
		Aluno aluno = (Aluno) usuarioDao.pesquisarUsuarioPorId(idAluno);
		Prova prova = provaDao.pesquisarProvaPorId(idProva);
		Nota nota = new Nota();
		
		Resposta respostaAtual;
		Nota notaEncontrada;
		
		for (int i = 0; i < 20; i++) {
			if (i == 0) {
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1, x1, x1 + (distanciaX), x1 + (2 * distanciaX), x1 + (3 * distanciaX), x1 + (4 * distanciaX)));		
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 1) {
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (distanciaY), x1, x1 + (distanciaX), x1 + (2 * distanciaX), x1 + (3 * distanciaX), x1 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);

				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 2) {
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (2 * distanciaY), x1, x1 + (distanciaX), x1 + (2 * distanciaX), x1 + (3 * distanciaX), x1 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);

				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 3) {
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (3 * distanciaY), x1, x1 + (distanciaX), x1 + (2 * distanciaX), x1 + (3 * distanciaX), x1 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);

				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 4) {
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (4 * distanciaY), x1, x1 + (distanciaX), x1 + (2 * distanciaX), x1 + (3 * distanciaX), x1 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);

				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 5) {
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (5 * distanciaY), x1, x1 + (distanciaX), x1 + (2 * distanciaX), x1 + (3 * distanciaX), x1 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
			
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 6) {
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (6 * distanciaY), x1, x1 + (distanciaX), x1 + (2 * distanciaX), x1 + (3 * distanciaX), x1 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 7) {
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (7 * distanciaY), x1, x1 + (distanciaX), x1 + (2 * distanciaX), x1 + (3 * distanciaX), x1 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
			
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 8) {
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (8 * distanciaY), x1, x1 + (distanciaX), x1 + (2 * distanciaX), x1 + (3 * distanciaX), x1 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 9) {
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (9 * distanciaY), x1, x1 + (distanciaX), x1 + (2 * distanciaX), x1 + (3 * distanciaX), x1 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}

			// -----------METADE-------------------
			if (i == 10) {// 870 940 1020 1095 1180
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1, x2, x2 + (distanciaX), x2 + (2 * distanciaX), x2 + (3 * distanciaX), x2 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 11) {// 870 940 1020 1095 1180
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (1 * distanciaY), x2, x2 + (distanciaX), x2 + (2 * distanciaX), x2 + (3 * distanciaX), x2 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 12) {// 870 940 1020 1095 1180
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (2 * distanciaY), x2, x2 + (distanciaX), x2 + (2 * distanciaX), x2 + (3 * distanciaX), x2 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 13) {// 870 940 1020 1095 1095
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (3 * distanciaY), x2, x2 + (distanciaX), x2 + (2 * distanciaX), x2 + (3 * distanciaX), x2 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 14) {// 870 940 1020 1095 1180
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (4 * distanciaY), x2, x2 + (distanciaX), x2 + (2 * distanciaX), x2 + (3 * distanciaX), x2 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 15) {// 870 940 1020 1095 1180
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (5 * distanciaY), x2, x2 + (distanciaX), x2 + (2 * distanciaX), x2 + (3 * distanciaX), x2 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 16) {// 870 940 1020 1095 1180
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (6 * distanciaY), x2, x2 + (distanciaX), x2 + (2 * distanciaX), x2 + (3 * distanciaX), x2 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 17) {// 870 940 1020 1095 1180
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (7 * distanciaY), x2, x2 + (distanciaX), x2 + (2 * distanciaX), x2 + (3 * distanciaX), x2 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 18) {// 870 940 1020 1095 1180
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (8 * distanciaY), x2, x2 + (distanciaX), x2 + (2 * distanciaX), x2 + (3 * distanciaX), x2 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
			if (i == 19) {// 870 940 1020 1095 1180
				respostaAtual = new Resposta();
				respostaAtual.setResposta(procuraAlternativa(image, y1 + (9 * distanciaY), x2, x2 + (distanciaX), x2 + (2 * distanciaX), x2 + (3 * distanciaX), x2 + (4 * distanciaX)));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(prova);
				
				respostas.add(respostaAtual);
				System.out.println("Resposta: " + respostaAtual.getResposta());
			}
		}
		
		int numeroDeAcertos = 0;
		int numeroDeErros = 0;
		int questoesSemResposta = 0;
		int acertosComPenalidade = 0;
		int penalidade = 0;
		double porcentagemDeAcerto = 0;
		
		for(int i = 0; i < respostasCorretas.size(); i++){
			respostas.get(i).setNumero(respostasCorretas.get(i).getNumero());
			respostas.get(i).setQuestao(respostasCorretas.get(i));
			if(respostas.get(i).getResposta().equals(respostasCorretas.get(i).getResposta())){
				numeroDeAcertos = numeroDeAcertos + 1;
				
				Questao questao = respostasCorretas.get(i);
				Explicacao explicacao = new Explicacao();
				
				questao.acertou();
				questao.incrementaResposta();
				
				explicacao.setAcertou(true);
				explicacao.setDescricao(questao.getDescricao());
				explicacao.setExplicacao(questao.getExplicacao());
				
				questaoDao.atualizarQuestao(questao);
				explicacaoDao.inserirExplicacao(explicacao);
			}
			else if(!respostas.get(i).getResposta().equals(respostasCorretas.get(i).getResposta()) && !respostas.get(i).getResposta().equals(Letra.NR)){
				numeroDeErros = numeroDeErros + 1;
				Questao questao = respostasCorretas.get(i);
				questao.incrementaResposta();
				questaoDao.atualizarQuestao(questao);
			}
			else{
				questoesSemResposta = questoesSemResposta + 1;
				Questao questao = respostasCorretas.get(i);
				questao.incrementaResposta();
				questaoDao.atualizarQuestao(questao);
			}
		}
		
		corrigirQuestoes(respostas, respostasCorretas, aluno, prova);
		
		if(prova.getValorDaPenalidade() != 0){
			penalidade = numeroDeErros/prova.getValorDaPenalidade();
			if(penalidade <= numeroDeAcertos)
				acertosComPenalidade = numeroDeAcertos - penalidade;
			else
				acertosComPenalidade = 0;
			porcentagemDeAcerto = (acertosComPenalidade * 100)/respostasCorretas.size();
		}else{
			acertosComPenalidade = numeroDeAcertos;
			porcentagemDeAcerto = (acertosComPenalidade * 100)/respostasCorretas.size();
		}
		
		nota.setAcertosComPenalidade(acertosComPenalidade);
		nota.setAluno(aluno);
		nota.setNota(numeroDeAcertos);
		nota.setNumeroDeErros(numeroDeErros);
		nota.setPenalidade(penalidade);
		nota.setPorcentagemDeAcerto(porcentagemDeAcerto);
		nota.setProva(prova);
		nota.setProvaCorrigida(true);
		nota.setQuestoesSemResposta(questoesSemResposta);
		nota.setCaminhoDaImagem("foto-" + aluno.getId() + "-" + prova.getId() + ".png");
		insignias.inserirInsigniasPorProva(nota);
		
		
		if(notaDao.pesquisarNota(aluno.getId(), prova.getId()) == null){
			notaDao.inserirNota(nota);
		}else{
			notaEncontrada = notaDao.pesquisarNota(aluno.getId(), prova.getId());
			notaEncontrada.setAcertosComPenalidade(acertosComPenalidade);
			notaEncontrada.setAluno(aluno);
			notaEncontrada.setNota(numeroDeAcertos);
			notaEncontrada.setNumeroDeErros(numeroDeErros);
			notaEncontrada.setPenalidade(penalidade);
			notaEncontrada.setPorcentagemDeAcerto(porcentagemDeAcerto);
			notaEncontrada.setProva(prova);
			notaEncontrada.setProvaCorrigida(true);
			notaEncontrada.setQuestoesSemResposta(questoesSemResposta);
			notaDao.atualizarNota(nota);
		}
		return 0;
	}
	
	public void corrigirQuestoes(List<Resposta> respostas, List<Questao> respostasCorretas, Aluno aluno, Prova prova){
		int size = respostasCorretas.size();
		for(int i = 0;i < size; i++){
			if(respostaDao.pesquisarResposta(aluno.getId(), prova.getId(), respostasCorretas.get(i).getNumero()) == null){
				Resposta resposta = new Resposta();
				resposta.setNumero(respostasCorretas.get(i).getNumero());
				resposta.setResposta(respostas.get(i).getResposta());
				resposta.setProva(prova);
				resposta.setAluno(aluno);
				respostaDao.inserirResposta(resposta);
			}else{
				Resposta resposta = respostaDao.pesquisarResposta(aluno.getId(), prova.getId(), respostasCorretas.get(i).getNumero());
				resposta.setResposta(respostas.get(i).getResposta());
				respostaDao.atualizarResposta(resposta);
			}
		}
	}
}