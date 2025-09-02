package br.com.alura.ecommerce.consumer;

import java.util.concurrent.Executors;

public class ServiceRunner<T> {

    private final ServiceProvider<T> provider;

    public ServiceRunner(ServiceFactory<T> serviceFactory) {
        this.provider = new ServiceProvider<>(serviceFactory);
    }

    public void start(int threadCound) {
        var pool = Executors.newFixedThreadPool(threadCound);
        for (int i = 0; i < threadCound; i++) {
            pool.submit(provider);
        }
    }
}
