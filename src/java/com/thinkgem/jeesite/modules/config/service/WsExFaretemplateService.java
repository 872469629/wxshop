package com.thinkgem.jeesite.modules.config.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.config.dao.WsExCarrymodeDao;
import com.thinkgem.jeesite.modules.config.dao.WsExFaretemplateDao;
import com.thinkgem.jeesite.modules.config.dao.WsExInclpostageprovisoDao;
import com.thinkgem.jeesite.modules.config.entity.WsExCarrymode;
import com.thinkgem.jeesite.modules.config.entity.WsExFaretemplate;
import com.thinkgem.jeesite.modules.config.entity.WsExInclpostageproviso;
import com.thinkgem.jeesite.modules.member.entity.WsAddress;
import com.thinkgem.jeesite.modules.member.service.WsAddressService;
import com.thinkgem.jeesite.modules.order.entity.WsOrder;
import com.thinkgem.jeesite.modules.order.entity.WsOrderItem;
import com.thinkgem.jeesite.modules.prod.entity.WsProduct;
import com.thinkgem.jeesite.modules.ws.utils.WsConstant;

/**
 * 快递模版Service
 * @author water
 * @version 2017-10-03
 */
@Service
@Transactional(readOnly = true)
public class WsExFaretemplateService extends CrudService<WsExFaretemplateDao, WsExFaretemplate> {

	@Autowired
	private WsExCarrymodeDao wsExCarrymodeDao;
	@Autowired
	private WsExInclpostageprovisoDao wsExInclpostageprovisoDao;
	@Autowired
	private WsAddressService wsAddressService;
	
	public WsExFaretemplate get(String id) {
		WsExFaretemplate wsExFaretemplate = super.get(id);
		wsExFaretemplate.setWsExCarrymodeList(wsExCarrymodeDao.findList(new WsExCarrymode(wsExFaretemplate)));
		wsExFaretemplate.setWsExInclpostageprovisoList(wsExInclpostageprovisoDao.findList(new WsExInclpostageproviso(wsExFaretemplate)));
		return wsExFaretemplate;
	}
	
	public List<WsExFaretemplate> findList(WsExFaretemplate wsExFaretemplate) {
		return super.findList(wsExFaretemplate);
	}
	
	public Page<WsExFaretemplate> findPage(Page<WsExFaretemplate> page, WsExFaretemplate wsExFaretemplate) {
		return super.findPage(page, wsExFaretemplate);
	}
	
	@Transactional(readOnly = false)
	public void save(WsExFaretemplate wsExFaretemplate) {
		super.save(wsExFaretemplate);
		for (WsExCarrymode wsExCarrymode : wsExFaretemplate.getWsExCarrymodeList()){
			if (wsExCarrymode.getId() == null){
				continue;
			}
			if (WsExCarrymode.DEL_FLAG_NORMAL.equals(wsExCarrymode.getDelFlag())){
				if (StringUtils.isBlank(wsExCarrymode.getId())){
					wsExCarrymode.setWsExFaretemplate(wsExFaretemplate);
					wsExCarrymode.preInsert();
					wsExCarrymodeDao.insert(wsExCarrymode);
				}else{
					wsExCarrymode.preUpdate();
					wsExCarrymodeDao.update(wsExCarrymode);
				}
			}else{
				wsExCarrymodeDao.delete(wsExCarrymode);
			}
		}
		for (WsExInclpostageproviso wsExInclpostageproviso : wsExFaretemplate.getWsExInclpostageprovisoList()){
			if (wsExInclpostageproviso.getId() == null){
				continue;
			}
			if (WsExInclpostageproviso.DEL_FLAG_NORMAL.equals(wsExInclpostageproviso.getDelFlag())){
				if (StringUtils.isBlank(wsExInclpostageproviso.getId())){
					wsExInclpostageproviso.setFareId(wsExFaretemplate);
					wsExInclpostageproviso.preInsert();
					wsExInclpostageprovisoDao.insert(wsExInclpostageproviso);
				}else{
					wsExInclpostageproviso.preUpdate();
					wsExInclpostageprovisoDao.update(wsExInclpostageproviso);
				}
			}else{
				wsExInclpostageprovisoDao.delete(wsExInclpostageproviso);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WsExFaretemplate wsExFaretemplate) {
		super.delete(wsExFaretemplate);
		wsExCarrymodeDao.delete(new WsExCarrymode(wsExFaretemplate));
		wsExInclpostageprovisoDao.delete(new WsExInclpostageproviso(wsExFaretemplate));
	}
	
	/**
	 * 计算运费
	 * 先求出单种商品的运费价格，然后根据金额最多的模板计算总运费
	 */
	@Transactional(readOnly = false)
	public BigDecimal countExFareMoney(WsOrder wsOrder) {
		//查询收货地址
		WsAddress wsAdress=wsAddressService.get(wsOrder.getAddress());
		/**
		 * 循环所有的商品sku,计算单品最大运费模板
		 */
		//单品最大运费金额
		BigDecimal maxExpressMoney=new BigDecimal(0);
		//需要计算运费的模板
		WsExCarrymode countExCarrymode=new WsExCarrymode();
		//所有的商品sku
		List<WsOrderItem> wsOrderItemList=wsOrder.getWsOrderItemList();
		//需要计算运费的商品sku
		List<WsOrderItem> countOrderItemList=new ArrayList<WsOrderItem>();
		for (WsOrderItem wsOrderItem:wsOrderItemList) {
			WsProduct wsProduct=wsOrderItem.getWsProduct();
			//判断商品是否包邮,如果商品包邮则跳过计算
			if(!wsProduct.getExpressId().equals("0")){
				WsExFaretemplate wsExFaretemplate=super.get(wsProduct.getExpressId());
				//判断是否指定条件包邮
				if(wsExFaretemplate.getIsInclPostAgeByif().equals(WsConstant.YES)){
					WsExInclpostageproviso wsExInclpostageproviso=new WsExInclpostageproviso();
					wsExInclpostageproviso.setFareId(wsExFaretemplate);
					wsExInclpostageproviso.setRegionId("0");
					List<WsExInclpostageproviso> wsExInclpostageprovisoList=wsExInclpostageprovisoDao.findList(wsExInclpostageproviso);
					if(wsExInclpostageprovisoList==null || wsExInclpostageprovisoList.size()==0){
						wsExInclpostageproviso.setRegionId("0");
						wsExInclpostageprovisoList=wsExInclpostageprovisoDao.findList(wsExInclpostageproviso);
					}
					wsExInclpostageproviso=wsExInclpostageprovisoList.get(0);
					if(wsOrder.getReallyPrice().compareTo(wsExInclpostageproviso.getAmount())<0){
						countOrderItemList.add(wsOrderItem);
						WsExCarrymode wsExCarrymode=new WsExCarrymode();
						wsExCarrymode.setWsExFaretemplate(wsExFaretemplate);
						wsExCarrymode.setRegion(wsAdress.getArea().getId());
						List<WsExCarrymode> wsExCarrymodeList=wsExCarrymodeDao.findList(wsExCarrymode);
						if(wsExCarrymodeList==null || wsExCarrymodeList.size()==0){
							wsExCarrymode.setRegion("0");
							wsExCarrymodeList=wsExCarrymodeDao.findList(wsExCarrymode);
						}
						wsExCarrymode=wsExCarrymodeList.get(0);
						wsExCarrymode.setWsExFaretemplate(wsExFaretemplate);
						BigDecimal expressMoney=new BigDecimal(0);
						BigDecimal orderQuantity=new BigDecimal(wsOrderItem.getQuantity());//商品数量
						if(wsExFaretemplate.getValuationModel().equals(WsConstant.EX_PIECE)) {
							/**
							 * 判断件数是否大于首件数量
							 * 如果商品数量大于首件数量，则运费等于首费用+((商品数量-首件数量)除以续费数量(有小数则取整加1))*续费
							 * 如果商品数量小于首件数量，则运费等于首费用
							 */
							if(wsExCarrymode.getFirstPiece().compareTo(orderQuantity)<0){
								//(商品数量-首件数量)除以续费数量(有小数则取整加1)
								BigDecimal secondPiece=orderQuantity.subtract(wsExCarrymode.getFirstPiece()).divide(wsExCarrymode.getSecondPiece());
								if(secondPiece.compareTo(new BigDecimal(secondPiece.intValue()))==1){
									secondPiece=new BigDecimal(secondPiece.intValue()+1);
								}
								expressMoney=wsExCarrymode.getFirstAmount().add(secondPiece.multiply(wsExCarrymode.getSecondAmount()));
							}else{
								expressMoney=wsExCarrymode.getFirstAmount();
							}
						}else if(wsExFaretemplate.getValuationModel().equals(WsConstant.EX_BULK)){
							/**
							 * 判断件数是否大于首件数量
							 * 如果商品数量大于首件数量，则运费等于首费用+((商品数量-首件数量)除以续费数量(有小数则取整加1))*续费
							 * 如果商品数量小于首件数量，则运费等于首费用
							 */
							if(wsExCarrymode.getFirstBulk().compareTo(orderQuantity)<0){
								//(商品数量-首件数量)除以续费数量(有小数则取整加1)
								BigDecimal secondBulk=orderQuantity.subtract(wsExCarrymode.getFirstBulk()).divide(wsExCarrymode.getSecondBulk());
								if(secondBulk.compareTo(new BigDecimal(secondBulk.intValue()))==1){
									secondBulk=new BigDecimal(secondBulk.intValue()+1);
								}
								expressMoney=wsExCarrymode.getFirstAmount().add(secondBulk.multiply(wsExCarrymode.getSecondAmount()));
							}else{
								expressMoney=wsExCarrymode.getFirstAmount();
							}
						}else{
							/**
							 * 判断件数是否大于首件数量
							 * 如果商品数量大于首件数量，则运费等于首费用+((商品数量-首件数量)除以续费数量(有小数则取整加1))*续费
							 * 如果商品数量小于首件数量，则运费等于首费用
							 */
							if(wsExCarrymode.getFirstWeight().compareTo(orderQuantity)<0){
								//(商品数量-首件数量)除以续费数量(有小数则取整加1)
								BigDecimal secondWeight=orderQuantity.subtract(wsExCarrymode.getFirstWeight()).divide(wsExCarrymode.getSecondWeight());
								if(secondWeight.compareTo(new BigDecimal(secondWeight.intValue()))==1){
									secondWeight=new BigDecimal(secondWeight.intValue()+1);
								}
								expressMoney=wsExCarrymode.getFirstAmount().add(secondWeight.multiply(wsExCarrymode.getSecondAmount()));
							}else{
								expressMoney=wsExCarrymode.getFirstAmount();
							}
						}
						if(expressMoney.compareTo(maxExpressMoney)==1){
							maxExpressMoney=expressMoney;
							countExCarrymode=wsExCarrymode;
						}
					}	
				}
			}
		}
		
		/**
		 * 根据单品最大运费模板，计算所有产品金额
		 */
		//设置运费模板
		if(countExCarrymode.getWsExFaretemplate()!=null && StringUtils.isNotEmpty(countExCarrymode.getWsExFaretemplate().getId())){
			wsOrder.setExpress(countExCarrymode.getWsExFaretemplate().getId());
			return countExFareMoneyByTemplate(countOrderItemList, countExCarrymode);
		}else{
			return new BigDecimal(0);
		}
	}
	
	
	public BigDecimal countExFareMoneyByTemplate(List<WsOrderItem> countOrderItemList,WsExCarrymode wsExCarrymode){
		BigDecimal totleWeight=new BigDecimal(0);
		BigDecimal totleVolume=new BigDecimal(0);
		BigDecimal totlePiece=new BigDecimal(0);
		for (WsOrderItem orderItem:countOrderItemList) {
			totleWeight.add(orderItem.getWsProduct().getWeight());
			totleVolume.add(orderItem.getWsProduct().getVolume());
			totlePiece.add(new BigDecimal(orderItem.getQuantity()));
		}
		WsExFaretemplate wsExFaretemplate=wsExCarrymode.getWsExFaretemplate();
		if(wsExFaretemplate.getValuationModel().equals(WsConstant.EX_PIECE)) {
			/**
			 * 判断件数是否大于首件数量
			 * 如果商品数量大于首件数量，则运费等于首费用+((商品数量-首件数量)除以续费数量(有小数则取整加1))*续费
			 * 如果商品数量小于首件数量，则运费等于首费用
			 */
			if(wsExCarrymode.getFirstPiece().compareTo(totlePiece)<0){
				//(商品数量-首件数量)除以续费数量(有小数则取整加1)
				BigDecimal secondPiece=totlePiece.subtract(wsExCarrymode.getFirstPiece()).divide(wsExCarrymode.getSecondPiece());
				if(secondPiece.compareTo(new BigDecimal(secondPiece.intValue()))==1){
					secondPiece=new BigDecimal(secondPiece.intValue()+1);
				}
				return wsExCarrymode.getFirstAmount().add(secondPiece.multiply(wsExCarrymode.getSecondAmount()));
			}else{
				return wsExCarrymode.getFirstAmount();
			}
		}else if(wsExFaretemplate.getValuationModel().equals(WsConstant.EX_BULK)){
			/**
			 * 判断件数是否大于首件数量
			 * 如果商品数量大于首件数量，则运费等于首费用+((商品数量-首件数量)除以续费数量(有小数则取整加1))*续费
			 * 如果商品数量小于首件数量，则运费等于首费用
			 */
			if(wsExCarrymode.getFirstBulk().compareTo(totleVolume)<0){
				//(商品数量-首件数量)除以续费数量(有小数则取整加1)
				BigDecimal secondBulk=totleVolume.subtract(wsExCarrymode.getFirstBulk()).divide(wsExCarrymode.getSecondBulk());
				if(secondBulk.compareTo(new BigDecimal(secondBulk.intValue()))==1){
					secondBulk=new BigDecimal(secondBulk.intValue()+1);
				}
				return wsExCarrymode.getFirstAmount().add(secondBulk.multiply(wsExCarrymode.getSecondAmount()));
			}else{
				return wsExCarrymode.getFirstAmount();
			}
		}else{
			/**
			 * 判断件数是否大于首件数量
			 * 如果商品数量大于首件数量，则运费等于首费用+((商品数量-首件数量)除以续费数量(有小数则取整加1))*续费
			 * 如果商品数量小于首件数量，则运费等于首费用
			 */
			if(wsExCarrymode.getFirstWeight().compareTo(totlePiece)<0){
				//(商品数量-首件数量)除以续费数量(有小数则取整加1)
				BigDecimal secondWeight=totlePiece.subtract(wsExCarrymode.getFirstWeight()).divide(wsExCarrymode.getSecondWeight());
				if(secondWeight.compareTo(new BigDecimal(secondWeight.intValue()))==1){
					secondWeight=new BigDecimal(secondWeight.intValue()+1);
				}
				return wsExCarrymode.getFirstAmount().add(secondWeight.multiply(wsExCarrymode.getSecondAmount()));
			}else{
				return wsExCarrymode.getFirstAmount();
			}
		}
	}
	
}