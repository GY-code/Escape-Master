package com.example.applicationtest001.Tool;

/**
 * 检测密码强度
 *
 * @author venshine
 */
public class CheckStrength {
    /**
     * NUM 数字
     * SMALL_LETTER 小写字母
     * CAPITAL_LETTER 大写字母
     * OTHER_CHAR 特殊字符
     */
    private static final int NUM = 1;
    private static final int SMALL_LETTER = 2;
    private static final int CAPITAL_LETTER = 3;
    private static final int OTHER_CHAR = 4;
    /**
     * 简单的密码字典
     */
    private final static String[] DICTIONARY = {"password", "abc123", "iloveyou", "adobe123", "123123", "sunshine",
            "1314520", "a1b2c3", "123qwe", "aaa111", "qweasd", "admin", "passwd"};
    /**
     *检查字符类型，包括num、大写字母、小写字母和其他字符。
     *
     * @param c
     * @return
     */
    private static int checkCharacterType(char c) {
        if (c >= 48 && c <= 57) {
            return NUM;
        }
        if (c >= 65 && c <= 90) {
            return CAPITAL_LETTER;
        }
        if (c >= 97 && c <= 122) {
            return SMALL_LETTER;
        }
        return OTHER_CHAR;
    }
    /**
     * 按不同类型计算密码的数量
     *
     * @param passwd
     * @param type
     * @return
     */
    private static int countLetter(String passwd, int type) {
        int count = 0;
        if (null != passwd && passwd.length() > 0) {
            for (char c : passwd.toCharArray()) {
                if (checkCharacterType(c) == type) {
                    count++;
                }
            }
        }
        return count;
    }
    /**
     * 检查密码的强度
     *
     * @param passwd
     * @return strength level
     */
    public static int checkPasswordStrength(String passwd) {
        int len = passwd.length();
        int level = 0;
        // 增加点
        //判断密码是否含有数字有level++
        if (countLetter(passwd, NUM) > 0) {
            level++;
        }
        //判断密码是否含有小写字母有level++
        if (countLetter(passwd, SMALL_LETTER) > 0) {
            level++;
        }
        //判断密码是否还有大写字母有level++
        if (len > 4 && countLetter(passwd, CAPITAL_LETTER) > 0) {
            level++;
        }
        //判断密码是否还有特殊字符有level++
        if (len > 6 && countLetter(passwd, OTHER_CHAR) > 0) {
            level++;
        }
        //密码长度大于4并且2种类型组合
        if (len > 4 && countLetter(passwd, NUM) > 0 && countLetter(passwd, SMALL_LETTER) > 0
                || countLetter(passwd, NUM) > 0 && countLetter(passwd, CAPITAL_LETTER) > 0
                || countLetter(passwd, NUM) > 0 && countLetter(passwd, OTHER_CHAR) > 0
                || countLetter(passwd, SMALL_LETTER) > 0 && countLetter(passwd, CAPITAL_LETTER) > 0
                || countLetter(passwd, SMALL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0
                || countLetter(passwd, CAPITAL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0) {
            level++;
        }
        //密码长度大于6并且3中类型组合
        if (len > 6 && countLetter(passwd, NUM) > 0 && countLetter(passwd, SMALL_LETTER) > 0
                && countLetter(passwd, CAPITAL_LETTER) > 0 || countLetter(passwd, NUM) > 0
                && countLetter(passwd, SMALL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0
                || countLetter(passwd, NUM) > 0 && countLetter(passwd, CAPITAL_LETTER) > 0
                && countLetter(passwd, OTHER_CHAR) > 0 || countLetter(passwd, SMALL_LETTER) > 0
                && countLetter(passwd, CAPITAL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0) {
            level++;
        }
        //密码长度大于8并且4种类型组合
        if (len > 8 && countLetter(passwd, NUM) > 0 && countLetter(passwd, SMALL_LETTER) > 0
                && countLetter(passwd, CAPITAL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0) {
            level++;
        }
        //密码长度大于6并且2种类型组合每种类型长度大于等于3或者2
        if (len > 6 && countLetter(passwd, NUM) >= 3 && countLetter(passwd, SMALL_LETTER) >= 3
                || countLetter(passwd, NUM) >= 3 && countLetter(passwd, CAPITAL_LETTER) >= 3
                || countLetter(passwd, NUM) >= 3 && countLetter(passwd, OTHER_CHAR) >= 2
                || countLetter(passwd, SMALL_LETTER) >= 3 && countLetter(passwd, CAPITAL_LETTER) >= 3
                || countLetter(passwd, SMALL_LETTER) >= 3 && countLetter(passwd, OTHER_CHAR) >= 2
                || countLetter(passwd, CAPITAL_LETTER) >= 3 && countLetter(passwd, OTHER_CHAR) >= 2) {
            level++;
        }
        //密码长度大于8并且3种类型组合每种类型长度大于等于3或者2
        if (len > 8 && countLetter(passwd, NUM) >= 2 && countLetter(passwd, SMALL_LETTER) >= 2
                && countLetter(passwd, CAPITAL_LETTER) >= 2 || countLetter(passwd, NUM) >= 2
                && countLetter(passwd, SMALL_LETTER) >= 2 && countLetter(passwd, OTHER_CHAR) >= 2
                || countLetter(passwd, NUM) >= 2 && countLetter(passwd, CAPITAL_LETTER) >= 2
                && countLetter(passwd, OTHER_CHAR) >= 2 || countLetter(passwd, SMALL_LETTER) >= 2
                && countLetter(passwd, CAPITAL_LETTER) >= 2 && countLetter(passwd, OTHER_CHAR) >= 2) {
            level++;
        }
        //密码长度大于10并且4种类型组合每种类型长度大于等于2
        if (len > 10 && countLetter(passwd, NUM) >= 2 && countLetter(passwd, SMALL_LETTER) >= 2
                && countLetter(passwd, CAPITAL_LETTER) >= 2 && countLetter(passwd, OTHER_CHAR) >= 2) {
            level++;
        }
        //特殊字符>=3 level++;
        if (countLetter(passwd, OTHER_CHAR) >= 3) {
            level++;
        }
        //特殊字符>=6 level++;
        if (countLetter(passwd, OTHER_CHAR) >= 6) {
            level++;
        }
        //长度>12 >16 level++
        if (len > 12) {
            level++;
            if (len >= 16) {
                level++;
            }
        }
        // 减少点
        if ("abcdefghijklmnopqrstuvwxyz".indexOf(passwd) > 0 || "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(passwd) > 0) {
            level--;
        }
        if ("qwertyuiop".indexOf(passwd) > 0 || "asdfghjkl".indexOf(passwd) > 0 || "zxcvbnm".indexOf(passwd) > 0) {
            level--;
        }
        if (StringUtils.isNumeric(passwd) && ("01234567890".indexOf(passwd) > 0 || "09876543210".indexOf(passwd) > 0)) {
            level--;
        }
        if (countLetter(passwd, NUM) == len || countLetter(passwd, SMALL_LETTER) == len
                || countLetter(passwd, CAPITAL_LETTER) == len) {
            level--;
        }
        if (len % 2 == 0) { // aaabbb
            String part1 = passwd.substring(0, len / 2);
            String part2 = passwd.substring(len / 2);
            if (part1.equals(part2)) {
                level--;
            }
            if (StringUtils.isCharEqual(part1) && StringUtils.isCharEqual(part2)) {
                level--;
            }
        }
        if (len % 3 == 0) { // ababab
            String part1 = passwd.substring(0, len / 3);
            String part2 = passwd.substring(len / 3, len / 3 * 2);
            String part3 = passwd.substring(len / 3 * 2);
            if (part1.equals(part2) && part2.equals(part3)) {
                level--;
            }
        }
        if (StringUtils.isNumeric(passwd) && len >= 6) { // 19881010 or 881010
            int year = 0;
            if (len == 8 || len == 6) {
                year = Integer.parseInt(passwd.substring(0, len - 4));
            }
            int size = StringUtils.sizeOfInt(year);
            int month = Integer.parseInt(passwd.substring(size, size + 2));
            int day = Integer.parseInt(passwd.substring(size + 2, len));
            if (year >= 1950 && year < 2050 && month >= 1 && month <= 12 && day >= 1 && day <= 31) {
                level--;
            }
        }
        if (null != DICTIONARY && DICTIONARY.length > 0) {// dictionary
            for (int i = 0; i < DICTIONARY.length; i++) {
                if (passwd.equals(DICTIONARY[i]) || DICTIONARY[i].indexOf(passwd) >= 0) {
                    level--;
                    break;
                }
            }
        }
        if (len <= 6) {
            level--;
            if (len <= 4) {
                level--;
                if (len <= 3) {
                    level = 0;
                }
            }
        }
        if (StringUtils.isCharEqual(passwd)) {
            level = 0;
        }
        if (level < 0) {
            level = 0;
        }
        return level;
    }

}