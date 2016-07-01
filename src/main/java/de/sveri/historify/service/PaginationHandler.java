package de.sveri.historify.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;

import de.sveri.historify.entity.BrowserLink;
import de.sveri.historify.entity.BrowserLinkPaginationRepository;
import de.sveri.historify.entity.User;
import de.sveri.historify.entity.UserRepository;
import lombok.Getter;
import lombok.Value;

public class PaginationHandler implements Pageable<BrowserLink> {

	@Getter
	private final List<BrowserLink> elements;

	private long totalElementCount;

	@Getter
	private final int pageNumber;

	@Getter
	private final long pageSize;

	public PaginationHandler(List<BrowserLink> elements, long totalElementCount, int pageNumber, int pageSize) {
		this.elements = elements;
		this.totalElementCount = totalElementCount;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	@Override
	public int getElementsCount() {
		return elements.size();
	}

	@Override
	public long getTotalPages() {
		if (totalElementCount % pageSize == 0)
			return totalElementCount / pageSize;
		return (totalElementCount / pageSize) + 1;
	}

	@Override
	public boolean isFirstPage() {
		return pageNumber == 1;
	}

	@Override
	public boolean isLastPage() {
		return pageNumber == getTotalPages();
	}

	@Value
	public class PageItems {
		private final boolean current;
		private final int number;
	}

	@Override
	public List<PageItems> getPageItems() {
		List<PageItems> pageItems = new ArrayList<>();
		long totalPages = getTotalPages();

		if (totalPages < 7) {
			for (int i = 1; i <= totalPages; i++) {
				if (pageNumber == i)
					pageItems.add(new PageItems(true, i));
				else
					pageItems.add(new PageItems(false, i));
			}
		} else {
			if (pageNumber < 3) {
				addPageItem(pageItems, 1, pageNumber);
			} else if (pageNumber > getTotalPages() - 3) {
				addPageItem(pageItems, (int) (getTotalPages() - 5), pageNumber);
			} else {
				addPageItem(pageItems, pageNumber - 2, pageNumber);
			}

		}

		return pageItems;
	}

	private void addPageItem(List<PageItems> pageItems, int startPage, int activePage) {
		for (int i = startPage; i < startPage + 5; i++) {
			if (activePage == i)
				pageItems.add(new PageItems(true, i));
			else
				pageItems.add(new PageItems(false, i));
		}
	}

	public static Pageable<BrowserLink> fromSearchCriteria(BrowserLinkPaginationRepository browserLinkProvider,
			UserRepository userRepo, Principal principal, int pageNumber, int pageSize, String searchFor) {

		User user = userRepo.findOneByUserName(principal.getName());

		Pageable<BrowserLink> paginationHandler = null;
		String searchCleaned = cleanSearchForParam(searchFor);
		if (StringUtils.isEmpty(searchCleaned)) {
			paginationHandler = new PaginationHandler(
					browserLinkProvider.findByUserOrderByVisitedAtDesc(user, new PageRequest(pageNumber - 1, pageSize)),
					browserLinkProvider.count(), pageNumber, pageSize);
		} else {
			PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize);
			List<BrowserLink> elements = browserLinkProvider.findByUserAndSearchable(user.getId(), searchCleaned,
					pageRequest.getPageSize(), pageRequest.getOffset());
			// TODO replace elements.size by elements.totalCount
			paginationHandler = new PaginationHandler(elements, elements.size(), pageNumber, pageSize);
		}

		return paginationHandler;
	}

	private static String cleanSearchForParam(String searchFor) {
		return searchFor.replace("|", "").trim().replace(" ", "&");
	}
}
