package de.sveri.historify.service;

import java.security.Principal;
import java.util.List;

import de.sveri.historify.entity.BrowserLink;
import de.sveri.historify.entity.Provider;

public class TestBrowserLinkProvider implements Provider<BrowserLink> {

	private int elementCount;

	public TestBrowserLinkProvider(int elementCount) {
		this.elementCount = elementCount;
	}

	@Override
	public List<BrowserLink> allByUser(Principal principal) {
		return null;
	}

	@Override
	public List<BrowserLink> findFromPageWithSizeByUser(Principal principal, int page, int size) {
		return null;
	}

	@Override
	public long totalElements() {
		return elementCount;
	}

}
