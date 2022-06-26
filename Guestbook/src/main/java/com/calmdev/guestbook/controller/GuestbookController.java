package com.calmdev.guestbook.controller;

import com.calmdev.guestbook.dto.GuestbookDto;
import com.calmdev.guestbook.dto.PageRequestDto;
import com.calmdev.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Slf4j
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    @GetMapping("/")
    public String index() {

        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model) {
        log.info("list......................" + pageRequestDto);

        model.addAttribute("result" , guestbookService.getList(pageRequestDto));
    }

    @GetMapping("register")
    public void register() {
        log.info("register get ... ");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDto dto, RedirectAttributes redirectAttributes) {
        log.info("dto ... " + dto);

        //새로 추가된 엔티티의 번호
        Long gno = guestbookService.register(dto);

        redirectAttributes.addFlashAttribute("msg" , gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping("/read")
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDto requestDto, Model model) {
        log.info("gno : " + gno );

        GuestbookDto dto = guestbookService.read(gno);

        model.addAttribute("dto", dto);
    }
}
