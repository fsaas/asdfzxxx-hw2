package hw2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("<varag �׽�Ʈ> �Է�: [1, 2, 3, 4, 5, 6, 7, 8] ���: �״��");
		FStream.of(1,2,3,4,5,6,7,8).map(x->x+" ").forEach(System.out::print);

		List<Integer> listForFrom = new ArrayList<Integer>();
		listForFrom.add(1); listForFrom.add(2); listForFrom.add(3); listForFrom.add(4);
		System.out.println("\n\n<from �׽�Ʈ> �Է�: " + listForFrom +" ���: �״�� * 3");
		FStream.from(listForFrom).map(x->x+" ").forEach(System.out::print); System.out.println("");
		FStream.from(listForFrom.iterator()).map(x->x+" ").forEach(System.out::print); System.out.println("");
		FStream.from(listForFrom.stream()).map(x->x+" ").forEach(System.out::print);  

		System.out.println("\n\n<take �׽�Ʈ> �Է�: [1, 2, 3, 4, 5, 6, 7, 8] ���: take 5");
		FStream.of(1,2,3,4,5,6,7,8).take(5).map(x->x+" ").forEach(System.out::print);
		System.out.println("\n<drop �׽�Ʈ> �Է�: [1, 2, 3, 4, 5, 6, 7, 8] ���: drop 5");
		FStream.of(1,2,3,4,5,6,7,8).drop(5).map(x->x+" ").forEach(System.out::print);
		
		System.out.println("\n\n<filter �׽�Ʈ> �Է�: [ba, ab, ca, ac] ���: a�� ����");
		FStream.of("e", "ab", "c", "ac").filter(x->x.startsWith("a")).map(x->x+" ").forEach(System.out::print);
		System.out.println("\n<map �׽�Ʈ> �Է�: [1, 2, 3, 4] ���: x*x");
		FStream.of(1,2,3,4).map(x->x*x+" ").forEach(System.out::print);

		System.out.println("\n\n<��� �׽�Ʈ> �Է�: [1, 2, 3, 4] ���: �״�� * 4");
		Iterator<Integer> iter = FStream.of(1,2,3,4).iterator();
		while(iter.hasNext()) { System.out.print(iter.next() +" "); } System.out.println("");
		List<Integer> list = FStream.of(1,2,3,4).toList();
		System.out.println(list);
		Set<Integer> set = FStream.of(1,2,3,4).toSet();
		System.out.println(set);
		Stream<Integer> stream = FStream.of(1,2,3,4).stream();
		stream.map(x-> x+" ").forEach(System.out::print);
		
		System.out.println("\n\n<���� �׽�Ʈ> �Է�: [1, 2, 3, 4] ���: ����");
		FStream.of(1,2,3,4).sort((x1,x2)-> x2 - x1).map(x->x+" ").forEach(System.out::print);

	}
}
