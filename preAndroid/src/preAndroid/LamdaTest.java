package preAndroid;

import com.kitri.dto.Test;
import com.kitri.dto.TestImpl;

public class LamdaTest {

	public static void test(Test t) {
		t.m(10);
	}
	
	public static void main(String[] args) {
		
		// 1. 일반적 방법
//		TestImpl impl = new TestImpl();
//		impl.m(10);  					 // 출력 : 10
//		test(impl);						 // 출력 : 10
		
		// 2. 람다식 방법
		test(t->System.out.println(t)); // t에 인터페이스(Test)를 구현한 하위 클래스(TestImpl) 새 객체가 자동 대입됨
										// -> 다음에 오는 내용으로 인터페이스의 메소드를 자동 오버라이드 함
										// 인터페이스의 메소드를 오버라이드 한 것이 1개일 때만 사용 가능함
										// so, 주로 이벤트 처리에 자주 사용.
	}

}
