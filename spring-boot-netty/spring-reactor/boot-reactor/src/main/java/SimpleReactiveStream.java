import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author shenlx
 * @description
 * @date 2024/4/17 15:26
 */
public class SimpleReactiveStream {
    /**
     * 实现一个简单的响应式编程发布者
     * 逻辑：当订阅者发起订阅时，像下游发送一个 HelloWorld，发布逻辑由 SimpleSubscription 完成
     */
    static class SimplePublisher implements Publisher {
        @Override
        public void subscribe(Subscriber s) {
            // 2. Publisher 发布数据之前，调用 Subscriber 的 onSubscribe
            s.onSubscribe(new SimpleSubscription(data(), s));
        }

        private String data() {
            return "Hello World";
        }
    }

    static class SimpleSubscriber implements Subscriber {
        @Override
        public void onSubscribe(Subscription s) {
            // 3. Subscriber 通过 Subscription#request 来请求数据
            // 或者 Subscription#cancel 来取消数据发布
            s.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(Object o) {
            System.out.println(o);
        }

        @Override
        public void onError(Throwable t) {
            System.out.println("error");
        }

        @Override
        public void onComplete() {
            System.out.println("complete");
        }
    }

    static class SimpleSubscription implements Subscription {
        String data;
        Subscriber actual;
        boolean isCanceled;

        public SimpleSubscription(String data, Subscriber actual) {
            this.data = data;
            this.actual = actual;
        }

        @Override
        public void request(long n) {
            if (!isCanceled) {
                try {
                    // 4. Subscription 在接收到订阅者的调用后
                    // 通过 Subscriber#onNext 向下游订阅者传递数据
                    actual.onNext(data);
                    // 5. 在数据发布完成后，调用 Subscriber#onComplete 结束本次流
                    actual.onComplete();
                } catch (Exception e) {
                    // 如果数据发布或者处理遇到错误会调用 Subscriber#onError
                    actual.onError(e);
                }
            }
        }

        @Override
        public void cancel() {
            isCanceled = true;
        }
    }

    public static void main(String[] args) {
        // 1. Subscriber ”订阅“ Publisher
        new SimplePublisher().subscribe(new SimpleSubscriber());
    }
}
