package com.example.springbootvue.repository;

import com.example.springbootvue.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByAge(int age);
}
