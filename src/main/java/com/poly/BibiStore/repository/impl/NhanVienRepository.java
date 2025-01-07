package com.poly.BibiStore.repository.impl;

import com.poly.BibiStore.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface NhanVienRepository extends CrudRepository<NhanVien, Integer> {
  // Truy vấn phân trang nhân viên theo từ khóa tìm kiếm
  @Query("SELECT n FROM NhanVien n WHERE " +
      "LOWER(n.ho) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
      "LOWER(n.tenDem) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
      "LOWER(n.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
  Page<NhanVien> findByKeyword(@Param("keyword") String keyword, Pageable pageable);


  Page<NhanVien> findAll(Pageable pageable);

  boolean existsByTaiKhoan(String taiKhoan);
  boolean existsByEmail(String email);
  boolean existsBySdt(String sdt);


}
