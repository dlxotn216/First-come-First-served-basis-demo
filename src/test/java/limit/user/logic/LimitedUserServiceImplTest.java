package limit.user.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Lee Tae Su on 2018-07-16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LimitedUserServiceImplTest {
	
	@Autowired
	@Qualifier("LimitedUserServiceImpl")
	private LimitUserService service;
	
	@Test
	public void multiThreadTest() {
		List<CompletableFuture<Void>> completableFutures = IntStream.rangeClosed(0, 100)
				.mapToObj(value -> CompletableFuture.runAsync(() -> {
					while(!service.isFinished()) {
						service.addLimitedUser();
					}
				}))
				.collect(Collectors.toList());
		
		completableFutures.forEach(CompletableFuture::join);
		System.out.println("Finished");
		
		assertThat(service.getLimittedUserTotalCount()).isEqualTo(LimitedUserServiceImpl.LIMIT_COUNT);
	}
}