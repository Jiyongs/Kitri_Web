package preAndroid;

import java.io.*;
import java.util.Date;

import com.kitri.dto.Child;
import com.kitri.dto.Product;

public class SerializeTest {
	
	public static void main(String[] args) {
		
		// [출력 / 직렬화]
		/*
		 * file-name 			: a.ser					(목적지)
		 * byte 단위 출력스트림 : FileOutputStream		(a.ser에 쓰기 위함)
		 * 객체 출력스트림 		: ObjectOutputStream	(byte가 불편하므로, filter스트림용으로 사용)
		 */
		

//		ObjectOutputStream oos = null;
//		
//		try {
//			
//			// 1. 출력 스트림 생성
//			oos = new ObjectOutputStream(new FileOutputStream("a.ser"));
//			
//			// 2. 내용 쓰기
//			// 날짜 타입
//			Date now = new Date(); 
//			
//			// Product 타입
//			Product p = new Product();
//			p.setProd_no("001");
//			p.setProd_name("아메리카노");
//			p.setProd_price(2500);
//			
//			// Child (자식 클래스) 타입
//			Child c = new Child();	
//			c.setC("Child Instance Variable");
//			
//			// 3. 직렬화 (얼리기)
//			oos.writeObject(now);
//			oos.writeObject(p);
//			oos.writeObject(c);
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			// 4. 출력 스트림 닫기
//			if( oos != null) {
//				try {
//					oos.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}

		
		
		// [입력 / 역직렬화]
		/*
		 * file-name 			: a.ser					(목적지)
		 * byte 단위 입력스트림 : FileInputStream		(a.ser을 읽어들이기 위함)
		 * 객체 입력스트림 		: ObjectInputStream	(byte가 불편하므로, filter스트림용으로 사용)
		 */
		
		ObjectInputStream ois = null;
		try {
			
			// 1. 입력 스트림 생성
			ois = new ObjectInputStream(new FileInputStream("a.ser"));
			
			// 2. 역직렬화 (녹이기)
			// 날짜 타입
			Object obj = ois.readObject();
			System.out.println(obj);
			
			// Product 타입
			obj = ois.readObject();
			System.out.println(obj);
			
			// Child 타입
			obj = ois.readObject();
			Child c1 = (Child) obj;
			System.out.println(c1.getC());  // 자식 클래스 객체 // 'Child Instance Variable' // 직렬화 됨
			System.out.println(c1.getP());  // 부모 클래스 객체 // 'null'					 // 직렬화 안 됨
											// -> 직렬화 대상이 자식 클래스. (Child implements Serializable)
											// -> 자식 클래스 영역 내의 객체만 직렬화가 됨.
											// => 해결 : 부모 클래스를 직렬화 대상으로 삼기 (Parent implements Serializable)
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

}
