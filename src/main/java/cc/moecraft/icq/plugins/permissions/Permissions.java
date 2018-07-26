package cc.moecraft.icq.plugins.permissions;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.event.events.message.EventMessage;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/04/22 创建!
 * Created by Hykilpikonna on 2018/04/22!
 * Github: https://github.com/hykilpikonna
 * Meow!
 */
public class Permissions
{
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
}
