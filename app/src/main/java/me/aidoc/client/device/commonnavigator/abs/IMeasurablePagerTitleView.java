package me.aidoc.client.device.commonnavigator.abs;

/**
 * 可测量内容区域的指示器标题
 */
public interface IMeasurablePagerTitleView extends IPagerTitleView {
    int getContentLeft();

    int getContentTop();

    int getContentRight();

    int getContentBottom();
}
