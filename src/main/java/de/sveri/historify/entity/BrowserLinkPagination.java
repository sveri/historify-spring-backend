package de.sveri.historify.entity;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface BrowserLinkPagination extends PagingAndSortingRepository<BrowserLink, Long> {
	List<BrowserLink> findByUserOrderByVisitedAtDesc(User user, Pageable pageable);

    @Query(value = "select * from browser_link_search where user_id = :userId and document @@ to_tsquery(:searchable)", nativeQuery = true)
    List<BrowserLink> findByUserAndSearchable(
            @Param("userId") int userId, 
            @Param("searchable") String searchable);

}
