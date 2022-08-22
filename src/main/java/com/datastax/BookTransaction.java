package com.datastax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookTransaction {

    public BookTransaction() {}

    public BookTransaction(final String publisher, final  String type , final Double royalty) {
        this.publisher = publisher;
        this.type = type;
        this.royalty = royalty;
        this.created = DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now());

    }

    public String type;
    public String publisher;
    public double royalty;

    public String created; // ISO_LOCAL_DATE format

    @Override
    public String toString() {
        return String.format("bookTransaction={created=%s,publisher=%s,type=%s,royalty=$%,.2f", this.created, this.publisher, this.type, this.royalty);
    }
}
