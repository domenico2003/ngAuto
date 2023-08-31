package application.entities;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import application.entities.enums.UtenteRuoli;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({ "password", "accountNonExpired", "accountNonLocked", "enabled", "credentialsNonExpired",
		"authorities" })
public class Utente implements UserDetails {
	@Id
	@GeneratedValue
	private UUID id;
	private String email;
	private String password;
	private String nome;
	private String cognome;
	private String username;

	@Enumerated(EnumType.STRING)
	private UtenteRuoli ruolo = UtenteRuoli.USER;
	private boolean isEnabled;
	private boolean isCredentialsNonExpired;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;

	// costruttore
	public Utente(String email, String password, String nome, String cognome, String username) {
		super();
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.isEnabled = true;
		this.isCredentialsNonExpired = true;
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
	}

	// metodi
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(ruolo.name()));
	}

}