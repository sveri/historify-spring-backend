package de.sveri.historify.service;

public class UriExtractor {

	public static String extractKeywords(String uri){
		String withoutHttp = removeHttp(uri);
		String withoutWWW = removeWWW(withoutHttp);
		String withoutParams = removeParams(withoutWWW);
		String withoutSlashes = replaceSpecialCharsByEmptyString(withoutParams);
		return withoutSlashes;
	}
	
	private static String replaceSpecialCharsByEmptyString(String uri) {
		return uri.replace("/", " ").replace("#", " ").replace(".", " ");
	}

	private static String removeParams(String uri) {
		if(uri.contains("&")){
			return uri.substring(0, uri.indexOf("&"));
		}
		return uri;
	}

	private static String removeWWW(String uri) {
		if(uri.contains("www.")){
			return uri.substring(uri.indexOf("www.") + 4);
		}
		return uri;
	}

	private static String removeHttp(String uri){
		if(uri.contains("https")){
			return uri.substring(8);
		} else if(uri.contains("http")){
			return uri.substring(7);
		}
		return uri;
	}
}
