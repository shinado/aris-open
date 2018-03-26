import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExp {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException {
//        p00();

        Class clazz = Class.forName("C0873a");
        Field[] fields = clazz.getFields();

        for( Field field : fields ){
            System.out.println( field.getName() + ": " + (int) field.get(clazz).toString().charAt(0) );
        }
    }

    public static String createPattern(String translation, String code) {
        String[] originalValues = code.split("->");//new String[]{"weixin", "apk", "nashui"};

        List<String> pattern = new ArrayList<>();
//        String[] pattern = new String[tValues.length];

        //reverse
        Map<Integer, Integer> indexes = new TreeMap<>();
        for (int i = 0; i < originalValues.length; i++) {
            int idx = translation.indexOf(originalValues[i]);
            indexes.put(idx, i);
        }

        String[] tValues = new String[indexes.size()];
        int j=0;
        for (int i: indexes.values()){
            tValues[j++] = originalValues[i];
        }

        String t = translation;
        String pre = "";
        for (int i = 0; i < tValues.length; i++) {
            String key = pre + tValues[i];
            String[] split = t.split(key);
            if (split.length >= 2) {
                pattern.add(split[0]);
                t = split[1];
            } else {
                //not matched
                if (t.startsWith(split[0])) {
                    pattern.add(split[0]);
                } else {
                    pre += key + "->";
                }
            }
        }

        String newPattern = "";
        if (pattern.isEmpty()) {
            newPattern = null;
        } else {
            for (String p : pattern) {
                newPattern += (p + "(.*)");
            }
        }

        return newPattern;
    }

    public static void p00() {
//        String translation = "baweixindeapkfageinashui";
//        String translation = "gennashuishuoxialaidaqiu";
//        String translation = "bagangpaidenazhangzhaopianfageinashui";
        String translation = "gangpaidezhaopian";

//        String code = "weixin->apk->nashui";
//        String code = "xialaidaqiu->nashui";
        String code = "dcim/camera->endw jpg->latest";

        String newPattern = createPattern(translation, code);

        System.out.println("new pattern: " + newPattern);

        if (newPattern != null) {
            Pattern r = Pattern.compile(newPattern);

            // Now create matcher object.
            Matcher m = r.matcher(translation);

            if (m.find()) {
                for (int i = 1; i <= m.groupCount(); i++) {
                    System.out.println("Found value: " + m.group(i));
                }
            } else {
                System.out.println("NO MATCH");
            }
        } else {
            System.out.println("Found value: " + code);
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
        String code[] = new String[]{"cd camera", "latest", "nashui"};
        //cd camera 刚刚拍
        //latest 那张照片
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