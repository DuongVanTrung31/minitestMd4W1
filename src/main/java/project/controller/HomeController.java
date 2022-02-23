package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.model.Product;
import project.service.IProductService;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    IProductService productService;

    @ModelAttribute("products")
    public Iterable<Product> products() {
        return productService.findAll();
    }

    @GetMapping
    public ModelAndView view() {
        ModelAndView modelAndView = new ModelAndView("list");
        if (!products().iterator().hasNext()) {
            modelAndView.addObject("message", "No products!");
            modelAndView.addObject("color", "red");
        }
        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("list");
        Product product = productService.findProductById(id);
        if (product == null) {
            modelAndView.addObject("message", "Id invalid!");
            modelAndView.addObject("color", "red");
        } else {
            productService.deleteProduct(id);
        }
        return modelAndView;
    }

    @GetMapping("view/{id}")
    public ModelAndView view (@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("detail");
        Product product = productService.findProductById(id);
        if (product != null) {
            modelAndView.addObject("product", product);
        } else {
            modelAndView.addObject("message", "Id invalid!");
        }
        return modelAndView;
    }

    @GetMapping("create")
    public ModelAndView createControl(@ModelAttribute Product product){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("product",product);
        return modelAndView;
    }

    @PostMapping("create")
    public ModelAndView create(@ModelAttribute Product product){
        ModelAndView modelAndView = new ModelAndView("create");
        Product productCreate = productService.saveProduct(product);
        if (productCreate != null) {
            modelAndView.addObject("message", "Create successfully!");
        }
        return modelAndView;
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("edit");
        Product product = productService.findProductById(id);
        if (product != null) {
            modelAndView.addObject("product", product);
        } else {
            modelAndView.addObject("message", "Id invalid!");
        }
        return modelAndView;
    }


    @PostMapping("edit/{id}")
    public ModelAndView edit(@ModelAttribute Product product, @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        product.setId(id);
        Product productEdit = productService.saveProduct(product);
        if (productEdit != null) {
            modelAndView.addObject("message", "Update successfully!");
        }
        return modelAndView;
    }

    @PostMapping("search")
    public ModelAndView search(String keyword) {
        ModelAndView modelAndView = new ModelAndView("list");
        List<Product> products = productService.searchProductsByName(keyword);
        modelAndView.addObject("products",products);
        return modelAndView;
    }
}
