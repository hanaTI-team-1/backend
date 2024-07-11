package kr.ac.kopo.jeonse.domain.test.service;

import kr.ac.kopo.jeonse.domain.test.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestMapper testMapper;

    public int test() {
        return testMapper.test();
    }
}
