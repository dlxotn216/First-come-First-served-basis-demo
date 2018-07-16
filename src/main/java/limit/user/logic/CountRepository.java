package limit.user.logic;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Lee Tae Su
 * @version 1.0
 * @project limit-user-test
 * @since 2018-07-16
 */
public interface CountRepository extends JpaRepository<Count, Long> {
}
