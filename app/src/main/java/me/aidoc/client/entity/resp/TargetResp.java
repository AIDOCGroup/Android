package me.aidoc.client.entity.resp;

import me.aidoc.client.entity.BaseResp;

public class TargetResp  extends BaseResp {
    /**
     * target : 0
     */

    private int target;

    public TargetResp(int target) {
        this.target = target;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
