package com.poly.BibiStore.repository.impl;

import com.poly.BibiStore.entity.KhachHang;
import com.poly.BibiStore.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
  Page<KhachHang> findAll(Pageable pageable);
}
