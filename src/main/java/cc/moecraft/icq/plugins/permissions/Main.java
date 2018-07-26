package cc.moecraft.icq.plugins.permissions;

import cc.moecraft.icq.command.interfaces.IcqCommand;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.pluginmanager.plugin.IcqPlugin;
import cc.moecraft.icq.plugins.permissions.commands.CommandAdmin;
import cc.moecraft.icq.plugins.permissions.commands.CommandGroups;
import cc.moecraft.icq.plugins.permissions.commands.CommandUser;
import lombok.Getter;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/07/16 创建!
 * Created by Hykilpikonna on 2018/07/16!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class Main extends IcqPlugin
{
    @Getter
    private static Database database;

    @Override
    public void onEnable()
    {
        // 加载插件的时候会运行这个方法
        instance = this;

        database = new Database();
        getConfig().getString("");
    }

    @Override
    public void onDisable()
    {
        // 卸载插件的时候会运行这个方法
    }

    @Override
    public IcqCommand[] commands()
    {
        return new IcqCommand[]
                {
                        new CommandAdmin(),
                        new CommandGroups(),
                        new CommandUser()
                };
    }

    @Override
    public IcqListener[] listeners()
    {
        return new IcqListener[]
                {
                };
    }

    /**
     * 获取配置中的管理员用户ID列表
     * @return 管理员用户ID列表
     */
    public ArrayList<Long> getAdmins()
    {
        ArrayList<Long> result = new ArrayList<>();
        ArrayList<String> original = new ArrayList<>(getConfig().getStringList("Admins"));

        for (String id : original) result.add(Long.valueOf(id));
        return result;
    }

    /**
     * 设置管理员用户ID列表
     * @param admins 管理员用户ID列表
     */
    public void setAdmins(ArrayList<Long> admins)
    {
        getConfig().set("Admins", admins);
        getConfig().save();
    }

    private static Main instance;

    protected static Main getInstance()
    {
        return instance;
    }
}
