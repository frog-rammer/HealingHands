package org.zerock.mmh.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mmh.entity.QnaBoard;
import org.zerock.mmh.entity.UserFavorite;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class UserFavoriteRepositoryTests {
    
    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            UserFavorite userFavorite = UserFavorite.builder()
                    .userFavNo("UF0" + i)
                    .userMemNo("UM0" + i)
                    .manuInfoNo("MI0" + i)
                    .productNo("PN0" + i % 10)
                    .build();
            System.out.println(userFavoriteRepository.save(userFavorite));
        });
    }

//    @Test
//    public void updateTest(){
//        Optional<UserFavorite> result = userFavoriteRepository.findById("UF00008");
//
//        if(result.isPresent()){
//            UserFavorite userFavorite = result.get();
//
//            userFavorite.changemanuInfoNo("changed InfoNo");
//            userFavorite.changeproductNo("changed productNo");
//
//            userFavoriteRepository.save(userFavorite);
//        }
//    }


}
