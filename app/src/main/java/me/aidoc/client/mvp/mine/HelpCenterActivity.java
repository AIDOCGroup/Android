package me.aidoc.client.mvp.mine;

import android.os.Bundle;

import me.aidoc.client.R;
import me.aidoc.client.base.BaseTitleActivity;
import me.aidoc.client.entity.resp.UserResp;
import me.aidoc.client.entity.resp.VersionResp;
import me.aidoc.client.mvp.mine.fragment.MineContract;
import me.aidoc.client.base.frame.BaseView;
import me.aidoc.client.mvp.mine.fragment.MinePresenter;

public class HelpCenterActivity extends BaseTitleActivity<MinePresenter> implements MineContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.help_center));
    }

    @Override
    public void setUserInfo(UserResp userInfo) {

    }

    @Override
    public void newVersion(VersionResp resp) {

    }

    @Override
    public void noNewVersion(VersionResp resp) {

    }

    @Override
    public BaseView getBaseView() {
        return null;
    }
}
