package br.com.fitrank.persistencia;

//import java.sql.Connection;

import br.com.fitrank.modelo.Usuario;
//import br.com.fitrank.util.JDBCFactory;

public class UsuarioDAO extends GenericDAO<Usuario> {

	public UsuarioDAO() {
		super(Usuario.class);
	}
	
//	// private final EntityManager em;
//	private Connection conexao;
//	
//	public UsuarioDAO() {
//		this.conexao = new JDBCFactory().getConnection();
//	}
//
//
//	//
//	// //Persiste o novo usuario do facebook
//	public Usuario persisteUsuario(Usuario usuario) {
//		// em.getTransaction().begin();
//		// usuario = em.merge(usuario);
//		// em.getTransaction().commit();
//		//
//		return usuario;
//	}

}
