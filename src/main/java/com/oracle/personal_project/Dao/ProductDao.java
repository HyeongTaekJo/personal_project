package com.oracle.personal_project.Dao;

import java.util.List;

import com.oracle.personal_project.model.Big_code;
import com.oracle.personal_project.model.Product;
import com.oracle.personal_project.model.Small_code;

public interface ProductDao {

	int productCnt(Product product);

	List<Product> productList(Product product);

	List<Big_code> bigList();

	List<Small_code> smallList();

	int deleteProduct(String p_code);

}
