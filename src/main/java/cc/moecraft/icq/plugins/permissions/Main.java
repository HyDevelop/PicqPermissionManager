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
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class Main extends IcqPlugin
{
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

    private static Main instance;

    protected static Main getInstance()
    {
        return instance;
    }
}
