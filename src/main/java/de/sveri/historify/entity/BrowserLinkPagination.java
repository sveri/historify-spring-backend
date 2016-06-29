package de.sveri.historify.entity;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface BrowserLinkPagination extends PagingAndSortingRepository<BrowserLink, Long> {
	List<BrowserLink> findByUser(User user, Pageable pageable);
	

//    @Modifying
//    @Query("update User u set u.email = :email "
//            + "where u.userName = :userName")
    @Query(value = "select * from browser_link_search where uid = :userId and document @@ to_tsquery(:searchable)", nativeQuery = true)
    List<BrowserLink> findByUserAndSearchable(
            @Param("userId") int userId, 
            @Param("searchable") String searchable);

}
