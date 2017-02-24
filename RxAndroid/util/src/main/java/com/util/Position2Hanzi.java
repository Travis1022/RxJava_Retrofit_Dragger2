package com.util;

/**
 * 位置转汉字：主要用于recyclerView等等
 * Created by Travis1022 on 2017/02/23.
 */
public class Position2Hanzi {
    private static String[] hanZiArray = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};

    public static String getHanziFromPosition(int position) {
        //默认position从0开始
        position += 1;
        StringBuffer hanzi = new StringBuffer();
        if (position > 999 || position < 1)
            return "";
        if (position > 99) {
            hanzi.append(hanZiArray[position / 100] + "百");
            position %= 100;
        }
        if (position > 9) {
            if (position < 20)
                hanzi.append("十");
            else
                hanzi.append(hanZiArray[position / 10] + "十");
            position %= 10;
        } else if (position <= 9 && position > 0 && hanzi.length() > 0) hanzi.append("零");
        if (position != 0)
            hanzi.append(hanZiArray[position % 10]);
        return hanzi.toString();
    }
}
