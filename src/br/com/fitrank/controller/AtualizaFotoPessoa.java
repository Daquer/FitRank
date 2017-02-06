package br.com.fitrank.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fitrank.modelo.Pessoa;
import br.com.fitrank.service.PessoaServico;

/**
 * Servlet implementation class AtualizaFotoPessoa
 */
@WebServlet("/AtualizaFotoPessoa")
public class AtualizaFotoPessoa extends HttpServlet {
	
	private static final long serialVersionUID = 6881676770964443354L;

	PessoaServico pessoaServico = new PessoaServico();
	

	private void inicia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String urlFoto = request.getAttribute("url") == null ? (String) request.getParameter("url") : (String) request.getAttribute("url");
		String idPessoa = request.getAttribute("id") == null ? (String) request.getParameter("id") : (String) request.getAttribute("id");
		
		Pessoa pessoa = pessoaServico.lePessoaPorIdServico(idPessoa);
		
		pessoa.setUrl_foto(urlFoto);
		
		pessoaServico.atualizaPessoaServico(pessoa, false);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
	}

}
