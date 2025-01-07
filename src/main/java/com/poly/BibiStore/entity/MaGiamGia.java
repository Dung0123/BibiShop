package com.poly.BibiStore.entity;

import com.azure.core.annotation.Get;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ma_giam_gia")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MaGiamGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "don_toi_thieu", precision = 38, scale = 2)
    private BigDecimal donToiThieu;

    @Column(name = "gia_tri_giam", precision = 38, scale = 2)
    private BigDecimal giaTriGiam;

    @Column(name = "giam_toi_da", precision = 38, scale = 2)
    private BigDecimal giamToiDa;

    @Column(name = "loai_voucher")
    private Integer loaiVoucher;

    @Column(name = "ma", length = 255)
    private String ma;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "so_lan_su_dung")
    private Integer soLanSuDung;

    @Column(name = "ngay_bat_dau")
    private Date ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private Date ngayKetThuc;

    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ten", length = 255)
    private String ten;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "hinh_thuc_giam_gia_id")
    private Long hinhThucGiamGiaId;

    // Getters and Setters
}
