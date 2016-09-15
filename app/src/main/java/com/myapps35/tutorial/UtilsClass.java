package com.myapps35.tutorial;

/**
 * Created by span-tech on 8/24/2016.
 */
public class UtilsClass
{

    public static boolean isValidString(String input)
    {
        return input != null && input.trim().length() > 0 && !input.equalsIgnoreCase("null");
    }

    public static String validateString(String input)
    {
        if(isValidString(input))
        {
            return input;
        }

        return "";
    }

}
