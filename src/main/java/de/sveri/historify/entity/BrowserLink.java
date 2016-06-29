package de.sveri.historify.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class BrowserLink {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long id;

	@NotNull
	private String uri;

	@NotNull
	private String uriKeywords;

	@Column(columnDefinition = "TEXT")
	private String description;

	@NotNull
	private String title;

	private String metaData;

	@NotNull
	private Date visitedAt;

	@NotNull
	private String client;

	@NotNull
	@OneToOne
	private User user;

}
