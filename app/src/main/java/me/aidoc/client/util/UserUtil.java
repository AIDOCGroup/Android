package me.aidoc.client.util;

import java.util.List;

import me.aidoc.client.base.MyApplication;
import me.aidoc.client.entity.resp.ArchivesResp;
import me.aidoc.client.entity.resp.CurrentResp;
import me.aidoc.client.entity.resp.UserResp;

public class UserUtil {
    public static UserResp getUser() {
        List<UserResp> list = DataUtil.getInstance(MyApplication.getContext()).getDataStorage().loadAll(UserResp.class);
        if (list.size() > 0)
            return list.get(0);
        else return null;
    }

    public static void saveUser(UserResp userResp) {
        DataUtil.getInstance(MyApplication.getContext()).saveOrUpdate(userResp);
    }

    public static void logout() {
        DataUtil.getInstance(MyApplication.getContext()).deleteAll();
    }

    public static UserResp getUser(String key) {
        Object load = DataUtil.getInstance(MyApplication.getContext()).load(UserResp.class, key);
        return load == null ? null : (UserResp) load;
    }

    public static void saveArchives(ArchivesResp resp) {
        DataUtil.getInstance(MyApplication.getContext()).saveOrUpdate(resp);
    }

    public static ArchivesResp getArchives(){
        List<ArchivesResp> list = DataUtil.getInstance(MyApplication.getContext()).getDataStorage().loadAll(ArchivesResp.class);
        if (list.size() > 0)
            return list.get(0);
        else return null;
    }

    public static void saveCurrentUser(CurrentResp resp) {
        DataUtil.getInstance(MyApplication.getContext()).saveOrUpdate(resp);
    }

    public static CurrentResp getCurrentUser(){
        List<CurrentResp> list = DataUtil.getInstance(MyApplication.getContext()).getDataStorage().loadAll(CurrentResp.class);
        if (list.size() > 0)
            return list.get(0);
        else return null;
    }
}
