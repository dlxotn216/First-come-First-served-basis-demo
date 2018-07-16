package limit.user.logic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lee Tae Su
 * @version 1.0
 * @project limit-user-test
 * @since 2018-07-16
 * <p>
 * 일반적인 시나리오
 */
@Service(value = "LimitedUserServiceImpl")
public class LimitedUserServiceImpl implements LimitUserService {
	public static final Long LIMIT_COUNT = 10000L;
	
	private LimitedUserRepository limitedUserRepository;
	
	public LimitedUserServiceImpl(LimitedUserRepository limitedUserRepository) {
		this.limitedUserRepository = limitedUserRepository;
	}
	
	@Transactional
	public void addLimitedUser() {
		if(!isFinished()) {
			limitedUserRepository.save(new LimitedUser());
		}
	}
	
	@Transactional(readOnly = true)
	public Long getLimittedUserTotalCount() {
		return limitedUserRepository.count();
	}
	
	public Boolean isFinished() {
		return limitedUserRepository.count() > LIMIT_COUNT;
	}
}
