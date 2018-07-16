package limit.user.logic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Lee Tae Su
 * @version 1.0
 * @project limit-user-test
 * @since 2018-07-16
 * <p>
 * 
 * 현재 조건에 맞는 LIMIT과 index를 설정하는 형태로 개선 가능할 것 같다.
 */
@Service(value = "LimitedUserServiceBySync")
public class LimitedUserServiceBySync implements LimitUserService {
	public static final Long LIMIT_COUNT = 1000L;
	
	private final AtomicLong index = new AtomicLong(1);
	
	private LimitedUserRepository limitedUserRepository;
	
	public LimitedUserServiceBySync(LimitedUserRepository limitedUserRepository) {
		this.limitedUserRepository = limitedUserRepository;
	}

	@Transactional
	public void addLimitedUser() {
		synchronized (index) {
			if(!isFinished()) {
				limitedUserRepository.save(new LimitedUser());
				index.set(getLimittedUserTotalCount());
			}
		}
	}
	
	@Transactional(readOnly = true)
	public Long getLimittedUserTotalCount() {
		return limitedUserRepository.count();
	}
	
	public Boolean isFinished() {
		return index.get() > (LIMIT_COUNT - 1);
	}
}
