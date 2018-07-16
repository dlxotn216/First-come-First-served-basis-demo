package limit.user.logic;

import lombok.Getter;

import javax.persistence.*;

/**
 * @author Lee Tae Su
 * @version 1.0
 * @project limit-user-test
 * @since 2018-07-16
 */
@Entity
@Table
@Getter
public class LimitedUser {
	@Id
	@GeneratedValue(generator = "LimitedUserSeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "LimitedUserSeqGen", sequenceName = "LIMITED_USER_SEQ", initialValue = 1, allocationSize = 1)
	private Long id;
	
	public LimitedUser() {
	}
	
	public LimitedUser(Long id) {
		this.id = id;
	}
}
