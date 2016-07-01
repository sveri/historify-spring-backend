package de.sveri.historify.service;

import java.util.List;

import de.sveri.historify.service.PaginationHandler.PageItems;

public interface Pageable<T> {

	int getElementsCount();

	long getTotalPages();

	boolean isFirstPage();

	boolean isLastPage();

	List<PageItems> getPageItems();

	List<T> getElements();

}