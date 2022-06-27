package com.calmdev.guestbook.service;

import com.calmdev.guestbook.dto.GuestbookDto;
import com.calmdev.guestbook.dto.PageRequestDto;
import com.calmdev.guestbook.dto.PageResultDto;
import com.calmdev.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GuestbookServiceTest {

    @Autowired
    private GuestbookService service;

    @Test
    void testRegister() {

        GuestbookDto guestbookDto = GuestbookDto.builder()
                                                .title("Sample Title ...")
                                                .content("Sample Content ...")
                                                .writer("user0")
                                                .build();

        System.out.println(service.register(guestbookDto));
    }

    @Test
    public void testList() {
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                                                      .page(1)
                                                      .size(10)
                                                      .build();

        PageResultDto<GuestbookDto, Guestbook> resultDTO = service.getList(pageRequestDto);

        System.out.println("PREV : " + resultDTO.isPrev());
        System.out.println("NEXT : " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());

        System.out.println("-------------------------------------------------------");
        for (GuestbookDto guestbookDto : resultDTO.getDtoList()) {
            System.out.println(guestbookDto);
        }

        System.out.println("======================================================");
        resultDTO.getPageList()
                 .forEach(i -> System.out.println(i));
    }

    @Test
    public void testSearch() {

        PageRequestDto pageRequestDto = PageRequestDto.builder()
                                                      .page(1)
                                                      .size(10)
                                                      .type("tc") // 검색 조건 t, c, w, tc, tcw ..
                                                      .keyword("한글")
                                                      .build();

        PageResultDto<GuestbookDto, Guestbook> resultDto = service.getList(pageRequestDto);

        System.out.println("PREV : " + resultDto.isPrev());
        System.out.println("NEXT : " + resultDto.isNext());
        System.out.println("TOTAL : " + resultDto.getTotalPage());

        System.out.println("---------------------------------------------------------");

        for (GuestbookDto guestbookDto : resultDto.getDtoList()) {
            System.out.println(guestbookDto);
        }

        System.out.println("======================================================");
        resultDto.getPageList()
                 .forEach(i -> System.out.println(i));

    }

}