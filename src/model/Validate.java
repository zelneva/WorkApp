package model;

public class Validate {

    public static boolean number(String str) {
        Boolean flag = false;
        String regex = "^[0-9]++$";

        if (!str.equals("") && str != null && (str.matches(regex))) {
            flag = true;
        }
        return flag;
    }


    public static boolean oneWord(String str) {
        Boolean flag = false;
        String regex = "^[а-яА-Я]++$"; // 1 слово

        if (!str.equals("") && str != null && str.matches(regex)) {
            flag = true;
        }

        return flag;
    }

    public static boolean twoWords(String str) {
        Boolean flag = false;
        String regex = "^[а-яА-Я]++[ ]++[а-яА-Я]++$"; // 2 слова

        if (!str.equals("") && str != null && str.matches(regex)) {
            flag = true;
        }

        return flag;
    }

    public static boolean threeWords(String str) {
        Boolean flag = false;
        String regex = "^[а-яА-Я]++[ ]++[а-яА-Я]++[ ]++[а-яА-Я]++$"; // 3 слова

        if (!str.equals("") && str != null && str.matches(regex)) {
            flag = true;
        }

        return flag;
    }


    public static boolean category(String str) {
        Boolean flag = false;
        String regex = "^[0-9]++[ ]++[а-яА-Я]++[ ]++[а-яА-Я]++$"; // 1 цифра и два слова

        if (!str.equals("") && str != null && str.matches(regex)) {
            flag = true;
        }

        return flag;
    }


}
