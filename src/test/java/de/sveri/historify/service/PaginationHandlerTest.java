package de.sveri.historify.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.sveri.historify.entity.BrowserLink;
import de.sveri.historify.service.PaginationHandler.PageItems;

public class PaginationHandlerTest {

	@Test
	public void testTotalPages() throws Exception {
		PaginationHandler paginationHandler = getPaginationHandler(10, 50, 1, 10);
		assertEquals(5, paginationHandler.getTotalPages());

		paginationHandler = getPaginationHandler(10, 10, 1, 10);
		assertEquals(1, paginationHandler.getTotalPages());

		paginationHandler = getPaginationHandler(9, 10, 1, 10);
		assertEquals(1, paginationHandler.getTotalPages());

		paginationHandler = getPaginationHandler(10, 11, 1, 10);
		assertEquals(2, paginationHandler.getTotalPages());

		paginationHandler = getPaginationHandler(10, 20, 1, 10);
		assertEquals(2, paginationHandler.getTotalPages());
		
		paginationHandler = getPaginationHandler(10, 51, 1, 10);
		assertEquals(6, paginationHandler.getTotalPages());
		
		paginationHandler = getPaginationHandler(20, 50, 1, 20);
		assertEquals(3, paginationHandler.getTotalPages());

	}

	@Test
	public void testGetPageItems() throws Exception {
		PaginationHandler paginationHandler = getPaginationHandler(10, 20, 1, 10);
		assertEquals(2, paginationHandler.getPageItems().size());
		
		paginationHandler = getPaginationHandler(10, 50, 1, 10);
		assertEquals(5, paginationHandler.getPageItems().size());
		
		paginationHandler = getPaginationHandler(10, 59, 1, 10);
		assertEquals(6, paginationHandler.getPageItems().size());
		
		paginationHandler = getPaginationHandler(10, 100, 5, 10);		
		List<PageItems> pageItems = paginationHandler.getPageItems();

		assertEquals(5, pageItems.size());
		assertEquals(3, pageItems.get(0).getNumber());
		assertEquals(4, pageItems.get(1).getNumber());
		assertEquals(5, pageItems.get(2).getNumber());
		assertEquals(6, pageItems.get(3).getNumber());
		assertEquals(7, pageItems.get(4).getNumber());
		

		
		paginationHandler = getPaginationHandler(12, 50, 3, 20);
		assertEquals(3, paginationHandler.getPageItems().size());
	 }
	
	@Test
	public void isLastPage() throws Exception {
		PaginationHandler paginationHandler = getPaginationHandler(10, 20, 2, 10);
		assertTrue(paginationHandler.isLastPage());
		
		paginationHandler = getPaginationHandler(10, 20, 1, 10);
		assertFalse(paginationHandler.isLastPage());
		
		paginationHandler = getPaginationHandler(20, 50, 3, 20);
		assertTrue(paginationHandler.isLastPage());
		
		paginationHandler = getPaginationHandler(20, 30, 2, 20);
		assertTrue(paginationHandler.isLastPage());
		
	}

	private PaginationHandler getPaginationHandler(int elementsSize, int totalCount, int pageNumber, int pageSize) {
		List<BrowserLink> elements = new ArrayList<>();
		for (int i = 0; i < elementsSize; i++) {
			elements.add(new BrowserLink());
		}
		PaginationHandler paginationHandler = new PaginationHandler(elements, totalCount, pageNumber, pageSize);
		return paginationHandler;
	}

}
