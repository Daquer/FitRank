package br.com.fitrank.service;

import java.sql.SQLException;

import br.com.fitrank.modelo.Usuario;
import br.com.fitrank.persistencia.UsuarioDAO;

import com.restfb.types.User;

public class UsuarioServico {
	
//private EntityManager em;	
	
public UsuarioServico(){
// this.em = new JPAUtil().getEntityManager();
}

/*
 * @Parametro:Novo Usuário do Facebook
 */
public Usuario persisteUsuarioServico(User usuarioFacebook){
	
	
	//O Usuario que será persistido
	Usuario usuario = new Usuario();
	
	//sempre necessário ter o id do usuario para persistir no SGBD
	if(usuarioFacebook.getId()!=null && !usuarioFacebook.getId().equals(""))
		usuario.setId(usuarioFacebook.getId());
	
	if(usuarioFacebook.getEmail()!=null)
		usuario.setEmail(usuarioFacebook.getEmail());

	if(usuarioFacebook.getFirstName()!=null)
		usuario.setNome(usuarioFacebook.getFirstName());
	
	if(usuarioFacebook.getBirthday()!=null)
	   usuario.setDataNascimento(usuarioFacebook.getBirthday());
	
	UsuarioDAO persiste = new UsuarioDAO();
    //return persiste.persisteUsuario(usuario);
	
	try {
		persiste.insert(usuario);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
	return usuario;
	
}

}
