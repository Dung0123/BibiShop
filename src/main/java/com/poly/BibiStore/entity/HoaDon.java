package com.poly.BibiStore.entity;

import com.poly.BibiStore.Constant.HoaDonStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "hoa_don")
public class HoaDon {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "ma_hoa_don", nullable = false, unique = true)
  private String maHoaDon;

  @Column(name = "ngay_tao")
  private LocalDate ngayTao;

  @Column(name = "tong_tien")
  private BigDecimal tongTien;

  @Column(name = "trang_thai")
  private String trangThai;

  @Enumerated(EnumType.STRING)
  private HoaDonStatus status;

  @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @ToString.Exclude  // Avoid circular reference in toString
  private List<HoaDonChiTiet> chiTietHoaDon;
}
