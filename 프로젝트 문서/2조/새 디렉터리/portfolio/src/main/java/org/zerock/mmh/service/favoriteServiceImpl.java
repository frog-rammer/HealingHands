package org.zerock.mmh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.mmh.dto.PageRequestDTO;
import org.zerock.mmh.dto.PageResultDTO;
import org.zerock.mmh.dto.UserFavoriteDTO;
import org.zerock.mmh.entity.UserFavorite;
import org.zerock.mmh.repository.UserFavoriteRepository;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class favoriteServiceImpl implements favoriteService {

    private final UserFavoriteRepository repository;

    @Override
    public String register(UserFavoriteDTO dto) {
        log.info("UserFavorite...");
        log.info(dto);

        UserFavorite entity = dtoToEntity(dto);
        log.info(entity);
        repository.save(entity);

        return entity.getUserFavoriteId();

    }

    @Override
    public PageResultDTO<UserFavoriteDTO, UserFavorite> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("userFavoriteId").descending());

        Page<UserFavorite> result = repository.findAll(pageable);

        Function<UserFavorite, UserFavoriteDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

}
