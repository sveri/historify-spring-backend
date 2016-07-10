package de.sveri.historify.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class BrowserLink {

	@SequenceGenerator(name = "browser_link_seq", sequenceName = "browser_link_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "browser_link_seq")
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

	// @NotNull
	// @OneToOne
	@Transient
	private User user;

}
