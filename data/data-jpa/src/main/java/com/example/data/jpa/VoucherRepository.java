/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.data.jpa;

import java.util.List;

import com.example.data.jpa.model.Voucher;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface VoucherRepository
		extends CrudRepository<Voucher, Integer>, ListPagingAndSortingRepository<Voucher, Integer> {

	List<Voucher> findByMsisdn(String msisdn);

	Page<Voucher> findByMsisdn(String msisdn, Pageable pageable);

	Slice<Voucher> findSliceByMsisdn(String msisdn, Pageable pageable);

	List<Voucher> findByMsisdn(String msisdn, Limit limit);

	List<Voucher> findTop1ByMsisdnOrderByStatusDesc(String msisdn);

	boolean existsByMsisdn(String msisdn);

}
