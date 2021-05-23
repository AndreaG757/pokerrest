package com.projectpokerrest.pokerrest.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ruolo")
public class Ruolo {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_PLAYER = "ROLE_PLAYER";
	public static final String ROLE_SPECIAL_PLAYER = "ROLE_SPECIAL_PLAYER";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "descrizione")
	private String descrizione;
	@Column(name = "codice")
	private String codice;

	public Ruolo() {
	}

	public Ruolo(String codice) {
		this.codice = codice;
	}

	public Ruolo(String descrizione, String codice) {
		this.descrizione = descrizione;
		this.codice = codice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	@Override
	public String toString() {
		return "Ruolo{" +
				"id=" + id +
				", descrizione='" + descrizione + '\'' +
				", codice='" + codice + '\'' +
				'}';
	}

	public static String getRoleAdmin() {
		return ROLE_ADMIN;
	}

	public static String getRolePlayer() {
		return ROLE_PLAYER;
	}

	public static String getRoleSpecialPlayer() {
		return ROLE_SPECIAL_PLAYER;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ruolo ruolo = (Ruolo) o;
		return codice.equals(ruolo.codice);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codice);
	}

}
