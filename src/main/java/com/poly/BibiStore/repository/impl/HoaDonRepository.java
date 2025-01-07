package com.poly.BibiStore.repository.impl;

import com.poly.BibiStore.Constant.HoaDonStatus;
import com.poly.BibiStore.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

public interface HoaDonRepository extends CrudRepository<HoaDon, Integer> {

  @Query("SELECT h FROM HoaDon h WHERE (:id IS NULL OR h.id = :id) " +
      "AND (:status IS NULL OR h.status = :status)")
  Page<HoaDon> searchHoaDon(@Param("id") Long id,
      @Param("status") HoaDonStatus status,
      Pageable pageable);
}
