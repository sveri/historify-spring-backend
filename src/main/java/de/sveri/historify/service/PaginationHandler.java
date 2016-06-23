package de.sveri.historify.service;

import java.util.ArrayList;
import java.util.List;

import de.sveri.historify.entity.BrowserLink;
import de.sveri.historify.entity.Provider;
import lombok.Value;

public class PaginationHandler {

	private Provider<BrowserLink> browserLinkProvider;

	public PaginationHandler(Provider<BrowserLink> browserLinkProvider) {
		this.browserLinkProvider = browserLinkProvider;
	}

	public long getTotalPages(int size) {
		return browserLinkProvider.totalElements() / size;
	}

	public static boolean isFirstPage(long page, long size) {
		return page == 0;
	}

	public static boolean isLastPage(long page, long size, long totalEntries) {
		return page == totalEntries / size;
	}

	@Value
	public class PageItems {
		private final boolean current;
		private final int number;
	}

	public List<PageItems> getPageItems(int pageSize) {
		List<PageItems> pageItems = new ArrayList<>();
		for (int i = 1; i <= getTotalPages(pageSize) + 1; i++) {
			pageItems.add(new PageItems(false, i));
		}
		return pageItems;
	}

}
