import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExp {

    public static void main(String[] args) {
        p00();
    }

    public static void p00() {
        String line = "把微信的apk发给那谁";
        String translation = "baweixindeapkfageinashui";
        String[] values = new String[]{"微信", "apk", "那谁"};

        String code = "weixin->apk->nashui";
        String[] tValues = code.split("->");//new String[]{"weixin", "apk", "nashui"};

//        String line = "跟Eric说下来打球";
//        String[] values = new String[]{"Eric", "下来打球"};

        String[] pattern = new String[tValues.length];

        String t = translation;
        for (int i = 0; i < tValues.length; i++) {
            String[] split = t.split(tValues[i]);
            pattern[i] = split[0];
            if (split.length >= 2) {
                t = split[1];
            }
        }

        String newPattern = "";
        for (String p : pattern) {
            newPattern += (p + "(.*)");
        }
        System.out.println(newPattern);

        // Create a Pattern object
        Pattern r = Pattern.compile(newPattern);

        // Now create matcher object.
        Matcher m = r.matcher(translation);

        if (m.find()) {
            for (int i=1; i<=m.groupCount(); i++){
                System.out.println("Found value: " + m.group(i));
            }
        } else {
            System.out.println("NO MATCH");
        }
    }

    public static void p0() {
        String line = "把微信的apk发给那谁";
        String[] values = new String[]{"微信", "apk", "那谁"};

//        String line = "跟Eric说下来打球";
//        String[] values = new String[]{"Eric", "下来打球"};

        String[] pattern = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            String[] split = line.split(values[i]);
            pattern[i] = split[0];
            if (split.length >= 2) {
                line = split[1];
            }
        }

        for (String p : pattern) {
            System.out.println(p);
        }
    }

    public static void p1() {
        String line = "跟Eric说下来打球";
        String pattern = "跟(.*)说(.*)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);

        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
        } else {
            System.out.println("NO MATCH");
        }
    }

    public static void p2() {
        String line = "baweixindeapkfageinashui";
        String pattern = "ba(.*)de(.*)fagei(.*)";
//        String line = "把微信的apk发给那谁";
//        String pattern = "把(.*)的(.*)发给(.*)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);

        System.out.println(pattern);
        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
            System.out.println("Found value: " + m.group(3));
        } else {
            System.out.println("NO MATCH");
        }
    }

    public static void p3() {
        String line = "在淘宝上找个杯子";
        String pattern = "在(.*)上找个(.*)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);

        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
        } else {
            System.out.println("NO MATCH");
        }
    }

    public static void p4() {
        String line = "跟Katie说我今晚要打球，要翻译一下";
        String pattern = "跟(.*)说(.*)，(.*)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);

        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
        } else {
            System.out.println("NO MATCH");
        }
    }

    public static void p5() {
        String line = "哎我这微信的版本是多少";
        String pattern = "[我这](.*)的(.*)是(.*)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);

        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
        } else {
            System.out.println("NO MATCH");
        }
    }

    public static void p6() {
        String line = "把刚刚拍的那张照片发给那谁";
        String pattern = "把(.*)的(.*)发给(.*)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);

        if (m.find()) {
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
            System.out.println("Found value: " + m.group(3));
        } else {
            System.out.println("NO MATCH");
        }
    }

    public static void p7() {
        String line = "把刚刚下的那张照片发给那谁";
        String pattern = "把(.*)的(.*)发给(.*)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);

        if (m.find()) {
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
            System.out.println("Found value: " + m.group(3));
        } else {
            System.out.println("NO MATCH");
        }
    }

}