package com.oracle.personal_project.Service;

import java.util.HashMap;
import java.util.List;

import com.oracle.personal_project.model.Big_code;
import com.oracle.personal_project.model.Customer;
import com.oracle.personal_project.model.Defect;
import com.oracle.personal_project.model.Emp;
import com.oracle.personal_project.model.Machine;
import com.oracle.personal_project.model.Performance;
import com.oracle.personal_project.model.Plan_order;
import com.oracle.personal_project.model.Product;
import com.oracle.personal_project.model.Ship;
import com.oracle.personal_project.model.Small_code;
import com.oracle.personal_project.model.Work_center;
import com.oracle.personal_project.model.Work_order;

public interface ProductService {

	int productCnt(Product product);

	List<Product> productList(Product product);

	List<Big_code> bigList();

	List<Small_code> smallList();

	int deleteProduct(String p_code);

	String newProductInsert(List<Product> dataList);

	int customerCnt(Customer customer);

	List<Customer> customerList(Customer customer);

	List<Emp> empList();

	List<Small_code> customerSmallList();

	int newCustomertInsert(List<Customer> customertList);

	int deleteCustomer(String c_code);

	List<Ship> shipList(Ship ship);

	List<Product> productList();

	int shipCnt(Ship ship);

	List<Customer> customerList();

	int newShiptInsert(List<Ship> shipList);

	int deleteShip(String ship_code);

	int planOrderCnt(Plan_order planOrder);

	List<Plan_order> planOrderList(Plan_order planOrder);

	List<Ship> shipList();

	int newPlanInsert(List<Plan_order> planList);

	int deletePlan(String plan_code);

	int workOrderCnt(Work_order workOrder);

	List<Work_order> workOrderList(Work_order workOrder);

	List<Machine> MachineList();

	List<Work_center> WorkCenterList();

	List<Small_code> workStatusList();

	List<Plan_order> planOrderListModal();

	int newWorkOrderInsert(List<Work_order> workOrderList);

	int deleteWorkOrder(String wo_code);

	int performanceCnt(Performance performance);

	List<Performance> performanceList(Performance performance);

	int defectCnt(Defect defect);

	List<Defect> defectList(Defect defect);


	int monthTotalOrderPrice();

	int lastMonthTotalOrderPrice();

	int monthTotalIncomePrice();

	int lastMonthTotalIncomePrice();

	int monthTotalDefectPrice();

	int lastMonthTotalDefectPrice();

	void selectYearOrderCnt(HashMap<String, Object> map);

	void selectYearReturnCnt(HashMap<String, Object> map);


}
