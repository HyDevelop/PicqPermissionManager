##### [PicqBotX 插件管理器](https://github.com/HyDevelop/PicqBotX-PluginManager) > [插件列表](https://github.com/HyDevelop/PicqBotX-PluginManager/blob/master/markdown/plugin-list.md) > PicqPermissionManager权限管理插件

<br>

#### 指令:

```
!op set [qq|@]            将一个QQ号设为管理员
!op remove [qq|@]         将一个QQ号移除管理员

!grp list                 列出现有权限组
!grp info [name]          查询权限组信息
!grp create [name]        创建权限组
!grp remove [name]        移除权限组
!grp perm add [name] [perm]     给一个组添加权限
!grp perm remove [name] [perm]  从一个组移除权限
!grp group add [name] [name]    给一个组添加子权限组
!grp group remove [name] [name] 从一个组移除子权限组

!usp info [qq|@]          查询用户权限信息
!usp add [qq|@] [name]    把用户加入权限组
!usp remove [qq|@] [name] 把用户从权限组移除
```

#### 调用:

执行指令的时候判断发送指令的用户有没有权限, 如果没有权限就发送缺少权限的消息:

```java
if (!Permissions.verifyAndSendText(事件, QQ号, "权限节点")) return null;
```

例子:

```java
if (!Permissions.verifyAndSendText(event, user.getId(), "permissionmanager.usp")) return null;
```
