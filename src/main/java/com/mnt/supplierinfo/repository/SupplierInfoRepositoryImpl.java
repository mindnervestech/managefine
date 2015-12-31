package com.mnt.supplierinfo.repository;

import org.springframework.stereotype.Service;

@Service
public  class SupplierInfoRepositoryImpl implements SupplierInfoRepository {

	
	
}

/*
for(SqlRow row: sqlRows){
	DateWiseHistoryVM dHistoryVM=new DateWiseHistoryVM();
	
	List<SqlRow> aLog = null;
			aLog = AduitLog.getDateHistory(row.getString("date"));
		
	dHistoryVM.setChangeDate(row.getString("date"));
	
	List<ValueListVM> vms = new ArrayList<>();
	for(SqlRow sRow: aLog){
		AduitLog aduitLog = AduitLog.getById(sRow.getLong("id"));
		List<HistoryAllLogVM> hAllList = new ArrayList<>();
		ValueListVM vListVM = new ValueListVM();
		 JSONArray array = new JSONArray(aduitLog.getJsonData());
		 for(int i=0; i<array.length(); i++){
			 HistoryAllLogVM hVm = new HistoryAllLogVM(); 
		        JSONObject jsonObj  = array.getJSONObject(i);
		        hVm.setProperty(jsonObj.getString("property"));
		        hVm.setOldVal(jsonObj.getString("oldVal"));
		        hVm.setNewVal(jsonObj.getString("newVal"));
		        hAllList.add(hVm);
		    }
		 vListVM.setHistoryAllLogVM(hAllList);
		 vms.add(vListVM);
	}
	dHistoryVM.setValueListVM(vms);
	vmList.add(dHistoryVM);
}*/