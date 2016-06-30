package de.sveri.historify.service;

import java.util.ArrayList;
import java.util.List;

import de.sveri.historify.entity.BrowserLink;
import lombok.Value;

public class PaginationHandler {

	// private Provider<BrowserLink> browserLinkProvider;

	private final List<BrowserLink> elements;
	private long totalElementCount;

	public PaginationHandler(List<BrowserLink> elements, long totalElementCount) {
		// this.browserLinkProvider = browserLinkProvider;
		this.elements = elements;
		this.totalElementCount = totalElementCount;
	}

	public List<BrowserLink> getElements() {
		return elements;
	}

	public int getElementsCount() {
		return elements.size();
	}

	public long getTotalPagesForPageSize(int pageSize) {
		if (totalElementCount % pageSize == 0)
			return totalElementCount / pageSize;
		return (totalElementCount / pageSize) + 1;
	}

	public static boolean isFirstPage(long page) {
		return page == 1;
	}

	public boolean isLastPage(long page, long size) {
		return page - 1 == totalElementCount / size;
	}

	@Value
	public class PageItems {
		private final boolean current;
		private final int number;
	}

	public List<PageItems> getPageItems(int pageSize, int activePage) {
		List<PageItems> pageItems = new ArrayList<>();
		long totalPages = getTotalPagesForPageSize(pageSize);

		if (totalPages < 7) {
			for (int i = 1; i <= totalPages; i++) {
				if (activePage == i)
					pageItems.add(new PageItems(true, i));
				else
					pageItems.add(new PageItems(false, i));
			}
		} else {
			if (activePage < 3) {
				addPageItem(pageItems, 1, activePage);
			} else if (activePage > getTotalPagesForPageSize(pageSize) - 3) {
				addPageItem(pageItems, (int) (getTotalPagesForPageSize(pageSize) - 5), activePage);
			} else {
				addPageItem(pageItems, activePage - 2, activePage);
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

}
