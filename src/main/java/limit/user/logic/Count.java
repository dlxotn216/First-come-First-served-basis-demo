package limit.user.logic;

import lombok.Getter;
import lombok.Setter;

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
@Setter
public class Count {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CountSeqGen")
	@SequenceGenerator(sequenceName = "COUNT_SEQ", name = "CountSeqGen")
	private Long id;
	
	private Long count;
}
