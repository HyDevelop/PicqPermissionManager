package cc.moecraft.icq.plugins.permissions;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.event.events.message.EventMessage;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/04/22 创建!
 * Created by Hykilpikonna on 2018/04/22!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 */
public class Permissions
{
    /**
     * 判断是否有权限
     * @param qq 用户QQ号
     * @param permission 权限
     * @return 是否有权限
     */
    public static boolean hasPermission(long qq, String permission)
    {
        if (isAdmin(qq)) return true;
        if (Main.getDatabase().getDefaultGroup().hasPermission(permission)) return true;
        for (Permission eachPermission : getAllPermissions(qq))
            if (eachPermission.hasPermission(permission)) return true;

        return false;
    }

    /**
     * 发送没有权限消息
     * @param event 消息事件
     * @param permission 权限
     */
    public static void sendNoPermissionText(EventMessage event, String permission)
    {
        event.respond("操作失败, 缺少权限: " + permission);
    }

    /**
     * 获取用户所有权限
     * @param qq 用户QQ号
     * @return 所有权限
     */
    public static ArrayList<Permission> getAllPermissions(long qq)
    {
        ArrayList<Permission> result = new ArrayList<>();

        Main.getDatabase().getUserPermissionGroups(qq).forEach(group -> result.addAll(group.getAllPermissions()));

        return result;
    }

    /**
     * 判断一个用户是不是管理员
     * @param qq 用户QQ号
     * @return 是不是管理员
     */
    public static boolean isAdmin(long qq)
    {
        return Main.getInstance().getAdmins().contains(qq);
    }

    /**
     * 把一个用户设置为管理员
     * @param qq 用户QQ号
     * @param admin 是否是管理员
     */
    public static void setAdmin(long qq, boolean admin)
    {
        ArrayList<Long> admins = Main.getInstance().getAdmins();

        if (admin) admins.add(qq);
        else admins.remove(qq);

        Main.getInstance().setAdmins(admins);
    }

    public static Database getDatabase()
    {
        return Main.getDatabase();
    }
}
