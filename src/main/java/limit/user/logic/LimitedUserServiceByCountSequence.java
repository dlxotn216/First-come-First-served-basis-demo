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
 * Scheduler를 통해 00시에 <code>index</code>를 초기화하고
 * LIMIT_COUNT가 요일별로 다르다면 읽어온 후 처리하도록 하여
 * 유연성 있게 개선 가능할 것 같다.
 */
@Service(value = "LimitedUserServiceByCountSequence")
public class LimitedUserServiceByCountSequence implements LimitUserService {
	public static final Long LIMIT_COUNT = 1000L;
	
	private final AtomicLong index = new AtomicLong(1);
	
	private LimitedUserRepository limitedUserRepository;
	
	public LimitedUserServiceByCountSequence(LimitedUserRepository limitedUserRepository) {
		this.limitedUserRepository = limitedUserRepository;
	}
	
	@Transactional
	public void addLimitedUser() {
		synchronized (index) {
			if(!isFinished()) {
				limitedUserRepository.save(new LimitedUser());
				index.addAndGet(1L);
			}
		}
	}
	
	@Transactional
	public Long getLimittedUserTotalCount() {
		return limitedUserRepository.count();
	}
	
	public Boolean isFinished() {
		return index.get() > LIMIT_COUNT;
	}
	
}
