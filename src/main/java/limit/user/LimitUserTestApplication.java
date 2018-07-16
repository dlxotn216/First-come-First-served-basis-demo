package limit.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 동시다발적인 요청에서 제한 된 사용자 수 만큼 서비스를 제공하는 
 * 형태의 Application에서 Transaction 관련 동시성 처리 데모
 * 
 * 결론적으로 Service 제공에 있어서 시나리오를 맞게 가져가는 편이 나은 것 같다
 * 
 * ex)
 * 선착순 100명에게 추첨권을 준다고 할 때
 * 매 요청마다 "추첨 된 사용자 테이블"에서 count가 100인지 조회하고
 * insert 한다
 * -> insert 성공 시 사용자에게 바로 추첨 되었음을 알린다
 * 
 * 이러한 시나리오인 경우 Test에서도 확인하였지만
 * 여러 트랜잭션이 count(99)를 읽은 후 validation을 통과하여 insert가 가능해진다
 * 따라서 100명을 초과하는 문제가 나타날 수 있다.
 * 
 * atomic 변수를 통해 count 결과를 synchronize 블록으로 감싸는 처리로 가능하겠지만
 * 성능 저하는 피할 수 없다.
 * 
 * 대신 위 시나리오 그대로 진행 하되 Insert 후
 * 현재 신청한 사용자가 상위에서 100명 안에 드는지 다시 조사하는 형태로
 * 진행하면 굳이 synchronize 블록으로 감싸지 않아도 시나리오에는 문제가 없다.
 */
@SpringBootApplication
public class LimitUserTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LimitUserTestApplication.class, args);
	}
}
