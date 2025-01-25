package org.zerock.mmh.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mmh.dto.PageRequestDTO;
import org.zerock.mmh.dto.ProductDTO;
import org.zerock.mmh.dto.QnaBoardDTO;
import org.zerock.mmh.entity.ProductImage;
import org.zerock.mmh.repository.ProductImageRepository;
import org.zerock.mmh.service.ProductService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
@Log4j2
@RequiredArgsConstructor //자동주입
public class ProductController {

    private final ProductService service;


    @GetMapping("/")
    public String index() {
        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list...........");
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get.........");
    }

    @PostMapping("/register")
    public String registerPost(ProductDTO productDTO, RedirectAttributes redirectAttributes) {
        log.info("dto..." + productDTO);

        //새로 추가된 entity의 번호
        String productNo = service.register(productDTO);
        redirectAttributes.addFlashAttribute("msg", productNo);
        return "redirect:/product/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(String productNo, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
        log.info("productNo: " + productNo);
        ProductDTO productDTO = service.getProduct(productNo);
        model.addAttribute("dto", productDTO);
    }

    @PostMapping("/remove")
    public String remove(String productNo, RedirectAttributes redirectAttributes) {
        log.info("productNo: " + productNo);
        service.removeWithImage(productNo);
        redirectAttributes.addFlashAttribute("msg", productNo);
        return "redirect:/product/list";
    }
// //첨부파일 수정가능하게
//    @PostMapping("/modify")
//    public String modify(ProductDTO dto,
//                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
//                         RedirectAttributes redirectAttributes) {
//        log.info("post modify.........................");
//        log.info("dto..." + dto);
//
//        service.modify(dto);
//
//        redirectAttributes.addAttribute("page", requestDTO.getPage());
//        redirectAttributes.addAttribute("productNo", dto.getProductNo());
//
//        return "redirect:/product/read";
//    }


    @PostMapping("/modify")
    public String modify(ProductDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("post modify.........................");
        log.info("dto..." + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("productNo", dto.getProductNo());

        return "redirect:/product/read";
    }


}
