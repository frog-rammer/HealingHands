package org.zerock.mmh.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mmh.dto.PageRequestDTO;
import org.zerock.mmh.dto.QnaBoardDTO;
import org.zerock.mmh.service.QnaBoardService;

@Controller
@RequestMapping("/qnaboard")
@Log4j2
@RequiredArgsConstructor//자동 주입을 위한 Annotation
public class QnaBoardController {

    private final QnaBoardService service;

    @GetMapping("/")
    public String index() {
        return "redirect:/qnaboard/list";
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
    public String registerPost(QnaBoardDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto..." + dto);

        //새로 추가된 entity의 번호
        String qnaBoardNo = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", qnaBoardNo);
        return "redirect:/qnaboard/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(String qnaBoardNo, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
        log.info("qnaBoardNo: " + qnaBoardNo);
        QnaBoardDTO dto = service.read(qnaBoardNo);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    public String remove(String qnaBoardNo, RedirectAttributes redirectAttributes) {
        log.info("qnaBoardNo: " + qnaBoardNo);
        service.remove(qnaBoardNo);
        redirectAttributes.addFlashAttribute("msg", qnaBoardNo);
        return "redirect:/qnaboard/list";
    }

    @PostMapping("/modify")
    public String modify(QnaBoardDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("post modify.........................");
        log.info("dto..." + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("qnaBoardNo", dto.getQnaBoardNo());

        return "redirect:/qnaboard/read";
    }

    @PostMapping("/reply")
    public String reply(QnaBoardDTO dto,
                        @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                        RedirectAttributes redirectAttributes) {
        log.info("post modify.........................");
        log.info("dto..." + dto);

        service.reply(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("qnaBoardNo", dto.getQnaBoardNo());

        return "redirect:/qnaboard/read";
    }
}
