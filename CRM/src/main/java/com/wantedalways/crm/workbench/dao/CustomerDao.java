package com.wantedalways.crm.workbench.dao;

import com.wantedalways.crm.workbench.entity.Customer;

public interface CustomerDao {

    Customer selectCustomerByName(String name);

    int insertCustomer(Customer customer);
}
