/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InfyGoBoot.UserMicro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import InfyGoBoot.UserMicro.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}