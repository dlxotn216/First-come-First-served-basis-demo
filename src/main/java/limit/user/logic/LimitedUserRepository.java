package limit.user.logic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Lee Tae Su
 * @version 1.0
 * @project limit-user-test
 * @since 2018-07-16
 */
public interface LimitedUserRepository extends JpaRepository<LimitedUser, Long> {
	@Query(value = "SELECT nextval('LIMITED_USER_SEQ')", nativeQuery = true)
	Long getSequence();
}
