package com.datastax;

import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.datastax.Authentication.PULSAR_TOKEN;

public class BookTransactionsGenerator {
    private static final String SERVICE_URL = "<YOUR_PULSAR_SERVICE_URL>";
    private static final String TOPIC_NAME = "persistent://marketplace/default/transactions" ;

    private static final List<String> publishers = List.of("Rolland Miller", "Felicia McKinney", "Esteban Sandoval");
    private static final List<String> bookTypes = List.of("Sci-Fi", "Tragedy", "Comics");

    public static void main(String[] args) throws PulsarClientException, InterruptedException {

        PulsarClient client = PulsarClient.builder()
                .serviceUrl(SERVICE_URL)
                .authentication(AuthenticationFactory.token(PULSAR_TOKEN))
                .build();

        Producer<BookTransaction> producer = client.newProducer(Schema.JSON(BookTransaction.class))
                .topic(TOPIC_NAME)
                .create();

        // Generate 1000 random transactions @ 1TPS
        int txCounter = 1000;
        do {
            final BookTransaction tx = createBookTransaction();
            producer.newMessage().key(UUID.randomUUID().toString()).value(tx).send();
            System.out.println(tx);
            TimeUnit.SECONDS.sleep(1);
        } while (--txCounter > 0);
    }
    private static BookTransaction createBookTransaction() {
        final String publisher = publishers.get(new Random().nextInt(publishers.size()));
        final String type = bookTypes.get(new Random().nextInt(bookTypes.size()));
        final Double royalty = ThreadLocalRandom.current().nextDouble(1.0, 10.0);

        return new BookTransaction(publisher, type, royalty);
    }
}