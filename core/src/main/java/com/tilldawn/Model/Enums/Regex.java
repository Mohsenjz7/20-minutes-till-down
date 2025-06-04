package com.tilldawn.Model.Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Regex {
    passWord("(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%&*()_]).{8,}");

    private final String pattern;
    Regex(String pattern) { this.pattern = pattern; }

    public Matcher GetMatcher(String input)
    {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if(matcher.matches())
        {
            return matcher;
        }
        return null;
    }
}
