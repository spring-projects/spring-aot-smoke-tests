package com.example.data.jpa;

import java.util.List;

import com.example.data.jpa.model.Voucher;

import org.springframework.data.repository.CrudRepository;

public interface VoucherRepository extends CrudRepository<Voucher, Integer> {

	List<Voucher> findByMsisdn(String msisdn);

}
