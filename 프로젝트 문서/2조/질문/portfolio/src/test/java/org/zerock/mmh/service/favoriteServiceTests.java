//package org.zerock.mmh.service;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.zerock.mmh.dto.PageRequestDTO;
//import org.zerock.mmh.dto.PageResultDTO;
//import org.zerock.mmh.dto.UserFavoriteDTO;
//import org.zerock.mmh.entity.UserFavorite;
//
//
//@SpringBootTest
//public class favoriteServiceTests {
//    @Autowired
//    private favoriteService service;
//
//    @Test
//    public void testRegister(){
//        UserFavoriteDTO userFavoriteDTO = UserFavoriteDTO.builder()
//                .userFavNo("UF01")
//                .userMemNo("UM01")
//                .manuInfoNo("MI01")
//                .productNo("PN01")
//                .build();
//
//       System.out.print(service.register(userFavoriteDTO));
//    }
//
//    @Test
//    public void testList(){
//
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
//        PageResultDTO<UserFavoriteDTO, UserFavorite> resultDTO = service.getList(pageRequestDTO);
//
//        System.out.println("PREV: "+resultDTO.isPrev());
//        System.out.println("NEXT: "+resultDTO.isNext());
//        System.out.println("total " + resultDTO.getTotalPage());
//
//        System.out.println("-------------------------------------");
//        for(UserFavoriteDTO userFavoriteDTO : resultDTO.getDtoList()){
//            System.out.println(userFavoriteDTO);
//        }
//
//        System.out.println("======================================");
//        resultDTO.getPageList().forEach(i -> System.out.print(i));
//    }
//}
