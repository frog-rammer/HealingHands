//package org.zerock.mmh.service;
//
//import org.zerock.mmh.dto.PageRequestDTO;
//import org.zerock.mmh.dto.UserFavoriteDTO;
//import org.zerock.mmh.entity.UserFavorite;
//
//public interface favoriteService {
//    String register(UserFavoriteDTO dto);
//
//    default UserFavorite dtoToEntity(UserFavoriteDTO dto) {
//        return null;
//    }
//
//    default UserFavoriteDTO entityToDto(UserFavorite entity) {
//
//        UserFavoriteDTO dto = UserFavoriteDTO.builder()
//                .userFavNo(entity.getUserFavNo())
//                .userMemNo(entity.getUserMemNo())
//                .manuInfoNo(entity.getManuInfoNo())
//                .productNo(entity.getProductNo())
//                .build();
//
//        return dto;
//    }
//
//    Object getList(PageRequestDTO pageRequestDTO);
//}
