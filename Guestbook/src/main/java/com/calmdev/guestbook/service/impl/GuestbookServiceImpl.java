package com.calmdev.guestbook.service.impl;

import com.calmdev.guestbook.dto.GuestbookDto;
import com.calmdev.guestbook.dto.PageRequestDto;
import com.calmdev.guestbook.dto.PageResultDto;
import com.calmdev.guestbook.entity.Guestbook;
import com.calmdev.guestbook.repository.GuestbookRepository;
import com.calmdev.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDto dto) {
        log.info("Dto ------------------------");
        log.info(String.valueOf(dto));

        Guestbook entity = dtoToEntity(dto);

        log.info(String.valueOf(entity));

        guestbookRepository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDto<GuestbookDto, Guestbook> getList(PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.getPageable(Sort.by("gno").descending());
        Page<Guestbook> result = guestbookRepository.findAll(pageable);

        Function<Guestbook, GuestbookDto> fn = (this::entityToDto);

        return new PageResultDto<>(result, fn);
    }

    @Override
    public GuestbookDto read(Long gno) {

        Optional<Guestbook> result = guestbookRepository.findById(gno);

        return result.isPresent()? entityToDto(result.get()) : null;
    }

    @Override
    public Guestbook dtoToEntity(GuestbookDto dto) {
        return GuestbookService.super.dtoToEntity(dto);
    }

    @Override
    public GuestbookDto entityToDto(Guestbook entity) {
        return GuestbookService.super.entityToDto(entity);
    }
}
