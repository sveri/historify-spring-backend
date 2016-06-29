package de.sveri.historify.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class UriExtractorTest {

	@Test
	public void removeHttp() throws Exception {
		assertTrue(!UriExtractor.extractKeywords("http://www.foo").contains("http:"));
		assertTrue(!UriExtractor.extractKeywords("https://www.foo").contains("https:"));
		assertTrue(!UriExtractor.extractKeywords("www.foo").contains("http"));
	}

	@Test
	public void removeWWW() throws Exception {
		assertTrue(!UriExtractor.extractKeywords("www.foo").contains("www"));
		assertTrue(!UriExtractor.extractKeywords("http://www.foo").contains("www"));
		assertTrue(!UriExtractor.extractKeywords("https://www.foo").contains("www"));
	}

	@Test
	public void removeParams() throws Exception {
		assertTrue(!UriExtractor.extractKeywords("http://www.foo.bar/sjk#sdfj&foo=bar").contains("&"));
	}

	@Test
	public void insertEmptySpaces() throws Exception {
		assertEquals("foo bar sjk sdfj", UriExtractor.extractKeywords("http://www.foo.bar/sjk#sdfj&foo=bar"));
		assertEquals("foo bar sjk mal sdfj", UriExtractor.extractKeywords("http://www.foo.bar/sjk/mal#sdfj&foo=bar"));
	}
}
