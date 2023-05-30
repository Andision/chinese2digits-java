import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    private static String []CHINESE_CHAR_LIST = new String[]{"幺","零", "一", "二", "两", "三", "四", "五", "六", "七", "八", "九", "十", "百", "千", "万", "亿"};
    private static String []CHINESE_SIGN_LIST = new String[]  {"负","正","-","+"};
    private static String [] CHINESE_CONNECTING_SIGN_LIST = new String[]  {".","点","·"};
    private static String [] CHINESE_PER_COUNTING_STRING_LIST = new String[]  {"百分之","千分之","万分之"};
    private static String CHINESE_PER_COUNTING_SEG = "分之";
    private static String [] CHINESE_PURE_NUMBER_LIST = new String[]  {"幺", "一", "二", "两", "三", "四", "五", "六", "七", "八", "九", "十","零"};

    private static Map<String,String>CHINESE_SIGN_DICT = new HashMap<String, String>(){{
        put("负","-");
        put("正","+");
        put("-","-");
        put("+","+");

    }};

    private static Map<String,String>CHINESE_PER_COUNTING_DICT = new HashMap<String, String>(){{
        put("百分之","%");
        put("千分之","‰");
        put("万分之","‱");
    }};

    private static Map<String,String>CHINESE_CONNECTING_SIGN_DICT = new HashMap<String, String>(){{
        put(".",".");
        put("点",".");
        put("·",".");

    }};
    private static Map<String,Integer>CHINESE_COUNTING_STRING = new HashMap<String, Integer>(){{
        put("十",10);
        put("百",100);
        put("千",1000);
        put("万",10000);
        put("亿",100000000);
    }};

private static String [] CHINESE_PURE_COUNTING_UNIT_LIST = new String[]  {"十","百","千","万","亿"};

    private static Map<String,String> TRADITIONAL_CONVERT_DICT = new HashMap<String, String>(){{
        put("壹","一");
        put("贰","二");
        put("叁","三");
        put("肆","四");
        put("伍","五");
        put("陆","六");
        put("柒","七");
        put("捌","八");
        put("玖","九");
        put("〇","零");
    }};

    private static Map<String,String> SPECIAL_TRADITIONAL_COUNTING_UNIT_CHAR_DICT = new HashMap<String, String>(){{
        put("拾","十");
        put("佰","百");
        put("仟","千");
        put("萬","万");
        put("億","亿");
    }};

    private static Map<String,String> SPECIAL_NUMBER_CHAR_DICT = new HashMap<String, String>(){{
        put("两","二");
        put("俩","二");
    }};

    private static Map<String,Integer> common_used_ch_numerals = new HashMap<String, Integer>(){{
        put("幺",1);
        put("零",0);
        put("一",1);
        put("二",2);
        put("两",2);
        put("三",3);
        put("四",4);
        put("五",5);
        put("六",6);
        put("七",7);
        put("八",8);
        put("九",9);
        put("十",10);
        put("百",100);
        put("千",1000);
        put("万",10000);
        put("亿",100000000);
    }};

    private static Map<String,String> digits_char_ch_dict = new HashMap<String, String>(){{
        put("0","零");
        put("1","一");
        put("2","二");
        put("3","三");
        put("4","四");
        put("5","五");
        put("6","六");
        put("7","七");
        put("8","八");
        put("9","九");
        put("%","百分之");
        put("‰","千分之");
        put("‱","万分之");
        put(".","点");
    }};


//    takingChineseDigitsMixRERules = re.compile(r"(?:(?:分之){0,1}(?:\+|\-){0,1}[正负]{0,1})";
//    r"(?:(?:(?:\d+(?:\.\d+){0,1}(?:[\%\‰\‱]){0,1}|\.\d+(?:[\%\‰\‱]){0,1}){0,1}";
//    r"(?:(?:(?:[一二三四五六七八九十千万亿幺零百]+(?:点[一二三四五六七八九万亿幺零]+){0,1})|(?:点[一二三四五六七八九万亿幺零]+))))";
//    r"|(?:(?:\d+(?:\.\d+){0,1}(?:[\%\‰\‱]){0,1}|\.\d+(?:[\%\‰\‱]){0,1})";
//    r"(?:(?:(?:[一二三四五六七八九十千万亿幺零百]+(?:点[一二三四五六七八九万亿幺零]+){0,1})|(?:点[一二三四五六七八九万亿幺零]+))){0,1}))");
//
//    PURE_DIGITS_RE = re.compile("[0-9]");




    private static String [] DIGITS_CHAR_LIST = new String[]  {"0","1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String [] DIGITS_SIGN_LIST = new String[]  {"-","+"};
    private static String [] DIGITS_CONNECTING_SIGN_LIST = new String[]  {"."};
    private static String [] DIGITS_PER_COUNTING_STRING_LIST = new String[]  {"%","‰","‱"};
//    takingDigitsRERule = re.compile(r"(?:(?:\+|\-){0,1}\d+(?:\.\d+){0,1}(?:[\%\‰\‱]){0,1}|(?:\+|\-){0,1}\.\d+(?:[\%\‰\‱]){0,1})");

    private static int max(List<Integer> list){
        int ret = list.get(0);
        for(int i=0;i< list.size();i++){
            if(ret>list.get(i)){
                ret=list.get(i);
            }
        }
        return ret;
    }
    private static String coreCHToDigits(String chineseChars) {
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

        if (total == 0) {
            if (countingUnit > 10) {
                return String.valueOf(countingUnit);
            } else {
                if (!tempVal.equals("")) {
                    return tempVal;
                } else {
                    return String.valueOf(total);
                }
            }
        } else {
            return String.valueOf(total);
        }
    }










    public static void main(String[] args) {
        // Press Ctrl+. with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.

        System.out.printf(coreCHToDigits("六十五"));

        // Press Ctrl+F5 or click the green arrow button in the gutter to run the code.
    }
}