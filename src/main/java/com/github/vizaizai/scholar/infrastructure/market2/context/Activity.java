package com.github.vizaizai.scholar.infrastructure.market2.context;

import com.github.vizaizai.scholar.infrastructure.market2.DiscountItem;
import com.github.vizaizai.scholar.infrastructure.market2.Item;
import com.github.vizaizai.scholar.infrastructure.market2.ShareType;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author liaochongwei
 * @date 2022/10/24 19:15
 */
public class Activity {
    /**
     * 默认分组
     */
    private static final Group undefinedGroup = new Group("undefined",Integer.MAX_VALUE);
    /**
     * 活动标识
     */
    private String id;
    /**
     * 活动标签
     */
    private String tag;
    /**
     * 同享类型
     */
    private ShareType shareType = ShareType.DISABLED;
    /**
     * 指定参与项id列表
     */
    private List<String> itemIds;
    /**
     * 排序值（组内）
     */
    private int sort;
    /**
     * 分组id
     */
    private Group group = undefinedGroup;
    /**
     * 组内同享类型
     */
    private ShareType groupShareType = ShareType.DISABLED;
    /**
     * 优惠明细
     */
    private List<DiscountItem> discountItems;
    /**
     * 营销策略
     */
    private final MarketStrategy<? extends Activity> marketStrategy;

    public Activity(String id, MarketStrategy<? extends Activity> marketStrategy) {
        if (id == null || marketStrategy == null) {
            throw new IllegalArgumentException("活动参数错误~");
        }
        this.id = id;
        this.marketStrategy = marketStrategy;
    }


    public Activity newActivity() {
        return new Activity(this.id,this.marketStrategy);
    }
    /**
     * 克隆活动
     * @return
     */
    public Activity cloneActivity() {
        Activity newActivity = this.newActivity();
        newActivity.setTag(this.tag);
        newActivity.setSort(this.sort);
        newActivity.setGroup(this.group);
        newActivity.setItemIds(this.itemIds);
        newActivity.setShareType(this.shareType);
        return newActivity;
    }

    /**
     * 获取活动优惠金额
     * @return
     */
    public BigDecimal getDiscountAmount() {
        if (discountItems == null || discountItems.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return discountItems.stream().map(DiscountItem::getDiscountAmount).reduce(BigDecimal.ZERO,BigDecimal::add);

    }

    @SuppressWarnings("unchecked")
    public MarketStrategy<Activity> getMarketStrategy() {
        return (MarketStrategy<Activity>)marketStrategy;
    }

    /**
     * 获取实际活动参与项列表
     * @param items 所有项
     * @return 活动参与项
     */
    public List<Item> getActualItems(List<Item> items) {
        List<Item> actualItems = new ArrayList<>(items);
        // 不指定全部可参与
        if (itemIds == null) {
            return actualItems;
        }
        // 移除未在指定列表里的项
        actualItems.removeIf(e -> !this.itemIds.contains(e.getId()));
        return actualItems;
    }

    /**
     * 两两是否能同享
     */
    public boolean allShareTo(Collection<Activity> activities) {
        return activities.stream().allMatch(this::shareTo);
    }
    public boolean shareTo(Activity other) {
        // 同组看组内的同享，非同组看组外同享
        if (other == this) {
            return false;
        }
        if (other.getGroup().equals(this.group)) {
            return other.getGroupShareType().shareTo(this.groupShareType);
        }else {
            return other.getShareType().shareTo(this.shareType);
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public ShareType getShareType() {
        return shareType;
    }

    public void setShareType(ShareType shareType) {
        this.shareType = shareType;
    }

    public List<String> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<String> itemIds) {
        this.itemIds = itemIds;
    }

    public List<DiscountItem> getDiscountItems() {
        return discountItems;
    }

    public void setDiscountItems(List<DiscountItem> discountItems) {
        this.discountItems = discountItems;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        if (group != null) {
            this.group = group;
        }
    }

    public ShareType getGroupShareType() {
        return groupShareType;
    }

    public void setGroupShareType(ShareType groupShareType) {
        this.groupShareType = groupShareType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) && shareType == activity.shareType && Objects.equals(itemIds, activity.itemIds) && Objects.equals(group, activity.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shareType, itemIds, group);
    }

    public static class Group {
        /**
         * 组标识
         */
        private final String groupId;
        /**
         * 排序值
         */
        private final int sort;

        public Group(String groupId, int sort) {
            this.groupId = groupId;
            this.sort = sort;
        }

        public String getGroupId() {
            return groupId;
        }

        public int getSort() {
            return sort;
        }

    }
}
