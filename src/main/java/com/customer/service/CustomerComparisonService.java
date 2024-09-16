package com.customer.service;

import com.customer.pojo.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerComparisonService {

    public List<Customer> onlyInListA(List<Customer> listA, List<Customer> listB) {
        return listA.stream()
                .filter(a -> listB.stream().noneMatch(b -> b.getCustomerId().equals(a.getCustomerId())))
                .collect(Collectors.toList());
    }

    public List<Customer> onlyInListB(List<Customer> listA, List<Customer> listB) {
        return listB.stream()
                .filter(b -> listA.stream().noneMatch(a -> a.getCustomerId().equals(b.getCustomerId())))
                .collect(Collectors.toList());
    }

    public List<Customer> inBothLists(List<Customer> listA, List<Customer> listB) {
        return listA.stream()
                .filter(a -> listB.stream().anyMatch(b -> b.getCustomerId().equals(a.getCustomerId())))
                .collect(Collectors.toList());
    }
}

