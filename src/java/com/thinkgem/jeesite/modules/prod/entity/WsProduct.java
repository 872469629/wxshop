package com.thinkgem.jeesite.modules.prod.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 产品信息Entity
 * @author 大胖老师
 * @version 2017-10-01
 */
public class WsProduct extends DataEntity<WsProduct> {
	
	private static final long serialVersionUID = 1L;
	private String prodCategoryId;		// 产品分类ID
	private String prodCategoryName;		// 分类名称
	private String brandId;		// 所属品牌
	private String brandName;		// 品牌名称
	private String pname;		// 名称=产品内部简称代号
	private String title;		// 标题=带产品基本描述的标题30文字
	private String isHomeRecommd;		// 是否首页推荐
	private String prodImage;		// 商品主图
	private String prodImages;		// 商品附属图
	private String rangePrice;		// 价格范围=系统自动计算出sku中的最低价格和最高价格
	private BigDecimal minPrice;		// 最小价格
	private BigDecimal defaultPrice;		// 默认价格
	private BigDecimal defaultReallyPrice;		// 默认实际支付价格
	private BigDecimal defaultRewardMoney;		// 默认分销金额
	private int defaultNum;		// 默认库存数量
	private String onGoodState;		// 产品状态
	private Date onGoodTime;		// 上架时间
	private BigDecimal rewardRate;		// 分销比例
	private String pnumber;		// 货号
	private BigDecimal volume;		// 体积
	private BigDecimal weight;		// 重量
	private String expressId;		// 快递模版
	private String isGift;		// 是否赠品0否1是=如果是赠品的话则不加入计算
	private String keyword;		// 关键描述=自动截取系统关键字，或者自己录入关键字搜索使用
	private String warehouse;		// 仓库发货地=手动填写
	private String prodContent;		// 产品描述=主要的内容产品描述html编辑器
	private String isReturn;		// 是否支持退货
	private int returnDate;		// 退货有效期(天)
	private int selNum;		// 已售数量
	private int clickNum;   //浏览次数
	private String isBaseChange;   //基础属性是否修改
	private String isSelChange;   //销售属性是否修改
	private String flag; //保存标识
	private List<String> prodImageList;
	private List<WsProdSku> wsProdSkuList = Lists.newArrayList();		// 子表列表
	private List<WsProdSkuAttr> wsProdSkuAttrBaseList = Lists.newArrayList();		// 子表列表
	private List<WsProdSkuAttr> wsProdSkuAttrSelList = Lists.newArrayList();		// 子表列表
	private List<WsProdAttr> wsProdAttrList = Lists.newArrayList();		// 子表列表
	/**
	 * 排序类型
	 */
	public enum OrderType {


		/** 销量降序 */
		salesDesc,
		
		/** 销量圣墟 */
		salesAsc,

		/** 价格降序 */
		priceDesc,
		
		/** 价格升序 */
		priceAsc,

		/** 日期降序 */
		dateDesc,
		
		/** 日期升序 */
		dateAsc
	}
	
	public WsProduct() {
		super();
	}

	public WsProduct(String id){
		super(id);
	}

	@Length(min=0, max=64, message="产品分类ID长度必须介于 0 和 64 之间")
	public String getProdCategoryId() {
		return prodCategoryId;
	}

	public void setProdCategoryId(String prodCategoryId) {
		this.prodCategoryId = prodCategoryId;
	}
	
	@Length(min=0, max=200, message="分类名称长度必须介于 0 和 200 之间")
	public String getProdCategoryName() {
		return prodCategoryName;
	}

	public void setProdCategoryName(String prodCategoryName) {
		this.prodCategoryName = prodCategoryName;
	}
	
	@Length(min=0, max=64, message="所属品牌长度必须介于 0 和 64 之间")
	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
	@Length(min=0, max=200, message="品牌名称长度必须介于 0 和 200 之间")
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	@Length(min=0, max=128, message="名称=产品内部简称代号长度必须介于 0 和 128 之间")
	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	
	@Length(min=0, max=512, message="标题=带产品基本描述的标题30文字长度必须介于 0 和 512 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=64, message="产品状态长度必须介于 0 和 64 之间")
	public String getOnGoodState() {
		return onGoodState;
	}

	public void setOnGoodState(String onGoodState) {
		this.onGoodState = onGoodState;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOnGoodTime() {
		return onGoodTime;
	}

	public void setOnGoodTime(Date onGoodTime) {
		this.onGoodTime = onGoodTime;
	}
	
	@Length(min=0, max=255, message="货号长度必须介于 0 和 255 之间")
	public String getPnumber() {
		return pnumber;
	}

	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}
	
	@Length(min=0, max=64, message="快递模版长度必须介于 0 和 64 之间")
	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}
	
	@Length(min=0, max=64, message="是否赠品0否1是=如果是赠品的话则不加入计算长度必须介于 0 和 64 之间")
	public String getIsGift() {
		return isGift;
	}

	public void setIsGift(String isGift) {
		this.isGift = isGift;
	}
	
	@Length(min=0, max=255, message="关键描述=自动截取系统关键字，或者自己录入关键字搜索使用长度必须介于 0 和 255 之间")
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Length(min=0, max=64, message="仓库发货地=手动填写长度必须介于 0 和 64 之间")
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	@Length(min=0, max=5000, message="产品描述=主要的内容产品描述html编辑器长度必须介于 0 和 5000 之间")
	public String getProdContent() {
		return prodContent;
	}

	public void setProdContent(String prodContent) {
		this.prodContent = prodContent;
	}
	
	@Length(min=0, max=10, message="是否支持退货长度必须介于 0 和 10 之间")
	public String getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}
	
	public List<WsProdSku> getWsProdSkuList() {
		return wsProdSkuList;
	}

	public void setWsProdSkuList(List<WsProdSku> wsProdSkuList) {
		this.wsProdSkuList = wsProdSkuList;
	}
	
	public String getRangePrice() {
		return rangePrice;
	}

	public void setRangePrice(String rangePrice) {
		this.rangePrice = rangePrice;
	}

	public int getDefaultNum() {
		return defaultNum;
	}

	public void setDefaultNum(int defaultNum) {
		this.defaultNum = defaultNum;
	}

	public BigDecimal getRewardRate() {
		return rewardRate;
	}

	public void setRewardRate(BigDecimal rewardRate) {
		this.rewardRate = rewardRate;
	}

	public int getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(int returnDate) {
		this.returnDate = returnDate;
	}

	public int getSelNum() {
		return selNum;
	}

	public void setSelNum(int selNum) {
		this.selNum = selNum;
	}

	public int getClickNum() {
		return clickNum;
	}

	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}

	public List<WsProdSkuAttr> getWsProdSkuAttrBaseList() {
		return wsProdSkuAttrBaseList;
	}

	public void setWsProdSkuAttrBaseList(List<WsProdSkuAttr> wsProdSkuAttrBaseList) {
		this.wsProdSkuAttrBaseList = wsProdSkuAttrBaseList;
	}

	public List<WsProdSkuAttr> getWsProdSkuAttrSelList() {
		return wsProdSkuAttrSelList;
	}

	public void setWsProdSkuAttrSelList(List<WsProdSkuAttr> wsProdSkuAttrSelList) {
		this.wsProdSkuAttrSelList = wsProdSkuAttrSelList;
	}

	public List<WsProdAttr> getWsProdAttrList() {
		return wsProdAttrList;
	}

	public void setWsProdAttrList(List<WsProdAttr> wsProdAttrList) {
		this.wsProdAttrList = wsProdAttrList;
	}

	public String getIsHomeRecommd() {
		return isHomeRecommd;
	}

	public void setIsHomeRecommd(String isHomeRecommd) {
		this.isHomeRecommd = isHomeRecommd;
	}
	
	@Length(min=0, max=2000, message="商品主图不能过多")
	public String getProdImages() {
		return prodImages;
	}

	public void setProdImages(String prodImages) {
		this.prodImages = prodImages;
	}

	public String getProdImage() {
		return prodImage;
	}

	public void setProdImage(String prodImage) {
		this.prodImage = prodImage;
	}

	public List<String> getProdImageList() {
		return prodImageList;
	}

	public void setProdImageList(List<String> prodImageList) {
		this.prodImageList = prodImageList;
	}

	public String getIsBaseChange() {
		return isBaseChange;
	}

	public void setIsBaseChange(String isBaseChange) {
		this.isBaseChange = isBaseChange;
	}

	public String getIsSelChange() {
		return isSelChange;
	}

	public void setIsSelChange(String isSelChange) {
		this.isSelChange = isSelChange;
	}

	public BigDecimal getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(BigDecimal defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public BigDecimal getDefaultReallyPrice() {
		return defaultReallyPrice;
	}

	public void setDefaultReallyPrice(BigDecimal defaultReallyPrice) {
		this.defaultReallyPrice = defaultReallyPrice;
	}

	public BigDecimal getDefaultRewardMoney() {
		return defaultRewardMoney;
	}

	public void setDefaultRewardMoney(BigDecimal defaultRewardMoney) {
		this.defaultRewardMoney = defaultRewardMoney;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}
	
	
}