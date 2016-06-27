package de.sveri.historify.entity;

import java.security.Principal;
import java.util.List;

public interface Provider<T> {

	public List<T> allByUser(Principal principal);

	public List<T> findFromPageWithSizeByUserAndUri(Principal principal, int page, int size, String uri);

	public long totalElements();

}
