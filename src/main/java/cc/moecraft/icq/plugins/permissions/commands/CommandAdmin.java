package cc.moecraft.icq.plugins.permissions.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.permissions.Permissions;
import cc.moecraft.icq.plugins.permissions.utils.CommandUtils;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/07/25 创建!
 * Created by Hykilpikonna on 2018/07/25!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandAdmin implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User user, String command, ArrayList<String> args)
    {
        if (!Permissions.verifyAndSendText(event, user.getId(), "permissionmanager.op")) return null;
        if (args.size() != 2) return help(command);

        Long qq = CommandUtils.getQqWithStringAndSendMessage(event, args.get(1));
        if (qq == null) return null;

        switch (args.get(0).toLowerCase())
        {
            case "set":
            {
                if (Permissions.isAdmin(qq)) return qq + "已经是管理员, 无法设置.";
                Permissions.setAdmin(qq, true);
                return String.format("成功设置%s为管理员!", qq);
            }
            case "remove":
            {
                if (!Permissions.isAdmin(qq)) return qq + "不是管理员, 无法移除.";
                Permissions.setAdmin(qq, false);
                return String.format("成功移除%s的管理员权限!", qq);
            }
            default: return help(command);
        }
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("op", "admin");
    }

    private String help(String command)
    {
        return String.format("!%s [set|remove] [qq|at]", command);
    }
}
