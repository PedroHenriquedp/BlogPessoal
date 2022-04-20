package org.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="tb_postagens")
public class Postagem {

	//------------------ATRIBUTOS------------------
	//ID
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	//TÍTULO
	@NotNull
	@NotBlank
	private String titulo;
	
	//TEXTO
	@NotNull
	@Size(min=20, max=400)
	private String texto;
	
	//DATA
	@UpdateTimestamp
	private LocalDateTime data;
	
	//FOREIGN KEY
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;
	//------------------GETTERS AND SETTERS--------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
