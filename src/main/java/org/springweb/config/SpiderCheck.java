package org.springweb.config;

public class SpiderCheck {

	private static ThreadLocal<Boolean> isSpiderLocal = new ThreadLocal<Boolean>();

	public static void set(Boolean isSpider) {
		isSpiderLocal.set(isSpider);
	}

	public static void remove() {
		isSpiderLocal.remove();
	}

	public static boolean get() {
		return isSpiderLocal.get() == null ? false : isSpiderLocal.get();
	}
}
