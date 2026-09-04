/*
 * Copyright 2026-present the original author or authors.
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

	// setFirstResult(query, …) + setMaxResults(query, pageable.getPageSize())
	Page<Voucher> findByMsisdn(String msisdn, Pageable pageable);

	// setMaxResults(query, pageable.getPageSize() + 1)
	Slice<Voucher> findSliceByMsisdn(String msisdn, Pageable pageable);

	// setMaxResults(query, limit.max())
	List<Voucher> findByMsisdn(String msisdn, Limit limit);

	// applyMaxResults(query, 1)
	List<Voucher> findTop1ByMsisdnOrderByStatusDesc(String msisdn);

	// setMaxResults(query, 1) + getResultList(query)
	boolean existsByMsisdn(String msisdn);

}
