package limit.user.logic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lee Tae Su
 * @version 1.0
 * @project limit-user-test
 * @since 2018-07-16
 * <p>
 * 트랜잭션의 격리레벨을 높여서 처리한다.
 * 
 * 현재 트랜잭션이 읽은 것을 다른 트랜잭션이 수정을 못할 뿐이지
 * 읽은 값에 대해 +1을 하는 시점이 항상 최신의 counter 값이라고는 보장 할 수 없다.
 * 
 */
@Service(value = "LimitedUserServiceByIsolationControl")
public class LimitedUserServiceByIsolationControl implements LimitUserService {
	public static final Long LIMIT_COUNT = 1000L;
	
	private LimitedUserRepository limitedUserRepository;
	private CountRepository countRepository;
	private final Long counterId;
	
	public LimitedUserServiceByIsolationControl(LimitedUserRepository limitedUserRepository, CountRepository countRepository) {
		this.limitedUserRepository = limitedUserRepository;
		this.countRepository = countRepository;
		Count count = new Count();
		count.setCount(1L);
		count = countRepository.save(count);
		counterId = count.getId();
	}
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void addLimitedUser() {
		if(!isFinished()) {
			limitedUserRepository.save(new LimitedUser());
			
			Count counter = countRepository.findById(counterId).orElseThrow(IllegalStateException::new);
			counter.setCount(counter.getCount() + 1);
			countRepository.save(counter);
		}
	}
	
	public Long getLimittedUserTotalCount() {
		return limitedUserRepository.count();
	}
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Boolean isFinished() {
		Count counter = countRepository.findById(counterId).orElseThrow(IllegalStateException::new);
		return counter.getCount() > LIMIT_COUNT;
	}
}
