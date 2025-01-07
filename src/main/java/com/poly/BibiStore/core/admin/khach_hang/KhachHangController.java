package com.poly.BibiStore.core.admin.khach_hang;

import com.poly.BibiStore.entity.DiaChi;
import com.poly.BibiStore.entity.KhachHang;
import com.poly.BibiStore.repository.impl.DiaChiRepository;
import com.poly.BibiStore.repository.impl.KhachHangRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class KhachHangController {
    private DiaChiRepository diaChiRepository;
    private KhachHangRepository khachHangRepository;

    @Autowired
    public KhachHangController(DiaChiRepository diaChiRepository,
                               KhachHangRepository khachHangRepository) {
        this.diaChiRepository = diaChiRepository;
        this.khachHangRepository = khachHangRepository;
    }


    @GetMapping("/khachhang/list")
    public String promotions(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
                             @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<KhachHang> KhachHangs = (Page<KhachHang>) khachHangRepository.findAll(
                PageRequest.of(page - 1, size));
        model.addAttribute("list", KhachHangs);
        return "admin/khach-hang/list";
    }

    // View details of a specific KhachHang
    @GetMapping("/khachhang/view/{id}")
    public String viewKhahHang(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<KhachHang> KhachHang = khachHangRepository.findById(id);
            if (KhachHang.isPresent()) {
                model.addAttribute("khachHang", KhachHang.get());
                return "admin/khach-hang/view"; // Thymeleaf template for displaying KhachHang details
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy Khách hàng với ID: " + id);
                return "redirect:/errorPage/404"; // Redirect to error page if not found
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
            return "redirect:/errorPage/403"; // Redirect to error page in case of exception
        }
    }


    // Delete a specific KhachHang

    @PostMapping("/khachhang/delete/{id}")
    public String softDeleteKhahHang(@PathVariable("id") int id,@ModelAttribute("KhachHang") KhachHang khachHang, RedirectAttributes redirectAttributes) {
        // Your method implementation
        try {
            Optional<KhachHang> KhachHang = khachHangRepository.findById(id);
            KhachHang newNhanVien = KhachHang.get();
            if (KhachHang != null) {
                newNhanVien.setTrangThai(0); // Assuming 0 means 'Inactive' and 1 means 'Active'
                khachHangRepository.save(newNhanVien); // Save the updated status
                redirectAttributes.addFlashAttribute("success", "Xóa Khách hàng thành công (xóa mềm)!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy Khách hàng với ID: " + id);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi cập nhật trạng thái của Khách hàng.");
        }
        return "redirect:/list"; // Redirect back to list page
    }

    @PostMapping("/khachhang/update/{id}")
    public String updateKhachHang(@PathVariable int id, @ModelAttribute("KhachHang") KhachHang khachHang, RedirectAttributes redirectAttributes) {
        if (khachHang != null && khachHang.getId() != null) {
            try {
                Optional<KhachHang> optionalKhachHangCu = khachHangRepository.findById(id);
                if (optionalKhachHangCu.isPresent()) {
                    KhachHang nhanVienCu = optionalKhachHangCu.get();
                    khachHang.setVaiTro(nhanVienCu.getVaiTro());
                    khachHang.setTrangThai(nhanVienCu.getTrangThai());
                    khachHang.setNgaySua(LocalDateTime.now());
                    DiaChi diaChi = (DiaChi) khachHang.getDiaChi();
                    if (diaChi != null) {
                        diaChiRepository.save(diaChi);
                    }
                    khachHangRepository.save(khachHang);
                    redirectAttributes.addFlashAttribute("success", "Cập nhật Khách hàng thành công!");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Khách hàng không tồn tại.");
                }
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Dữ liệu Khách hàng không hợp lệ.");
        }
        return "redirect:/admin/khachhang/list";
    }
}
