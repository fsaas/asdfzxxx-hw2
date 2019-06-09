package hw2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import hw1.FOption;

public interface FStream<T> extends AutoCloseable {
	public FOption<T> next();
	
	public static <T> FStream<T> empty(){
		return new FStream<T>() {
			@Override
			public void close() throws Exception {
				// TODO Auto-generated method stub
			}
			@Override
			public FOption<T> next() {
				// TODO Auto-generated method stub
				return FOption.empty();
			}
		};
	}
	
	@SafeVarargs
	public static <T> FStream<T> of(T... value){
		return new FStream<T>() {
			int count = 0;
			@Override
			public void close() throws Exception {
				// TODO Auto-generated method stub
			}
			@Override
			public FOption<T> next() {
				// TODO Auto-generated method stub
				if(count < value.length) {
					return FOption.of(value[count++]);
				}
				return FOption.empty();
			}
		};
	}
	
	public static <T> FStream<T> from(Iterable<? extends T> values){
		return new FStream<T>() {
			Iterator<? extends T> iter = values.iterator();
			@Override
			public void close() throws Exception {
				// TODO Auto-generated method stub
			}
			@Override
			public FOption<T> next() {
				// TODO Auto-generated method stub
				while(iter.hasNext()) {
					return FOption.of(iter.next());
				}
				return FOption.empty();
			}
		};
	}
	public static <T> FStream<T> from(Iterator<? extends T> iter){
		return new FStream<T>() {
			@Override
			public void close() throws Exception {
				// TODO Auto-generated method stub
			}
			@Override
			public FOption<T> next() {
				// TODO Auto-generated method stub
				while(iter.hasNext()) {
					return FOption.of(iter.next());
				}
				return FOption.empty();
			}
		};
	}
	public static <T> FStream<T> from(Stream<? extends T> stream){
		return new FStream<T>() {
			Iterator<? extends T> iter = stream.iterator();
			@Override
			public void close() throws Exception {
				// TODO Auto-generated method stub
			}
			@Override
			public FOption<T> next() {
				// TODO Auto-generated method stub
				if(iter.hasNext())
					return FOption.of(iter.next());
				return FOption.empty();
			}
		};
	}

	public default FStream<T> take(long count){ 
		return new FStream<T>() {
			long c = count;
			@Override
			public void close() throws Exception {
				// TODO Auto-generated method stub
			}
			@Override
			public FOption<T> next() {
				// TODO Auto-generated method stub
				FOption<T> next;
				if(c > 0) {
					c--;
					next = FStream.this.next();
					return next;
				}
				return FOption.empty();
			}
		};
	}
	public default FStream<T> drop(long count){
		return new FStream<T>() {
			long c = count;

			@Override
			public void close() throws Exception {
				// TODO Auto-generated method stub
			}
			@Override
			public FOption<T> next() {
				// TODO Auto-generated method stub
				FOption<T> next;
				while(c>0) {
					c--;
					next = FStream.this.next();
				}
				next = FStream.this.next();
				return next;
			}
		};
	}
	public default void forEach(Consumer<? super T> effect) {
		FOption<T> next = FStream.this.next();
		while(next.isPresent()) {
			effect.accept(next.get());
			next = FStream.this.next();
		}
	}
	public default FStream<T> filter(Predicate<? super T> pred){
		return new FStream<T>() {
			@Override
			public void close() throws Exception {
				// TODO Auto-generated method stub
			}
			@Override
			public FOption<T> next() {
				// TODO Auto-generated method stub
				FOption<T> next;
				do {
					next = FStream.this.next();
					if(next.isAbsent())
						return FOption.empty();
				}while(!pred.test(next.get()));
				return next;
			}
		};
	}
	public default <S> FStream<S> map(Function<? super T, ? extends S> mapper){
		return new FStream<S>() {
			@Override
			public void close() throws Exception {
				// TODO Auto-generated method stub
			}
			@Override
			public FOption<S> next() {
				// TODO Auto-generated method stub
				FOption<T> next = FStream.this.next();
				if(next.isAbsent())
					return FOption.empty();
				FOption<S> mappedNext = FOption.of(mapper.apply(next.get()));
				return mappedNext; 
			}
		};
	}
	public default Iterator<T> iterator(){
		ArrayList<T> list = new ArrayList<>();
		FOption<T> next = FStream.this.next();
		while(next.isPresent()) {
			list.add(next.get());
			next = FStream.this.next();
		}
		return list.iterator();
	}

	public default ArrayList<T> toList(){
		ArrayList<T> list = new ArrayList<T>();
		FOption<T> next = FStream.this.next();
		while(next.isPresent()) {
			list.add(next.get());
			next = FStream.this.next();
		}
		return list;	
	}
	public default HashSet<T> toSet(){
		HashSet<T> hashSet = new HashSet<T>();
		FOption<T> next = FStream.this.next();
		while(next.isPresent()) {
			hashSet.add(next.get());
			next = FStream.this.next();
		}
		return hashSet;	
	}
	
	@SuppressWarnings("unchecked")
	public default <S> S[] toArray(Class<S> componentType) {
		ArrayList<T> list = new ArrayList<T>();
		FOption<T> next = FStream.this.next();
		while(next.isPresent()) {
			list.add(next.get());
			next = FStream.this.next();
		}
		return (S[]) list.toArray();
	}
	public default Stream<T> stream(){
		ArrayList<T> list = new ArrayList<T>();
		FOption<T> next = FStream.this.next();
		while(next.isPresent()) {
			list.add(next.get());
			next = FStream.this.next();
		}
		return list.stream();
	}
	public default FStream<T> sort(Comparator<? super T> cmp){
		return new FStream<T>() {
			boolean isSorted = false;
			ArrayList<T> list = new ArrayList<T>();
			FStream<T> fStream;
			@Override
			public void close() throws Exception {
				// TODO Auto-generated method stub
			}
			@Override
			public FOption<T> next() {
				// TODO Auto-generated method stub
				if(!isSorted) {
					FOption<T> next = FStream.this.next();
					while(next.isPresent()) {
						list.add(next.get());
						next = FStream.this.next();
					}
					list.sort(cmp);
					isSorted = true;
					fStream = FStream.from(list);
				}
				return fStream.next();
			}
		};
	}
}
