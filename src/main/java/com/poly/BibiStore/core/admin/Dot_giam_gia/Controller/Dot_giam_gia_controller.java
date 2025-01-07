package com.poly.BibiStore.core.admin.Dot_giam_gia.Controller;


import com.poly.BibiStore.core.admin.Ma_giam_gia.dto.CreatMaGiamGia;
import com.poly.BibiStore.entity.DotGiamGia;
import com.poly.BibiStore.entity.MaGiamGia;
import com.poly.BibiStore.repository.impl.DotGiamGiaRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
public class Dot_giam_gia_controller {

  @Autowired
  private DotGiamGiaRepository dotGiamGiaRepository;

  public Dot_giam_gia_controller(DotGiamGiaRepository dotGiamGiaRepository) {
    this.dotGiamGiaRepository = dotGiamGiaRepository;
  }

  @GetMapping("dotgiamgia/list")
  public String listDotGiamGia(
      Model model,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "5") int size) {

    // Gọi findAll để lấy danh sách DotGiamGia
    Page<DotGiamGia> dotGiamGiaPage = dotGiamGiaRepository.findAll(PageRequest.of(page - 1, size));
    System.out.println(dotGiamGiaPage);
    // Truyền dữ liệu vào model
    model.addAttribute("list", dotGiamGiaPage);
    return "admin/Dot_giam_gia/list";
  }

  @GetMapping("dotgiamgia/view/{id}")
  public String updateDotagiamgia(@PathVariable int id, Model model) {
    dotGiamGiaRepository.findById(id).ifPresent(dotGiamGia ->
        model.addAttribute("updateDotagiamgia", dotGiamGia));
    return "admin/Dot_giam_gia/viewAndUpdate";
  }

  @GetMapping("dotgiamgia/add")
  public String addDotgiamgia(Model model) {
    model.addAttribute("newDotagiamgia", new DotGiamGia());
    return "admin/Dot_giam_gia/createDotGiamGia";
  }

  @PostMapping("dotgiamgia/add")
  public String addMagiamgia(@ModelAttribute("newDotagiamgia") DotGiamGia dotGiamGia,
      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      // Nếu có lỗi, truyền lại thông báo lỗi vào model
      redirectAttributes.addFlashAttribute("message", "Có lỗi khi nhập thông tin." +bindingResult.getFieldError());
      redirectAttributes.addFlashAttribute("messageType", "error");
      return "redirect:/admin/dotgiamgia/add";  // Quay lại trang tạo mã giảm giá
    }

    if (dotGiamGia != null) {
      try {
        DotGiamGia newDotGiamGia = new DotGiamGia();
        newDotGiamGia.setDonToiThieu(dotGiamGia.getDonToiThieu());
        newDotGiamGia.setGiamToiDa(dotGiamGia.getGiamToiDa());
        newDotGiamGia.setGiaTriGiam(dotGiamGia.getGiaTriGiam());
        if (dotGiamGia.getHinhThucGiamGiaId() == 1){
          newDotGiamGia.setLoaiVoucher(1);
        }else {
          newDotGiamGia.setLoaiVoucher(0);
        }
        newDotGiamGia.setMa(dotGiamGia.getMa());
        newDotGiamGia.setSoLuong(dotGiamGia.getSoLuong());
        newDotGiamGia.setSoLanSuDung(dotGiamGia.getSoLanSuDung());
        newDotGiamGia.setNgayBatDau(dotGiamGia.getNgayBatDau());
        newDotGiamGia.setNgayKetThuc(dotGiamGia.getNgayKetThuc());
        newDotGiamGia.setTen(dotGiamGia.getTen());
        newDotGiamGia.setTrangThai(dotGiamGia.getTrangThai());
        newDotGiamGia.setHinhThucGiamGiaId(dotGiamGia.getHinhThucGiamGiaId());
        newDotGiamGia.setNgayTao(LocalDate.now().atStartOfDay());
        dotGiamGiaRepository.save(newDotGiamGia);

        redirectAttributes.addFlashAttribute("message", "Thêm đợt giảm giá thành công!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/admin/dotgiamgia/list";
      } catch (Exception e) {
        redirectAttributes.addFlashAttribute("message", "Lỗi khi thêm đợt giảm giá. Vui lòng thử lại!");
        redirectAttributes.addFlashAttribute("messageType", "error");
      }
    } else {
      redirectAttributes.addFlashAttribute("message", "Lỗi khi thêm đợt giảm giá. Vui lòng thử lại!");
      redirectAttributes.addFlashAttribute("messageType", "error");
    }

    // Quay lại danh sách mã giảm giá
    return "redirect:/admin/dotgiamgia/list";
  }

  @PostMapping("dotgiamgia/delete/{id}")
  public String delete(@PathVariable int id) {
    try {
      Optional<DotGiamGia> newdotgiamgia = dotGiamGiaRepository.findById(id);
      DotGiamGia dotgiamGia = newdotgiamgia.get();
      if (dotgiamGia.getTrangThai() == 1){
        dotgiamGia.setTrangThai(0);
        dotGiamGiaRepository.save(dotgiamGia);
      }else {
        dotgiamGia.setTrangThai(1);
        dotGiamGiaRepository.save(dotgiamGia);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "redirect:/admin/dotgiamgia/list";
  }


  @PostMapping("dotgiamgia/update/{id}")
  public String updateMagiamgia(@PathVariable int id, @ModelAttribute("updateDotagiamgia") DotGiamGia dotGiamGia,
      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (dotGiamGiaRepository.findById(id).isEmpty()) {
      dotGiamGia.setId(id);
    }
    if (bindingResult.hasErrors()) {
      // Nếu có lỗi, truyền lại thông báo lỗi vào model
      redirectAttributes.addFlashAttribute("message", "Có lỗi khi nhập thông tin.");
      redirectAttributes.addFlashAttribute("messageType", "error");
      return "redirect:/admin/dotgiamgia/viewAndUpdate";  // Quay lại trang tạo mã giảm giá
    }

    if (dotGiamGia != null) {
      try {
        if (dotGiamGia.getHinhThucGiamGiaId() == 1){
          dotGiamGia.setLoaiVoucher(1);
        }else {
          dotGiamGia.setLoaiVoucher(0);
        }
        dotGiamGia.setHinhThucGiamGiaId(dotGiamGia.getHinhThucGiamGiaId());
        dotGiamGia.setNgaySua(LocalDate.now().atStartOfDay());
        dotGiamGiaRepository.save(dotGiamGia);

        redirectAttributes.addFlashAttribute("message", "Cập nhật đợt giảm giá thành công!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/admin/dotgiamgia/list";
      } catch (Exception e) {
        redirectAttributes.addFlashAttribute("message", "Lỗi khi Cập nhật đợt giảm giá. Vui lòng thử lại!");
        redirectAttributes.addFlashAttribute("messageType", "error");
      }
    } else {
      redirectAttributes.addFlashAttribute("message", "Lỗi khi Cập nhật đợt giảm giá. Vui lòng thử lại!");
      redirectAttributes.addFlashAttribute("messageType", "error");
    }

    // Quay lại danh sách mã giảm giá
    return "redirect:/admin/dotgiamgia/list";
  }
}
