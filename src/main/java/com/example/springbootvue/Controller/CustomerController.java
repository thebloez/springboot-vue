package com.example.springbootvue.Controller;

import com.example.springbootvue.model.Customer;
import com.example.springbootvue.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerRepository repos;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        LOGGER.info("Get all Customers ...");
        List<Customer> allCustomers = new ArrayList<>();
        repos.findAll().forEach(allCustomers::add);

        return allCustomers;
    }

    @PostMapping("/customer")
    public Customer postCustomer(@RequestBody Customer customer){
        LOGGER.info("Adding customer with name {}, and age {}", customer.getName(), customer.getAge());
        Customer _customer = repos.save(new Customer(customer.getName(), customer.getAge()));
        LOGGER.info("Success save into db name : {}", customer.getName());

        return _customer;
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") long id){
        LOGGER.info("Deleting customer with id {}", id);
        repos.deleteById(id);

        return new ResponseEntity<>("Customer Has Been Deleted!", HttpStatus.OK);
    }

    @GetMapping("/customers/{age}")
    public List<Customer> findCustomerByAge(@PathVariable("age") int age){
        List<Customer> customers = repos.findByAge(age);
        return customers;
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id,
                                                   @RequestBody Customer customer){
        LOGGER.info("try to updating customer with ID {} ", id);

        Optional<Customer> customerData = repos.findById(id);

        if (customerData.isPresent()){
            Customer _customer = customerData.get();
            _customer.setName(customer.getName());
            _customer.setAge(customer.getAge());
            _customer.setActive(customer.isActive());
            LOGGER.info("customer with ID {} has been updated", id);
            return new ResponseEntity<>(repos.save(_customer), HttpStatus.OK);
        } else {
            LOGGER.info("customer with ID {} is not exist", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
