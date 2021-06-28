package com.github.vizaizai.scholar.infrastructure.market;

import com.github.vizaizai.scholar.infrastructure.market.constants.ActivityType;
import com.github.vizaizai.scholar.infrastructure.market.constants.ItemType;
import com.github.vizaizai.scholar.infrastructure.market.constants.MutexType;

import java.math.BigDecimal;
import java.util.*;

/**
 * 折扣参数
 * @author liaochongwei
 * @date 2021/6/9 14:30
 */
public class Discount extends Activity{
    /**
     * 折扣，如7折，8折
     */
    private BigDecimal value;
    /**
     * 折扣组，同组择优选择
     */
    private List<Discount> group;

    /**
     * 活动商品可参与数量列表<商品id,限制数量>
     */
    private final Map<String,Integer> balanceList = new HashMap<>();

    private Discount() {
        // 默认禁用互斥
        this.setMutexType(MutexType.DISABLED);
        // 默认全部都参与活动
        this.setItems(Collections.singletonList(DiscountItem.createAll()));
        this.setType(ActivityType.DISCOUNT);
    }

    @Override
    protected void preAddItem(Item item) {
        if (!(item instanceof DiscountItem)) {
            throw new IllegalArgumentException("折扣参与项类型错误，请使用DiscountItem");
        }
        if (((DiscountItem) item).getMaxQuantity() == null) {
            throw new IllegalArgumentException("折扣最大限制不能为空");
        }
    }

    public static Discount create(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("折扣值不能为空");
        }
        if (value.compareTo(BigDecimal.ZERO) <= 0 || value.compareTo(BigDecimal.TEN) == 0) {
            throw new IllegalArgumentException("折扣值区间(0,10]");
        }
        Discount discount = new Discount();
        discount.value = value;
        return discount;
    }

    public static Discount create(List<Discount> group) {
        if (group == null) {
            throw new IllegalArgumentException("折扣选项不能为空");
        }
        Discount discount = new Discount();
        discount.group = group;
        return discount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public List<Discount> getGroup() {
        return group;
    }

    /**
     * 获取最低折扣活动
     * @param commodity commodity
     * @return Discount
     */
    public Discount getLowestDiscount(Commodity commodity) {
        if (this.value != null && this.isActivityCommodity(commodity)) {
            return this;
        }
        // 从折扣组中选择
        if (this.group != null) {
            // 返回最小折扣
            return this.group.stream()
                        .filter(e->e.isActivityCommodity(commodity))
                        .min(Comparator.comparing(e->e.value)).orElse(null);
        }
        return null;
    }

    @Override
    public List<Commodity> getActivityCommodities(List<Commodity> commodities) {
        if (this.value != null) {
            return super.getActivityCommodities(commodities);
        }
        Set<Commodity> commodityAllSet = new HashSet<>();
        // 返回折扣组的商品列表
        for (Discount discount : group) {
            commodityAllSet.addAll(new HashSet<>(discount.getActivityCommodities(commodities)));
        }
        return new ArrayList<>(commodityAllSet);
    }

    /**
     * 获取活动商品可参与数量
     * @param commodity commodity
     * @return Limit
     */
    public Integer getQuantity(Commodity commodity) {
        // 初始化
        if (!this.balanceList.containsKey(commodity.getId())) {
            if (this.getItemType().equals(ItemType.ALL)) {
               this.balanceList.put(commodity.getId(),((DiscountItem)this.getItems().get(0)).getMaxQuantity());
            }else {
                Item item = this.getItemsMap().get(commodity.getId());
                if (item != null) {
                    this.balanceList.put(commodity.getId(), ((DiscountItem)item).getMaxQuantity());
                }
            }
        }
        Integer quantity = this.balanceList.get(commodity.getId());
        return quantity == null ? 0: quantity;
    }

    /**
     * 减少活动商品可参与数量
     * @param commodity
     */
    public void decreaseQuantity(Commodity commodity) {
        Integer quantity = this.balanceList.get(commodity.getId());
        if (quantity != null && quantity != -1 && quantity != 0) {
            this.balanceList.put(commodity.getId(), quantity - 1);
        }
    }

}
