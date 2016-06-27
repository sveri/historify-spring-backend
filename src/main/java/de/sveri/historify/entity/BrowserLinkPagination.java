package de.sveri.historify.entity;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BrowserLinkPagination extends PagingAndSortingRepository<BrowserLink, Long> {
	List<BrowserLink> findByUserAndUriContainingIgnoreCase(User user, String uri, Pageable pageable);

}
