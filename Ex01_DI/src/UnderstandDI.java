import java.util.Date;

class Member {
	String name;
	String nickname;
	private  Member() {}	// 생성자를 private으로 만듦 => 인스턴스화X
}
public class UnderstandDI {
	public static void main(String[] args) {
		// 날짜를 구하기 위해서는 Date 클래스에 의존해야 한다.
		Date date = new Date();
		System.out.println(date);
	}
	
	public static void getDate(Date d) {
		Date date = d;
		System.out.println(date);
	}
	
	public static void memberUse1() {
		// 강한 결합 : 직접 생성
//		Member m1 = new Member();	// error: 인스턴스화 불가
	}
	public static void memberUse2(Member m) {
		// 약한 결합 : 생성된 것을 주입 받음 - 의존 주입 (Dependency Injection)
		Member m2 = m;	// 의존 주입은 가능
	}
}
