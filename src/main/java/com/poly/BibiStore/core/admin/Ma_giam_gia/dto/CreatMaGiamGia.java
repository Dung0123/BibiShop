package com.poly.BibiStore.core.admin.Ma_giam_gia.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatMaGiamGia {



  @NotNull(message = "Tên mã giảm giá không được để trống")
  @Size(min = 3, max = 255, message = "Tên mã giảm giá phải từ 3 đến 255 ký tự")
  private String tenMaGiamGia;

  @NotNull(message = "Mã giảm giá không được để trống")
  @Size(min = 5, max = 20, message = "Mã giảm giá phải từ 5 đến 20 ký tự")
  private String maGiamGia;

  @NotNull(message = "Giá trị đơn tối thiểu không được để trống")
  @Min(value = 1000, message = "Giá trị đơn tối thiểu phải lớn hơn 1000")
  private BigDecimal giaTriDonToiThieu;

  @NotNull(message = "Giá trị giảm không được để trống")
  @Min(value = 100, message = "Giá trị giảm phải lớn hơn 100")
  private BigDecimal giaTriGiamGia;

  @NotNull(message = "Giá trị giảm tối đa không được để trống")
  @Min(value = 100, message = "Giá trị giảm tối đa phải lớn hơn 100")
  private BigDecimal giaTriGiamToiDa;

  @NotNull(message = "Tổng số lượng không được để trống")
  @Min(value = 1, message = "Tổng số lượng phải lớn hơn 0")
  private Integer tongSoLuong;

  @NotNull(message = "Số lần sử dụng tối đa không được để trống")
  @Min(value = 1, message = "Số lần sử dụng tối đa phải lớn hơn 0")
  private Integer soLanSuDungToiDa;

  @NotNull(message = "Ngày bắt đầu áp dụng không được để trống")
  private Date ngayBatDauApDung;

  @NotNull(message = "Ngày kết thúc áp dụng không được để trống")
  private Date ngayKetThucApDung;

  @NotNull(message = "Trạng thái không được để trống")
  private Integer trangThaiMaGiamGia;

  @NotNull(message = "Hình thức giảm giá không được để trống")
  private Long hinhThucGiamGiaId;

  private String loaiMaGiamGia;

}