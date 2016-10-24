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

    public static String validateLocalImagePath(String imgPath)
    {

        if(isValidString(imgPath))
        {

            if(imgPath.startsWith("file:///"))
            {
                return imgPath.substring(7);
            } else
            {
                return imgPath;
            }
        } else
        {
            return "";
        }
    }
}
