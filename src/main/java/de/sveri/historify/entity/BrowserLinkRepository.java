package de.sveri.historify.entity;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrowserLinkRepository extends CrudRepository<BrowserLink, Long> {
	List<BrowserLink> findAllByUserOrderByVisitedAtDesc(User user);

}