package com.bjwg.back.base.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RescOperationUtil {
	
	private static Map<String,List<RescOperation>> Operation_Result = new HashMap<String,List<RescOperation>>();
	
	static {
		List<RescOperation> result = null;
		for(RescOperation operation : RescOperation.values()){
			if(operation == RescOperation.childDataDel || operation == RescOperation.othersDataDel){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.roleDelete);
				result.add(RescOperation.managerDelete);
				result.add(RescOperation.userDelete);
				result.add(RescOperation.walletDelete);
				result.add(RescOperation.projectDelete);
				result.add(RescOperation.mycouponDelete);
				result.add(RescOperation.infoDelete);
				result.add(RescOperation.infoSortDelete);
				result.add(RescOperation.bulletinDelete);
				result.add(RescOperation.shopDelete);
				result.add(RescOperation.configDelete);
				result.add(RescOperation.dictDelete);
				result.add(RescOperation.dictDetailDelete);
				result.add(RescOperation.slideDelete);
			} else if(operation == RescOperation.childDataExamine || operation == RescOperation.othersDataExamine){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.withdrawAuth);
			} else if(operation == RescOperation.childDataOperate || operation == RescOperation.othersDataOperate){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.managerEditInit);
				result.add(RescOperation.managerFreeze);
				result.add(RescOperation.roleChildList);
				result.add(RescOperation.roleEditInit);
				result.add(RescOperation.roleRescList);
				result.add(RescOperation.roleUserList);
				result.add(RescOperation.userPCEditInit);
				result.add(RescOperation.projectEditInit);
				result.add(RescOperation.projectEdit);
				result.add(RescOperation.bulletinEditInit);
				result.add(RescOperation.bulletinEdit);
				result.add(RescOperation.infoEditInit);
				result.add(RescOperation.infoEdit);
				result.add(RescOperation.infoSortEditInit);
				result.add(RescOperation.infoSortEdit);
				result.add(RescOperation.shopEditInit);
				result.add(RescOperation.shopEdit);
				result.add(RescOperation.userEditInit);
				result.add(RescOperation.userEdit);
				result.add(RescOperation.userFreeze);
				result.add(RescOperation.walletEditInit);
				result.add(RescOperation.walletEdit);
			}else if(operation == RescOperation.userEditInit){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.userEdit);
			}else if(operation == RescOperation.projectEditInit){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.projectEdit);
			}else if(operation == RescOperation.bulletinEditInit){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.bulletinEdit);
			}else if(operation == RescOperation.infoEditInit){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.infoEdit);
			}else if(operation == RescOperation.infoSortEditInit){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.infoSortEdit);
			}else if(operation == RescOperation.shopEditInit){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.shopEdit);
			}else if(operation == RescOperation.walletEditInit){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.walletEdit);
			}else if(operation == RescOperation.configList){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.configEditInit);
				result.add(RescOperation.configEdit);
				result.add(RescOperation.configDelete);
			}else if(operation == RescOperation.dictList){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.dictEditInit);
				result.add(RescOperation.dictEdit);
				result.add(RescOperation.dictDelete);
			}else if(operation == RescOperation.dictDetailList){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.dictDetailEditInit);
				result.add(RescOperation.dictDetailEdit);
				result.add(RescOperation.dictDetailDelete);
			}else if(operation == RescOperation.kefulList){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.kefuEditInit);
				result.add(RescOperation.kefuEdit);
			}else if(operation == RescOperation.pactList){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.pactEditInit);
				result.add(RescOperation.pactEdit);
			}else if(operation == RescOperation.siteList){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.siteEditInit);
				result.add(RescOperation.siteEdit);
			}else if(operation == RescOperation.slideList){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.slideEditInit);
				result.add(RescOperation.slideEdit);
			}else if(operation == RescOperation.userConfigList){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.userConfigEditInit);
				result.add(RescOperation.userConfigEdit);
			}else if(operation == RescOperation.orderExport){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.orderExportCheck);
			}else if(operation == RescOperation.userExport){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.userExportCheck);
			}else if(operation == RescOperation.withdrawExport){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.withdrawExportCheck);
			}else if(operation == RescOperation.myprojectExport){
				result = new ArrayList<RescOperation>();
				result.add(RescOperation.myprojectExportCheck);
			}
			Operation_Result.put(operation.getValue(), result);
		}
	}
	
	public static List<RescOperation> getUseableOperation(String stringCurrent){
		return Operation_Result.get(stringCurrent);
	}
	
	public static boolean isUseableOperation(String stringCurrent , String operationDist){
		if(stringCurrent == null) {
			return false;
		}
		RescOperation dist = RescOperation.findByValue(operationDist);
		if(dist==null)return false;
		List<RescOperation> result = getUseableOperation(stringCurrent);
		if(result==null)return false;
		return result.contains(dist);
	}
}
