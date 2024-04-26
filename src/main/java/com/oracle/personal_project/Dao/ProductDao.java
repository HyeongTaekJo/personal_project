package com.oracle.personal_project.Dao;

import java.util.List;

import com.oracle.personal_project.model.Big_code;
import com.oracle.personal_project.model.Customer;
import com.oracle.personal_project.model.Emp;
import com.oracle.personal_project.model.Plan_order;
import com.oracle.personal_project.model.Product;
import com.oracle.personal_project.model.Ship;
import com.oracle.personal_project.model.Small_code;

public interface ProductDao {

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

	int newCustomerInsert(List<Customer> customertList);

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

}
