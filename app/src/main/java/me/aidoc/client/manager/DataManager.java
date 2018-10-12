package me.aidoc.client.manager;


import android.support.constraint.solver.widgets.WidgetContainer;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager _sInstance;
    private WidgetContainer mWidgetContainer;

    private List<Class<? extends BaseFragment>> mComponentsNames;
    private List<Class<? extends BaseFragment>> mUtilNames;
    private List<Class<? extends BaseFragment>> mLabNames;

    public DataManager() {
        mWidgetContainer = WidgetContainer.getInstance();
        initComponentsDesc();
        initUtilDesc();
        initLabDesc();
    }

    public static DataManager getInstance() {
        if (_sInstance == null) {
            _sInstance = new DataManager();
        }
        return _sInstance;
    }


    /**
     * Components
     */
    private void initComponentsDesc() {
        mComponentsNames = new ArrayList<>();
        mComponentsNames.add(ButtonFragment.class);
        mComponentsNames.add(DialogFragment.class);
        mComponentsNames.add(FloatLayoutFragment.class);
        mComponentsNames.add(EmptyViewFragment.class);
        mComponentsNames.add(TabSegmentFragment.class);
        mComponentsNames.add(ProgressBarFragment.class);
        mComponentsNames.add(BottomSheetFragment.class);
        mComponentsNames.add(GroupListViewFragment.class);
        mComponentsNames.add(TipDialogFragment.class);
        mComponentsNames.add(RadiusImageViewFragment.class);
        mComponentsNames.add(VerticalTextViewFragment.class);
        mComponentsNames.add(PullRefreshFragment.class);
        mComponentsNames.add(PopupFragment.class);
        mComponentsNames.add(SpanTouchFixTextViewFragment.class);
        mComponentsNames.add(LinkTextViewFragment.class);
        mComponentsNames.add(QQFaceFragment.class);
        mComponentsNames.add(SpanFragment.class);
        mComponentsNames.add(CollapsingTopBarLayoutFragment.class);
        mComponentsNames.add(ViewPagerFragment.class);
        mComponentsNames.add(LayoutFragment.class);
        mComponentsNames.add(PriorityLinearLayoutFragment.class);
    }

    /**
     * Helper
     */
    private void initUtilDesc() {
        mUtilNames = new ArrayList<>();
        mUtilNames.add(ColorHelperFragment.class);
        mUtilNames.add(DeviceHelperFragment.class);
        mUtilNames.add(DrawableHelperFragment.class);
        mUtilNames.add(StatusBarHelperFragment.class);
        mUtilNames.add(ViewHelperFragment.class);
        mUtilNames.add(NotchHelperFragment.class);
    }

    /**
     * Lab
     */
    private void initLabDesc() {
        mLabNames = new ArrayList<>();
        mLabNames.add(AnimationListViewFragment.class);
        mLabNames.add(SnapHelperFragment.class);
        mLabNames.add(ArchTestFragment.class);
    }

    public ItemDescription getDescription(Class<? extends BaseFragment> cls) {
        return mWidgetContainer.get(cls);
    }

    public String getName(Class<? extends BaseFragment> cls) {
        ItemDescription itemDescription = getDescription(cls);
        if (itemDescription == null) {
            return null;
        }
        return itemDescription.getName();
    }

    public List<ItemDescription> getComponentsDescriptions() {
        List<ItemDescription> list = new ArrayList<>();
        for (int i = 0; i < mComponentsNames.size(); i++) {
            list.add(mWidgetContainer.get(mComponentsNames.get(i)));
        }
        return list;
    }

    public List<ItemDescription> getUtilDescriptions() {
        List<ItemDescription> list = new ArrayList<>();
        for (int i = 0; i < mUtilNames.size(); i++) {
            list.add(mWidgetContainer.get(mUtilNames.get(i)));
        }
        return list;
    }

    public List<ItemDescription> getLabDescriptions() {
        List<ItemDescription> list = new ArrayList<>();
        for (int i = 0; i < mLabNames.size(); i++) {
            list.add(mWidgetContainer.get(mLabNames.get(i)));
        }
        return list;
    }
}
