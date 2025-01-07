package com.poly.BibiStore.core.admin.don_hang;
import com.poly.BibiStore.Constant.HoaDonStatus;
import com.poly.BibiStore.entity.HoaDon;
import com.poly.BibiStore.entity.HoaDonChiTiet;
import com.poly.BibiStore.repository.impl.HoaDonChiTietRepository;
import com.poly.BibiStore.repository.impl.HoaDonRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/admin")
public class HoaDonController {


  private final HoaDonRepository  hoaDonRepository;
  private final HoaDonChiTietRepository hoaDonChiTietRepository;
  public HoaDonController(HoaDonRepository hoaDonRepository,
      HoaDonChiTietRepository hoaDonChiTietRepository) {
    this.hoaDonRepository = hoaDonRepository;
    this.hoaDonChiTietRepository = hoaDonChiTietRepository;
  }


  @GetMapping("/hoadon/list")
  public String listHoaDon(
      @RequestParam(value = "id", required = false) Long id,
      @RequestParam(value = "status", required = false) HoaDonStatus status,
      @RequestParam(value = "page", defaultValue = "0") int page,
      Model model) {

    Page<HoaDon> hoaDons = hoaDonRepository.searchHoaDon(id, status, PageRequest.of(page, 5));
    model.addAttribute("hoaDons", hoaDons);
    model.addAttribute("id", id);
    model.addAttribute("status", status);

    return "admin/cap-nhat-don-hang/list";
  }

  @PostMapping("/hoadon/cancel")
  public String cancelHoaDon(@RequestParam("id") int id) {
    // Logic to cancel the invoice, e.g., setting status to CANCELLED
    Optional<HoaDon> hoaDon = hoaDonRepository.findById(id);
    if (hoaDon.isPresent()) {
      HoaDon newhoaDon = hoaDon.get();
      newhoaDon.setStatus(HoaDonStatus.CANCELLED);
      hoaDonRepository.save(newhoaDon);
    }
    return "redirect:/hoadon/list";
  }
  @GetMapping("/hoadon/xulydonhang/{id}")
  public String xulyDonhang(@PathVariable int id, Model model) {
    Optional<HoaDon> hoaDonOptional = hoaDonRepository.findById(id);

    if (hoaDonOptional.isPresent()) {
      HoaDon hoaDon = hoaDonOptional.get();
      List<HoaDonChiTiet> chiTietHoaDon = hoaDon.getChiTietHoaDon(); // Directly get the list

      model.addAttribute("chiTietHoaDon", chiTietHoaDon); // List added to model
      model.addAttribute("hoaDon", hoaDon);
    } else {
      // Handle the case where the order is not found
      model.addAttribute("errorMessage", "Order not found.");
      return "error-page"; // Redirect to an error page or handle the error as appropriate
    }

    return "admin/cap-nhat-don-hang/xulydonhang";
  }
}
