package poly.edu.asmjava5.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import poly.edu.asmjava5.model.ProductDto;
import poly.edu.asmjava5.service.CategoryService;
import poly.edu.asmjava5.service.ProductService;
import poly.edu.asmjava5.service.StorageService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    StorageService storageService;

    // hiển thị danh sách categories
    @ModelAttribute("categories")
    public List<CategoryDto> getCategories() {
        return categoryService.findAll().stream().map(item -> {
            CategoryDto dto = new CategoryDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).toList();
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("product", new ProductDto());
        return "admin/products/addOrEdit";
    }

    @GetMapping("edit/{productId}")
    public String edit(
            ModelMap model
            , RedirectAttributes redirectAttributes
            , @PathVariable("productId") Long productId
    ) {
        Optional<Product> opt = productService.findById(productId);
        ProductDto dto = new ProductDto();
        if (opt.isPresent()) {
            Product entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            /*
                !hứng dữ liệu của categoryId
                !Vì bên product hn có kiểu là object còn bên dto hn lại là một thuộc tính bình thường
                !nên ko thể sao chép được nên mùng dùng  entity.getCategory().getCategoryId(); làm cầu nối
                !để lấy giá trị của categoryId rồi gắng vô dto
            */
            Long id = entity.getCategory().getCategoryId();
            dto.setCategoryId(id);
            System.out.println(dto.getCategoryId());
//            dto.setIdEdit(true); // cho phép sửa đổi thông tin
            model.addAttribute("product", dto);
            return "/admin/products/addOrEdit";
        }
        redirectAttributes.addFlashAttribute("message", "Product is not existed!");

        return "redirect:/admin/products/add";
    }

    @GetMapping("delete/{productId}")
    public String delete(@PathVariable("productId") Long productId
            ,ModelMap model) {
        productService.deleteById(productId);
        model.addAttribute("message", "Product is deleted!");
        return "forward:/admin/products";
    }

    @PostMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap model
            , @Valid @ModelAttribute("product") ProductDto dto
            , BindingResult result
            , RedirectAttributes redirectAttributes) {

        // kiểu tra dữ liệu nếu ko nhập mà nhấn lưu
        if (result.hasErrors()) {
            return "/admin/products/addOrEdit";
        }

        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);

        Category category = new Category();
        category.setCategoryId(dto.getCategoryId());
        entity.setCategory(category);
        entity.setEnteredDate(new Date());

        if (!dto.getImageFile().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            entity.setImage(storageService.getStorageFilename(dto.getImageFile(), uuString));
            storageService.store(dto.getImageFile(), entity.getImage());
        }

        entity = productService.save(entity);

        if (entity != null) {
            redirectAttributes.addFlashAttribute("message", "Product is saved!");
        }

        return "redirect:/admin/products";
    }

    @GetMapping("")
    public String list(
            ModelMap model,
            @RequestParam(value = "pageNo", defaultValue = "0") Optional<Integer> pageNo,
            @RequestParam(name = "name", required = false, defaultValue = "") String name
    ) {
        Pageable pageable = PageRequest.of(pageNo.orElse(0), 5);

        model.addAttribute("name", name);
        Page<Product> page = productService.findAllByNameLike("%"+name+"%",pageable);

//        List<Category> list = categoryService.findAll();
        model.addAttribute("products", page);
        return "admin/products/listProduct";
    }

    @GetMapping("search")
    public String search(
            ModelMap model,
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "pageNo", defaultValue = "0") Optional<Integer> pageNo
    ) {
        Pageable pageable = PageRequest.of(pageNo.orElse(0), 5);
        Page<Product> list = null;
        if (StringUtils.hasText(name)) {
            list = productService.findAllByNameLike("%"+name+"%",pageable);
        }else {
            list = productService.findAll(pageable);
        }
        model.addAttribute("name", name);
        model.addAttribute("products", list);
        return "admin/products/listProduct";
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
