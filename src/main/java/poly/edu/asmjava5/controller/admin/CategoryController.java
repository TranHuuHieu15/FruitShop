package poly.edu.asmjava5.controller.admin;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import poly.edu.asmjava5.domain.Category;
import poly.edu.asmjava5.domain.Product;
import poly.edu.asmjava5.model.CategoryDto;
import poly.edu.asmjava5.service.CategoryService;

import java.util.Optional;

@Controller
@RequestMapping("/admin/categories")
    /*
        !: Trang thêm sản phẩm và danh sách nằm trên 1 trang
    */
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    HttpServletResponse response;

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("category", new CategoryDto());

        //hiển thị danh sách(khi chạy lên là hiện danh sách luôn)
//        List<Category> list = categoryService.findAll();
//        model.addAttribute("categories", list);

        return "admin/categories/addOrEdit";
    }

    @GetMapping("edit/{categoryId}")
    public String edit(ModelMap model
            , RedirectAttributes redirectAttributes
            , @PathVariable("categoryId") Long categoryId) {
        Optional<Category> opt = categoryService.findById(categoryId);
        CategoryDto dto = new CategoryDto();
        if (opt.isPresent()) {
            Category entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIdEdit(true); // cho phép sửa đổi thông tin
            model.addAttribute("category", dto);
            return "/admin/categories/addOrEdit";
        }
        redirectAttributes.addFlashAttribute("message", "Category is not existed!");

        return "redirect:/admin/categories/add";
    }

    @GetMapping("delete/{categoryId}")
    public String delete(@PathVariable("categoryId") Long categoryId
            ,ModelMap model) {
        categoryService.deleteById(categoryId);
        model.addAttribute("message", "Category is deleted!");
        return "forward:/admin/categories";
    }

    @PostMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap model
            , @Valid @ModelAttribute("category") CategoryDto dto
            , BindingResult result
            , RedirectAttributes redirectAttributes) {

        // kiểu tra dữ liệu nếu ko nhập mà nhấn lưu
        if (result.hasErrors()) {
           return "/admin/categories/addOrEdit";
        }

        Category entity = new Category();
        BeanUtils.copyProperties(dto, entity);
        entity = categoryService.save(entity);

        if (entity != null) {
            redirectAttributes.addFlashAttribute("message", "Category is saved!");
        }

        return "redirect:/admin/categories";
    }

    // Ngầm định là khi chạy trang add thì  chạy luôn phần list(danh sách categories) phía dưới luôn
    @GetMapping("")
    public String list(
            ModelMap model,
            @RequestParam(value = "pageNo", defaultValue = "0") Optional<Integer> pageNo,
            @RequestParam(name = "name", required = false, defaultValue = "") String name
    ) {
        Pageable pageable = PageRequest.of(pageNo.orElse(0), 5);

        model.addAttribute("name", name);
        Page<Category> page = categoryService.findAllByNameLike("%"+name+"%",pageable);

//        List<Category> list = categoryService.findAll();
        model.addAttribute("categories", page);
        return "admin/categories/listCategory";
    }

    @GetMapping("search")
    public String search(
            ModelMap model,
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "pageNo", defaultValue = "0") Optional<Integer> pageNo
    ) {
        Pageable pageable = PageRequest.of(pageNo.orElse(0), 5);
        Page<Category> list = null;
        if (StringUtils.hasText(name)) {
            list = categoryService.findAllByNameLike("%"+name+"%",pageable);
        }else {
            list = categoryService.findAll(pageable);
        }
        model.addAttribute("name", name);
        model.addAttribute("categories", list);
        return "admin/categories/listCategory";
    }

}
