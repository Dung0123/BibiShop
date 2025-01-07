package com.poly.BibiStore.core.admin.Ma_giam_gia.Controller;

import com.poly.BibiStore.core.admin.Ma_giam_gia.dto.CreatMaGiamGia;
import com.poly.BibiStore.entity.MaGiamGia;
import com.poly.BibiStore.repository.impl.MaGiamGiaRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/")
public class Ma_giam_gia_controller {

  private final MaGiamGiaRepository maGiamGiaRepository;


  public Ma_giam_gia_controller(MaGiamGiaRepository maGiamGiaRepository) {
    this.maGiamGiaRepository = maGiamGiaRepository;
  }

  @GetMapping("MaGiamGia/list")
  public String getAllvoucher(Model model,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "5") int size) {
    Page<MaGiamGia> maGiamGia = maGiamGiaRepository.findAllMaGiamGia(
        PageRequest.of(page - 1, size));
    model.addAttribute("list", maGiamGia);

    return "admin/ma-giam-gia/list";
  }

  @PostMapping("MaGiamGia/delete/{id}")
  public String delete(@PathVariable int id) {
    try {
      Optional<MaGiamGia> newMgiamgia = maGiamGiaRepository.findById(id);
      MaGiamGia magiamGia = newMgiamgia.get();
      if (magiamGia.getTrangThai() == 1){
        magiamGia.setTrangThai(0);
        maGiamGiaRepository.save(magiamGia);
      }else {
        magiamGia.setTrangThai(1);
        maGiamGiaRepository.save(magiamGia);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "redirect:/admin/MaGiamGia/list";
  }


  @GetMapping("MaGiamGia/add")
  public String addMagiamgia(Model model) {
    model.addAttribute("createMagiamgia", new CreatMaGiamGia());
    return "admin/ma-giam-gia/createMagiamgia";
  }

  @GetMapping("/MaGiamGia/view/{id}")
  public String updateMagiamgia(@PathVariable int id, Model model) {
    maGiamGiaRepository.findById(id).ifPresent(maGiamGia ->
        model.addAttribute("updateMagiamgia", maGiamGia));
    return "admin/ma-giam-gia/viewAndUpdate";
  }

  @PostMapping("MaGiamGia/add")
  public String addMagiamgia(@ModelAttribute("createMagiamgia") CreatMaGiamGia creatMaGiamGia,
      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      // Nếu có lỗi, truyền lại thông báo lỗi vào model
      redirectAttributes.addFlashAttribute("message", "Có lỗi khi nhập thông tin.");
      redirectAttributes.addFlashAttribute("messageType", "error");
      return "redirect:/admin/MaGiamGia/createMagiamgia";  // Quay lại trang tạo mã giảm giá
    }

    if (creatMaGiamGia != null) {
      try {
        MaGiamGia newMaGiamGia = new MaGiamGia();
        newMaGiamGia.setDonToiThieu(creatMaGiamGia.getGiaTriDonToiThieu());
        newMaGiamGia.setGiamToiDa(creatMaGiamGia.getGiaTriGiamToiDa());
        newMaGiamGia.setGiaTriGiam(creatMaGiamGia.getGiaTriGiamGia());
        if (creatMaGiamGia.getHinhThucGiamGiaId() == 1){
          newMaGiamGia.setLoaiVoucher(1);
        }else {
          newMaGiamGia.setLoaiVoucher(0);
        }
        newMaGiamGia.setMa(creatMaGiamGia.getMaGiamGia());
        newMaGiamGia.setSoLuong(creatMaGiamGia.getTongSoLuong());
        newMaGiamGia.setSoLanSuDung(creatMaGiamGia.getSoLanSuDungToiDa());
        newMaGiamGia.setNgayBatDau(creatMaGiamGia.getNgayBatDauApDung());
        newMaGiamGia.setNgayKetThuc(creatMaGiamGia.getNgayKetThucApDung());
        newMaGiamGia.setTen(creatMaGiamGia.getTenMaGiamGia());
        newMaGiamGia.setTrangThai(creatMaGiamGia.getTrangThaiMaGiamGia());
        newMaGiamGia.setHinhThucGiamGiaId(creatMaGiamGia.getHinhThucGiamGiaId());
        newMaGiamGia.setNgayTao(Date.valueOf(LocalDate.now()));
        maGiamGiaRepository.save(newMaGiamGia);

        redirectAttributes.addFlashAttribute("message", "Thêm mã giảm giá thành công!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/admin/MaGiamGia/list";
      } catch (Exception e) {
        redirectAttributes.addFlashAttribute("message", "Lỗi khi thêm mã giảm giá. Vui lòng thử lại!");
        redirectAttributes.addFlashAttribute("messageType", "error");
      }
    } else {
      redirectAttributes.addFlashAttribute("message", "Lỗi khi thêm mã giảm giá. Vui lòng thử lại!");
      redirectAttributes.addFlashAttribute("messageType", "error");
    }

    // Quay lại danh sách mã giảm giá
    return "redirect:/admin/MaGiamGia/list";
  }

  @PostMapping("MaGiamGia/update/{id}")
  public String updateMagiamgia(@PathVariable int id, @ModelAttribute("updateMagiamgia") MaGiamGia updateMagiamgia,
      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (maGiamGiaRepository.findById(id).isEmpty()) {
      updateMagiamgia.setId(id);
    }
    if (bindingResult.hasErrors()) {
      // Nếu có lỗi, truyền lại thông báo lỗi vào model
      redirectAttributes.addFlashAttribute("message", "Có lỗi khi nhập thông tin.");
      redirectAttributes.addFlashAttribute("messageType", "error");
      return "redirect:/admin/MaGiamGia/createMagiamgia";  // Quay lại trang tạo mã giảm giá
    }

    if (updateMagiamgia != null) {
      try {
        if (updateMagiamgia.getHinhThucGiamGiaId() == 1){
          updateMagiamgia.setLoaiVoucher(1);
        }else {
          updateMagiamgia.setLoaiVoucher(0);
        }
        updateMagiamgia.setHinhThucGiamGiaId(updateMagiamgia.getHinhThucGiamGiaId());
        updateMagiamgia.setNgaySua(Date.valueOf(LocalDate.now()));
        maGiamGiaRepository.save(updateMagiamgia);

        redirectAttributes.addFlashAttribute("message", "Cập nhật mã giảm giá thành công!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/admin/MaGiamGia/list";
      } catch (Exception e) {
        redirectAttributes.addFlashAttribute("message", "Lỗi khi Cập nhật mã giảm giá. Vui lòng thử lại!");
        redirectAttributes.addFlashAttribute("messageType", "error");
      }
    } else {
      redirectAttributes.addFlashAttribute("message", "Lỗi khi Cập nhật mã giảm giá. Vui lòng thử lại!");
      redirectAttributes.addFlashAttribute("messageType", "error");
    }

    // Quay lại danh sách mã giảm giá
    return "redirect:/admin/MaGiamGia/list";
  }
}