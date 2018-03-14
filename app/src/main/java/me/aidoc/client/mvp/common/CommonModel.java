package me.aidoc.client.mvp.common;

import java.io.File;

import me.aidoc.client.api.helper.HttpUtils;
import me.aidoc.client.api.helper.RetrofitHelper;
import me.aidoc.client.entity.BaseResp;
import me.aidoc.client.entity.req.AreaReq;
import me.aidoc.client.entity.req.AvatarReq;
import me.aidoc.client.entity.req.BirthdayReq;
import me.aidoc.client.entity.req.LanguageReq;
import me.aidoc.client.entity.req.SexReq;
import me.aidoc.client.entity.resp.ArchivesResp;
import me.aidoc.client.entity.resp.AreaResp;
import me.aidoc.client.entity.resp.CurrentResp;
import me.aidoc.client.entity.resp.UploadFileResp;
import me.aidoc.client.entity.resp.VersionResp;
import me.aidoc.client.util.UserUtil;
import me.aidoc.client.base.frame.MvpModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class CommonModel extends MvpModel {

    /**
     * 上传图片
     * @param file
     */
    public static void uploadPhoto(File file, HttpUtils.OnResultListener<UploadFileResp> listener) {
        RequestBody requestBody =RequestBody.create(MediaType.parse("image/jpg"),file);
        MultipartBody.Part body =MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        Observable<UploadFileResp> loginObservable = RetrofitHelper.getService().upload(body);
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<UploadFileResp>() {

            @Override
            public void onSuccess(UploadFileResp uploadFileResp) {
                listener.onSuccess(uploadFileResp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }

    /**
     * 设置头像
     * @param avatarUrl
     */
    public static void setAvatar(String avatarUrl, HttpUtils.OnResultListener<BaseResp> listener) {
        Observable<BaseResp> loginObservable = RetrofitHelper.getService().avatar(new AvatarReq(avatarUrl));
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<BaseResp>() {

            @Override
            public void onSuccess(BaseResp baseResp) {
                listener.onSuccess(baseResp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }

    /**
     * 保存所在地区
     */
    public static void setCityId(int cityId, HttpUtils.OnResultListener<BaseResp> listener) {
        Observable<BaseResp> loginObservable = RetrofitHelper.getService().saveArea(new AreaReq(cityId));
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<BaseResp>() {

            @Override
            public void onSuccess(BaseResp baseResp) {
                listener.onSuccess(baseResp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }

    /**
     * 保存所在地区
     */
    public static void setBirthday(String birthday, HttpUtils.OnResultListener<BaseResp> listener) {
        Observable<BaseResp> loginObservable = RetrofitHelper.getService().birthday(new BirthdayReq(birthday));
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<BaseResp>() {

            @Override
            public void onSuccess(BaseResp baseResp) {
                listener.onSuccess(baseResp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }
    /**
     * 保存所在地区
     */
    public static void setSex(int sex, HttpUtils.OnResultListener<BaseResp> listener) {
        Observable<BaseResp> loginObservable = RetrofitHelper.getService().setSex(new SexReq(sex));
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<BaseResp>() {

            @Override
            public void onSuccess(BaseResp baseResp) {
                listener.onSuccess(baseResp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }

    /**
     * 当前用户信息
     * @param listener
     */
    public static void getCurrentUserInfo(HttpUtils.OnResultListener<CurrentResp> listener) {
        if (UserUtil.getUser()==null ) {
            listener.onError(new Throwable("CurrentUser is null"),"CurrentUser is null");
            return;
        }
        int userId = UserUtil.getUser().getId();
        String userName =UserUtil.getUser().getUsername();
        Observable<CurrentResp> loginObservable = RetrofitHelper.getService().current(userId,userName);
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<CurrentResp>() {

            @Override
            public void onSuccess(CurrentResp baseResp) {
                UserUtil.saveCurrentUser(baseResp);
                listener.onSuccess(baseResp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }
    /**
     * 当前用户档案
     * @param listener
     */
    public static void getArchivesInfo(HttpUtils.OnResultListener<ArchivesResp> listener) {
        Observable<ArchivesResp> loginObservable = RetrofitHelper.getService().archives();
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<ArchivesResp>() {

            @Override
            public void onSuccess(ArchivesResp resp) {
                UserUtil.saveArchives(resp);
                listener.onSuccess(resp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }
    /**
     * 当前用户档案
     * @param listener
     */
    public static void modifyUserInfo(int type ,HttpUtils.OnResultListener<ArchivesResp> listener) {
        Observable<ArchivesResp> loginObservable = RetrofitHelper.getService().archives();
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<ArchivesResp>() {

            @Override
            public void onSuccess(ArchivesResp resp) {
                UserUtil.saveArchives(resp);
                listener.onSuccess(resp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }
    /**
     * 检查更新
     * @param listener
     */
    public static void chkVersion(String version,HttpUtils.OnResultListener<VersionResp> listener) {
        Observable<VersionResp> loginObservable = RetrofitHelper.getService().chkVersion(version);
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<VersionResp>() {

            @Override
            public void onSuccess(VersionResp resp) {
                listener.onSuccess(resp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }

    /**
     * 当前用户档案
     * @param listener
     */
    public static void putLanguage(int language,HttpUtils.OnResultListener<BaseResp> listener) {
        Observable<BaseResp> loginObservable = RetrofitHelper.getService().language(new LanguageReq(language));
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<BaseResp>() {

            @Override
            public void onSuccess(BaseResp resp) {
                listener.onSuccess(resp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }

    /**
     * 获取国际地区
     * @param listener
     */
    public static void getArea(String code,String p,String p_size,HttpUtils.OnResultListener<AreaResp> listener) {
        Observable<AreaResp> loginObservable = RetrofitHelper.getService().getArea(code,p,p_size);
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<AreaResp>() {

            @Override
            public void onSuccess(AreaResp resp) {
                listener.onSuccess(resp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }

    /**
     * 退出
     * @param listener
     */
    public static void logout(HttpUtils.OnResultListener<BaseResp> listener) {
        Observable<BaseResp> observable = RetrofitHelper.getService().logout();
        HttpUtils.requestNetByPost(observable, new HttpUtils.OnResultListener<BaseResp>() {

            @Override
            public void onSuccess(BaseResp resp) {
                listener.onSuccess(resp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }

}
