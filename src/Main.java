import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static class myTuple{
        public String first;
        public String second;
        public int third;

        myTuple(String a,String b,int c){
            first = a;
            second = b;
            third = c;
        }

        public int compareTo(Object obj){
            myTuple mt = (myTuple) obj;
            return third-mt.third;
        }
    }
    private static String[] CHINESE_CHAR_LIST = new String[]{"幺", "零", "一", "二", "两", "三", "四", "五", "六", "七", "八", "九", "十", "百", "千", "万", "亿"};
    private static List<String> CHINESE_SIGN_LIST = Arrays.asList("负", "正", "-", "+");
    private static String[] CHINESE_CONNECTING_SIGN_LIST = new String[]{".", "点", "·"};
    private static String[] CHINESE_PER_COUNTING_STRING_LIST = new String[]{"百分之", "千分之", "万分之"};
    private static String CHINESE_PER_COUNTING_SEG = "分之";
    private static List<String> CHINESE_PURE_NUMBER_LIST = Arrays.asList("幺", "一", "二", "两", "三", "四", "五", "六", "七", "八", "九", "十", "零");

    private static Map<String, String> CHINESE_SIGN_DICT = new HashMap<String, String>() {{
        put("负", "-");
        put("正", "+");
        put("-", "-");
        put("+", "+");

    }};

    private static Map<String, String> CHINESE_PER_COUNTING_DICT = new HashMap<String, String>() {{
        put("百分之", "%");
        put("千分之", "‰");
        put("万分之", "‱");
    }};

    private static Map<String, String> CHINESE_CONNECTING_SIGN_DICT = new HashMap<String, String>() {{
        put(".", ".");
        put("点", ".");
        put("·", ".");

    }};
    private static Map<String, Integer> CHINESE_COUNTING_STRING = new HashMap<String, Integer>() {{
        put("十", 10);
        put("百", 100);
        put("千", 1000);
        put("万", 10000);
        put("亿", 100000000);
    }};

    //private static String [] CHINESE_PURE_COUNTING_UNIT_LIST = new String[]  {"十","百","千","万","亿"};
    private static List<String> CHINESE_PURE_COUNTING_UNIT_LIST = Arrays.asList("十", "百", "千", "万", "亿");

    private static Map<String, String> TRADITIONAL_CONVERT_DICT = new HashMap<String, String>() {{
        put("壹", "一");
        put("贰", "二");
        put("叁", "三");
        put("肆", "四");
        put("伍", "五");
        put("陆", "六");
        put("柒", "七");
        put("捌", "八");
        put("玖", "九");
        put("〇", "零");
    }};

    private static Map<String, String> SPECIAL_TRADITIONAL_COUNTING_UNIT_CHAR_DICT = new HashMap<String, String>() {{
        put("拾", "十");
        put("佰", "百");
        put("仟", "千");
        put("萬", "万");
        put("億", "亿");
    }};

    private static Map<String, String> SPECIAL_NUMBER_CHAR_DICT = new HashMap<String, String>() {{
        put("两", "二");
        put("俩", "二");
    }};

    private static Map<String, Integer> common_used_ch_numerals = new HashMap<String, Integer>() {{
        put("幺", 1);
        put("零", 0);
        put("一", 1);
        put("二", 2);
        put("两", 2);
        put("三", 3);
        put("四", 4);
        put("五", 5);
        put("六", 6);
        put("七", 7);
        put("八", 8);
        put("九", 9);
        put("十", 10);
        put("百", 100);
        put("千", 1000);
        put("万", 10000);
        put("亿", 100000000);
    }};

    private static Map<String, String> digits_char_ch_dict = new HashMap<String, String>() {{
        put("0", "零");
        put("1", "一");
        put("2", "二");
        put("3", "三");
        put("4", "四");
        put("5", "五");
        put("6", "六");
        put("7", "七");
        put("8", "八");
        put("9", "九");
        put("%", "百分之");
        put("‰", "千分之");
        put("‱", "万分之");
        put(".", "点");
    }};


//    takingChineseDigitsMixRERules = re.compile(r"(?:(?:分之){0,1}(?:\+|\-){0,1}[正负]{0,1})";
//    r"(?:(?:(?:\d+(?:\.\d+){0,1}(?:[\%\‰\‱]){0,1}|\.\d+(?:[\%\‰\‱]){0,1}){0,1}";
//    r"(?:(?:(?:[一二三四五六七八九十千万亿幺零百]+(?:点[一二三四五六七八九万亿幺零]+){0,1})|(?:点[一二三四五六七八九万亿幺零]+))))";
//    r"|(?:(?:\d+(?:\.\d+){0,1}(?:[\%\‰\‱]){0,1}|\.\d+(?:[\%\‰\‱]){0,1})";
//    r"(?:(?:(?:[一二三四五六七八九十千万亿幺零百]+(?:点[一二三四五六七八九万亿幺零]+){0,1})|(?:点[一二三四五六七八九万亿幺零]+))){0,1}))");
//
//    PURE_DIGITS_RE = re.compile("[0-9]");


    private static String[] DIGITS_CHAR_LIST = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String[] DIGITS_SIGN_LIST = new String[]{"-", "+"};
    private static String[] DIGITS_CONNECTING_SIGN_LIST = new String[]{"."};
    private static String[] DIGITS_PER_COUNTING_STRING_LIST = new String[]{"%", "‰", "‱"};
//    takingDigitsRERule = re.compile(r"(?:(?:\+|\-){0,1}\d+(?:\.\d+){0,1}(?:[\%\‰\‱]){0,1}|(?:\+|\-){0,1}\.\d+(?:[\%\‰\‱]){0,1})");

    private static int max(List<Integer> list) {
        int ret = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (ret < list.get(i)) {
                ret = list.get(i);
            }
        }
        return ret;
    }

    private static int min(List<Integer> list) {
        int ret = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (ret > list.get(i)) {
                ret = list.get(i);
            }
        }
        return ret;
    }

    private static String coreCHToDigits(String chineseChars) {
//        System.out.println("coreCHToDigits_IN" + ' ' + chineseChars);
        int total = 0;
        String tempVal = "";
        int countingUnit = 1;
        List<Integer> countingUnitFromString = new ArrayList<>();
        countingUnitFromString.add(1);

        for (int i = chineseChars.length() - 1; i >= 0; i--) {
            int val = common_used_ch_numerals.get(String.valueOf(chineseChars.charAt(i)));
            if (val >= 10 && i == 0) {
                if (val > countingUnit) {
                    countingUnit = val;
                    total += val;
                    countingUnitFromString.add(Integer.valueOf(val));
                } else {
                    countingUnitFromString.add(val);
                    countingUnit = max(countingUnitFromString) * val;
                }
            } else if (val >= 10) {
                if (val > countingUnit) {
                    countingUnit = val;
                    countingUnitFromString.add(val);
                } else {
                    countingUnitFromString.add(val);
                    countingUnit = max(countingUnitFromString) * val;
                }
            } else {
                if (i > 0) {
                    if (common_used_ch_numerals.get(String.valueOf(chineseChars.charAt(i - 1))) < 10) {
                        tempVal = val + tempVal;
                    } else {
                        total += countingUnit * Integer.parseInt(val + tempVal);
                        tempVal = "";
                    }
                } else {
                    if (countingUnit == 1) {
                        tempVal = val + tempVal;
                    } else {
                        total += countingUnit * Integer.parseInt(val + tempVal);
                    }
                }
            }
        }

        String ret;

        if (total == 0) {
            if (countingUnit > 10) {
                ret = String.valueOf(countingUnit);
            } else {
                if (!tempVal.equals("")) {
                    ret = tempVal;
                } else {
                    ret = String.valueOf(total);
                }
            }
        } else {
            ret = String.valueOf(total);
        }
//        System.out.println("coreCHToDigits_OUT" + ' ' + ret);
        return ret;

    }


    private static String chineseToDigits(String chineseDigitsMixString, boolean percentConvert, Object... args) {
//        System.out.println("chineseToDigits_IN" + ' ' + chineseDigitsMixString + ' ' + percentConvert);
        String[] chineseCharsListByDiv = chineseDigitsMixString.split("分之");
        List<String> convertResultList = new ArrayList<>();
        for (int k = 0; k < chineseCharsListByDiv.length; k++) {
            String tempChineseChars = chineseCharsListByDiv[k];

            String[] chineseCharsDotSplitList = new String[0];

            String sign = "";
            for (char chars : tempChineseChars.toCharArray()) {
                if (CHINESE_SIGN_DICT.containsKey(String.valueOf(chars))) {
                    sign = CHINESE_SIGN_DICT.get(String.valueOf(chars));
                    tempChineseChars = tempChineseChars.replace(String.valueOf(chars), "");
                }
            }

            String chineseChars = tempChineseChars;
            for (String chars : CHINESE_CONNECTING_SIGN_DICT.keySet()) {
                if (chineseChars.contains(String.valueOf(chars))) {
                    chineseCharsDotSplitList = chineseChars.split(String.valueOf(chars));
                }
            }

            String convertResult;
            if (chineseCharsDotSplitList.length == 0) {
                convertResult = coreCHToDigits(chineseChars);
            } else {
                String tempCountString = "";
                for (int ii = chineseCharsDotSplitList[chineseCharsDotSplitList.length - 1].length() - 1; ii >= 0; ii--) {
                    if (CHINESE_PURE_COUNTING_UNIT_LIST.contains(String.valueOf(chineseCharsDotSplitList[chineseCharsDotSplitList.length - 1].charAt(ii)))) {
                        tempCountString = chineseCharsDotSplitList[chineseCharsDotSplitList.length - 1].charAt(ii) + tempCountString;
                    } else {
                        chineseCharsDotSplitList[chineseCharsDotSplitList.length - 1] = chineseCharsDotSplitList[chineseCharsDotSplitList.length - 1].substring(0, ii + 1);
                        break;
                    }
                }
                BigDecimal tempCountNum;
                if (!tempCountString.equals("")) {
                    tempCountNum = new BigDecimal(coreCHToDigits(tempCountString));
                } else {
                    tempCountNum = new BigDecimal(1.0);
                }
                if (chineseCharsDotSplitList[0].equals("")) {
                    convertResult = "0." + coreCHToDigits(chineseCharsDotSplitList[1]);
                } else {
                    convertResult = coreCHToDigits(chineseCharsDotSplitList[0]) + "." + coreCHToDigits(chineseCharsDotSplitList[1]);
                }
                convertResult = String.valueOf(new BigDecimal(convertResult).multiply(tempCountNum));
            }

            if (convertResult.equals("")) {
                convertResult = "1";
            }

            convertResult = sign + convertResult;
            convertResultList.add(convertResult);
        }

        String finalTotal;
        if (convertResultList.size() > 1) {
            if (percentConvert) {
                finalTotal = String.valueOf(new BigDecimal(convertResultList.get(1)).divide(new BigDecimal(convertResultList.get(0))));
            } else {
                if (convertResultList.get(0).equals("100")) {
                    finalTotal = convertResultList.get(1) + "%";
                } else if (convertResultList.get(0).equals("1000")) {
                    finalTotal = convertResultList.get(1) + "‰";
                } else if (convertResultList.get(0).equals("10000")) {
                    finalTotal = convertResultList.get(1) + "‱";
                } else {
                    finalTotal = convertResultList.get(1) + "/" + convertResultList.get(0);
                }
            }
        } else {
            finalTotal = convertResultList.get(0);
        }

        if(finalTotal.contains(".")){
        finalTotal = finalTotal.replaceAll("\\.?0*$", "");
        }

//        System.out.println("chineseToDigits_OUT" + ' ' + finalTotal);
        return finalTotal;
    }

    private static String chineseToDigits(String chineseDigitsMixString) {
        return chineseToDigits(chineseDigitsMixString, true);
    }

    private static String chineseToDigitsHighTolerance(String chineseDigitsMixString, boolean percentConvert, boolean skipError, List<String> errorChar, List<String> errorMsg) {
//        System.out.println("chineseToDigitsHighTolerance_IN " + chineseDigitsMixString + " " + percentConvert + " " + skipError + " " + errorChar + " " + errorChar + " " + errorMsg);
        String total = "";
        if (skipError) {
            try {
                total = chineseToDigits(chineseDigitsMixString, percentConvert);
            } catch (Exception e) {
                total = "";
                errorChar.add(chineseDigitsMixString);
                errorMsg.add(e.getMessage());
            }
        } else {
            total = chineseToDigits(chineseDigitsMixString, percentConvert);
        }
//        System.out.println("chineseToDigitsHighTolerance_OUT " + total);
        return total;
    }

    private static String chineseToDigitsHighTolerance(String chineseDigitsMixString) {
        return chineseToDigitsHighTolerance(chineseDigitsMixString, true, false, new ArrayList<>(), new ArrayList<>());
    }

    public static boolean checkChineseNumberReasonable(String chNumber) {
        if (chNumber.length() > 0) {
            for (String i : CHINESE_PURE_NUMBER_LIST) {
                if (chNumber.contains(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String traditionalTextConvertFunc(String chString, boolean traditionalConvertSwitch) {
        List<String> chStringList = new ArrayList<String>();
        for (char c : chString.toCharArray()) {
            chStringList.add(String.valueOf(c));
        }
        int stringLength = chStringList.size();

        if (traditionalConvertSwitch == true) {

            for (int i = 0; i < stringLength; i++) {
                // 繁体中文数字转简体中文数字
                if (TRADITIONAL_CONVERT_DICT.get(chStringList.get(i)) != null) {
                    chStringList.set(i, TRADITIONAL_CONVERT_DICT.get(chStringList.get(i)));
                }
            }
        }
        if (stringLength > 1) {
            // 检查繁体单体转换
            for (int i = 0; i < stringLength; i++) {
                // 如果 前后有 pure 汉字数字 则转换单位为简体
                if (SPECIAL_TRADITIONAL_COUNTING_UNIT_CHAR_DICT.get(chStringList.get(i)) != null) {
                    // 如果前后有单纯的数字 则进行单位转换
                    if (i == 0) {
                        if (CHINESE_PURE_NUMBER_LIST.contains(chStringList.get(i + 1))) {
                            chStringList.set(i, SPECIAL_TRADITIONAL_COUNTING_UNIT_CHAR_DICT.get(chStringList.get(i)));
                        }
                    } else if (i == stringLength - 1) {
                        if (CHINESE_PURE_NUMBER_LIST.contains(chStringList.get(i - 1))) {
                            chStringList.set(i, SPECIAL_TRADITIONAL_COUNTING_UNIT_CHAR_DICT.get(chStringList.get(i)));
                        }
                    } else {
                        if (CHINESE_PURE_NUMBER_LIST.contains(chStringList.get(i - 1)) || CHINESE_PURE_NUMBER_LIST.contains(chStringList.get(i + 1))) {
                            chStringList.set(i, SPECIAL_TRADITIONAL_COUNTING_UNIT_CHAR_DICT.get(chStringList.get(i)));
                        }
                    }
                }
                // 特殊变换 俩变二
                if (SPECIAL_NUMBER_CHAR_DICT.get(chStringList.get(i)) != null) {
                    // 如果前后有单位 则进行转换
                    if (i == 0) {
                        if (CHINESE_PURE_COUNTING_UNIT_LIST.contains(chStringList.get(i + 1))) {
                            chStringList.set(i, SPECIAL_NUMBER_CHAR_DICT.get(chStringList.get(i)));
                        }
                    } else if (i == stringLength - 1) {
                        if (CHINESE_PURE_COUNTING_UNIT_LIST.contains(chStringList.get(i - 1))) {
                            chStringList.set(i, SPECIAL_NUMBER_CHAR_DICT.get(chStringList.get(i)));
                        }
                    } else {
                        if (CHINESE_PURE_COUNTING_UNIT_LIST.contains(chStringList.get(i - 1)) || CHINESE_PURE_COUNTING_UNIT_LIST.contains(chStringList.get(i + 1))) {
                            chStringList.set(i, SPECIAL_NUMBER_CHAR_DICT.get(chStringList.get(i)));
                        }
                    }
                }
            }
        }

        String ret = "";
        for (String s : chStringList) {
            ret += s;
        }
        return ret;

    }

    private static String traditionalTextConvertFunc(String chString) {
        return traditionalTextConvertFunc(chString, true);
    }

    private static String standardChNumberConvert(String chNumberString) {
        List<String> chNumberStringList = new ArrayList<String>();
        for (char c : chNumberString.toCharArray()) {
            chNumberStringList.add(String.valueOf(c));
        }

        //大于2的长度字符串才有检测和补位的必要
        if (chNumberStringList.size() > 2) {
            //十位补一：
            int tenNumberIndex = chNumberStringList.indexOf("十");
            if (tenNumberIndex == 0) {
                chNumberStringList.add(tenNumberIndex, "一");
            } else if (tenNumberIndex != -1) {
                // 如果没有左边计数数字 插入1
                if (!CHINESE_PURE_NUMBER_LIST.contains(chNumberStringList.get(tenNumberIndex - 1))) {
                    chNumberStringList.add(tenNumberIndex, "一");
                }
            }

            //差位补零
            //逻辑 如果最后一个单位 不是十结尾 而是百以上 则数字后面补一个比最后一个出现的单位小一级的单位
            //从倒数第二位开始看,且必须是倒数第二位就是单位的才符合条件

            int lastCountingUnit = CHINESE_PURE_COUNTING_UNIT_LIST.indexOf(chNumberStringList.get(chNumberStringList.size() - 2));
            // 如果最末位的是百开头
            if (lastCountingUnit >= 1) {
                // 则字符串最后拼接一个比最后一个单位小一位的单位 例如四万三 变成四万三千

                // 如果最后一位结束的是亿 则补千万
                if (lastCountingUnit == 4) {
                    chNumberStringList.add("千");
                    chNumberStringList.add("万");
                } else {
                    chNumberStringList.add(CHINESE_PURE_COUNTING_UNIT_LIST.get(lastCountingUnit - 1));
                }
            }

        }
        //检查是否是 万三  千四点五这种表述 百三百四
        int perCountSwitch = 0;
        if (chNumberStringList.size() > 1) {
            if (chNumberStringList.get(0).equals("千") || chNumberStringList.get(0).equals("万") || chNumberStringList.get(0).equals("百")) {
                for (int i = 1; i < chNumberStringList.size(); i++) {
                    //其余位数都是纯数字 才能执行
                    if (CHINESE_PURE_NUMBER_LIST.contains(chNumberStringList.get(i))) {
                        perCountSwitch = 1;
                    } else {
                        perCountSwitch = 0;
                        //y有一个不是数字 直接退出循环
                        break;
                    }
                }
            }
        }
        if (perCountSwitch == 1) {
            chNumberStringList.add(1, "分");
            chNumberStringList.add(2, "之");
        }

        String ret = "";
        for (String s : chNumberStringList) {
            ret += s;
        }

        return ret;
    }

    private static List<String> checkNumberSeg(List<String> chineseNumberList, String originText) {
        List<String> newChineseNumberList = new ArrayList<>();
        String tempPreText = "";
        String tempMixedString = "";
        int segLen = chineseNumberList.size();
        if (segLen > 0) {
            if (CHINESE_PER_COUNTING_SEG.contains(chineseNumberList.get(0).substring(0, Math.min(2, chineseNumberList.get(0).length())))) {
                tempPreText = chineseNumberList.get(0);
                newChineseNumberList.add(chineseNumberList.get(0).substring(2));
            } else {
                newChineseNumberList.add(chineseNumberList.get(0));
            }
            if (segLen > 1) {
                for (int i = 1; i < segLen; i++) {
                    if (CHINESE_PER_COUNTING_SEG.contains(chineseNumberList.get(i).substring(0, Math.min(2, chineseNumberList.get(i).length())))) {
                        tempMixedString = chineseNumberList.get(i - 1) + chineseNumberList.get(i);
                        if (originText.contains(tempMixedString)) {
                            if (!tempPreText.isEmpty()) {
                                if (CHINESE_PURE_COUNTING_UNIT_LIST.contains(String.valueOf(tempPreText.charAt(tempPreText.length() - 1)))) {
                                    newChineseNumberList.set(newChineseNumberList.size() - 1, newChineseNumberList.get(newChineseNumberList.size() - 1).substring(0, newChineseNumberList.get(newChineseNumberList.size() - 1).length() - 1));
                                    newChineseNumberList.add(String.valueOf(tempPreText.charAt(tempPreText.length() - 1)) + chineseNumberList.get(i));
                                } else {
                                    newChineseNumberList.add(chineseNumberList.get(i).substring(2));
                                }
                            } else {
                                if (!newChineseNumberList.isEmpty()) {
                                    newChineseNumberList.set(newChineseNumberList.size() - 1, tempMixedString);
                                } else {
                                    newChineseNumberList.add(tempMixedString);
                                }
                            }
                        } else {
                            newChineseNumberList.add(chineseNumberList.get(i).substring(2));
                        }
                        tempPreText = chineseNumberList.get(i);
                    } else {
                        newChineseNumberList.add(chineseNumberList.get(i));
                        tempPreText = "";
                    }
                }
            }
        }
        return newChineseNumberList;
    }

    private static List<String> checkSignSeg(List<String> chineseNumberList) {
        List<String> newChineseNumberList = new ArrayList<>();
        String tempSign = "";
        for (int i = 0; i < chineseNumberList.size(); i++) {
            String newChNumberString = tempSign + chineseNumberList.get(i);
            String lastString = newChNumberString.substring(newChNumberString.length() - 1);
            if (CHINESE_SIGN_LIST.contains(lastString)) {
                tempSign = lastString;
                newChNumberString = newChNumberString.substring(0, newChNumberString.length() - 1);
            } else {
                tempSign = "";
            }
            newChineseNumberList.add(newChNumberString);
        }
        return newChineseNumberList;
    }


    private static List<String> digitsToCHChars(List<String> mixedStringList) {
        List<String> resultList = new ArrayList<String>();
        for (String mixedString : mixedStringList) {
            if (mixedString.startsWith(".")) {
                mixedString = "0" + mixedString;
            }
            for (String key : digits_char_ch_dict.keySet()) {
                if (mixedString.contains(key)) {
                    mixedString = mixedString.replace(key, digits_char_ch_dict.get(key));

                    for (String k : CHINESE_PER_COUNTING_STRING_LIST) {
                        if (mixedString.contains(k)) {
                            String temp = k + mixedString.replace(k, "");
                            mixedString = temp;
                        }
                    }
                }
            }
            resultList.add(mixedString);
        }
        return resultList;
    }

    private static final Pattern takingChineseDigitsMixRERules = Pattern.compile("(?:(?:分之){0,1}(?:\\+|\\-){0,1}[正负]{0,1})"
            + "(?:(?:(?:\\d+(?:\\.\\d+){0,1}(?:[\\%\\‰\\‱]){0,1}|\\.\\d+(?:[\\%\\‰\\‱]){0,1}){0,1}"
            + "(?:(?:(?:[一二三四五六七八九十千万亿幺零百]+(?:点[一二三四五六七八九万亿幺零]+){0,1})|(?:点[一二三四五六七八九万亿幺零]+))))"
            + "|(?:(?:\\d+(?:\\.\\d+){0,1}(?:[\\%\\‰\\‱]){0,1}|\\.\\d+(?:[\\%\\‰\\‱]){0,1})"
            + "(?:(?:(?:[一二三四五六七八九十千万亿幺零百]+(?:点[一二三四五六七八九万亿幺零]+){0,1})|(?:点[一二三四五六七八九万亿幺零]+))){0,1}))");

    private static Map<String, Object> takeChineseNumberFromString(String chText, boolean percentConvert, boolean traditionalConvert, boolean digitsNumberSwitch, boolean verbose) {
//        if (digitsNumberSwitch) {
//            return takeDigitsNumberFromString(chText, percentConvert);
//        }

        String convertedCHString = traditionalTextConvertFunc(chText, traditionalConvert);

        Matcher matcher = takingChineseDigitsMixRERules.matcher(convertedCHString);
        List<String> CHNumberStringListTemp = new ArrayList<>();
        while (matcher.find()) {
            CHNumberStringListTemp.add(matcher.group());
        }
        CHNumberStringListTemp = checkNumberSeg(CHNumberStringListTemp, convertedCHString);
        CHNumberStringListTemp = checkSignSeg(CHNumberStringListTemp);
        List<String> OriginCHNumberTake = new ArrayList<>(CHNumberStringListTemp);

        CHNumberStringListTemp = digitsToCHChars(CHNumberStringListTemp);

        List<String> CHNumberStringList = new ArrayList<>();
        List<String> OriginCHNumberForOutput = new ArrayList<>();
        for (String tempText : CHNumberStringListTemp) {
            if (checkChineseNumberReasonable(tempText)) {
                CHNumberStringList.add(tempText);
                OriginCHNumberForOutput.add(OriginCHNumberTake.get(CHNumberStringListTemp.indexOf(tempText)));
            }
        }

        CHNumberStringListTemp = new ArrayList<>();
        for (String chNumberString : CHNumberStringList) {
            CHNumberStringListTemp.add(standardChNumberConvert(chNumberString));
        }

        List<String> digitsStringList = new ArrayList<>();
        String replacedText = convertedCHString;
        List<String> errorCharList = new ArrayList<>();
        List<String> errorMsgList = new ArrayList<>();
        if (!CHNumberStringListTemp.isEmpty()) {
            for(int kk=0;kk<CHNumberStringListTemp.size();kk++){
                digitsStringList.add(chineseToDigitsHighTolerance(CHNumberStringListTemp.get(kk),percentConvert,verbose,errorCharList,errorMsgList));
            }
            List<myTuple> tupleToReplace = new ArrayList<>();
            for(int t=0;t<min(Arrays.asList(OriginCHNumberForOutput.size(),digitsStringList.size(),OriginCHNumberForOutput.size()));t++){
                String d = OriginCHNumberForOutput.get(t);
                String c = digitsStringList.get(t);
                int i = OriginCHNumberForOutput.get(t).length();
                if(!c.equals("")){
                    tupleToReplace.add(new myTuple(d,c,i));
                }
            }

            Comparator<myTuple> lengthComparator = Comparator.comparing(mt -> mt.third);
            tupleToReplace.sort(lengthComparator.reversed());
            for(myTuple mt:tupleToReplace){
                replacedText = replacedText.replace(mt.first, mt.second);
            }
        }

        String finalReplacedText = replacedText;
        Map<String, Object> ret = new HashMap<>() {{
            put("inputText", chText);
            put("replacedText", finalReplacedText);
            put("CHNumberStringList",OriginCHNumberForOutput);
            put("digitsStringList",digitsStringList);
        }};

        return ret;
    }

    private static Map<String, Object> takeChineseNumberFromString(String chText){
        return takeChineseNumberFromString(chText,true,true,false,false);
    }

    private static Map<String, Object> takeChineseNumberFromString(String chText, boolean percentConvert){
        return takeChineseNumberFromString(chText,percentConvert,true,false,false);
    }


    public static void main(String[] args) {
        // Press Ctrl+. with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.

        System.out.printf(String.valueOf(takeChineseNumberFromString("20万 111万")));
        System.out.println(takeChineseNumberFromString("3.1千万"));

        System.out.println(takeChineseNumberFromString("拾"));

        System.out.println(takeChineseNumberFromString("12.55万"));
        System.out.println(takeChineseNumberFromString("一兆韦德二〇二三哦哦一百03"));


        System.out.println(takeChineseNumberFromString("三零万二零千拉阿拉啦啦30万20千嚯嚯或百四嚯嚯嚯四百三十二分之2345啦啦啦啦",false));
        System.out.println(takeChineseNumberFromString("百分之5负千分之15"));
        System.out.println(takeChineseNumberFromString("啊啦啦啦300十万你好我20万.3%万你好啊300咯咯咯-.34%啦啦啦300万"));
        System.out.println(takeChineseNumberFromString("百分之四百三十二万分之四三千分之五今天天气不错三百四十点零零三四"));

        System.out.println(takeChineseNumberFromString("234%lalalal-%nidaye+2.34%",true));
        System.out.println(takeChineseNumberFromString("aaaa.3%万"));

        System.out.println(takeChineseNumberFromString("十分之一"));
        System.out.println(takeChineseNumberFromString("四分之三啦啦五百分之二",false));
        System.out.println(takeChineseNumberFromString("4分之3负五分之6咿呀呀 四百分之16ooo千千万万"));
        System.out.println(takeChineseNumberFromString("百分之五1234%"));
        System.out.println(takeChineseNumberFromString("五百分之一",false));


        System.out.println(takeChineseNumberFromString("百分之四百三十二万分之四三千分之五"));


        System.out.println(takeChineseNumberFromString("四千三"));
        System.out.println(takeChineseNumberFromString("伍亿柒仟万拾柒今天天气不错百分之三亿二百万五啦啦啦啦负百分之点二八你好啊三万二"));
        System.out.println(takeChineseNumberFromString("llalala万三威风威风千四五"));
        System.out.println(takeChineseNumberFromString("哥两好"));
        System.out.println(takeChineseNumberFromString("伍亿柒仟万拾柒百分之"));
        System.out.println(takeChineseNumberFromString("负百分之点二八你好啊百分之三五是不是点五零百分之负六十五点二八"));

        System.out.println(takeChineseNumberFromString("50万"));

        // Press Ctrl+F5 or click the green arrow button in the gutter to run the code.
    }
}