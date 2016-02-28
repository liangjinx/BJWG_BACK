package com.bjwg.back.util;

/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: EncodeUtils.java 1595 2011-05-11 16:41:16Z calvinxiu $
 */
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * 
 * @author calvin
 */
public abstract class EncodeUtils {
	private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	private static final String DEFAULT_URL_ENCODING = "UTF-8";

	public static String encodeHex(byte[] input) {
		return Hex.encodeHexString(input);
	}

	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}

	public static String encodeBase64(byte[] input) {
		return Base64.encodeBase64String(input);
	}

	public static String encodeUrlSafeBase64(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	public static byte[] decodeBase64(String input) {
		return Base64.decodeBase64(input);
	}

	public static String encodeBase62(long num) {
		return alphabetEncode(num, 62);
	}

	public static long decodeBase62(String str) {
		return alphabetDecode(str, 62);
	}

	private static String alphabetEncode(long num, int base) {
		num = Math.abs(num);
		StringBuilder sb = new StringBuilder();
		for (; num > 0; num /= base) {
			sb.append(ALPHABET.charAt((int) (num % base)));
		}

		return sb.toString();
	}

	private static long alphabetDecode(String str, int base) {
		AssertUtils.hasText(str);

		long result = 0;
		for (int i = 0; i < str.length(); i++) {
			result += ALPHABET.indexOf(str.charAt(i)) * Math.pow(base, i);
		}

		return result;
	}

	public static String urlEncode(String part) {
		try {
			return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static String urlDecode(String part) {

		try {
			return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * Html 杞爜.
	 */
	public static String htmlEscape(String html) {
		return StringEscapeUtils.escapeHtml(html);
	}

	/**
	 * Html 瑙ｇ爜.
	 */
	public static String htmlUnescape(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml(htmlEscaped);
	}

	/**
	 * Xml 杞爜.
	 */
	public static String xmlEscape(String xml) {
		return StringEscapeUtils.escapeXml(xml);
	}

	/**
	 * Xml 瑙ｇ爜.
	 */
	public static String xmlUnescape(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}
}
