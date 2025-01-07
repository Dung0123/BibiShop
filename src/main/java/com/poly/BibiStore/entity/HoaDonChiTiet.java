package com.poly.BibiStore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
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
@Table(name = "hoa_don_chi_tiet")
public class HoaDonChiTiet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "hoa_don_id", nullable = false)
  @ToString.Exclude  // Avoid circular reference in toString
  private HoaDon hoaDon;

  @Column(name = "ten_san_pham", nullable = false)
  private String tenSanPham;

  @Column(name = "so_luong", nullable = false)
  private int soLuong;

  @Column(name = "don_gia", nullable = false)
  private BigDecimal donGia;

  // thanhTien is calculated as donGia * soLuong
  @Transient  // Not stored in the database, only calculated
  public BigDecimal getThanhTien() {
    return donGia.multiply(BigDecimal.valueOf(soLuong));
  }
}
