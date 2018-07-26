package cc.moecraft.icq.plugins.permissions.utils;

import cc.moecraft.icq.event.events.message.EventMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 此类由 Hykilpikonna 在 2018/07/26 创建!
 * Created by Hykilpikonna on 2018/07/26!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandUtils
{
    private static Pattern patternToFindAt = Pattern.compile("(?<=qq=).*(?=])");

    public static long getQqWithString(String original) throws AtAllException, NotNumberException
    {
        long qq;

        try
        {
            Matcher matcher = patternToFindAt.matcher(original);
            if (matcher.find())
            {
                String qqInString = matcher.group();

                if (qqInString.toLowerCase().equals("all")) throw new AtAllException();
                else qq = Long.parseLong(qqInString);
            }
            else qq = Long.parseLong(original);
        }
        catch (NumberFormatException e)
        {
            throw new NotNumberException();
        }

        return qq;
    }

    public static Long getQqWithStringAndSendMessage(EventMessage event, String original)
    {
        try
        {
            return getQqWithString(original);
        }
        catch (AtAllException e)
        {
            event.respond("不能@所有人.");
        }
        catch (NotNumberException e)
        {
            event.respond("用户必须是数字的QQ号或者@, 不能是 \"" + original + "\"");
        }

        return null;
    }

    public static class NotNumberException extends Exception {}
    public static class AtAllException extends Exception {}
}
