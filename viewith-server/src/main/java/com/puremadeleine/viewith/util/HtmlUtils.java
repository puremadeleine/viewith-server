package com.puremadeleine.viewith.util;

import org.jsoup.Jsoup;

public class HtmlUtils {

    public static String removeHtml(String input) {
        return Jsoup.parse(input).text();
    }
}
