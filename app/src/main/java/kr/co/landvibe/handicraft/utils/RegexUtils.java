package kr.co.landvibe.handicraft.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.landvibe.handicraft.error.RegexException;

public class RegexUtils {

    private static Pattern p;
    private static Matcher m;

    public static final String tokenFilter(String token) throws RegexException {
        p = Pattern.compile("craft (\\w+)");
        m = p.matcher(token);
        if (m.find()) {
            return m.group(1);
        } else {
            throw new RegexException("Invalid Token");
        }
    }
}
