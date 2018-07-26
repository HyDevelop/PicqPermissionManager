package cc.moecraft.icq.plugins.permissions.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.permissions.*;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/07/26 创建!
 * Created by Hykilpikonna on 2018/07/26!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandGroups implements EverywhereCommand
{
    /**
     * 用法:
     *  管理权限组:
     *
     *  !grp list                          查看权限组列表
     *  !grp info [名字]                   查询权限组 2
     *  !grp create [名字]                 创建权限组 2
     *  !grp remove [名字]                 移除权限组 2
     *  !grp perm add [名字] [权限]        添加权限   4
     *  !grp perm remove [名字] [权限]     移除权限   4
     *  !grp group add [名字] [权限组]     添加继承   4
     *  !grp group remove [名字] [权限组]  移除继承   4
     *
     * @param event 消息事件
     * @param user 发送者
     * @param command 指令
     * @param args 参数
     * @return 回复
     */
    @Override
    public String run(EventMessage event, User user, String command, ArrayList<String> args)
    {
        if (!Permissions.verifyAndSendText(event, user.getId(), "permissionmanager.groups")) return null;

        if (args.size() == 1 && args.get(0).equalsIgnoreCase("list"))
        {
            return "已有的权限组: " + Main.getDatabase().getKeys("Groups");
        }

        if (args.size() == 2)  // 创建移除查询权限组
        {
            if (args.get(0).equals("create"))
            {
                if (Main.getDatabase().getGroup(args.get(1)) == null)
                {
                    PermissionGroup group = new PermissionGroup(args.get(1));
                    Main.getDatabase().setGroup(group);

                    return "权限组" + args.get(1) + "已成功创建";
                }
                else
                {
                    return "无法创建, 权限组" + args.get(1) + "已存在";
                }
            }
        }

        return help(command);
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("permissiongroups", "groups", "grp");
    }

    private String help(String command)
    {
        return String.format("!%s [info|create|remove] <权限组名>", command) + "\n" +
                String.format("!%s [perm|group] [add|remove] <权限组名> [权限|子权限组]", command);
    }
}
