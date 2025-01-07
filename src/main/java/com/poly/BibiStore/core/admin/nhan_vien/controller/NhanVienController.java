package com.poly.BibiStore.core.admin.nhan_vien.controller;

import com.poly.BibiStore.entity.DiaChi;
import com.poly.BibiStore.entity.NhanVien;
import com.poly.BibiStore.repository.impl.DiaChiRepository;
import com.poly.BibiStore.repository.impl.NhanVienRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/")
public class NhanVienController {

  private final DiaChiRepository diaChiRepository;
  private NhanVienRepository nhanVienRepository;

  @Autowired
  public NhanVienController(NhanVienRepository nhanVienRepository,
      DiaChiRepository diaChiRepository) {
    this.nhanVienRepository = nhanVienRepository;
    this.diaChiRepository = diaChiRepository;
  }


  @GetMapping("nhanvien/list")
  public String listNhanVien(
      Model model,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "5") int size) {

    // Gọi findAll để lấy danh sách nhân viên
    Page<NhanVien> nhanViens = nhanVienRepository.findAll(PageRequest.of(page - 1, size));

    // Truyền dữ liệu vào model
    model.addAttribute("list", nhanViens);
    return "admin/nhan-vien/list";
  }

  @GetMapping("nhanvien/search")
  public String searchNhanVien(
      Model model,
      @RequestParam(name = "keyword") String keyword,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "5") int size) {

    // Gọi findByKeyword để tìm kiếm nhân viên
    Page<NhanVien> nhanViens = nhanVienRepository.findByKeyword(keyword.trim(),
        PageRequest.of(page - 1, size));

    // Truyền dữ liệu vào model
    model.addAttribute("list", nhanViens);
    model.addAttribute("keyword", keyword); // Giữ lại keyword trên giao diện
    return "admin/nhan-vien/list";
  }

  // View details of a specific NhanVien
  @GetMapping("nhanvien/view/{id}")
  public String viewNhanVien(@PathVariable("id") int id, Model model,
      RedirectAttributes redirectAttributes) {
    try {
      Optional<NhanVien> nhanVien = nhanVienRepository.findById(id);
      if (nhanVien.isPresent()) {
        model.addAttribute("nhanVien", nhanVien.get());
        return "admin/nhan-vien/view"; // Thymeleaf template for displaying NhanVien details
      } else {
        redirectAttributes.addFlashAttribute("error", "Không tìm thấy nhân viên với ID: " + id);
        return "redirect:/errorPage/404"; // Redirect to error page if not found
      }
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
      return "redirect:/errorPage/403"; // Redirect to error page in case of exception
    }
  }


  @GetMapping("nhanvien/add")
  public String addNhanVien(Model model) {
    model.addAttribute("nhanVien", new NhanVien());
    return "admin/nhan-vien/createNhanvien";
  }

  @PostMapping("nhanvien/delete/{id}")
  public String softDeleteNhanVien(@PathVariable("id") int id,
      RedirectAttributes redirectAttributes) {
    // Your method implementation
    try {
      Optional<NhanVien> nhanVien = nhanVienRepository.findById(id);
      NhanVien newNhanVien = nhanVien.get();
      if (nhanVien != null) {
        newNhanVien.setTrangThai(0); // Assuming 0 means 'Inactive' and 1 means 'Active'
        newNhanVien.setNgayTao(LocalDateTime.now());
        nhanVienRepository.save(newNhanVien); // Save the updated status
        redirectAttributes.addFlashAttribute("success", "Xóa nhân viên thành công (xóa mềm)!");
      } else {
        redirectAttributes.addFlashAttribute("error", "Không tìm thấy nhân viên với ID: " + id);
      }
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error",
          "Đã xảy ra lỗi khi cập nhật trạng thái của nhân viên.");
    }
    return "redirect:/list"; // Redirect back to list page
  }


    @PostMapping("nhanvien/add")
    public String addNhanVien(@ModelAttribute("nhanVien") NhanVien nhanVien,
        RedirectAttributes redirectAttributes,
        BindingResult bindingResult, Model model) {
        NhanVien ErrorNhanvien = new NhanVien();
        ErrorNhanvien = nhanVien;
        // Kiểm tra null

        if (nhanVien == null) {
            redirectAttributes.addFlashAttribute("error", "Thông tin nhân viên không được để trống.");
            return "redirect:/admin/nhanvien/add";
        }

        // Kiểm tra trùng tài khoản
        if (nhanVienRepository.existsByTaiKhoan(nhanVien.getTaiKhoan())) {
            redirectAttributes.addFlashAttribute("error", "Tài khoản đã tồn tại.");
             ErrorNhanvien.setTaiKhoan("");
             model.addAttribute("nhanVien", ErrorNhanvien);
            return "redirect:/admin/nhanvien/add";
        }
      // Kiểm tra trùng tài khoản
      if (nhanVienRepository.existsByTaiKhoan(nhanVien.getTaiKhoan())) {
        redirectAttributes.addFlashAttribute("error", "Tài khoản đã tồn tại.");
        ErrorNhanvien.setTaiKhoan("");
        model.addAttribute("nhanVien", ErrorNhanvien);
        return "redirect:/admin/nhanvien/add";
      }

        if (nhanVienRepository.existsByEmail(nhanVien.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email đã tồn tại.");
            ErrorNhanvien.setEmail("");
            model.addAttribute("nhanVien", ErrorNhanvien);
            return "redirect:/admin/nhanvien/add";
        }

        if (nhanVienRepository.existsBySdt(nhanVien.getSdt())) {
            redirectAttributes.addFlashAttribute("error", "Số điện thoại đã tồn tại.");
            ErrorNhanvien.setSdt("");
            model.addAttribute("nhanVien", ErrorNhanvien);
            return "redirect:/admin/nhanvien/add";
        }

        // Kiểm tra họ tên không được để trống
        if (nhanVien.getTen() == null || nhanVien.getHo().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Họ, tên không được để trống.");
            return "redirect:/admin/nhanvien/add";
        }

        // Kiểm tra email hợp lệ
        if (nhanVien.getEmail() == null || !nhanVien.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            redirectAttributes.addFlashAttribute("error", "Email không hợp lệ.");
            ErrorNhanvien.setEmail("");
            model.addAttribute("nhanVien", ErrorNhanvien);
            return "redirect:/admin/nhanvien/add";
        }

        // Kiểm tra số điện thoại hợp lệ
        if (nhanVien.getSdt() == null || !nhanVien.getSdt().matches("^[0-9]{10}$")) {
            redirectAttributes.addFlashAttribute("error", "Số điện thoại phải gồm 10 chữ số.");
            ErrorNhanvien.setSdt("");
            model.addAttribute("nhanVien", ErrorNhanvien);
            return "redirect:/admin/nhanvien/add";
        }

        // Kiểm tra giới tính
        if (nhanVien.getGioiTinh() == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng chọn giới tính.");
            return "redirect:/admin/nhanvien/add";
        }

        // Lưu thông tin nếu hợp lệ
        diaChiRepository.save(nhanVien.getDiaChi());
        nhanVienRepository.save(nhanVien);

        redirectAttributes.addFlashAttribute("success", "Thêm nhân viên thành công!");
        return "redirect:/admin/nhanvien/list";
    }


  @PostMapping("nhanvien/update/{id}")
  public String updateNhanVien(
      @PathVariable("id") int id,
      @ModelAttribute("nhanVien") NhanVien nhanVien,
      RedirectAttributes redirectAttributes) {

    if (nhanVien != null && nhanVien.getId() != null) {
      try {
        // Find existing employee by ID
        Optional<NhanVien> optionalNhanVienCu = nhanVienRepository.findById(id);

        if (optionalNhanVienCu.isPresent()) {
          NhanVien nhanVienCu = optionalNhanVienCu.get();

          // Retain existing role and status
          nhanVien.setVaiTro(nhanVienCu.getVaiTro());
          nhanVien.setTrangThai(nhanVienCu.getTrangThai());
          nhanVien.setNgaySua(LocalDateTime.now());

          // Update DiaChi if present
          DiaChi diaChi = nhanVien.getDiaChi();
          if (diaChi != null) {
            diaChi.setId(nhanVien.getDiaChi().getId());
            diaChiRepository.save(diaChi);
          }

          // Save the updated NhanVien
          nhanVienRepository.save(nhanVien);
          redirectAttributes.addFlashAttribute("success", "Cập nhật nhân viên thành công!");

        } else {
          redirectAttributes.addFlashAttribute("error", "Nhân viên không tồn tại.");
        }
      } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error",
            "Đã xảy ra lỗi khi cập nhật nhân viên: " + e.getMessage());
        e.printStackTrace();
      }
    } else {
      redirectAttributes.addFlashAttribute("error", "Dữ liệu nhân viên không hợp lệ.");
    }

    return "redirect:/admin/nhanvien/list"; // Redirect to the correct list page after update
  }

}
