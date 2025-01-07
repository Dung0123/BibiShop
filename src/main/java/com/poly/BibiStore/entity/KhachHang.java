package com.poly.BibiStore.entity;

import jakarta.persistence.*;
import java.util.Set;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "KHACH_HANG")
public class KhachHang {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "ngay_sua")
  private LocalDateTime ngaySua;

  @Column(name = "ngay_tao")
  private LocalDateTime ngayTao;

  @Column(name = "gioi_tinh")
  private Integer gioiTinh;

  @Column(name = "ho")
  private String ho;

  @Column(name = "mat_khau")
  private String matKhau;

  @Column(name = "ngay_sinh")
  private LocalDate ngaySinh;

  @Column(name = "sdt")
  private String sdt;

  @Column(name = "tai_khoan")
  private String taiKhoan;

  @Column(name = "ten")
  private String ten;

  @Column(name = "ten_dem")
  private String tenDem;

  @Column(name = "trang_thai")
  private Integer trangThai;

  @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL)
  private Set<DiaChi> diaChi; // Changed to Set<DiaChi> to hold multiple addresses

  @Column(name = "vai_tro")
  private int vaiTro;

}
