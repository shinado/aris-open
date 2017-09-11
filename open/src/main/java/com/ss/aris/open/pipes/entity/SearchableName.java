package com.ss.aris.open.pipes.entity;

public class SearchableName {

    private String syntax = null;
    private String[] name;

    /**
     * accepting no languages other than English
     * however, accepting pronunciation of other languages such as Chinese
     */
    public SearchableName(String... name) {
        this.name = name;
    }

    public String[] getNames() {
        return name;
    }

    public void setSyntac(String syntax) {
        this.syntax = syntax;
    }

    /**
     * if email contains key in a way that"s friendly for searching
     * e.g.
     * contains(["google", "map"], "gm") -> true
     * contains(["google", "map"], "gom") -> true
     * contains(["google", "map"], "gma") -> true
     * contains(["google", "map"], "map") -> true
     * contains(["google", "map"], "gg") -> false
     */
    public boolean contains(String key) {
        key = removeSpace(key).toLowerCase();
        for (int i = 0; i < name.length; i++) {
            String str = name[i].toLowerCase();
            if (str.isEmpty()) continue;

            char c = str.charAt(0);
            //key justStart with the first character of email
            //e.g. ["face", "book"], "boo" => true
            if (key.startsWith(c + "")) {
                if (contains(name, key, i, true)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getKeyIndex(String key) {
        return getKeyIndex(key, true);
    }

    private int getKeyIndex(String key, boolean first) {
        int i = 2;
        //set key index
        //with highest priority
        if (first) {
            if (key.equals(toString())) {
                return 0;
            }
            if (toString().startsWith(key)) {
                return 1;
            }
        }

        for (String str : getNames()) {
            if (str.isEmpty()) continue;
            if (str.startsWith(key)) break;

            if (key.startsWith(str)) {
                i = getKeyIndex(key.replace(str, ""), false);
                break;
            }
            i++;
        }

        return i;
    }

    private String removeSpace(String key) {
        return key.replace(" ", "");
    }

    private boolean contains(String name[], String key, int i, boolean firstTime) {
        if (i >= name.length) {
            return false;
        }
        String str = name[i].toLowerCase();
        if (str.isEmpty()) return false;

        char c = str.charAt(0);
        if (key.startsWith(c + "")) {
            for (int j = 1; j < key.length() && j < str.length(); j++) {
                //not matched, find next
                if (key.charAt(j) != str.charAt(j)) {
                    String sub = key.substring(j, key.length());
                    return contains(name, sub, i + 1, false);
                }
            }
            if (key.length() <= str.length()) {
                return true;
            } else {
                String sub = key.substring(str.length(), key.length());
                return contains(name, sub, i + 1, false);
            }
        } else {
            return
                    //commenting firstTime&& below makes searching constant
                    //ok I know you are expecting an "e.g.", then let's make an "e.g.":
                    //for item "facebook", since it's split as ["fa", "ce", "boo", "k"]
                    //by commenting firstTime&&, contains("fb") returns false, contains("fc") returns true
                    //otherwise, they both return true
                    firstTime &&
                            contains(name, key, i + 1, true);

        }
    }

    public String toSimpleString() {
        String string = "";
        for (String str : name) {
            if (str.length() > 0)
                string += str.charAt(0);
        }
        return string;
    }

    @Override
    public String toString() {
        String string = "";
        for (String str : name) {
            string += str;
        }
        return string;
    }

    public boolean equals(String name) {
        return toString().equals(name) ||
                (syntax != null && name.startsWith(syntax) && name.endsWith(syntax));
    }

}
