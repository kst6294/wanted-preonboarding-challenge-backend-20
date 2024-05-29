package com.wanted.preonboarding.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    /**
     * 데이터를 초기화 하는 코드
     * 서버를 시작할때 마다 데이터를 넣기 때문에
     * 데이터 추가를 원치 않으시면 주석 처리 하세요.
     *
     *
     * 데이터 생성 수는 DataGeneratorRegister 에 size 수를 조정하세요.
     *
     * User 객체 생성시 포스트맨으로 Test를 쉽게하기 위해
     * 항상 master 계정이 맨처음 생성 됨
     * UserDataInitializer 참고
     *
     *
     * @param args
     * @throws Exception
     */

    @Override
    public void run(String... args) throws Exception {
//        for (DataGenerator generator : DataProvider.getGenerators()) {
//            int size = DataProvider.getSize(generator);
//            generator.generate(size);
//        }
    }

}
