package com.poly.BibiStore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "dia_chi")
public class DiaChi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dia_chi")
    private String diaChi1;

    @Column(name = "dia_chi_cu_the")
    private String diaChiCuThe;

    @Column(name = "ho_va_ten")
    private String hoVaTen;

    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "phuong_xa")
    private String phuongXa;

    @Column(name = "quan_huyen")
    private String quanHuyen;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "thanh_pho")
    private String thanhPho;

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne
    @JoinColumn(name = "khach_hang_id") // Foreign key reference to KhachHang
    private KhachHang khachHang;

}
