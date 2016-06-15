package de.sveri.historify.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrowserRepository extends CrudRepository<BrowserLink, Long> {
    BrowserLink findOneByUser(User user);
    
}