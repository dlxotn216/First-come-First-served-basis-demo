package limit.user.logic;

/**
 * @author Lee Tae Su
 * @version 1.0
 * @project limit-user-test
 * @since 2018-07-16
 */
public interface LimitUserService {
	void addLimitedUser() ;
	
	Long getLimittedUserTotalCount();
	
	Boolean isFinished();
}
