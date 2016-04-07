package br.com.fitrank.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.fitrank.modelo.Amizade;
import br.com.fitrank.modelo.Configuracao;
import br.com.fitrank.modelo.Pessoa;
import br.com.fitrank.persistencia.PessoaDAO;
import br.com.fitrank.util.StringUtil;

import com.restfb.types.User;

public class PessoaServico {
	
	private PessoaDAO pessoaDAO;
	private Pessoa pessoa;
	private AmizadeServico amizadeServico;
	private ConfiguracaoServico configuracaoServico;

	public Pessoa adicionaPessoaServico(Pessoa pessoa){
		
		this.pessoaDAO = new PessoaDAO();
			
		pessoa.setData_cadastro(new Date());
		pessoa.setData_ultimo_login(new Date());
		
	    try {
			return pessoaDAO.adicionaPessoa(pessoa);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	    
	}
	
	public Pessoa atualizaPessoaServico(Pessoa pessoa, boolean proprioUsuario){
		
		this.pessoaDAO = new PessoaDAO();
				
		pessoa.setData_ultimo_login(new Date());
		
	    try {
			pessoa = pessoaDAO.atualizaPessoa(pessoa, proprioUsuario);
			preenchePessoa(pessoa);
			
			return pessoa;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	    
	}

	public Pessoa lePessoaServico(User usuarioFacebook){
		
		pessoa = new Pessoa();
		this.pessoaDAO = new PessoaDAO();
		
		if(!StringUtil.isEmptyOrNull(usuarioFacebook.getId())){
			pessoa.setId_usuario(usuarioFacebook.getId());
		}
		
	    try {
			pessoa =  pessoaDAO.lePessoa(pessoa.getId_usuario());
			preenchePessoa(pessoa);
			
			return pessoa;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	public Pessoa lePessoaPorIdServico(String id_pessoa){
		pessoa = new Pessoa();
		this.pessoaDAO = new PessoaDAO();
		
		if(!StringUtil.isEmptyOrNull(id_pessoa)){
			pessoa.setId_usuario(id_pessoa);
		}
		
	    try {
			pessoa =  pessoaDAO.lePessoa(pessoa.getId_usuario());
			
			return pessoa;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void preenchePessoa(Pessoa pessoa) throws SQLException{
		if(pessoa != null){
			preencheListaAmigosPessoa(pessoa);
			recuperaConfiguracaoFavoritaPessoa(pessoa);
		}
	}
	
	private void preencheListaAmigosPessoa(Pessoa pessoa) throws SQLException{
		this.amizadeServico = new AmizadeServico();
		List<Amizade> listaAmizades = amizadeServico.listaAmizades(pessoa.getId_usuario());
		
		ArrayList<Pessoa> amigosPessoa = new ArrayList<Pessoa>();
		
		for (Amizade amizade : listaAmizades) {
			Pessoa amigo = pessoaDAO.lePessoa(amizade.getId_amigo());
			amigosPessoa.add(amigo);
		}
		
		pessoa.setAmigos(amigosPessoa);
	}
	
	private void recuperaConfiguracaoFavoritaPessoa(Pessoa pessoa) throws SQLException{
		this.configuracaoServico = new ConfiguracaoServico();
		Configuracao configuracao = configuracaoServico.leConfiguracaoFavorita(pessoa.getId_usuario());
		pessoa.setConfiguracaoFavorita(configuracao);
	}
	
	public boolean removePessoaFromIdServico(String userId){
		
	    try {
			return pessoaDAO.removePessoaFromId(userId);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
