package com.poly.BibiStore.repository.impl;

import com.poly.BibiStore.entity.DotGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface DotGiamGiaRepository extends CrudRepository<DotGiamGia, Integer> {

  Page<DotGiamGia> findAll(Pageable pageable);
}
