package cc.moecraft.icq.plugins.permissions;

import cc.moecraft.yaml.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/07/17 创建!
 * Created by Hykilpikonna on 2018/07/17!
 * Github: https://github.com/hykilpikonna
 * Meow!
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
     * 向配置写入一个权限组
     * @param group 权限组
     */
    public void setGroup(PermissionGroup group)
    {
        String currentPrefix = GROUPS_PREFIX + group.getGroupName() + ".";

        set(currentPrefix + "ContainingGroups", groupListToNameList(group.getContainings()));
        set(currentPrefix + "Permissions", permissionListToNameList(group.getThisGroupPermissions()));

        save();
    }

    /**
     * 向配置移除一个权限组
     * @param group 权限组
     */
    public void removeGroup(PermissionGroup group)
    {
        String currentPrefix = GROUPS_PREFIX + group.getGroupName() + ".";

        set(GROUPS_PREFIX + group.getGroupName(), null);
        set(currentPrefix + "ContainingGroups", null);
        set(currentPrefix + "Permissions", null);

        save();
    }

    /**
     * 从配置获取一个权限组
     * @param name 权限组名
     * @return 权限组
     */
    public PermissionGroup getGroup(String name)
    {
        if (loadedGroups.containsKey(name)) return loadedGroups.get(name);

        String currentPrefix = GROUPS_PREFIX + name + ".";

        if (!contains(currentPrefix + "Permissions") && !contains(currentPrefix + "ContainingGroups")) return null;

        PermissionGroup result = new PermissionGroup(name);

        ArrayList<String> containingGroupNames = (ArrayList<String>) getStringList(currentPrefix + "ContainingGroups");
        containingGroupNames.forEach(groupName -> result.getContainings().add(getGroup(groupName))); // 递归, 配置不好可能出现无限递归

        ArrayList<String> permissionNames = (ArrayList<String>) getStringList(currentPrefix + "Permissions");
        permissionNames.forEach(permissionName -> result.getThisGroupPermissions().add(new Permission(permissionName)));

        return result;
    }

    /**
     * 权限组列表转换到权限组名字列表
     * @param groups 权限组列表
     * @return 权限组名字列表
     */
    public static ArrayList<String> groupListToNameList(ArrayList<PermissionGroup> groups)
    {
        ArrayList<String> result = new ArrayList<>();

        groups.forEach(permissionGroup -> result.add(permissionGroup.getGroupName()));

        return result;
    }

    /**
     * 权限列表转换到权限名字列表
     * @param permissions 权限列表
     * @return 权限名字列表
     */
    public static ArrayList<String> permissionListToNameList(ArrayList<Permission> permissions)
    {
        ArrayList<String> result = new ArrayList<>();

        permissions.forEach(permissionGroup -> result.add(permissionGroup.toString()));

        return result;
    }

    /**
     * 获取用户权限组
     * @param qq 用户QQ号
     * @return 权限组
     */
    public ArrayList<PermissionGroup> getUserPermissionGroups(long qq)
    {
        String currentPrefix = USERS_PREFIX + qq;

        ArrayList<String> permissionGroupNames = (ArrayList<String>) getStringList(currentPrefix);
        ArrayList<PermissionGroup> result = new ArrayList<>();

        permissionGroupNames.forEach(name -> result.add(getGroup(name)));
        return result;
    }

    /**
     * 保存用户权限组
     * @param qq 用户QQ号
     * @param permissionGroups 用户所有的权限组
     */
    public void setUserPermissionGroups(long qq, ArrayList<PermissionGroup> permissionGroups)
    {
        String currentPrefix = USERS_PREFIX + qq;

        set(currentPrefix, groupListToNameList(permissionGroups));

        save();
    }

    /**
     * 预加载, 可以重载
     */
    @Override
    public void readConfig()
    {
        loadedGroups = new HashMap<>();

        getKeys(GROUPS_PREFIX.replace(".", "")).forEach(key -> loadedGroups.put(key, getGroup(key)));

        defaultGroup = getGroup(getString("DefaultGroup"));
    }

    @Override
    public void writeDefaultConfig()
    {
        // 默认用户权限分3组:
        //  default:    irc.user.regular.*
        //  VIP:        irc.user.vip.* ( 继承 default )
        //  Supporter:  irc.user.sup.* ( 继承 vip )

        // 默认管理权限分2组:
        //  Admin:      irc.admin.*
        //  Manager:    irc.admin.managing.*

        PermissionGroup defaultGroup = new PermissionGroup("default").setPermissions("irc.user.regular.*");
        PermissionGroup vip = new PermissionGroup("vip").setPermissions("irc.user.vip.*").setContainings(defaultGroup);
        PermissionGroup supporter = new PermissionGroup("supporter").setPermissions("irc.user.sup.*").setContainings(vip);

        PermissionGroup admin = new PermissionGroup("admin").setPermissions("irc.admin.*");
        PermissionGroup manager = new PermissionGroup("manager").setPermissions("irc.admin.managing.*");

        setDefaultGroup(defaultGroup);

        setGroup(defaultGroup);
        setGroup(vip);
        setGroup(supporter);
        setGroup(admin);
        setGroup(manager);

        save();
    }

    public PermissionGroup getDefaultGroup()
    {
        return defaultGroup;
    }

    public void setDefaultGroup(PermissionGroup defaultGroup)
    {
        this.defaultGroup = defaultGroup;

        set("DefaultGroup", defaultGroup.getGroupName());

        save();
    }
}
