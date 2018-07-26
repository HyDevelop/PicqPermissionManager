package cc.moecraft.icq.plugins.permissions.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.plugins.permissions.Database;
import cc.moecraft.icq.plugins.permissions.Main;
import cc.moecraft.icq.plugins.permissions.PermissionGroup;
import cc.moecraft.icq.plugins.permissions.Permissions;
import cc.moecraft.icq.plugins.permissions.utils.CommandUtils;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 此类由 Hykilpikonna 在 2018/07/26 创建!
 * Created by Hykilpikonna on 2018/07/26!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandUser implements EverywhereCommand
{
    /**
     * 用法:
     *  管理用户权限组:
     *
     *  !usr info [用户名]              查询用户权限信息
     *  !usr add [用户名] [权限组]      把用户加入权限组
     *  !usr remove [用户名] [权限组]   把用户从权限组中移除
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
        if (!Permissions.verifyAndSendText(event, user.getId(), "permissionmanager.usp")) return null;

        if (args.size() < 2) return help(command);

        Long qq = CommandUtils.getQqWithStringAndSendMessage(event, args.get(1));
        if (qq == null) return null;


        return help(command);
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("userpermission", "usp");
    }

    private String help(String command)
    {
        return String.format("!%s [info] <用户ID>", command) + "\n" +
                String.format("!%s [add|remove] <用户ID> <权限组名>", command);
    }
}
