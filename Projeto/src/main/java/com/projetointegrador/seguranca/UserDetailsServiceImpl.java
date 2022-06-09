package com.projetointegrador.seguranca;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projetointegrador.model.Cliente;
import com.projetointegrador.model.Funcionario;
import com.projetointegrador.repository.ClienteRepository;
import com.projetointegrador.repository.FuncionarioRepository;


//Indicando que essa é uma classe de serviço

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository userRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	/**
	 * 
	 * Sobrescreve (@Override) o método loadUserByUsername.
	 * 
	 * A implementação de autenticação chama o método loadUserByUsername(String username),
	 * para obter os dados de um usuário com um determinado nome de usuário. 
	 * O nome do usuário deve ser único. O usuário retornado por este método é um objeto
	 * da classe UserDetailsImpl. 
	 * 
	 */

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		//Busca o cliente no Banco de dados
		
		Optional<Cliente> user = userRepository.findByEmail(email);
		
		//Se não existir o método lança uma Exception do tipo UsernameNotFoundException
		
		user.orElseThrow(() -> new UsernameNotFoundException(email+": not found"));
		
		/**
		 * Retorna um objeto do tipo UserDetailsImpl criado com os dados recuperados do
		 * Banco de dados.
		 * 
		 * O operador :: faz parte de uma expressão que referencia um método, complementando
		 * uma função lambda. Neste exemplo, o operador faz referência ao construtor da 
		 * Classe UserDetailsImpl. 
		 */
		return user.map(UserDetailsImpl::new).get();
		}
	
	public UserDetails loadByUserFunc(String codf) throws UsernameNotFoundException {
		Optional<Funcionario> userFunc = Optional.ofNullable(funcionarioRepository.findByCodf(codf))
				.orElseThrow(() -> new UsernameNotFoundException(codf + " not found!"));
		return userFunc.map(UserDetailsImpl::new).get();
	}
	
	}
	



