package com.calmdev.guestbook.service;

import com.calmdev.guestbook.dto.GuestbookDto;
import com.calmdev.guestbook.dto.PageRequestDto;
import com.calmdev.guestbook.dto.PageResultDto;
import com.calmdev.guestbook.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDto dto);

    PageResultDto<GuestbookDto, Guestbook> getList(PageRequestDto pageRequestDto);

    GuestbookDto read(Long gno);

    void remove(Long gno);

    void modify(GuestbookDto dto);

    default Guestbook dtoToEntity(GuestbookDto dto) {
        return Guestbook.builder()
                        .gno(dto.getGno())
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .writer(dto.getWriter())
                        .build();
    }

    default GuestbookDto entityToDto(Guestbook entity) {
        return GuestbookDto.builder()
                           .gno(entity.getGno())
                           .title(entity.getTitle())
                           .content(entity.getContent())
                           .writer(entity.getWriter())
                           .regDate(entity.getRegDate())
                           .modDate(entity.getModDate())
                           .build();
    }
}
