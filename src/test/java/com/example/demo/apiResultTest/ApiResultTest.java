package com.example.demo.apiResultTest;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.ApiResult;
import com.example.demo.repository.ApiResultRepository;

@SpringBootTest
public class ApiResultTest {

	@Autowired
	ApiResultRepository repository;
	
	@Test
	public void 등록() {
		ApiResult apiResult = ApiResult.builder()
							  .no(1)
							  .result_code("01")
							  .result_msg("OK")
							  .total_count(10)
							  .build();
		repository.save(apiResult);
	}
	
	@Test
	public void 조회() {
		Optional<ApiResult> result = repository.findById(1);
		System.out.println(result.get());
	}
	
	@Test
	public void 수정() {
		Optional<ApiResult> result = repository.findById(1);
		ApiResult apiResult = result.get();
		
		apiResult.setResult_msg("NO");
		
		repository.save(apiResult);
	}
	
	@Test
	public void 삭제() {
		repository.deleteById(1);
	}
}
