package me.aidoc.client.entity.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

public class JsonBean implements IPickerViewData {
    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.name;
    }

    private int id;
    private String name;
    private List<JsonBean> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<JsonBean> getChildren() {
        return children;
    }

    public void setChildren(List<JsonBean> children) {
        this.children = children;
    }
}
