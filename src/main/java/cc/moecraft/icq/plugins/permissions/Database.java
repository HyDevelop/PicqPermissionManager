package cc.moecraft.icq.plugins.permissions;

import cc.moecraft.yaml.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/07/17 创建!
 * Created by Hykilpikonna on 2018/07/17!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class Database extends Config
{
    private Map<String, PermissionGroup> loadedGroups;
    private PermissionGroup defaultGroup;

    private static final String GROUPS_PREFIX = "Groups.";
    private static final String USERS_PREFIX = "Users.";

    public Database()
    {
        super("data", "permissions", "yml", false, true, true);
    }

    /**
     * 预加载, 可以重载
     */
    @Override
    public void readConfig()
    {
    }

    @Override
    public void writeDefaultConfig()
    {
    }
}
