package com.calmdev.ex2.controller;

import com.calmdev.ex2.dto.SampleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sample")
@Slf4j
public class SampleController {

    @GetMapping("/ex1")
    public void ex1() {

        log.info("ex....................");
    }

    @GetMapping("/ex2")
    public void ex2(Model model) {
        List<SampleDTO> list = IntStream.rangeClosed(1,20).asLongStream()
                .mapToObj(i -> {
                    return SampleDTO.builder()
                            .sno(i)
                            .first("First.." + i)
                            .last("Last.." + i)
                            .regTime(LocalDateTime.now())
                            .build();
                }).collect(Collectors.toList());

        model.addAttribute("list",list);
    }
}
