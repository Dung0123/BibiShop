package com.poly.BibiStore.core.admin.khach_hang;

import com.poly.BibiStore.entity.DiaChi;
import com.poly.BibiStore.repository.impl.DiaChiRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class DiaChiController {
  private final DiaChiRepository diaChiRepository;

  @Autowired
  public DiaChiController(DiaChiRepository diaChiRepository) {
    this.diaChiRepository = diaChiRepository;
  }

  @GetMapping("/view/editDiaChi/{id}")
  public String showAddressDetails(@PathVariable("id") int id, Model model) {
    Optional<DiaChi> diaChi = diaChiRepository.findById((id));  // Giả sử bạn đã có service để lấy thông tin địa chỉ
    model.addAttribute("diaChi", diaChi.get());
    return "admin/khach-hang/editDiachi";
  }
@PostMapping("/updateDiachi/{id}")
public String updateDiaChi(@PathVariable("id") int id, @ModelAttribute("diaChi") DiaChi diaChi, BindingResult result) {
  System.out.println("Method updateDiaChi() called with ID: " + id);
  if (result.hasErrors()) {
    return "redirect:/khachhang/view/editDiaChi"; // Handling validation errors
  }

  try {
    // Log details to verify the object
    System.out.println("Dia chi: " + diaChi.getDiaChi1());

    Optional<DiaChi> existingDiaChi = diaChiRepository.findById((id));
    if (existingDiaChi.isPresent()) {
      DiaChi diaChiToUpdate = existingDiaChi.get();

      // Update fields
      diaChiToUpdate.setDiaChi1(diaChi.getDiaChi1());
      diaChiToUpdate.setDiaChiCuThe(diaChi.getDiaChiCuThe());
      diaChiToUpdate.setHoVaTen(diaChi.getHoVaTen());
      diaChiToUpdate.setPhuongXa(diaChi.getPhuongXa());
      diaChiToUpdate.setQuanHuyen(diaChi.getQuanHuyen());
      diaChiToUpdate.setThanhPho(diaChi.getThanhPho());
      diaChiToUpdate.setSoDienThoai(diaChi.getSoDienThoai());
      diaChiToUpdate.setTrangThai(diaChi.getTrangThai());

      diaChiRepository.save(diaChiToUpdate);

      int customerId = Math.toIntExact(diaChiToUpdate.getKhachHang().getId());
      return "redirect:/admin/khachhang/view/" + customerId;
    } else {
      return "redirect:/khachhang/view/editDiaChi";
    }
  } catch (Exception e) {
    e.printStackTrace();
    return "redirect:/khachhang/view/editDiaChi";
  }
}

}
